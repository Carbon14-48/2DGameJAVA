package Sounds;

import kuusisto.tinysound.TinySound;
import kuusisto.tinysound.Music;

public class Sound {
    private kuusisto.tinysound.Sound[] sounds = new kuusisto.tinysound.Sound[30];
    private Music currentMusic;
    public int volumeScale = 3;
    private double volume = 0.6;
    
    public Sound() {
        TinySound.init();
        loadAllSounds();
    }
    
    private void loadAllSounds() {
        try {
            sounds[0] = TinySound.loadSound("/sounds/BlueBoyAdventure.wav");
            sounds[1] = TinySound.loadSound("/sounds/coin.wav");
            sounds[2] = TinySound.loadSound("/sounds/powerup.wav");
            sounds[3] = TinySound.loadSound("/sounds/unlock.wav");
            sounds[4] = TinySound.loadSound("/sounds/pause.wav");
            sounds[5] = TinySound.loadSound("/sounds/DawnOfNewTime_TitleScreenSound.wav");
            sounds[6] = TinySound.loadSound("/sounds/teleport.wav");
            sounds[7] = TinySound.loadSound("/sounds/damage.wav");
            sounds[8] = TinySound.loadSound("/sounds/Healing.wav");
            sounds[9] = TinySound.loadSound("/sounds/fanfare.wav");
            sounds[10] = TinySound.loadSound("/sounds/cursor.wav");
            sounds[11] = TinySound.loadSound("/sounds/fireball.wav");
            sounds[12] = TinySound.loadSound("/sounds/GameOver.wav");
            
            // Add your SoundPool sounds to the array
            sounds[13] = TinySound.loadSound("/sounds/swinging.wav");
            sounds[14] = TinySound.loadSound("/sounds/hitMonster.wav");
            sounds[15] = TinySound.loadSound("/sounds/fireball.wav");
            sounds[16] = TinySound.loadSound("/sounds/cutting.wav");
            
            checkVolme();
            System.out.println("All sounds loaded with TinySound!");
        } catch (Exception e) {
            System.out.println("Error loading sounds: " + e.getMessage());
        }
    }
    
    public void setFile(int i) {
        try {
            if (currentMusic != null) {
                currentMusic.stop();
            }
            
            String[] musicPaths = {
                "/sounds/BlueBoyAdventure.wav",
                "/sounds/coin.wav",
                "/sounds/powerup.wav",
                "/sounds/unlock.wav", 
                "/sounds/pause.wav",
                "/sounds/DawnOfNewTime_TitleScreenSound.wav",
                "/sounds/teleport.wav",
                "/sounds/damage.wav",
                "/sounds/Healing.wav",
                "/sounds/fanfare.wav",
                "/sounds/cursor.wav",
                "/sounds/fireball.wav",
                "/sounds/GameOver.wav"
            };
            
            if (i >= 0 && i < musicPaths.length) {
                currentMusic = TinySound.loadMusic(musicPaths[i]);
            }
        } catch (Exception e) {
            System.out.println("Error setting music file: " + e.getMessage());
        }
    }
    
    public void play() {
        if (currentMusic != null) {
            currentMusic.stop(); // Stop first to apply new volume
            currentMusic.play(false); // Play without volume parameter
            currentMusic.setVolume(volume); // Set volume separately
        }
    }
    
    public void loop() {
        if (currentMusic != null) {
            currentMusic.stop(); // Stop first to apply new volume
            currentMusic.play(true); // Loop without volume parameter
            currentMusic.setVolume(volume); // Set volume separately
        }
    }
    
    public void stop() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
    }
    
    public void playSE(int i) {
        if (i >= 0 && i < sounds.length && sounds[i] != null) {
            sounds[i].play(volume);
        }
    }
    
    public void checkVolme() {
        switch (volumeScale) {
            case 0: volume = 0.0; break;
            case 1: volume = 0.2; break;
            case 2: volume = 0.4; break;
            case 3: volume = 0.6; break;
            case 4: volume = 0.8; break;
            case 5: volume = 1.0; break;
            default: volume = 0.6; break;
        }
        
        // UPDATE CURRENT MUSIC VOLUME IMMEDIATELY
        if (currentMusic != null && currentMusic.playing()) {
            currentMusic.setVolume(volume);
        }
    }
    
    public void cleanup() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
        TinySound.shutdown();
    }
}