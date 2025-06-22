package Monster;

import java.util.Random;

import Object.OBJ_Coin_Bronze;
import Object.OBJ_Heart;
import Object.OBJ_ManaCrystal;
import Object.OBJ_Rock;
import entity.Entity;
import main.GamePanel;
import Strategy.MonsterAttackStrategy;
import Strategy.PanicSlimeStrategy;
import Strategy.DefensiveSlimeStrategy;


public class MON_greenSlime extends Entity {
    private MonsterAttackStrategy attackStrategy;

    public MON_greenSlime(GamePanel gp){
        super(gp);
        type=type_monster;
        name="Green Slime";
        speed=1;
        maxLife=4;
        life=maxLife;
        attack=2;
        defense=0;
        exp=1;
        projectile = new OBJ_Rock(gp);
        solidArea.x=3;
        solidArea.y=18;
        solidArea.width=42;
        solidArea.height=30;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        getImage();
        // Default strategy
        this.attackStrategy = new DefensiveSlimeStrategy();
    }

    public void setAttackStrategy(MonsterAttackStrategy strategy) {
        this.attackStrategy = strategy;
    }

    public void performAttack(Entity target) {
        if (attackStrategy != null) {
            attackStrategy.attack(this, target, gp);
        }
        System.out.println("called perform attack");
    }

    public void getImage(){
        up1=setup("/Monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        up2=setup("/Monster/greenslime_down_2",gp.tileSize,gp.tileSize);
        down1=setup("/Monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        down2=setup("/Monster/greenslime_down_2",gp.tileSize,gp.tileSize);
        left1=setup("/Monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        left2=setup("/Monster/greenslime_down_2",gp.tileSize,gp.tileSize);
        right1=setup("/Monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        right2=setup("/Monster/greenslime_down_2",gp.tileSize,gp.tileSize);
    }
    @Override
    public void damageReaction() {
        actionLocManager=0;
        direction=gp.player.direction;
    }

    public void setAction(){
        actionLocManager++;
        if(actionLocManager==120){
            Random random = new Random();
            int i=random.nextInt(100)+1;
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
        int i = new Random().nextInt(100)+1;
        if(i>99 && projectile.alive==false&& shotAvailableCounter==30){
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter=0;
        }
    }

    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100)+1;
        if(i<50) dropItem(new OBJ_Coin_Bronze(gp));
        if(i>=50 && i<=75) dropItem(new OBJ_ManaCrystal(gp));
        if(i>=90 && i<=100) dropItem(new OBJ_Heart(gp));
    }
    private int attackCounter = 0;
  @Override
public void update() {
    super.update();
    setAction();

    int dx = Math.abs(this.worldX - gp.player.worldX);
    int dy = Math.abs(this.worldY - gp.player.worldY);
    int attackRange = gp.tileSize;
    attackCounter++;

    // Panic mode: 
    if (this.life < this.maxLife / 2) {
        this.setAttackStrategy(new PanicSlimeStrategy());
        if (attackCounter >= 20) {
            this.performAttack(gp.player);
            attackCounter = 0;
        }
    }
    // Normal mode:
    else if (dx < attackRange && dy < attackRange) {
        this.setAttackStrategy(new DefensiveSlimeStrategy());
        if (attackCounter >= 60) {
            this.performAttack(gp.player);
            attackCounter = 0;
        }
    }}
}
