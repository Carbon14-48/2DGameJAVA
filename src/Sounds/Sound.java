package Sounds;


import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class Sound {
  private Clip clip;
  
    FloatControl fc;
private URL soundURL[]= new URL[30];
public int volumeScale=3;
float volume;

 public Sound(){
    soundURL[0]=getClass().getResource("/sounds/BlueBoyAdventure.wav");
    soundURL[1]=getClass().getResource("/sounds/coin.wav");
    soundURL[2]=getClass().getResource("/sounds/powerup.wav");
    soundURL[3]=getClass().getResource("/sounds/unlock.wav");
    soundURL[4]=getClass().getResource("/sounds/pause.wav");
    soundURL[5]=getClass().getResource("/sounds/DawnOfNewTime_TitleScreenSound.wav");
    soundURL[6]=getClass().getResource("/sounds/teleport.wav");
    soundURL[7]=getClass().getResource("/sounds/damage.wav");
    soundURL[8]= getClass().getResource("/sounds/Healing.wav");
    soundURL[9]= getClass().getResource("/sounds/fanfare.wav");
    soundURL[10]= getClass().getResource("/sounds/cursor.wav");
    soundURL[11]= getClass().getResource("/sounds/fireball.wav");
   
    
 }

 public void setFile(int i){

   try {
      
      if(soundURL[i] == null) {
          System.out.println("Sound file at index " + i + " not found!");
          return;
      }
      if(clip != null && clip.isOpen()) {
          clip.close();
      }
      AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
      clip = AudioSystem.getClip();
      clip.open(ais);
      fc=(FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
      checkVolme();
  } catch (Exception e) {
      System.out.println("Error opening sound file at index " + i + ": " + e.getMessage());
      e.printStackTrace();
      clip = null;
  }

 }

 public void playSE(int i) {
   try {
       if (soundURL[i] == null) {
           System.out.println("Sound file at index " + i + " not found!");
           return;
       }
       AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
       Clip tempClip = AudioSystem.getClip();
       tempClip.open(ais);
       tempClip.start();
   } catch (Exception e) {
       System.out.println("Error playing sound at index " + i + ": " + e.getMessage());
   }
}

 public void play(){
    clip.start();

 }
 public  void loop(){
    clip.loop(Clip.LOOP_CONTINUOUSLY);

 }

 public void stop(){
    clip.stop();



 }
 public void checkVolme(){
    switch ((volumeScale)) {
        case 0://-80 6
            volume=-80;
            break;
            case 1:
            volume=20;
            break;
    
            case 2:
            volume=7;
            break;
    
            case 3:
            volume=-5;
            break;
            case 4:
            volume=4;
            break;
            case 5:
            volume=6;
            break;
    
    
    
        
    }
    fc.setValue(volume);
 }
}

