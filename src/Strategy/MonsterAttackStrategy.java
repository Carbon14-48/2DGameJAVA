package Strategy;
import entity.Entity;
import main.GamePanel;

public interface MonsterAttackStrategy {
    void attack(Entity monster, Entity target, GamePanel gp);
}