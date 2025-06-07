package main;

import Monster.MON_greenSlime;
import Object.OBJ_Axe;
import Object.OBJ_Coin_Bronze;
import Object.OBJ_Heart;

import Object.OBJ_ManaCrystal;
import Object.OBJ_Potion_Red;

import Object.OBJ_Shield_Blue;
import entity.NPC_OldMan;
import tile_interactives.IT_DryTree;


public class AssetSetter {
    GamePanel gp ;
    
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject (){
        int i =0;
        int mapNum=0;
        
        gp.obj[mapNum][i]=new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*37;
        gp.obj[mapNum][i].worldY=gp.tileSize*9;
        i++;
        gp.obj[mapNum][i]=new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*35;
        gp.obj[mapNum][i].worldY=gp.tileSize*28;
        i++;
        gp.obj[mapNum][i]=new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*30;
        gp.obj[mapNum][i].worldY=gp.tileSize*28;
        i++;
        gp.obj[mapNum][i]=new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*35;
        gp.obj[mapNum][i].worldY=gp.tileSize*42;
        i++;
        gp.obj[mapNum][i]=new OBJ_Shield_Blue(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*13;
        gp.obj[mapNum][i].worldY=gp.tileSize*33;
        i++;
        gp.obj[mapNum][i]=new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*11;
        gp.obj[mapNum][i].worldY=gp.tileSize*10;
        i++;
        gp.obj[mapNum][i]=new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*9;
        gp.obj[mapNum][i].worldY=gp.tileSize*28;
        i++;
        gp.obj[mapNum][i]=new OBJ_ManaCrystal(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*9;
        gp.obj[mapNum][i].worldY=gp.tileSize*23;
        i++;
    }
    public void setNPC(){
        int mapNum=0;
        gp.npc[mapNum][0]=new NPC_OldMan(gp);
        gp.npc[mapNum][0].worldX=gp.tileSize*19;
        gp.npc[mapNum][0].worldY=gp.tileSize*20;

    }
    public void setMonster(){
        int mapNum=0;
int i =0;
        gp.monster[mapNum][i]=new MON_greenSlime(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*23;
        gp.monster[mapNum][i].worldY=gp.tileSize*36;
    i++;
        gp.monster[mapNum][i]=new MON_greenSlime(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*27;
        gp.monster[mapNum][i].worldY=gp.tileSize*38;
        i++;
        gp.monster[mapNum][i]=new MON_greenSlime(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*24;
        gp.monster[mapNum][i].worldY=gp.tileSize*37;

        i++;
        gp.monster[mapNum][i]=new MON_greenSlime(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*34;
        gp.monster[mapNum][i].worldY=gp.tileSize*42;

        i++;
        gp.monster[mapNum][i]=new MON_greenSlime(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*38;
        gp.monster[mapNum][i].worldY=gp.tileSize*42;



    }
    public void setInteractiveTiles(){
        int mapNum=0;
        int i =0; 
        gp.iTile[mapNum][i]=new IT_DryTree(gp,27,12);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,28,12);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,29,12);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,30,12);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,31,12);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,32,12);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,33,12);i++;
        mapNum=1;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,27,12);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,28,12);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,29,12);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,30,12);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,31,12);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,32,12);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,33,12);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,31,21);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,18,40);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,17,40);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,16,40);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,15,40);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,14,40);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,13,40);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,13,41);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,12,40);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,11,40);i++;
        gp.iTile[mapNum][i]=new IT_DryTree(gp,10,40);i++;
    }
}
