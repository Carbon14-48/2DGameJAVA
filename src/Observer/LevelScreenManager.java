package Observer;

import entity.Player;

public class LevelScreenManager implements LevelObserver {
    @Override
    public void onLevelUp(Player player, int newLevel) {
        player.level++;
        player.nexLevelExp=player.nexLevelExp*2;
        player.maxLife+=2;
        player.strength++;
        player.dexterity++;
        player.attack=player.getAttack();
        player.defense=player.getDefense();
        player.gp.playSE(9);
        player.gp.setGameState(player.gp.dialogueState);
        player.gp.ui.currentDialogue=" You ARE LEVEL "+player.level+" NOW \n"+" YOU ARE NOW STRONGER";
    }
}