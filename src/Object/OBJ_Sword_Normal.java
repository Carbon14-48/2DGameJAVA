package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity{
    public OBJ_Sword_Normal( GamePanel gp ){
        super(gp);
        name ="Normal Sword";
        type=type_sword;
        attackArea.width=36;
        attackArea.height=36;
        down1=setup("/Objects/sword_normal", gp.tileSize, gp.tileSize);
        attackValue=1;
        description=" [ "+ name+ " ]"+"\nAn Old But Sharp Sword";
        
    }

}
