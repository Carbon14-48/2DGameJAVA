package Monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_greenSlime extends Entity {
       
    public MON_greenSlime(GamePanel gp){
        super(gp);
        type=2;
        name="Green Slime";
        speed=1;
        maxLife=4;
        life=maxLife;

        solidArea.x=3;
        solidArea.y=18;
        solidArea.width=42;
        solidArea.height=30;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        getImage();

    }

    public void getImage(){
        up1=setup("/Monster/greenslime_down_1");
        up2=setup("/Monster/greenslime_down_2");
        down1=setup("/Monster/greenslime_down_1");
        down2=setup("/Monster/greenslime_down_2");
        left1=setup("/Monster/greenslime_down_1");
        left2=setup("/Monster/greenslime_down_2");
        right1=setup("/Monster/greenslime_down_1");
        right2=setup("/Monster/greenslime_down_2");
    }
    
    public void setAction(){
        Random random = new Random();
int i=random.nextInt(100)+1;
    actionLocManager++;
    if(actionLocManager==120){
        if(i<=25){
            direction="up";
        }
        if(i>25 && i<=50){
            direction="down";
        }
        if(i>50 && i<=75){
            direction="left";
        }
        if(i>75 && i<100){
            direction="right";
        }
        actionLocManager=0;
    }
    }
}
