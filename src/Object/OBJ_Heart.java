package Object;


import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    GamePanel gp;
    public OBJ_Heart(GamePanel gp){
        super(gp);
        this.gp=gp;
        name ="Heart";
        type=type_pickUPOnly;
        value=2;
        down1 =setup("/Objects/heart_full",gp.tileSize,gp.tileSize);// to be drawed by entity to be picked up
        image =setup("/Objects/heart_full",gp.tileSize,gp.tileSize);
        image2 =setup("/Objects/heart_half",gp.tileSize,gp.tileSize);
        image3=setup("/Objects/heart_blank",gp.tileSize,gp.tileSize);

    }
    public void use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("Life+ "+value);
        entity.life+=value;
        if(entity.life>entity.maxLife){
            entity.life=entity.maxLife;
        }
    }
}
