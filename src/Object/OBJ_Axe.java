package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe  extends Entity{
    public OBJ_Axe(GamePanel gp){
        super(gp);
        name ="Axe";
        type=type_axe;
        //range 
        attackArea.width=30;
        attackArea.height=30;
        //
        down1=setup("/Objects/axe",gp.tileSize,gp.tileSize);
        attackValue=2;
        description="A heavy axe built to bring trees crashing \ndown";
    }
}
