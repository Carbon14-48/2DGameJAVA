package main;

import javax.swing.JFrame;

import Database.Config;
import Database.ConfigDAO;
import Database.ConfigDAOMySQL;
import Observer.LevelScreenManager;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static JFrame window;
    public static void main(String[] args) {
        ConfigDAO dao = null;
        GamePanel gamePanel = GamePanel.getInstance();

        //connection a my sql
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Game", "root", ""
            );
            dao = new ConfigDAOMySQL(conn);
            System.out.println("Connected to database, using MySQL for config.");
        } catch (Exception e) {
            System.err.println("Could not connect to database, using file config instead.");
            e.printStackTrace();
        }

        Config config = new Config(gamePanel, dao);
        gamePanel.conf = config;
        gamePanel.conf.addObserver(gamePanel.soundManager);
        gamePanel.conf.addObserver(gamePanel.ui);
        LevelScreenManager levelScreenManager = new LevelScreenManager();
        gamePanel.player.addLevelObserver(levelScreenManager);
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D GAME ADVENTURE");
        window.add(gamePanel);

        gamePanel.conf.loadConfig();
        if(gamePanel.fullScreenOn == true){
            window.setUndecorated(true);
        }
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}