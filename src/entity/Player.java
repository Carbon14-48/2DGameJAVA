package entity;


import main.KeyHandler;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Object.OBJ_Fireball;
import Object.OBJ_Key;

import Object.OBJ_Shield_Wood;
import Object.OBJ_Sword_Normal;
import main.GamePanel;
import Observer.LevelObserver;


public class Player extends Entity{
   
    KeyHandler keyH;
    public final  int screenX;
    public final int screenY;
    public boolean attackCanceled=false;
    public ArrayList<Entity> inventory =new ArrayList<>();
    public final int inventorySize=20;

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


        /// attack range
        //attackArea.width=36;
        //attackArea.height=36;


        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    public void setDefaultValues(){
       worldX=gp.tileSize*23;
        worldY=gp.tileSize*21;
       // worldX=gp.tileSize*12;
        //worldY=gp.tileSize*13;
        speed=4;
        direction="down";

        //player status 
        //1 life = half heart(1/2 heart) so 3 hearts= 6 life
//players satus
        level=1;
        maxLife=6;
        life=maxLife;
        strength=1;
        maxMana=4;
        mana=maxMana;
        dexterity=1;
        exp=0;
        coin=0;
        currentWeapon=new OBJ_Sword_Normal(gp);
        currentShield=new OBJ_Shield_Wood(gp);
        projectile=new OBJ_Fireball(gp);
        attack=getAttack();
        defense=getDefense();
        setInventoryItems();
    }
    public  void setInventoryItems(){
        inventory.clear();//for retry
        inventory.add(currentWeapon);
inventory.add(currentShield);
inventory.add(new OBJ_Key(gp));
inventory.add(new OBJ_Key(gp));

    }
    public void setDefaultPositions(){
        worldX=gp.tileSize*23;
        worldY=gp.tileSize*21;
        direction="down";
    }
    public void restoreLifeAndMana(){
        life=maxLife;
        mana=maxMana;
        invincible=false;
    }

