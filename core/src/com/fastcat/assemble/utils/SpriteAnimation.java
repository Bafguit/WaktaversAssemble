package com.fastcat.assemble.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.WakTower;

import java.util.HashMap;

public class SpriteAnimation {

    private final HashMap<String, SpriteAnimationData> animations = new HashMap<>();

    private SpriteAnimationData current;
    private Queue<SpriteAnimationData> next = new Queue<>();
    private Color color = Color.WHITE.cpy();
    private float timer = 0f;
    private boolean isRunning = true;

    public Vector2i pos, size;

    public SpriteAnimation() {

    }

    public void setAnimation(String key) {
        isRunning = true;
        timer = 0f;
        current = animations.get(key);
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

    //todo spriteAnimation copy
    public SpriteAnimation cpy() {
        return new SpriteAnimation();
    }

    public class SpriteAnimationData {

        private final Sprite[] frames;
        private float duration, frameDuration, timescale = 1.0f;
        private boolean isLoop;
        private Vector2i axis;

        public final String key;

        //todo json 불러오는걸로 교체 필요
        public SpriteAnimationData(String key, Array<Sprite> sprites, float duration) {
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

            return new SpriteAnimationData(key, temp, frameDuration);
        }
    }
}
