package Object;



import entity.Entity;
import main.GamePanel;

public class OBJ_Key  extends Entity{
    GamePanel gp;
    public OBJ_Key(GamePanel gp){
        super(gp);
        name ="Key";
        down1=setup("/Objects/key",gp.tileSize,gp.tileSize);
        description=" [ "+ name+ " ]"+"\nYour key to Find the Treasure";
    }
}