    public int getAttack(){
        attackArea=currentWeapon.attackArea;
        return attack=strength*currentWeapon.attackValue;
    }
    public int getDefense(){
        return defense=dexterity*currentShield.defenseValue;
    }
    public void getPlayerImage(){
        up1=setup("/player/boy_up_1",gp.tileSize,gp.tileSize);
        up2=setup("/player/boy_up_2",gp.tileSize,gp.tileSize);
        down1=setup("/player/boy_down_1",gp.tileSize,gp.tileSize);
        down2=setup("/player/boy_down_2",gp.tileSize,gp.tileSize);
        left1=setup("/player/boy_left_1",gp.tileSize,gp.tileSize);
        left2=setup("/player/boy_left_2",gp.tileSize,gp.tileSize);
        right1=setup("/player/boy_right_1",gp.tileSize,gp.tileSize);
        right2=setup("/player/boy_right_2",gp.tileSize,gp.tileSize);



    }
  public void getPlayerAttackImage(){
    if(currentWeapon.type==type_sword){
    attackUp1=setup("/player/boy_attack_up_1",gp.tileSize,gp.tileSize*2 );
    attackUp2=setup("/player/boy_attack_up_2",gp.tileSize,gp.tileSize*2);
    attackDown1=setup("/player/boy_attack_down_1",gp.tileSize,gp.tileSize*2);
    attackDown2=setup("/player/boy_attack_down_2",gp.tileSize,gp.tileSize*2);
    attackLeft1=setup("/player/boy_attack_left_1",gp.tileSize*2,gp.tileSize);
    attackLeft2=setup("/player/boy_attack_left_2",gp.tileSize*2,gp.tileSize);
    attackRight1=setup("/player/boy_attack_right_1",gp.tileSize*2,gp.tileSize);
    attackRight2=setup("/player/boy_attack_right_2",gp.tileSize*2,gp.tileSize);
}
if(currentWeapon.type==type_axe){
    attackUp1=setup("/player/boy_axe_up_1",gp.tileSize,gp.tileSize*2 );
    attackUp2=setup("/player/boy_axe_up_2",gp.tileSize,gp.tileSize*2);
    attackDown1=setup("/player/boy_axe_down_1",gp.tileSize,gp.tileSize*2);
    attackDown2=setup("/player/boy_axe_down_2",gp.tileSize,gp.tileSize*2);
    attackLeft1=setup("/player/boy_axe_left_1",gp.tileSize*2,gp.tileSize);
    attackLeft2=setup("/player/boy_axe_left_2",gp.tileSize*2,gp.tileSize);
    attackRight1=setup("/player/boy_axe_right_1",gp.tileSize*2,gp.tileSize);
    attackRight2=setup("/player/boy_axe_right_2",gp.tileSize*2,gp.tileSize);
}
  }
    public void update(){
        if(attacking==true){
            attacking();

        }else if ( keyH.downPressed==true || keyH.rightPressed==true || keyH.leftPressed==true||keyH.upPressed==true||keyH.enterPressed==true) {  
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
         gp.cChecker.checkEntity(this, gp.iTile);
        

        
        if(collisionOn==false && keyH.enterPressed==false){
            switch (direction) {
                case "up":worldY-=speed; break;
                case "down":worldY+=speed;break;
                case "left":worldX-=speed;break;
                case "right":worldX+=speed;break;
            }

        }
        if(keyH.enterPressed==true && attackCanceled==false){
            gp.playSE(13);
            attacking=true;
            spritCounter=0;
        }
        attackCanceled=false;
        gp.keyH.enterPressed=false;
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
    if(gp.keyH.shootKeyPressed==true && projectile.alive ==false && shotAvailableCounter==30 &&projectile.haveResource(this)==true){//u cant shoot a fire whle other is
        
             projectile.set(worldX,worldY,direction,true,this);                                                   //still on screen
        projectile.substractResource(this);
             gp.projectileList.add(projectile);
             shotAvailableCounter=0;
             gp.playSE(15);
    }
            
    //invincible state 
    if(invincible==true){

        invincibleCounter++;
        if(invincibleCounter>60){
            invincible=false;
            invincibleCounter=0;
        }
    }
    if(shotAvailableCounter<30){
        shotAvailableCounter++;
    }

    if(life<=0){
        gp.setGameState(gp.gameOverState);
        gp.playSE(12);
    }
    }

public void attacking(){
    spritCounter++;
    if(spritCounter<=5){
        spriteNum=1;
    }
    if(spritCounter>5 && spritCounter<=25){
        spriteNum=2;
        //save the current X y and solid area
        int currentWorldX= worldX;
        int currentWorldY= worldY;
        int solidAreaWidth = solidArea.width;
        int solidAreaHeight= solidArea.height;

        switch (direction) {
            case "up": worldY-=attackArea.height;break;
            
            case "down": worldY+=attackArea.height;break;

            case "left": worldX-=attackArea.height;break;

            case "right":worldX+=attackArea.height;break;    
        }
        //atackarea become solid area
        solidArea.width=attackArea.width;
        solidArea.height=attackArea.height;

        //check Monster collision with the updated work
        int monsterIndex=gp.cChecker.checkEntity(this, gp.monster);
        damageMonster(monsterIndex,attack);
        int iTileIndex=gp.cChecker.checkEntity(this, gp.iTile);
        damageIneractiveTile(iTileIndex);
//restore original data
        worldX=currentWorldX;
        worldY=currentWorldY;
        solidArea.width=solidAreaWidth;
        solidArea.height=solidAreaHeight;

    }
    if(spritCounter>25 ){
        spriteNum=1;
        spritCounter=0;
        attacking=false;
    }
}

public void damageIneractiveTile(int iTileIndex){
    if(iTileIndex!=999 && gp.iTile[gp.currentMap][iTileIndex].destructible==true & gp.iTile[gp.currentMap][iTileIndex].isCorrectItem(this)==true){
        generateParticule(gp.iTile[gp.currentMap][iTileIndex], gp.iTile[gp.currentMap][iTileIndex]);
        gp.iTile[gp.currentMap][iTileIndex].playSE();
        gp.iTile[gp.currentMap][iTileIndex]=gp.iTile[gp.currentMap][iTileIndex].getDestroyedForm();

    }
}


public void interactNPC(int i){
    if(gp.keyH.enterPressed==true){
        if(i!=999){
            attackCanceled=true;
            gp.setGameState(gp.dialogueState);
            gp.npc[gp.currentMap][i].speak();
    
        }
    }
}
    
    

private ArrayList<LevelObserver> levelObservers = new ArrayList<>();

public void addLevelObserver(LevelObserver observer) {
    levelObservers.add(observer);
}
public void removeLevelObserver(LevelObserver observer) {
    levelObservers.remove(observer);
}
private void notifyLevelUp(int newLevel) {
    for (LevelObserver obs : levelObservers) {
        obs.onLevelUp(this, newLevel);
    }
}
    


public void contactMonster(int i ){

    if(i!=999){
        if(invincible==false && gp.monster[gp.currentMap][i].dying==false ){
        life-=1;
        int damage=gp.monster[gp.currentMap][i].attack-defense;
        if(damage<0) damage=0;
        life-=damage;
        gp.playSE(7);
        invincible=true;
    }
    }
}
public void damageMonster(int i,int attack){
    if(i!=999){
        
    if(gp.monster[gp.currentMap][i].invincible==false){
        gp.playSE(14);
        int damage=attack-gp.monster[gp.currentMap][i].defense;
        if(damage<0) damage=0;
        gp.ui.addMessage(damage+" damage !");
        gp.monster[gp.currentMap][i].life-=damage;
        
        gp.monster[gp.currentMap][i].invincible=true;
        gp.monster[gp.currentMap][i].damageReaction();
        if(gp.monster[gp.currentMap][i].life<=0){
            gp.monster[gp.currentMap][i].dying = true;
            gp.ui.addMessage("Killed The "+gp.monster[gp.currentMap][i].name+"!");
            gp.ui.addMessage("Exp + "+gp.monster[gp.currentMap][i].exp+"!");
            exp+=gp.monster[gp.currentMap][i].exp;
            checkLevelUp();
            
        }
    }
    }

}

public void checkLevelUp(){
    if(exp>=nexLevelExp){
        notifyLevelUp(level);
     }
        
    }
    public void pickUpObject(int i){
        String text ;
        if(i!=999){
            if(gp.obj[gp.currentMap][i].type==type_pickUPOnly){
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i]=null;
            }else{
                if(inventory.size()!=inventorySize){
                    inventory.add(gp.obj[gp.currentMap][i]);
                    gp.playSE(1);
                    text ="U picked up A "+gp.obj[gp.currentMap][i].name +"!";
                    }else{
                        text ="The backpack Is full!!";
                    }
                    gp.ui.addMessage(text);
                    gp.obj[gp.currentMap][i]=null;
            }
           
        }
    }

