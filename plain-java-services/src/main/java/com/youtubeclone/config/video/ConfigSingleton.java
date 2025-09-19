package com.youtubeclone.config.video;

public class ConfigSingleton {
    private static volatile ConfigSingleton instance = null;

    private final int maxLimit;
    private final long windowMillis;

    private ConfigSingleton(int maxLimit, long windowMillis) {
        this.maxLimit = maxLimit;
        this.windowMillis = windowMillis;
    }

    public static ConfigSingleton getInstance() {
        if(instance == null){
            synchronized (ConfigSingleton.class) {
                if(instance == null){
                    instance = new ConfigSingleton(100, 60000);
                }
            }
        }
        return instance;
    }

    public static void init(int maxLimit, long windowMillis) {
        if(instance == null){
            synchronized (ConfigSingleton.class) {
                if(instance == null){
                    instance = new ConfigSingleton(maxLimit, windowMillis);
                }
            }
        }
    }

    public int getMaxLimit() {
        return maxLimit;
    }
    public long getWindowMillis() {
        return windowMillis;
    }
}
