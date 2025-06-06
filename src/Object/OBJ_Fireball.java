package Object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball  extends Projectile{
    GamePanel gp;
    public OBJ_Fireball(GamePanel gp){
        super(gp);
        this.gp=gp;
        name="FireBall";
        speed=8;//speed
        maxLife=120;//range
        life=maxLife;
        attack=3;
        useCost=1;
        alive=false;
        getImage();
    }
    public void getImage(){
        up1=setup("/projectile/fireball_up_1", gp.tileSize, gp.tileSize);
        up2=setup("/projectile/fireball_up_2", gp.tileSize, gp.tileSize);
        down1=setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
        down2=setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
        left1=setup("/projectile/fireball_left_1", gp.tileSize, gp.tileSize);
        left2=setup("/projectile/fireball_left_2", gp.tileSize, gp.tileSize);
        right1=setup("/projectile/fireball_right_1", gp.tileSize, gp.tileSize);
        right2=setup("/projectile/fireball_right_2", gp.tileSize, gp.tileSize);

    }
    public boolean haveResource(Entity user){
        boolean haveResource=false;
        if(user.mana>=useCost) haveResource=true;
        return haveResource;
    }
    public void substractResource(Entity user){
        user.mana-=useCost;
    }
    @Override
    public Color getParticleColor(){
       Color color = new Color(240,50,0);
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
