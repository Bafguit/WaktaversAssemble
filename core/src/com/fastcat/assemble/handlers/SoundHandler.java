package com.fastcat.assemble.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.WakTower;

import java.util.HashMap;

public final class SoundHandler implements Disposable {

    public static final HashMap<String, Sound> sfx = new HashMap<>();
    public static final HashMap<String, MusicData> music = new HashMap<>();
    public static final Queue<MusicData> nextMusic = new Queue<>();
    private static final float FADE = 2.5f;
    private static final float MASTER_M = 0.4f;
    private static final float MASTER_S = 0.8f;
    public static MusicData curMusic;

    /***
     * Instance of handler.
     * Initialized on getInstance()
     */
    private static SoundHandler instance;

    private SoundHandler() {
        generateSound();
        generateMusic();
    }

    /***
     * Returns instance of handler, if not exist, create new.
     * @return instance of handler
     */
    public static SoundHandler getInstance() {
        if (instance == null) return (instance = new SoundHandler());
        return instance;
    }

    private static void generateSound() {
        sfx.put("CLICK", getSound("sound/sfx/click.ogg"));
    }

    private static void generateMusic() {
        //music.put(s = "BATTLE_1", new MusicData(s, getMusic("sound/bgm/" + s.toLowerCase() + ".mp3")));
    }

    private static Sound getSound(String url) {
        FileHandle fileHandle = Gdx.files.internal(url);
        return Gdx.audio.newSound(fileHandle);
    }
/*
    private static Music getMusic(String url) {
        FileHandle fileHandle = Gdx.files.internal(url);
        return Gdx.audio.newMusic(fileHandle);
    }
*/
    public static Sound playSfx(String key) {
        Sound s = sfx.get(key);
        if (s != null) {
            s.play(sVol());
        }
        return s;
    }

    public static MusicData playMusic(String key, boolean isLoop, boolean fadeIn) {
        if (curMusic != null && curMusic.key.equals(key)) return curMusic;
        else {
            MusicData d = music.get(key);
            if (d != null) {
                Music s = d.music;
                s.setLooping(isLoop);
                if (fadeIn) {
                    d.isFadingOut = false;
                    d.isFading = true;
                    d.isDone = false;
                    d.fadeOutStartVolume = mVol();
                    d.setFadeTime(FADE);
                    s.setVolume(0.0f);
                } else s.setVolume(mVol());
                curMusic = d;
                s.play();
            }
            return d;
        }
    }

    /*
    public static MusicData addWay() {
        return addMusic("WAY_" + AbstractLabyrinth.floorNum, true, true);
    }

    public static MusicData addBattle() {
        if (currentFloor.currentRoom.type == BOSS) {
            return addMusic("BOSS_" + AbstractLabyrinth.floorNum, true, true);
        } else {
            return addMusic("BATTLE_" + AbstractLabyrinth.floorNum, true, true);
        }
    }
    */

    public static MusicData addMusic(String key, boolean isLoop, boolean fadeIn) {
        if (curMusic != null && curMusic.key.equals(key)) return curMusic;
        else {
            MusicData d = music.get(key);
            if (d != null) {
                Music s = d.music;
                s.setLooping(isLoop);
                if (fadeIn) {
                    d.isFadingOut = false;
                    d.isFading = true;
                    d.isDone = false;
                    d.fadeOutStartVolume = mVol();
                    d.setFadeTime(FADE);
                    s.setVolume(0.0f);
                } else s.setVolume(mVol());
                nextMusic.addLast(d);
            }
            return d;
        }
    }

    public static void fadeOutAll() {
        for (MusicData d : music.values()) {
            if (d != null && d.music.isPlaying()) {
                d.isFadingOut = true;
                d.isFading = true;
                d.fadeOutStartVolume = d.music.getVolume();
                d.setFadeTime(FADE);
            }
        }
    }

    public static void stopAllMusic() {
        for (MusicData d : music.values()) {
            if (d != null && d.music.isPlaying()) {
                d.music.stop();
            }
        }
    }

    public static void reset() {
        for (MusicData d : music.values()) {
            if (d != null) {
                d.stop = true;
            }
        }
    }

    public static void fadeOutMusic(String key) {
        MusicData d = music.get(key);
        if (d != null) {
            d.isFadingOut = true;
            d.isFading = true;
            d.fadeOutStartVolume = d.music.getVolume();
            d.setFadeTime(FADE);
        }
    }

    public static float mVol() {
        return SettingHandler.setting.volumeBgm * 0.01f * MASTER_M;
    }

    public static float sVol() {
        return SettingHandler.setting.volumeSfx * 0.01f * MASTER_S;
    }

    public void update() {
        if (curMusic != null) {
            curMusic.update();
            if (curMusic.isDone) {
                if (nextMusic.size > 0) {
                    curMusic = nextMusic.removeFirst();
                    curMusic.music.play();
                } else curMusic = null;
            }
        } else if (nextMusic.size > 0) {
            curMusic = nextMusic.removeFirst();
            curMusic.music.play();
        }
    }

    @Override
    public void dispose() {
        for (Sound s : sfx.values()) {
            s.dispose();
        }
        for (MusicData m : music.values()) {
            m.dispose();
        }
    }

    public static class MusicData implements Disposable {
        public String key;
        public float fadeTime = FADE;
        public float fadeTimer = 0.0F;
        public boolean isFadingOut = false;
        public boolean isFading = false;
        public boolean isDone = false;
        public boolean stop = true;
        public Music music;
        private float fadeOutStartVolume;

        public MusicData(String key, Music music) {
            this.music = music;
            this.key = key;
        }

        public void update() {
            if (music.isPlaying()) {
                if (!isFading) {
                    music.setVolume(mVol());
                } else {
                    if (!isFadingOut) {
                        updateFadeIn();
                    } else {
                        updateFadeOut();
                    }
                }
            } else isDone = true;
        }

        public void setFadeTime(float time) {
            fadeTime = fadeTimer = time;
        }

        public void updateFadeIn() {
            fadeTimer -= WakTower.tick * 2;
            if (fadeTimer <= 0.0F) {
                fadeTimer = 0.0F;
                isFading = false;
            } else {
                setVolume(Interpolation.fade.apply(0.0F, fadeOutStartVolume, 1.0F - fadeTimer / fadeTime));
            }
        }

        public void updateFadeOut() {
            fadeTimer -= WakTower.tick * 2;
            if (fadeTimer <= 0.0F) {
                fadeTimer = 0.0F;
                isDone = true;
                isFading = false;
                if (stop) music.stop();
                else music.pause();
            } else {
                setVolume(Interpolation.fade.apply(fadeOutStartVolume, 0.0F, 1.0F - fadeTimer / fadeTime));
            }
        }

        public void setVolume(float volume) {
            if (volume < 0f || volume > 1f) {
                // Gdx.app.error("SoundHandler", "Volume not valid: " + volume);
                volume = Math.max(0, Math.min(volume, 1));
            }
            music.setVolume(volume);
        }

        @Override
        public void dispose() {
            music.dispose();
        }
    }
}
