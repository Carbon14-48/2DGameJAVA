package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


public class Config {
    GamePanel gp;
    public Config(GamePanel gp){
        this.gp=gp;
    }
    public void SaveConfig(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            //full screen
            if(gp.fullScreenOn==true){
                bw.write("On");
            }
            if(gp.fullScreenOn==false){
                bw.write("Off");
            }
            bw.newLine();
            //save music volume
            bw.write(String.valueOf(gp.soundManager.getMusicVolumeScale()));
            bw.newLine();
            //se volume
            bw.write(String.valueOf(gp.soundManager.getMusicVolumeScale()));
            bw.newLine();
            bw.close();
            System.out.println("Conf saved seccesufully");
        } catch (Exception e) {
            e.printStackTrace();
        }
      
    }
    public void loadConfig(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
            String s=br.readLine();

            //full screen
            if(s.equals("On")){
                gp.fullScreenOn=true;
            }if(s.equals("Off")){
                gp.fullScreenOn=false;
            }

            //music volume
            s=br.readLine();
            int musicVol = Integer.parseInt(s);
            gp.soundManager.setMusicVolume(musicVol);
            
            s = br.readLine();
            int seVol = Integer.parseInt(s);
            gp.soundManager.setSEVolume(seVol);
            
            br.close();
            
            System.out.println("Config loaded - Music volume: " + musicVol + ", SE volume: " + seVol);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
