package tile_interactives;

import entity.Entity;
import main.GamePanel;

public class IT_DryTree  extends InteractiveTile{
    GamePanel gp;
    public IT_DryTree(GamePanel gp,int col , int row){
        super(gp, row, col);
        this.worldX=gp.tileSize*col;
        this.worldY=gp.tileSize*row;
        this.gp=gp;
        destructible=true;
        down1=setup("/tile_interactive/drytree", gp.tileSize,gp.tileSize);
    }

    public boolean isCorrectItem (Entity entity){
        boolean isCorrectItem=false;
        if(entity.currentWeapon.type==type_axe){
            isCorrectItem=true;
    
        }
        return isCorrectItem;
     }
     public void playSE(){
        gp.cuttingPool.play();
     }
     public InteractiveTile getDestroyedForm(){
        InteractiveTile tile =new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;
     }
}
