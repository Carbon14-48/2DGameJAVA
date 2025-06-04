package main;
import java.awt.*;

public class UI {
    GamePanel gp;
    Font arial_40, arial_80B ;
   Graphics2D g2;
    public boolean messageOn ;
    public String message ="";
    int messageCounter =0;
    public boolean hasFinished=false;
    public String currentDialogue;
   
    public UI(GamePanel gp){
        this.gp=gp;
        arial_40=new Font("Arial", Font.PLAIN, 40);
    
        arial_80B=new Font("Arial", Font.BOLD, 80);
    }
    public void showMessage(String text ){
        message=text;
        messageOn=true;

    }


    public void draw(Graphics2D  g2){
        
      this.g2=g2;

      g2.setFont(arial_40);
      g2.setColor(Color.BLUE);
      ///play state
      if(gp.gameState==gp.playState){

      }
      //pause state 
      if(gp.gameState==gp.pauseState){
        drawPauseScreen();
      }
      //dialogue state
      if(gp.gameState==gp.dialogueState){
        drawDialogueScreen();
      }

    }

    public void drawDialogueScreen(){
        //create dialgue window
        int x,y,width,height;
        x=gp.tileSize*2;
        y=gp.tileSize/2;
        width=gp.screenWidth-gp.tileSize*4;
        height=gp.tileSize*5;

    drawSubWindows(x, y, width, height);
    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30));
    x+=gp.tileSize;
    y+=gp.tileSize;
    g2.drawString(currentDialogue, x, y);

    }

    public void drawSubWindows(int x, int y  , int width, int height){
        Color c= new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height,35,35);
        c=new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25,25);
    }
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
        String text ="PAUSED";
        int x=getXToCenterText( text);
        
        int y=gp.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public int getXToCenterText(String text){
        int length=(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
         return gp.screenWidth/2-length/2;
    }


}
