package Sounds;

import Database.Config;
import Observer.ConfigObserver;

public class SoundManager implements ConfigObserver {
    private static SoundManager instance;
    private Sound music;
    private Sound se;

    private SoundManager() {
        music = new Sound();
        se = new Sound();
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
    }

    public void loopMusic(int i) {
        music.setFile(i);
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.playSE(i);
    }

    public void setMusicVolume(int scale) {
        music.volumeScale = scale;
        music.checkVolme();
    }

    public void setSEVolume(int scale) {
        se.volumeScale = scale;
        se.checkVolme();
    }

    // === ADD THESE GETTERS ===
    public int getMusicVolumeScale() {
        return music.volumeScale;
    }

    public int getSEVolumeScale() {
        return se.volumeScale;
    }
    // === END GETTERS ===

    public void cleanup() {
        music.cleanup();
        se.cleanup();
    }
    @Override
    public void onConfigChanged(Config config) {
        // Update volumes from config
        setMusicVolume(config.gp.soundManager.getMusicVolumeScale());
        setSEVolume(config.gp.soundManager.getSEVolumeScale());
    }

}