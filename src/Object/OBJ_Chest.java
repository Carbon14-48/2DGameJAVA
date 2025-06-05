package Object;



import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{
    GamePanel gp;
    public OBJ_Chest(GamePanel gp){
        super(gp);
        name ="Chest";
        down1=setup("/Objects/chest",gp.tileSize,gp.tileSize);
        
    }
}
