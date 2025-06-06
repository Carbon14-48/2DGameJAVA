package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
    GamePanel gp ;
    public OBJ_ManaCrystal(GamePanel gp){
        super(gp);
        this.gp=gp;
        name="Mana Crystal";
        type=type_pickUPOnly;
        value=1;
        down1=setup("/Objects/manacrystal_full", gp.tileSize, gp.tileSize);

        image=setup("/Objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image2=setup("/Objects/manacrystal_blank", gp.tileSize, gp.tileSize);
    }
    public void use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("Mana+ "+value);
        entity.mana+=value;
        if(entity.mana>entity.maxMana){
            entity.mana=entity.maxMana;
        }
    }
}
