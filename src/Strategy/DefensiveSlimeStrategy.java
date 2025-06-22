package Strategy;

import entity.Entity;
import main.GamePanel;

public class DefensiveSlimeStrategy implements MonsterAttackStrategy {
    @Override
    public void attack(Entity monster, Entity target, GamePanel gp) {
        if (monster.life > monster.maxLife / 2) {
            System.out.println(monster.name + " attacks defensively.");
            target.life -= monster.attack;
        } else {
            System.out.println(monster.name + " defends!");
            monster.defense += 1;
        }
    }
}