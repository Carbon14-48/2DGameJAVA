package Object;


import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gp){
        super(gp);
        name ="Heart";
        image =setup("/Objects/heart_full",gp.tileSize,gp.tileSize);
        image2 =setup("/Objects/heart_half",gp.tileSize,gp.tileSize);
        image3=setup("/Objects/heart_blank",gp.tileSize,gp.tileSize);

    }
}
