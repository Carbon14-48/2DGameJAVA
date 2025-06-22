package Strategy;

import Object.OBJ_Rock;
import entity.Entity;
import main.GamePanel;

public class PanicSlimeStrategy implements MonsterAttackStrategy {
    @Override
    public void attack(Entity monster, Entity target, GamePanel gp) {
        // Throw rocks in all four directions, rapidly!
        String[] directions = {"up", "down", "left", "right"};
        for (String dir : directions) {
            OBJ_Rock rock = new OBJ_Rock(gp);
            rock.set(monster.worldX, monster.worldY, dir, true, monster);
            rock.attack = 1;
            gp.projectileList.add(rock);
            
        }
        System.out.println("Panic mode hh");
      gp.soundManager.playSE(3);
    }
}