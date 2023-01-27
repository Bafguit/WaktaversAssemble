package com.fastcat.assemble.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Preferences;

public final class SettingHandler {

    public static SettingData setting;

    public static void initialize() {
        setting = new SettingData();
        Preferences prefs = Gdx.app.getPreferences("Setting");
        setting.screenMode = prefs.getInteger("screenMode", 0);
        if (setting.screenMode == 0) {
            setting.width = prefs.getInteger("width", 1600);
            setting.height = prefs.getInteger("height", 900);
        }

        // 볼륨 설정
        setting.volumeBgm = prefs.getInteger("volumeBgm", 80);
        setting.volumeSfx = prefs.getInteger("volumeSfx", 80);

        // 기타 설정
        setting.shake = prefs.getBoolean("shake", true);
        setting.fastMode = prefs.getBoolean("fastMode", false);
        save();
        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setForegroundFPS(displayMode.refreshRate);
        if(setting.screenMode == 0) {
            Gdx.graphics.setWindowedMode(setting.width, setting.height);
        } else if(setting.screenMode == 1) {
            Gdx.graphics.setFullscreenMode(displayMode);
        } else {
            Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Gdx.graphics.setUndecorated(true);
        }
    }

    public static void save() {
        Preferences prefs = Gdx.app.getPreferences("Setting");
        prefs.putInteger("volumeBgm", setting.volumeBgm);
        prefs.putInteger("volumeSfx", setting.volumeSfx);
        prefs.putInteger("width", setting.width);
        prefs.putInteger("height", setting.height);
        prefs.putInteger("screenMode", setting.screenMode);
        prefs.putBoolean("shake", setting.shake);
        prefs.putBoolean("fastMode", setting.fastMode);
        prefs.flush();
        prefs.clear();
    }

    public static class SettingData {
        public int volumeBgm; // 음악 볼륨
        public int volumeSfx; // 효과음 볼륨
        public int width; // 창모드일때만 활성화
        public int height; // 창모드일때만 활성화
        public int screenMode; // 0:창, 1:전체화면, 2:전체창(테두리 없음)
        public boolean shake; // 화면 흔들림
        public boolean fastMode; // 효과와 액션 배속
    }
}
