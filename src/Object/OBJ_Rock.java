package Object;

import java.awt.Color;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile {
    GamePanel gp;
    public OBJ_Rock(GamePanel gp){
        super(gp);
        this.gp=gp;
        name="Rock";
        speed=10;//speed
        maxLife=80;//range
        life=maxLife;
        attack=1;
        useCost=1;
        alive=false;
        getImage();
    }
    public void getImage(){
        up1=setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        up2=setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down1=setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down2=setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left1=setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left2=setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right1=setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right2=setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);

    }
    @Override
    public Color getParticleColor(){
       Color color = new Color(40,50,0);
       return color;
    }
    @Override
    public int getParticleSize(){
       int size = 6;
       return size;
    }
    @Override
    public int getParticleSpeed(){
       int speed=1;
       return speed;
    }
    @Override
    public int getParticleMaxLife(){
       int maxLife=20;
       return maxLife;
    }
}