package main;
import java.awt.*;
import java.awt.image.BufferedImage;

import Object.OBJ_Key;
public class UI {
    GamePanel gp;
    Font arial_40, arial_80B ;
    BufferedImage keyImage;
    public boolean messageOn ;
    public String message ="";
    int messageCounter =0;
    public boolean hasFinished=false;
    double playTime;
    public UI(GamePanel gp){
        this.gp=gp;
        arial_40=new Font("Arial", Font.PLAIN, 40);
        OBJ_Key key = new OBJ_Key();
        keyImage =key.image;
        arial_80B=new Font("Arial", Font.BOLD, 80);
    }
    public void showMessage(String text ){
        message=text;
        messageOn=true;

    }


    public void draw(Graphics2D  g2){
        if(hasFinished){
            // First line - treasure found
        g2.setFont(arial_40);
        g2.setColor(Color.BLUE);
        String text = " YOUUU FOUND THE TREASURE ";
        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - textLength/2;
        int y = gp.screenHeight/2 - 60; // Move up
        g2.drawString(text, x, y);

     
        

// Second line - congratulations (do NOT overlap)
g2.setFont(arial_80B);
g2.setColor(Color.BLUE);
text = " CONGRATULATIONS ";
textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
x = gp.screenWidth/2 - textLength/2;
y = gp.screenHeight/2 + 60; // Move down
g2.drawString(text, x, y);
             gp.gameThread=null;
        }else{
            
            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);

            g2.setFont(arial_40);          // <-- Make sure this is set to a large font (see below)
            g2.setColor(Color.YELLOW);      // <-- Use a visible color!
            g2.drawString("x " + gp.player.hasKey, 80, 60);

            //time 
            playTime+=(double)1/60;
            g2.drawString("TIME ---> " + String.format("%.4f", playTime), gp.tileSize*9, 65);            // message 
            if(messageOn==true){
                g2.setFont(g2.getFont().deriveFont(20f));
    g2.setColor(Color.WHITE); // Color for pickup messages
    g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
    messageCounter++;
    if (messageCounter > 100) {
        messageCounter = 0;
        messageOn = false;
    }
            }
        }
       
    }   
}
