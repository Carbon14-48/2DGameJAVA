package main;

import javax.swing.JPanel;

import Database.Config;
import Sounds.SoundManager;
import State.GameState;
import State.TitleState;
import State.PlayState;
import State.PauseState;
import State.CharacterState;
import State.DialogueState;
import State.OptionsState;
import State.GameOverState;
import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactives.InteractiveTile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    private static GamePanel instance;
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;// 48x48
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize;// 960 px
    public final int screenHeight = maxScreenRow * tileSize;// 576 px

    // sort
    Comparator<Entity> c = (a, b) -> {
        return a.worldY - b.worldY;
    };

    // change the map
    public final int maxMap = 10;
    public int currentMap = 0;

    // fps
    final int FPS = 60;
    int frameCount = 0;
    long lastTime = System.currentTimeMillis();
    int currentFPS = 0;

    // world settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // full screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    // tile manager
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public Player player = new Player(this, keyH);
    public EventHandler eventHandler = new EventHandler(this);
    public Config conf;
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public UI ui = new UI(this);

    // objects
    public AssetSetter aSetter = new AssetSetter(this);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particlesList = new ArrayList<>();

    // sound
    public final SoundManager soundManager = SoundManager.getInstance();

    // State Pattern fields
    public GameState currentState;
    public GameState titleState;
    public GameState playState;
    public GameState pauseState;
    public GameState characterState;
    public GameState dialogueState;
    public GameState optionsState;
    public GameState gameOverState;

    public void setGameState(GameState newState) {
        this.currentState = newState;
        keyH.resetKeys(); 
    }

    private GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        
        this.titleState = new TitleState(this);
        this.playState = new PlayState(this, keyH);
        this.pauseState = new PauseState(this);
        this.characterState = new CharacterState(this);
        this.dialogueState = new DialogueState(this);
        this.optionsState = new OptionsState(this, keyH);
        this.gameOverState = new GameOverState(this);

        this.currentState = titleState; 
    }

    public static GamePanel getInstance() {
        if (instance == null) {
            instance = new GamePanel();
        }
        return instance;
    }

    public void retry() {
        player.restoreLifeAndMana();
        player.setDefaultPositions();
        aSetter.setMonster();
        aSetter.setNPC();
    }

    public void restart() {
        player.setDefaultValues();
        player.restoreLifeAndMana();
        player.setDefaultPositions();
        aSetter.setMonster();
        aSetter.setNPC();
        player.setInventoryItems();
        aSetter.setInteractiveTiles();
        aSetter.setObject();
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTiles();
        conf.loadConfig();
        playMusic(5);
        setGameState(titleState);
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        if (fullScreenOn) {
            setFullScreen();
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;// 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {
            update();
            drawToTempScreen();
            drawToScreen();
            frameCount++;
            if (System.currentTimeMillis() - lastTime >= 1000) {
                currentFPS = frameCount;
                frameCount = 0;
                lastTime = System.currentTimeMillis();
            }
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if (remainingTime < 0)
                    remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (currentState == playState) {
            player.update();
            eventHandler.checkEvent();
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
                        monster[currentMap][i].update();
                    }
                    if (monster[currentMap][i].alive == false) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).alive == true) {
                        projectileList.get(i).update();
                    }
                    if (projectileList.get(i).alive == false) {
                        projectileList.remove(i);
                    }
                }
            }
            for (int i = 0; i < particlesList.size(); i++) {
                if (particlesList.get(i) != null) {
                    if (particlesList.get(i).alive == true) {
                        particlesList.get(i).update();
                    }
                    if (particlesList.get(i).alive == false) {
                        particlesList.remove(i);
                    }
                }
            }
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
        }
    }

    public void setFullScreen() {
        // get local screen details
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void drawToTempScreen() {
        long drawStart = 0;
        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }
        // title Screen
        if (currentState == titleState) {
            ui.draw(g2);
        } else {
            // tile
            tileM.draw(g2);
            // interactive tiles
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }
            // add all entities to the list
            entityList.add(player);
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null)
                    entityList.add(npc[currentMap][i]);
            }
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null)
                    entityList.add(obj[currentMap][i]);
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null)
                    entityList.add(monster[currentMap][i]);
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null)
                    entityList.add(projectileList.get(i));
            }
            for (int i = 0; i < particlesList.size(); i++) {
                if (particlesList.get(i) != null)
                    entityList.add(particlesList.get(i));
            }
            // sort
            Collections.sort(entityList, c);

            for (var e : entityList) {
                if (e != null) {
                    e.draw(g2);
                }
            }

            entityList.clear();
            ui.draw(g2);
            // Draw FPS
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("FPS: " + currentFPS, 10, 20);
            if (keyH.checkDrawTime == true) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setColor(Color.black);
                g2.drawString("Draw time ->> " + passed, 0, 550);
                System.out.println("Draw time :  " + passed);
            }
        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i) {
        soundManager.playMusic(i);
        soundManager.loopMusic(i);
    }

    public void stopMusic() {
        soundManager.stopMusic();
    }

    public void playSE(int i) {
        soundManager.playSE(i);
    }
}