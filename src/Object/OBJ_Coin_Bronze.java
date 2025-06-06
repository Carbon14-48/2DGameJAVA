package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze  extends Entity{
    GamePanel gp;

    public OBJ_Coin_Bronze(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type =type_pickUPOnly;
        name="Bronze Coin";
        value =2;
        down1 = setup("/Objects/coin_bronze", gp.tileSize, gp.tileSize);
    }
    public void use(Entity entity){
        gp.playSE(1);
        gp.ui.addMessage("Coin "+value);
        gp.player.coin+=value;
        

    }

}
