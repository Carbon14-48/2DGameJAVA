package main;

import javax.swing.JPanel;

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
    public final int worldWidth =tileSize*maxWorldCol;
    public final int  worldHeight =tileSize*maxWorldRow;
TileManager tileM = new TileManager(this);

    KeyHandler keyH = new KeyHandler();
      public   Player player = new Player(this, keyH);
    Thread gameThread;
 public  CollisionChecker cChecker=new CollisionChecker(this);
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
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
                long currentTime =System.nanoTime();
                System.out.println("current Time"+currentTime);  
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
            player.update();
        }
        public void paintComponent(Graphics g){

            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            
            tileM.draw(g2);
            player.draw(g2);
             // Draw FPS
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("FPS: " + currentFPS, 10, 20);
            g2.dispose();
        }
}