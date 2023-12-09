package com.fastcat.assemble.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.interfaces.OnAnimationFinished;

import java.util.HashMap;

public class SpriteAnimation {

    private final HashMap<String, SpriteAnimationData> animations = new HashMap<>();
    private final Array<OnAnimationFinished> animationFinishedListeners = new Array<>();
    private OnAnimationFinished singlAnimationTimer;
    private float singleTimer;
    private boolean singleTimerEnded;

    private final String id;
    private SpriteAnimationData current;
    private Queue<SpriteAnimationData> next = new Queue<>();
    private Color color = Color.WHITE.cpy();
    private float timer = 0f;
    private boolean isRunning = true;
    private SpriteAnimationType type;

    public Vector2 pos, size;

    public SpriteAnimation(String id, SpriteAnimationType type) {
        this.id = id;
        this.type = type;
        generateAnimationData();
    }

    public SpriteAnimation(String id) {
        this.id = id;
        generateUIAnimationData();
    }

    public void setAnimation(String key) {
        isRunning = true;
        timer = 0f;
        current = animations.get(key);
    }

    private void generateAnimationData() {
        JsonValue json = FileHandler.getInstance().jsonMap.get("animation_" + type.name() + "_"  + id);
        for(JsonValue v : json) {
            Array<Sprite> frames = new Array<>();
            for(int i = 0; i < v.getInt("frameCount"); i++) {
                FileHandler.getInstance().assetManager.get("animation/" + type + "/" + id + "/" + v.name + "/" + i + ".webp");
            }
            animations.put(v.name, new SpriteAnimationData(v.name, frames, v.getFloat("frameDuration"), v.getBoolean("isLoop"), new Vector2(v.getInt("axisX", 0), v.getInt("axisY", 0))));
        }
    }

    private void generateUIAnimationData() {
        JsonValue json = FileHandler.getInstance().jsonMap.get("animation_ui_"  + id);
        for(JsonValue v : json) {
            Array<Sprite> frames = new Array<>();
            for(int i = 0; i < v.getInt("frameCount"); i++) {
                FileHandler.getInstance().assetManager.get("animation/ui/" + id + "/" + v.name + "/" + i + ".webp");
            }
            animations.put(v.name, new SpriteAnimationData(v.name, frames, v.getFloat("frameDuration"), v.getBoolean("isLoop"), new Vector2(v.getInt("axisX", 0), v.getInt("axisY", 0))));
        }
    }

    public void addAnimation(String key) {
        next.addLast(animations.get(key));
    }

    public void addAnimationFinishedListener(OnAnimationFinished listener) {
        animationFinishedListeners.add(listener);
    }

    public void setSingleAnimationListener(OnAnimationFinished listener, float time) {
        singlAnimationTimer = listener;
        singleTimer = time;
        singleTimerEnded = false;
    }

    public void render(SpriteBatch sb) {
        boolean hasAnimation = true;

        if(current != null) {
            if(singlAnimationTimer != null && !singleTimerEnded && timer >= singleTimer) {
                singlAnimationTimer.onSingleFinished(current.key);
                singleTimerEnded = true;
                singlAnimationTimer = null;
            }
            if(!current.isLoop && timer > current.duration) {
                for(OnAnimationFinished l : animationFinishedListeners) {
                    l.onAnimationFinished(current.key);
                }
                animationFinishedListeners.clear();
                if(next.size > 0) {
                    timer = 0f;
                    current = next.removeFirst();
                } else {
                    timer = current.duration - current.frameDuration;
                    isRunning = false;
                }
            }
        } else {
            if(next.size > 0) {
                timer = 0f;
                current = next.removeFirst();
            } else {
                hasAnimation = false;
            }
        }

        if(hasAnimation) {
            Sprite frame = current.getFrame(timer);
            sb.draw(frame, pos.x, pos.y);
            if(isRunning) tickDuration();
        }
    }

    private void tickDuration() {
        timer += WakTower.tick;
        if(timer > current.duration) {
            timer -= current.duration;
        }
    }

    public SpriteAnimation cpy() {
        SpriteAnimation a = new SpriteAnimation(id);
        a.type = type;
        for(SpriteAnimationData data : animations.values()) {
            a.animations.put(data.key, data.cpy());
        }
        return a;
    }

    public class SpriteAnimationData {

        private final Sprite[] frames;
        private float duration, frameDuration, timescale = 1.0f;
        private boolean isLoop;
        private Vector2 axis;

        public final String key;

        public SpriteAnimationData(String key, Array<Sprite> sprites, float duration, boolean isLoop, Vector2 axis) {
            this.key = key;
            frames = sprites.toArray(Sprite.class);
            frameDuration = duration;
            this.duration = frameDuration * frames.length;
            this.isLoop = isLoop;
            this.axis = new Vector2(axis);
        }

        public Sprite getFrame(float time) {
            return getFrame((int) (time / frameDuration));
        }

        public Sprite getFrame(int number) {
            return frames[number % frames.length];
        }

        public SpriteAnimationData cpy() {
            Array<Sprite> temp = new Array<>();
            for(Sprite s : frames) {
                temp.add(new Sprite(s));
            }
            return new SpriteAnimationData(key, temp, frameDuration, isLoop, new Vector2(axis));
        }
    }

    public enum SpriteAnimationType {
        member, entity
    }
}
