package Sounds;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPool {
    private Clip[] pool;
    private int poolIndex = 0;

    public SoundPool(URL soundURL, int poolSize) {
        pool = new Clip[poolSize];
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
            for (int i = 0; i < poolSize; i++) {
                pool[i] = AudioSystem.getClip();
                pool[i].open(AudioSystem.getAudioInputStream(soundURL));
            }
        } catch (Exception e) {
            System.out.println("Error creating sound pool: " + e.getMessage());
        }
    }

    public void play() {
        try {
            if (pool[poolIndex] != null) {
                pool[poolIndex].stop();
                pool[poolIndex].setFramePosition(0);
                pool[poolIndex].start();
                poolIndex = (poolIndex + 1) % pool.length;
            }
        } catch (Exception e) {
            System.out.println("Error playing from sound pool: " + e.getMessage());
        }
    }
}