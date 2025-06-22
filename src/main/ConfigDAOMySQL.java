package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConfigDAOMySQL implements ConfigDAO {
    private Connection conn;

    public ConfigDAOMySQL(Connection conn) {
        this.conn = conn;
    }

    @Override
    public String getConfigValue(String key) {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT config_value FROM config WHERE config_key = ?")) {
            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("config_value");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setConfigValue(String key, String value) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "REPLACE INTO config (config_key, config_value) VALUES (?, ?)")) {
            stmt.setString(1, key);
            stmt.setString(2, value);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}