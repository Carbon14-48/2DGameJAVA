package Observer;

import Database.Config;

public interface ConfigObserver {
    void onConfigChanged(Config config);
}
