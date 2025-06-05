package main;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import java.util.ArrayList;

import Object.OBJ_Heart;

import entity.Entity;

public class UI {
    GamePanel gp;
    Font maruMonica;
   Graphics2D g2;
   BufferedImage heart_full , heart_half, heart_blank;
    public boolean messageOn ;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
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

            Entity heart= new OBJ_Heart(gp);
            heart_full=heart.image;
            heart_half=heart.image2;
            heart_blank=heart.image3;
        
    }
    public void draw(Graphics2D  g2){
        
      this.g2=g2;

      g2.setFont(maruMonica);
      g2.setColor(Color.BLUE);
      //titleState

      if(gp.gameState==GamePanel.titleState){
        titleScreen.drawTitleScreen(g2,commandNum);
      }
      ///play state
      if(gp.gameState==GamePanel.playState){
        drawPLayerLife();
        drawMessage();
      }
      //pause state 
      if(gp.gameState==GamePanel.pauseState){
        drawPLayerLife();
        drawPauseScreen();
      }
      //dialogue state
      if(gp.gameState==GamePanel.dialogueState){
        drawPLayerLife();
        drawDialogueScreen();
      }
      if(gp.gameState == GamePanel.characterState){
        drawCharacterScreen();
      }

    }
    public void addMessage(String txt){
      message.add(txt);
      messageCounter.add(0);
    }

    private void  drawPLayerLife(){
      //max life
      int x=gp.tileSize/2;
      int y=gp.tileSize/2;
      int i =0;
      while(i<gp.player.maxLife/2){
        g2.drawImage(heart_blank, x, y, null);
        i++;
        x+=gp.tileSize;
      }
       x=gp.tileSize/2;
       y=gp.tileSize/2;
       i =0;
       while(i<gp.player.life){
        g2.drawImage(heart_half, x, y, null);
        i++;
        if(i<gp.player.life){
          g2.drawImage(heart_full, x,y , null) ;
                }
                i++;
                x+=gp.tileSize;

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
    public void drawMessage(){
      int messageX=gp.tileSize;
      int messageY=gp.tileSize*4;
      g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));
      for(int i=0;i<message.size();i++){
        if(message.get(i)!=null){
          g2.setColor(Color.black);
          g2.drawString(message.get(i), messageX+2, messageY);
          g2.setColor(Color.white);
          g2.drawString(message.get(i), messageX, messageY);
          int counter=messageCounter.get(i)+1;//message couner ++
          messageCounter.set(i, counter);
          messageY+=50;
          if(messageCounter.get(i)>180){
            message.remove(i);
            messageCounter.remove(i);
          }
        }

      }
    }
    public void drawCharacterScreen(){
      // create a frame 
      final int frameX=gp.tileSize;
      final int frameY=gp.tileSize;
      final int frameWidth=gp.tileSize*5;
      final int frameHeight=gp.tileSize*10;
      drawSubWindows(frameX, frameY, frameWidth, frameHeight);

      //TEXT 
      g2.setColor(Color.white);
      g2.setFont(g2.getFont().deriveFont(32f));

      int textX=frameX+20;
      int textY= frameY+gp.tileSize;
      final int lineHeight=32;

      //names
      g2.drawString("Level", textX, textY);
      textY+=lineHeight;
      g2.drawString("Life", textX, textY);
      textY+=lineHeight;
      g2.drawString("Strength", textX, textY);
      textY+=lineHeight;
      g2.drawString("Dextirity", textX, textY);
      textY+=lineHeight;
      g2.drawString("Attack", textX, textY);
      textY+=lineHeight;
      g2.drawString("Defense", textX, textY);
      textY+=lineHeight;
      g2.drawString("Exp", textX, textY);
      textY+=lineHeight;
      g2.drawString("Next Level", textX, textY);
      textY+=lineHeight;
      g2.drawString("Coin", textX, textY);
      textY+=lineHeight+30;
      g2.drawString("Weapon", textX, textY);
      textY+=lineHeight+30;
      g2.drawString("Shield", textX, textY);
      textY+=lineHeight;
      //values 
      int tailX=(frameX+frameWidth)-30;
      // reset 
      textY=frameY+gp.tileSize;
      String value;
      value=String.valueOf(gp.player.level);
      textX=getXForRightAlignedText(value, tailX);
      g2.drawString(value, textX, textY);
      textY+=lineHeight;
      value=String.valueOf(gp.player.life+"/"+gp.player.maxLife);
      textX=getXForRightAlignedText(value, tailX);
      g2.drawString(value, textX, textY);
      textY+=lineHeight;
      value=String.valueOf(gp.player.strength);
      textX=getXForRightAlignedText(value, tailX);
      g2.drawString(value, textX, textY);
      textY+=lineHeight;
      
      value=String.valueOf(gp.player.dexterity);
      textX=getXForRightAlignedText(value, tailX);
      g2.drawString(value, textX, textY);
      textY+=lineHeight;
      value=String.valueOf(gp.player.attack);
      textX=getXForRightAlignedText(value, tailX);
      g2.drawString(value, textX, textY);
      textY+=lineHeight;
      value=String.valueOf(gp.player.defense);
      textX=getXForRightAlignedText(value, tailX);
      g2.drawString(value, textX, textY);
      textY+=lineHeight;
      value=String.valueOf(gp.player.exp);
      textX=getXForRightAlignedText(value, tailX);
      g2.drawString(value, textX, textY);
      textY+=lineHeight;
      value=String.valueOf(gp.player.nexLevelExp);
      textX=getXForRightAlignedText(value, tailX);
      g2.drawString(value, textX, textY);
      textY+=lineHeight;
      value=String.valueOf(gp.player.coin);
      textX=getXForRightAlignedText(value, tailX);
      g2.drawString(value, textX, textY);
      textY+=lineHeight;
      g2.drawImage(gp.player.currentWeapon.down1, tailX-gp.tileSize, textY, null);
      textY+=gp.tileSize+10;
      g2.drawImage(gp.player.currentShield.down1, tailX-gp.tileSize, textY, null);



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

    public int getXForRightAlignedText(String text , int tailX){
      int length=(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
         return tailX-length;
    }
}
