package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity{
    int value = 5;
    GamePanel gp;
    public OBJ_Potion_Red(GamePanel gp){
        
        super(gp);
        this.gp=gp;
        type=type_consumable;
        name="Red Potion";
        down1=setup("/Objects/potion_red", gp.tileSize, gp.tileSize);
        description="A glowing red potion\n that fills you with life ";
    }
    public void use(Entity entity){
        gp.gameState=GamePanel.dialogueState;
        gp.ui.currentDialogue="A fiery warmth rushes through \nyou—you are alive again";
        entity.life+=value;
        if(gp.player.life>gp.player.maxLife){
            gp.player.life=gp.player.maxLife;
        }
        gp.playSE(2);

    }
}