    public void selectItem(){
        int itemIndex=gp.ui.getItemIndexOnSlot();

        if(itemIndex<inventory.size()){// to make sure not empty slot
            Entity selectedItem= inventory.get(itemIndex);
            if(selectedItem.type==type_sword|| selectedItem.type==type_axe){
                currentWeapon=selectedItem;
                attack=getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type==type_shield){
                currentShield=selectedItem;
                defense=getDefense();
            }
            if(selectedItem.type==type_consumable){
                
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int drawWidth = gp.tileSize;
        int drawHeight = gp.tileSize;
        int drawX = screenX;
        int drawY = screenY;
        
        switch(direction){
            case "up":
                if(attacking==false){
                    if(spriteNum==1) image=up1;
                    if(spriteNum==2) image=up2;
                } else if(attacking==true){
                    if(spriteNum==1) image=attackUp1;
                    if(spriteNum==2) image=attackUp2;
                    // Attack up images are 16x32 (tileSize x tileSize*2)
                    drawHeight = gp.tileSize*2;
                    drawY = screenY - gp.tileSize; // Move up so player stays centered
                }
                break;
    
            case "down":
                if(attacking==false){
                    if(spriteNum==1) image=down1;
                    if(spriteNum==2) image=down2;
                } else if(attacking==true){
                    if(spriteNum==1) image=attackDown1;
                    if(spriteNum==2) image=attackDown2;
                    // Attack down images are 16x32 (tileSize x tileSize*2)
                    drawHeight = gp.tileSize*2;
                    // Keep Y position same for down attacks
                }
                break;
                
            case "left":
                if(attacking==false){
                    if(spriteNum==1) image=left1;
                    if(spriteNum==2) image=left2;
                } else if(attacking==true){
                    if(spriteNum==1) image=attackLeft1;
                    if(spriteNum==2) image=attackLeft2;
                    // Attack left images are 32x16 (tileSize*2 x tileSize)
                    drawWidth = gp.tileSize*2;
                    drawX = screenX - gp.tileSize; // Move left so player stays centered
                }
                break;
                
            case "right":
                if(attacking==false){
                    if(spriteNum==1) image=right1;
                    if(spriteNum==2) image=right2;
                } else if(attacking==true){
                    if(spriteNum==1) image=attackRight1;
                    if(spriteNum==2) image=attackRight2;
                    // Attack right images are 32x16 (tileSize*2 x tileSize)
                    drawWidth = gp.tileSize*2;
                    // Keep X position same for right attacks
                }
                break;
        }
        
        if(invincible==true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        g2.drawImage(image, drawX, drawY, drawWidth, drawHeight, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
    }
    
}
