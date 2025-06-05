package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public  abstract class  Entity {
    public int worldX,worldY ;
    
     public GamePanel gp;
    public BufferedImage image ,image2, image3;
   
    public boolean collision=false;
    public BufferedImage up1, up2, down1, down2, left1, left2 , right1,right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2 , attackRight1,attackRight2;
    public String direction="down";//setting a defalutmane
    public int spritCounter =0;
    public int spriteNum =1;
    public boolean invincible=false;
    public boolean alive=true;
    public boolean dying = false;
    public boolean hpBarOn=false;
     public int dyingcounter=0;
    public int invincibleCounter=0;
    int hpBarCounter = 0;
    public Rectangle solidArea =new Rectangle(0,0,48,48);
    public Rectangle attackArea= new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn=false;
    public int actionLocManager;
    boolean attacking=false;
    public String dialogues[] =new String[20];
    int dialogueIndex=0;
    public int type;//0 =player , 1=npc , 2=monster

    //character status
    public String name ;
    public int speed;
    public int maxLife;
    public int life;
    public int  level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nexLevelExp=2;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    
    // item attributes
    public int attackValue;
    public int defenseValue;
    public String description;

    public Entity(GamePanel gp){
        this.gp=gp;
    }


      public BufferedImage setup(String imageName,int width,int height){
        UtilityTool uTool=new UtilityTool();
        BufferedImage image=null;
        try  {
            image = ImageIO.read(getClass().getResourceAsStream(imageName+".png"));
            image=uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            System.out.println("Error fetching images");

        }
        return image;
    }
    public void speak(){
        if(dialogues[dialogueIndex]==null) dialogueIndex=0;
    gp.ui.currentDialogue=dialogues[dialogueIndex];
    dialogueIndex++;
    switch (gp.player.direction) {
        case "up":
            direction="down";
            break;
            case "down":
            direction="up";
            break;
            case "right":
            direction="left";
            break;
            case "left":
            direction="right";
            break;
        
    }
    }
public  void setAction(){};
public void damageReaction(){}
public void update(){
setAction();

collisionOn=false;
gp.cChecker.checkTile(this);
gp.cChecker.checkObject(this, false);
gp.cChecker.checkEntity(this, gp.npc);
gp.cChecker.checkEntity(this, gp.monster);
 boolean contactPlayer=gp.cChecker.checkPlayer(this);
 if(this.type==2 && contactPlayer==true){
    if(gp.player.invincible==false){
        gp.player.life-=1;
        gp.playSE(7);
        int damage=attack-gp.player.defense;
        if(damage<0) damage=0;
        gp.player.life-=damage;
        gp.player.invincible=true;
    }
 }

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


if(invincible==true){

    invincibleCounter++;
    // invinible oounter to 40 rather then 60 to attack more rapidly 
    if(invincibleCounter>40){
        invincible=false;
        invincibleCounter=0;
    }
}
};

   public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX =worldX -gp.player.worldX+gp.player.screenX;
        int screenY =worldY -gp.player.worldY+gp.player.screenY;

        if(worldX + gp.tileSize>gp.player.worldX-gp.player.screenX 
        && worldX -gp.tileSize<gp.player.worldX+gp.player.screenX 
        &&  worldY +gp.tileSize>gp.player.worldY-gp.player.screenY
         && worldY -gp.tileSize<gp.player.worldY+gp.player.screenY ){
           
            switch(direction){
             case "up":
             if(spriteNum==1)image=up1;
             if(spriteNum==2)image=up2;
             break;

             case "down":
             if(spriteNum==1)image=down1;
             if(spriteNum==2)image=down2;
             break;

             case "left":
             if(spriteNum==1)image=left1;
             if(spriteNum==2)image=left2;
             break;

             case "right":
             if(spriteNum==1) image=right1;
             if(spriteNum==2)image=right2;
             break;
            }
            //monster health bar 
        if(type==2 && hpBarOn ==true){//check if monster 
            double oneScale = (double) gp.tileSize/maxLife;
            double hpBarValue = oneScale*life;
            g2.setColor(new Color(255,255,255));
            g2.fillRect(screenX-1, screenY-14,gp.tileSize+2, 12);

            g2.setColor(new Color(255, 0,30));
            g2.fillRect(screenX, screenY-13, (int)hpBarValue, 10);
            hpBarCounter++;
            if(hpBarCounter>600){
                hpBarCounter=0;
                hpBarOn=false;
            }
        }
            




if(invincible==true){
    hpBarOn=true;
    hpBarCounter=0;
            changeAlpha(g2, 0.4f);

        }
        if(dying==true){
            dyingAnimation(g2);
        }
            g2.drawImage(image, screenX,screenY, gp.tileSize, gp.tileSize, null);
            changeAlpha(g2, 1f);        }
    }
    public void dyingAnimation(Graphics2D g2){
        dyingcounter++;
        int i=15;
        if(dyingcounter<=i)changeAlpha(g2, 0f);
        if(dyingcounter>i && dyingcounter <=i*2)changeAlpha(g2, 1f);
        if(dyingcounter>i*2 && dyingcounter <=i*3)changeAlpha(g2, 0f);
        if(dyingcounter>i*3 && dyingcounter <=i*4)changeAlpha(g2, 1f);
        if(dyingcounter>i*4&& dyingcounter <=i*5)changeAlpha(g2, 0f);
        if(dyingcounter>i*5 && dyingcounter <=i*6)changeAlpha(g2, 1f);
        if(dyingcounter>i*6 && dyingcounter <=i*7)changeAlpha(g2, 0f);
        if(dyingcounter>i*7 && dyingcounter <=i*8)changeAlpha(g2, 1f);
        if(dyingcounter>i){
            dying=false;
            alive=false;
        }

        
    }
    public  void changeAlpha(Graphics2D g2 ,  float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));
    }

}
