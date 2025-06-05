package main;

import javax.swing.JPanel;


import entity.Entity;
import entity.Player;
import main.Sounds.Sound;
import main.Sounds.SoundPool;
import tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16;
    final int scale = 3;

  public  final int tileSize = originalTileSize*scale;//48x48
  public  final int maxScreenCol=16;
     public final int maxScreenRow=12;
     public final int screenWidth = maxScreenCol*tileSize;//768 px
     public final int screenHeight = maxScreenRow*tileSize;//576 px
//sort
Comparator<Entity> c = (a,b)->{return a.worldY-b.worldY;};
    //fps
    final int FPS=60;
    int frameCount = 0;
    long lastTime = System.currentTimeMillis();
    int currentFPS = 0;

    //world settings 

    public final int maxWorldCol =50;
    public final int maxWorldRow =50;
    

    //tile manager
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
      public   Player player = new Player(this, keyH);

      public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;
 public  CollisionChecker cChecker=new CollisionChecker(this);
 public UI ui = new UI(this);
 // objects 
public AssetSetter aSetter = new AssetSetter(this);
public Entity obj[]=new Entity[10];
public Entity npc[] = new Entity[10];
public Entity monster[] =new Entity[20];
ArrayList<Entity> entityList=new ArrayList<>();

//sound 
Sound music = new Sound();
public Sound se = new Sound();
//using sound pool for ressource eaterrs
public SoundPool swingPool;
public SoundPool hitPool;
;


///gAME STATE 
 public int gameState;
 public  static final int titleState=0;
 public  static final int playState =1;
 public  static final int pauseState =2;
 public  static final int dialogueState=3;
 public  static final int characterState=4;
 



    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
    }

    
    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        swingPool = new SoundPool(getClass().getResource("/sounds/swinging.wav"), 1); // pool size 10
        hitPool = new SoundPool(getClass().getResource("/sounds/hitMonster.wav"), 1);
        playMusic(5);
        gameState = titleState;

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
                repaint();
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
                for(int i=0;i<npc.length;i++){
                    if(npc[i]!=null){
                        npc[i].update();
                    }
                }

                for(int i=0;i<monster.length;i++){
                    if(monster[i]!=null){
                        if(monster[i].alive==true && monster[i].dying==false){
                            monster[i].update();
                        }
                        if(monster[i].alive==false){
                            monster[i]=null;
                        }
                        
                    }
                }
            }
            if(gameState==pauseState){
               
            }
            
        }
        public void paintComponent(Graphics g){
            long drawStart= 0;
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            if(keyH.checkDrawTime==true){drawStart=System.nanoTime();}
                //title Screen
                if(gameState==titleState){
                    ui.draw(g2);
                }else{
                   
                    //tile
                    tileM.draw(g2);
                    // add all entities to the list
                   entityList.add(player);
                   for(int i=0;i<npc.length;i++){
                    if (npc[i]!=null)
                    entityList.add(npc[i]);
                   }
                  
                   for(int i=0;i<obj.length;i++){
                    if(obj[i]!=null)
                    entityList.add(obj[i]);
                   }
                   for(int i=0;i<monster.length;i++){
                    if(monster[i]!=null)
                    entityList.add(monster[i]);
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
                    //Debug Function drawing time 
                    g2.dispose();
                }
            
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