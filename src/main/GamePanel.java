package main;

import javax.swing.JPanel;

import Sounds.Sound;
import Sounds.SoundPool;
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
public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16;
    final int scale = 3;

  public  final int tileSize = originalTileSize*scale;//48x48
  public  final int maxScreenCol=20;
     public final int maxScreenRow=12;
     public final int screenWidth = maxScreenCol*tileSize;//7960 px
     public final int screenHeight = maxScreenRow*tileSize;//576 px
//sort
Comparator<Entity> c = (a,b)->{return a.worldY-b.worldY;};
//change the map
public final int maxMap=10;
public int currentMap=0;
    //fps
    final int FPS=60;
    int frameCount = 0;
    long lastTime = System.currentTimeMillis();
    int currentFPS = 0;

    //world settings 

    public final int maxWorldCol =50;
    public final int maxWorldRow =50;
    //full screen 
    int screenWidth2=screenWidth;
    int screenHeight2=screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    boolean fullScreenOn=false;
    //tile manager
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
      public   Player player = new Player(this, keyH);

      public EventHandler eventHandler = new EventHandler(this);
    public  Config conf = new Config(this);
    Thread gameThread;
 public  CollisionChecker cChecker=new CollisionChecker(this);
 public UI ui = new UI(this);
 // objects 
public AssetSetter aSetter = new AssetSetter(this);
public Entity obj[][]=new Entity[maxMap][20];
public Entity npc[] []= new Entity[maxMap][10];
public Entity monster[][] =new Entity[maxMap][20];
public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50]; 
ArrayList<Entity> entityList=new ArrayList<>();
public ArrayList<Entity> projectileList=new ArrayList<>();
public ArrayList<Entity> particlesList=new ArrayList<>();

//sound 
Sound music = new Sound();
public Sound se = new Sound();
//using sound pool for ressource eaterrs
public SoundPool swingPool;
public SoundPool hitPool;
public SoundPool firePool;
public SoundPool cuttingPool;


