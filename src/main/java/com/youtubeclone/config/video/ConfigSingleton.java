package com.youtubeclone.config.video;

public class ConfigSingleton {
    private static volatile ConfigSingleton instance = null;

    private final int maxLimit;
    private final long windowMillis;

    private ConfigSingleton() {
        this.maxLimit = 5;
        this.windowMillis = 1000;
    }

    public static ConfigSingleton getInstance() {
        if(instance == null){
            synchronized (ConfigSingleton.class) {
                if(instance == null){
                    instance = new ConfigSingleton();
                }
            }
        }
        return instance;
    }

    public int getMaxLimit() {
        return maxLimit;
    }
    public long getWindowMillis() {
        return windowMillis;
    }
}
