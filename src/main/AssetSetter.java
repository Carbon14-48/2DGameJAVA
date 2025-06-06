package main;

import Monster.MON_greenSlime;
import Object.OBJ_Axe;
import Object.OBJ_Key;
import Object.OBJ_Potion_Red;

import Object.OBJ_Shield_Blue;
import entity.NPC_OldMan;


public class AssetSetter {
    GamePanel gp ;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject (){
        int i =0;
        gp.obj[i]=new OBJ_Key(gp);
        gp.obj[i].worldX=gp.tileSize*37;
        gp.obj[i].worldY=gp.tileSize*9;
        i++;
        gp.obj[i]=new OBJ_Key(gp);
        gp.obj[i].worldX=gp.tileSize*35;
        gp.obj[i].worldY=gp.tileSize*28;
        i++;
        gp.obj[i]=new OBJ_Key(gp);
        gp.obj[i].worldX=gp.tileSize*30;
        gp.obj[i].worldY=gp.tileSize*28;
        i++;
        gp.obj[i]=new OBJ_Axe(gp);
        gp.obj[i].worldX=gp.tileSize*35;
        gp.obj[i].worldY=gp.tileSize*42;
        i++;
        gp.obj[i]=new OBJ_Shield_Blue(gp);
        gp.obj[i].worldX=gp.tileSize*13;
        gp.obj[i].worldY=gp.tileSize*33;
        i++;
        gp.obj[i]=new OBJ_Potion_Red(gp);
        gp.obj[i].worldX=gp.tileSize*11;
        gp.obj[i].worldY=gp.tileSize*10;
        i++;
    }
    public void setNPC(){
        gp.npc[0]=new NPC_OldMan(gp);
        gp.npc[0].worldX=gp.tileSize*19;
        gp.npc[0].worldY=gp.tileSize*20;

    }
    public void setMonster(){
int i =0;
        gp.monster[i]=new MON_greenSlime(gp);
        gp.monster[i].worldX=gp.tileSize*23;
        gp.monster[i].worldY=gp.tileSize*36;
    i++;
        gp.monster[i]=new MON_greenSlime(gp);
        gp.monster[i].worldX=gp.tileSize*27;
        gp.monster[i].worldY=gp.tileSize*38;
        i++;
        gp.monster[i]=new MON_greenSlime(gp);
        gp.monster[i].worldX=gp.tileSize*24;
        gp.monster[i].worldY=gp.tileSize*37;

        i++;
        gp.monster[i]=new MON_greenSlime(gp);
        gp.monster[i].worldX=gp.tileSize*34;
        gp.monster[i].worldY=gp.tileSize*42;

        i++;
        gp.monster[i]=new MON_greenSlime(gp);
        gp.monster[i].worldX=gp.tileSize*38;
        gp.monster[i].worldY=gp.tileSize*42;



    }
}
