package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity{
   
    GamePanel gp;
    public OBJ_Potion_Red(GamePanel gp){
        
        super(gp);
        this.gp=gp;
        type=type_consumable;
        name="Red Potion";
        value = 5;
        down1=setup("/Objects/potion_red", gp.tileSize, gp.tileSize);
        description="A glowing red potion\n that fills you with life ";
    }
    public void use(Entity entity){
        gp.setGameState(gp.dialogueState);
        gp.ui.currentDialogue="A fiery warmth rushes through \n MANA RESTORED";
        entity.life+=value;
        if(gp.player.life>gp.player.maxLife){
            gp.player.life=gp.player.maxLife;
            
        }
        gp.player.mana=gp.player.maxMana;
        

        gp.playSE(2);

    }
}
