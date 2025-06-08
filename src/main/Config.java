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
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();
            //se volume
            bw.write(String.valueOf(gp.se.volumeScale));
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
            gp.music.volumeScale=Integer.parseInt(s);
            gp.music.checkVolme(); // APPLY THE LOADED MUSIC VOLUME
            
            //se volume
            s=br.readLine();
            gp.se.volumeScale=Integer.parseInt(s);
            gp.se.checkVolme(); // APPLY THE LOADED SE VOLUME
            
            br.close();
            
            System.out.println("Config loaded - Music volume: " + gp.music.volumeScale + ", SE volume: " + gp.se.volumeScale);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
