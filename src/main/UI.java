package main;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import java.util.ArrayList;

import Database.Config;
import Object.OBJ_Heart;
import Object.OBJ_ManaCrystal;
import Observer.ConfigObserver;
import entity.Entity;

public class UI implements ConfigObserver{
    GamePanel gp;
    Font maruMonica;
   Graphics2D g2;
   BufferedImage heart_full , heart_half, heart_blank,crystal_full, crystal_blank;
    public boolean messageOn ;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean hasFinished=false;
    public String currentDialogue;
    TitleScreen titleScreen;
    public  int commandNum =0;
    public int slotCol=0;
    public int slotRow=0;
    int subState=0;

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
            Entity crystal = new OBJ_ManaCrystal(gp);
            crystal_full=crystal.image;
            crystal_blank=crystal.image2;
        
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
        drawInventory();
      }
      if(gp.gameState==GamePanel.OptionsState){
        drawOptionsScreen();
      }
      if(gp.gameState==GamePanel.gameOverState){
        drawGameOverScreen();
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
              ///draw Max mana
              x=(gp.tileSize/2)-5;
              y=(int)(gp.tileSize*1.5);
              i=0;
              while (i<gp.player.maxMana) {
                g2.drawImage(crystal_blank,x, y,null);
                i++;
                x+=35;
                
              }
              //draw Mana
              x=(gp.tileSize/2)-5;
              y=(int)(gp.tileSize*1.5);
              i=0;
              while(i<gp.player.mana){
                g2.drawImage(crystal_full,x, y,null);
                i++;x+=35;
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
     public void drawOptionsScreen(){
      g2.setColor(Color.white);
      g2.setFont(g2.getFont().deriveFont(32F));

      int frameX=gp.tileSize*6;
      int frameY=gp.tileSize;
      int frameWidth=gp.tileSize*8;
      int frameHeight=gp.tileSize*10;
      drawSubWindows(frameX, frameY, frameWidth, frameHeight);

      switch (subState) {
        case 0:
          options_top(frameX, frameY);
          break;
      
          case 1:
          options_fullScreenNotification( frameX, frameY);
          break;
          case 2:
          Options_control(frameX, frameY);break;
          case 3:
          Options_endGame(frameX, frameY);break;
      }
      gp.keyH.enterPressed=false;

    }
    
    public void options_top(int frameX, int frameY){
      int textX;
      int textY;
      String text ="Options";
      textX= getXToCenterText(text);
      textY=frameY+gp.tileSize;
      g2.drawString(text, textX, textY);

      //full screen on-<off
      textX =frameX +gp.tileSize;
      textY=frameY+gp.tileSize*2;
      g2.drawString("Full screen", textX, textY);
      if(commandNum==0){
        g2.drawString(">", textX-25, textY);
        if(gp.keyH.enterPressed==true){
          if(gp.fullScreenOn==false) {
              gp.fullScreenOn=true;
          } else if(gp.fullScreenOn==true){
              gp.fullScreenOn=false;
          }
          gp.conf.SaveConfig();
          subState=1;
      }    
    }
      ///music 
    textY+=gp.tileSize;
    g2.drawString("Music", textX, textY);
    if(commandNum==1){
      g2.drawString(">", textX-25, textY);
    }
    textY+=gp.tileSize;
    g2.drawString("SE", textX, textY);
    if(commandNum==2){
      g2.drawString(">", textX-25, textY);    }
    textY+=gp.tileSize;
    g2.drawString("Control", textX, textY);
    if(commandNum==3){
      g2.drawString(">", textX-25, textY);  
    if(gp.keyH.enterPressed){

subState=2;
commandNum=0;


    }  }
    textY+=gp.tileSize;
    g2.drawString("End Game", textX, textY);
    if(commandNum==4){
      g2.drawString(">", textX-25, textY); 
      if(gp.keyH.enterPressed==true)   {
        subState=3;
        commandNum=0;
      }
    }
    textY+=gp.tileSize*1;
    g2.drawString("Back", textX, textY);
    if(commandNum==5){
      g2.drawString(">", textX-25, textY);   
      if(gp.keyH.enterPressed==true){
        gp.gameState=GamePanel.playState;
        commandNum=0;
        subState=0;
      }
         }
      //full screen chcekckbox
    textX=frameX+(int)(gp.tileSize*4.5);
    textY=frameY+gp.tileSize+24;
    g2.setStroke(new BasicStroke(3));
      g2.drawRect(textX, textY, 24, 24);
if(gp.fullScreenOn==true) g2.fillRect(textX, textY, 24, 24);

      //music volume
      textY+=gp.tileSize;
      g2.drawRect(textX, textY, 120, 24);
      int volumeWidth =24*gp.soundManager.getMusicVolumeScale();
      g2.fillRect(textX, textY, volumeWidth, 24);
      //se volume
      textY+=gp.tileSize;
      g2.drawRect(textX, textY, 120, 24);
     volumeWidth =24*gp.soundManager.getSEVolumeScale();
      g2.fillRect(textX, textY, volumeWidth, 24);
    
      }
    
  public void Options_control(int frameX, int frameY){
    int textX;
    int textY;

    String text = "Control";
    textX=getXToCenterText(text);
    textY=frameY+gp.tileSize;
    g2.drawString(text, textX, textY);
    textX=frameX+gp.tileSize;
    textY+=gp.tileSize;
    g2.drawString("Move", textX, textY);textY+=gp.tileSize;
    g2.drawString("Confirm/Attack", textX, textY);textY+=gp.tileSize;
    g2.drawString("Shoot/Cast", textX, textY);textY+=gp.tileSize;
    g2.drawString("Character Screen", textX, textY);textY+=gp.tileSize;
    g2.drawString("Pause", textX, textY);textY+=gp.tileSize;
    g2.drawString("Options", textX, textY);textY+=gp.tileSize;
    textX=frameX+gp.tileSize*6;
    textY=frameY+gp.tileSize*2;
    g2.drawString("WASD", textX, textY);textY+=gp.tileSize;
    g2.drawString("Enter", textX, textY);textY+=gp.tileSize;
    g2.drawString("F", textX, textY);textY+=gp.tileSize;
    g2.drawString("C", textX, textY);textY+=gp.tileSize;
    g2.drawString("P", textX, textY);textY+=gp.tileSize;
    g2.drawString("ESC", textX, textY);textY+=gp.tileSize;
   //back 
   textX=frameX+gp.tileSize;
    textY=frameY+gp.tileSize*9;
    g2.drawString("Back", textX, textY);
    if(commandNum==0){
      g2.drawString(">", textX-25, textY);
      if(gp.keyH.enterPressed==true){
        subState=0;
        commandNum=3;
      }
    }

  }
  
    public void drawCharacterScreen(){
      // create a frame 
      final int frameX=gp.tileSize*2;
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
      g2.drawString("Mana", textX, textY);
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
      value=String.valueOf(gp.player.mana+"/"+gp.player.maxMana);
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
    public void drawGameOverScreen(){
      g2.setColor(new Color(0,0,0,150));
      g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
      int x; 
      int y ;
      String text;
      g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
      text="Game Over";
      g2.setColor(Color.BLACK);
      x=getXToCenterText(text);
      y=gp.tileSize*4;
      g2.drawString(text, x, y);
      // Main
      g2.setColor(Color.WHITE);
      g2.drawString(text, x, y);

      //retry 
      g2.setFont(g2.getFont().deriveFont(50F));
      text="Retry";
      x=getXToCenterText(text);
      y+=gp.tileSize*4;
      g2.drawString(text, x, y);
      if(commandNum==0){
        g2.drawString(">", x-40, y);
      }
      text="Quit";
      x=getXToCenterText(text);
      y+=55;
      g2.drawString(text, x, y);
      if(commandNum==1){
        g2.drawString(">", x-40, y);
      }

    }
    public void drawInventory(){
      int frameX=gp.tileSize*12;
      int frameY=gp.tileSize;
      int frameHeight=gp.tileSize*5;
      int frameWidth=gp.tileSize*6;
      drawSubWindows(frameX, frameY, frameWidth, frameHeight);
        //slots
        final int slotXstart =frameX+20;
        final int slotYstart =frameY+20;
        int slotX=slotXstart;
        int slotY = slotYstart;
        //draw player items
        for(int i =0;i<gp.player.inventory.size();i++){
          if(gp.player.inventory.get(i)!=null){
            if(gp.player.inventory.get(i)==gp.player.currentWeapon|| gp.player.inventory.get(i)==gp.player.currentShield){
              g2.setColor(new Color(240,190,90));
              g2.fillRoundRect(slotX, slotY,gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(gp.player.inventory.get(i).down1, slotX,slotY,null);
            slotX+=gp.tileSize;
            if(i==4 || i==9 || i==14){slotX=slotXstart;slotY+=gp.tileSize;} 
          }
        }
        //cursor
        int cursorX=slotXstart+(gp.tileSize*slotCol);
        int cursorY=slotYstart+(gp.tileSize*slotRow);
        int cursorWidth=gp.tileSize;
        int cursorHeight=gp.tileSize;
        //draw cursor
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        int dFrameX=frameX;
        int dFrameY=frameY+frameHeight;
        int dFrameWidth=frameWidth;
        int dFrameHeight=gp.tileSize*3;
       
        int textX=dFrameX+20;
        int textY=dFrameY+gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(18f));
        int itemIndex = getItemIndexOnSlot();
        if(itemIndex<gp.player.inventory.size()){
          drawSubWindows(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
          for(var line :gp.player.inventory.get(itemIndex).description.split("\n")){
            g2.drawString(line, textX, textY);
            textY+=32;
          }
         
        
    }}
    public void Options_endGame(int frameX, int frameY){
      int textX=frameX+gp.tileSize;
      int textY=frameY+gp.tileSize;
      currentDialogue="Quit the game  and \n return to the title screen?";
      for(var line : currentDialogue.split("\n")){
        g2.drawString(line, textX, textY);
        textY+=gp.tileSize;
      }
      String text="YES";
      textX=getXToCenterText(text);
      textY+=gp.tileSize*3;
      g2.drawString(text, textX, textY);
      if(commandNum==0){
      g2.drawString(">", textX-25, textY);
      if(gp.keyH.enterPressed==true){
        subState=0;
        gp.gameState=GamePanel.titleState;
        commandNum=0;
        gp.stopMusic();
            gp.playMusic(5);
      }
    }
      text="No";
      textX=getXToCenterText(text);
      textY+=gp.tileSize*3;
      g2.drawString(text, textX, textY);
      if(commandNum==1){          
      g2.drawString(">", textX-25, textY);
      if(gp.keyH.enterPressed==true){
        subState=0;
       commandNum=4;
      }
    }
    }
public int getItemIndexOnSlot(){
int itemIndex=slotCol+(slotRow*5);
return itemIndex;
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
    public void options_fullScreenNotification(int frameX,int frameY){
      int textX=frameX+gp.tileSize;
      int textY=frameY+gp.tileSize*3;
      currentDialogue="The change will take effect \nafter restarting the game";
      for(String line : currentDialogue.split("\n")){
        g2.drawString(line ,textX, textY);
        textY+=40;
      }
      //back
      textY=frameY+gp.tileSize*9;
      g2.drawString("Back", textX, textY);
      if(commandNum==0){
        g2.drawString(">", textX-25, textY);
        if(gp.keyH.enterPressed==true){
          subState=0;
        }
      }
    }
    @Override
    public void onConfigChanged(Config config) {
        
        addMessage("Config changed! Music: " + gp.soundManager.getMusicVolumeScale() +
                   ", SE: " + gp.soundManager.getSEVolumeScale());
        System.out.println("Config changed! (UI observer notified)");
    }
}