///gAME STATE 
 public int gameState;
 public  static final int titleState=0;
 public  static final int playState =1;
 public  static final int pauseState =2;
 public  static final int dialogueState=3;
 public  static final int characterState=4;
 public static final int OptionsState=5;
 public static final int gameOverState=6;
 
 



    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
    }

    public void retry(){
        player.restoreLifeAndMana();
        player.setDefaultPositions();
        aSetter.setMonster();
        aSetter.setNPC();
    }
    public void restart(){
        player.setDefaultValues();
        player.restoreLifeAndMana();
        player.setDefaultPositions();
        aSetter.setMonster();
        aSetter.setNPC();
    player.setInventoryItems();
    aSetter.setInteractiveTiles();
    aSetter.setObject();
    }
    
    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTiles();
        swingPool = new SoundPool(getClass().getResource("/sounds/swinging.wav"), 4); // pool size 10
        hitPool = new SoundPool(getClass().getResource("/sounds/hitMonster.wav"), 4);
        firePool = new SoundPool(getClass().getResource("/sounds/fireball.wav"), 4);
        cuttingPool = new SoundPool(getClass().getResource("/sounds/cutting.wav"), 4);
        playMusic(5);
        gameState = titleState;
    tempScreen= new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
    g2=(Graphics2D) tempScreen.getGraphics();
    if(fullScreenOn==true){
        setFullScreen();
    }

    }



   
     public void startGameThread(){
        gameThread = new Thread(this);
    gameThread.start();
     }
        @Override
        public void run() {
            double drawIterval=1000000000/FPS;//0.01666 seconds
            double nextDrawTime = System.nanoTime()+drawIterval;
            while(gameThread!=null){
               
                update();
                drawToTempScreen();
                drawToScreen();
                frameCount++;
        if (System.currentTimeMillis() - lastTime >= 1000) {
            currentFPS = frameCount;
            frameCount = 0;
            lastTime = System.currentTimeMillis();
        }
                try{
                    double remainingTime =nextDrawTime-System.nanoTime();
                    remainingTime=remainingTime/1000000;
                    if(remainingTime<0) remainingTime=0;
                    Thread.sleep((long)remainingTime);
                    nextDrawTime+=drawIterval;
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
              
            }
            
        }
        public void update (){

          if(gameState==playState){
                player.update();
                eventHandler.checkEvent();
                for(int i=0;i<npc[1].length;i++){
                    if(npc[currentMap][i]!=null){
                        npc[currentMap][i].update();
                    }
                }

                for(int i=0;i<monster[1].length;i++){
                    if(monster[currentMap][i]!=null){
                        if(monster[currentMap][i].alive==true && monster[currentMap][i].dying==false){
                            monster[currentMap][i].update();
                        }
                        if(monster[currentMap][i].alive==false){
                            monster[currentMap][i].checkDrop();
                            monster[currentMap][i]=null;
                        }
                        
                    }
                }
                for(int i=0;i<projectileList.size();i++){
                    if(projectileList.get(i)!=null){
                        if(projectileList.get(i).alive==true ){
                            projectileList.get(i).update();
                        }
                        if(projectileList.get(i).alive==false){
                            projectileList.remove(i);
                        }
                        
                    }
                }
                for(int i=0;i<particlesList.size();i++){
                    if(particlesList.get(i)!=null){
                        if(particlesList.get(i).alive==true ){
                            particlesList.get(i).update();
                        }
                        if(particlesList.get(i).alive==false){
                            particlesList.remove(i);
                        }
                        
                    }
                }

                for(int i =0 ;i<iTile[1].length;i++){
                    if(iTile[currentMap][i]!=null){
                        iTile[currentMap][i].update();
                    }
                }
            }
            if(gameState==pauseState){
               
            }
            
        }

        public void setFullScreen(){
            //get local screen details
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd =ge.getDefaultScreenDevice();
            gd.setFullScreenWindow(Main.window);

            screenWidth2=Main.window.getWidth();
            screenHeight2=Main.window.getHeight();
        }
            public void drawToTempScreen(){
  long drawStart= 0;
            if(keyH.checkDrawTime==true){drawStart=System.nanoTime();}
                //title Screen
                if(gameState==titleState){
                    ui.draw(g2);
                }else{
                   
                    //tile
                    tileM.draw(g2);
                    //intreracetives tiles
                    for(int i =0; i<iTile[1].length;i++){
                        if(iTile[currentMap][i]!=null){
                            iTile[currentMap][i].draw(g2);
                        }
                    }
                    // add all entities to the list
                   entityList.add(player);
                   for(int i=0;i<npc[1].length;i++){
                    if (npc[currentMap][i]!=null)
                    entityList.add(npc[currentMap][i]);
                   }
                  
                   for(int i=0;i<obj[1].length;i++){
                    if(obj[currentMap][i]!=null)
                    entityList.add(obj[currentMap][i]);
                   }
                   for(int i=0;i<monster[1].length;i++){
                    if(monster[currentMap][i]!=null)
                    entityList.add(monster[currentMap][i]);
                   }
                   for(int i=0;i<projectileList.size();i++){
                    if(projectileList.get(i)!=null)
                    entityList.add(projectileList.get(i));
                   }
                   for(int i=0;i<particlesList.size();i++){
                    if(particlesList.get(i)!=null)
                    entityList.add(particlesList.get(i));
                   }
                   //sort
                   Collections.sort(entityList,c);

                   for(var e : entityList){
                    if(e!=null){
                        e.draw(g2);
                    }
                   }

                  entityList.clear();
                    ui.draw(g2);
                     // Draw FPS
                    g2.setColor(Color.YELLOW);
                    g2.setFont(new Font("Arial", Font.BOLD, 20));
                    g2.drawString("FPS: " + currentFPS, 10, 20);
                    if(keyH.checkDrawTime==true){
                        long drawEnd= System.nanoTime();
                        long passed= drawEnd-drawStart;
                        g2.setColor(Color.black);
                        g2.drawString("Draw time ->> "+passed, 0, 550);
                        System.out.println("Draw time :  "+passed );
            }
            }
            }

        public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
        }

public void playMusic(int i){
    music.setFile(i);
    music.play();
    music.loop();
}


public void stopMusic(){
    music.stop();
}

public void playSE(int i){

    se.playSE(i);

}






}