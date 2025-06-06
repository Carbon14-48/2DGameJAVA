package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue  extends Entity{
    public OBJ_Shield_Blue( GamePanel gp ){
        super(gp);
        type=type_shield;
        name ="Blue Shield";
        down1=setup("/Objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue=3;
        description=" [ "+ name+ " ]"+"\nA radiant blue shield, \nborn to block even the fiercest blows";
    }
}
