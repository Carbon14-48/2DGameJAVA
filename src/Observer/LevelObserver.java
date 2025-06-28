package Observer;

import entity.Player;

public interface LevelObserver {
void onLevelUp(Player player, int newLevel);
    
} 
