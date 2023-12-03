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

import java.util.HashMap;

public class SpriteAnimation {

    private final HashMap<String, SpriteAnimationData> animations = new HashMap<>();

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

    private SpriteAnimation(String id) {
        this.id = id;
    }

    public void setAnimation(String key) {
        isRunning = true;
        timer = 0f;
        current = animations.get(key);
    }

    private final void generateAnimationData() {
        JsonValue json = FileHandler.getInstance().jsonMap.get("animation_" + id);
        for(JsonValue v : json.child) {
            Array<Sprite> frames = new Array<>();
            for(int i = 0; i < v.getInt("frameCount"); i++) {
                FileHandler.getInstance().assetManager.get("animation/" + type + "/" + id + "/" + v.name + "/" + i + ".webp");
            }
            animations.put(v.name, new SpriteAnimationData(v.name, frames, v.getFloat("frameDuration"), new Vector2i(v.getInt("axisX", 0), v.getInt("axisY", 0))));
        }
    }

    public void addAnimation(String key) {
        next.addLast(animations.get(key));
    }

    public void render(SpriteBatch sb) {
        boolean hasAnimation = true;

        if(current != null) {
            if(!current.isLoop && timer > current.duration) {
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
        private Vector2i axis;

        public final String key;

        public SpriteAnimationData(String key, Array<Sprite> sprites, float duration, Vector2i axis) {
            this.key = key;
            frames = sprites.items;
            frameDuration = duration;
            this.duration = frameDuration * frames.length;
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
            return new SpriteAnimationData(key, temp, frameDuration, new Vector2i(axis));
        }
    }

    public enum SpriteAnimationType {
        member, entity
    }
}
