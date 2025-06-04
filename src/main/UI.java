package main;
import java.awt.*;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Font maruMonica;
   Graphics2D g2;
    public boolean messageOn ;
    public String message ="";
    int messageCounter =0;
    public boolean hasFinished=false;
    public String currentDialogue;
    TitleScreen titleScreen;
    public  int commandNum =0;
    public UI(GamePanel gp){
    
        this.gp=gp;
        titleScreen= new TitleScreen(gp);
        InputStream is = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
        try{maruMonica=Font.createFont(Font.TRUETYPE_FONT, is);
        
        }catch(Exception E){
            System.out.println("Error getting the font ");
            }
        
    }
    public void showMessage(String text ){
        message=text;
        messageOn=true;

    }
    public void draw(Graphics2D  g2){
        
      this.g2=g2;

      g2.setFont(maruMonica);
      g2.setColor(Color.BLUE);
      //titleState

      if(gp.gameState==gp.titleState){
        titleScreen.drawTitleScreen(g2,commandNum);
      }
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
    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35));
    x+=gp.tileSize;
    y+=gp.tileSize;
    for(var line : currentDialogue.split("\n")){
    g2.drawString(line, x, y);
    y+=40;
}

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
