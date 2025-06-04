package main;

import javax.swing.JPanel;

import Object.SuperObject;
import entity.Entity;
import entity.Player;
import tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16;
    final int scale = 3;

  public  final int tileSize = originalTileSize*scale;//48x48
  public  final int maxScreenCol=16;
     public final int maxScreenRow=12;
     public final int screenWidth = maxScreenCol*tileSize;//768 px
     public final int screenHeight = maxScreenRow*tileSize;//576 px

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
    Thread gameThread;
 public  CollisionChecker cChecker=new CollisionChecker(this);
 public UI ui = new UI(this);
 // objects 
public AssetSetter aSetter = new AssetSetter(this);
public SuperObject obj[]=new SuperObject[10];
public Entity npc[] = new Entity[10];
//sound 
Sound music = new Sound();
Sound se = new Sound();




///gAME STATE 
 public int gameState;
 public final int titleState=0;
 public final int playState =1;
 public final int pauseState =2;
 public final int dialogueState=3;
 



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
        playMusic(0);
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
            }
            if(gameState==pauseState){
                //nothing
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
                    
                    //object 
                    for(int i =0 ;i<obj.length;i++){
                        if(obj[i]!=null){
                            obj[i].draw(g2,this);
                        }
                    }
                    //npc
                    for(int i =0;i<npc.length;i++){
                        if(npc[i]!=null){
                            npc[i].draw(g2);
                        }
                    }
                    //player
                    player.draw(g2);
        
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
    se.setFile(i);
    se.play();
}






}