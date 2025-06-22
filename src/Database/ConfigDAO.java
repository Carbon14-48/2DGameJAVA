package Database;

public interface ConfigDAO {
    String getConfigValue(String key);
    void setConfigValue(String key, String value);
}