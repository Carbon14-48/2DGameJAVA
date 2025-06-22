package Database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import Observer.*;


import main.GamePanel;


public class Config {
    public GamePanel gp;
    private ConfigDAO dao;
    public Config(GamePanel gp, ConfigDAO dao) {
        this.gp = gp;
        this.dao = dao;
    }

    private List<ConfigObserver> observers = new ArrayList<>();
    // --- Observer Methods ---
    public void addObserver(ConfigObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(ConfigObserver observer) {
        observers.remove(observer);
    }
    private void notifyObservers() {
        for (ConfigObserver obs : observers) {
            obs.onConfigChanged(this);
        }
    }
/////observers logic
    public void setMusicVolume(int newVol) {
        gp.soundManager.setMusicVolume(newVol);
        notifyObservers();
    }
    public void setSEVolume(int newVol) {
        gp.soundManager.setSEVolume(newVol);
        notifyObservers();
    }
    public void setFullScreen(boolean full) {
        gp.fullScreenOn = full;
        notifyObservers();
    }

    public void SaveConfig() {
        if (dao != null) {
            // Save to DB
            dao.setConfigValue("fullscreen", gp.fullScreenOn ? "On" : "Off");
            dao.setConfigValue("music_volume", String.valueOf(gp.soundManager.getMusicVolumeScale()));
            dao.setConfigValue("se_volume", String.valueOf(gp.soundManager.getSEVolumeScale()));
            System.out.println("Config saved to DB successfully");
        } else {
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
    }
    public void loadConfig(){
        if (dao != null) {
            // Load from DB
            String fs = dao.getConfigValue("fullscreen");
            if ("On".equals(fs)) gp.fullScreenOn = true;
            if ("Off".equals(fs)) gp.fullScreenOn = false;

            String musicVol = dao.getConfigValue("music_volume");
            if (musicVol != null) gp.soundManager.setMusicVolume(Integer.parseInt(musicVol));

            String seVol = dao.getConfigValue("se_volume");
            if (seVol != null) gp.soundManager.setSEVolume(Integer.parseInt(seVol));

            System.out.println("Config loaded from DB - Music volume: " + musicVol + ", SE volume: " + seVol);
        }else{
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
    }}
  
}
