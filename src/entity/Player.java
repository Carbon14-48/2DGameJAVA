package entity;


import main.KeyHandler;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;


import main.GamePanel;

public class Player extends Entity{
   
    KeyHandler keyH;
    public final  int screenX;
    public final int screenY;
     
     int standCount ;
    public Player (GamePanel gp , KeyHandler keyH){
        super(gp);
       
        this.keyH=keyH;

        screenX=gp.screenWidth/2-(gp.tileSize/2);
        screenY=gp.screenHeight/2 -(gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x=8;
        solidArea.y=16;
        solidArea.width=32;
        solidArea.height=32;
        solidAreaDefaultX =solidArea.x;
        solidAreaDefaultY= solidArea.y;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX=gp.tileSize*23;
        worldY=gp.tileSize*21;
        speed=4;
        direction="down";

        //player status 
        //1 life = half heart(1/2 heart) so 3 hearts= 6 life

        maxLife=6;
        life=maxLife;
    }
    public void getPlayerImage(){
        up1=setup("/player/boy_up_1");
        up2=setup("/player/boy_up_2");
        down1=setup("/player/boy_down_1");
        down2=setup("/player/boy_down_2");
        left1=setup("/player/boy_left_1");
        left2=setup("/player/boy_left_2");
        right1=setup("/player/boy_right_1");
        right2=setup("/player/boy_right_2");



    }
  
    public void update(){
        
        if ( keyH.downPressed==true || keyH.rightPressed==true || keyH.leftPressed==true||keyH.upPressed==true) {  
            int playerCol = worldX / gp.tileSize;
        int playerRow = worldY / gp.tileSize;
        System.out.println("Player at tile: Col=" + playerCol + ", Row=" + playerRow);      
        if(keyH.upPressed==true){
            direction="up";
            
        }else if(keyH.downPressed==true){
            direction="down";
           
        }else if (keyH.leftPressed==true){
            direction="left";
            
        }else if (keyH.rightPressed==true){
            direction="right";
            
        }
        collisionOn=false;
        gp.cChecker.checkTile(this);


        //object collsion

         int objIndex =gp.cChecker.checkObject(this, true);
         pickUpObject(objIndex);

         //npc 
         int npcIndex=gp.cChecker.checkEntity(this, gp.npc);
         interactNPC(npcIndex);



         ///check monster collision
         int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
         contactMonster(monsterIndex);
         
        //event checkng
        gp.eventHandler.checkEvent();

        gp.keyH.enterPressed=false;
        if(collisionOn==false){
            switch (direction) {
                case "up":worldY-=speed; break;
                case "down":worldY+=speed;break;
                case "left":worldX-=speed;break;
                case "right":worldX+=speed;break;
            }
        }
        spritCounter++;
        if(spritCounter>15){
            if(spriteNum==1){
                spriteNum=2;
            }
            else if (spriteNum==2) {
                spriteNum=1;
            }
            spritCounter=0;
        }
    }else{
        standCount++;

        if(standCount ==20){
            spriteNum=1;
            standCount =0;
        }
        
    }
    // this needs to be outside of key if statment
    if(invincible==true){

        invincibleCounter++;
        if(invincibleCounter>60){
            invincible=false;
            invincibleCounter=0;
        }
    }
    }

public void interactNPC(int i){
    if(i!=999){
        if(gp.keyH.enterPressed==true){
                gp.gameState=gp.dialogueState;
                gp.npc[i].speak();
        }
    }
   
}

public void contactMonster(int i ){

    if(i!=999){
        if(invincible==false){
        life-=1;
        gp.playSE(7);
        invincible=true;
    }
    }
}

    public void pickUpObject(int i){
        if(i!=999){

        }
    }
    public void draw(Graphics2D g2){

       BufferedImage image = null;
       switch(direction){
        case "up":
        if(spriteNum==1){
            image=up1;
        }
        if(spriteNum==2){
            image=up2;
        }
       
        break;
        case "down":
        if(spriteNum==1){
            image=down1;
        }
        if(spriteNum==2){
            image=down2;
        }
        break;
        case "left":
        if(spriteNum==1){
            image=left1;
        }
        if(spriteNum==2){
            image=left2;
        }
        break;
        case "right":
        if(spriteNum==1){
            image=right1;
        }
        if(spriteNum==2){
            image=right2;
        }
        break;
       }
       if(invincible==true){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
       }
       g2.drawImage(image,screenX, screenY, gp.tileSize,gp.tileSize,null);
       g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


       /* 
        CHECK COLLIISION 
       g2.setColor(Color.red);
       g2.drawRect(screenX+solidArea.x,screenY+solidArea.y, solidArea.width,solidArea.height);*/ 
    }
    
}
