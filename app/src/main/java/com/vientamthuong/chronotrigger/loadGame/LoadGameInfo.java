package com.vientamthuong.chronotrigger.loadGame;

public class LoadGameInfo {
    private String location;
    private String timeSave;
    private int gold;
    private String level;
    private String timePlay;
    private boolean isAutoSave;

    public LoadGameInfo(String location, String timeSave, int gold, String level, String timePlay, boolean isAutoSave) {
        this.location = location;
        this.timeSave = timeSave;
        this.gold = gold;
        this.level = level;
        this.timePlay = timePlay;
        this.isAutoSave = isAutoSave;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimeSave() {
        return timeSave;
    }

    public void setTimeSave(String timeSave) {
        this.timeSave = timeSave;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTimePlay() {
        return timePlay;
    }

    public void setTimePlay(String timePlay) {
        this.timePlay = timePlay;
    }

    public boolean isAutoSave() {
        return isAutoSave;
    }

    public void setAutoSave(boolean autoSave) {
        isAutoSave = autoSave;
    }
}
