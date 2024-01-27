package com.fastcat.assemble.scene2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.interfaces.OnAnimationFinished;

import java.util.HashMap;

import org.checkerframework.common.subtyping.qual.Bottom;

public class SpriteAnimation extends Table {

    private final HashMap<String, SpriteAnimationData> animations = new HashMap<>();
    private final Array<OnAnimationFinished> animationFinishedListeners = new Array<>();
    private OnAnimationFinished singlAnimationTimer;
    private float singleTimer;
    private boolean singleTimerEnded;

    private String id;
    private SpriteAnimationData current;
    private Queue<SpriteAnimationData> next = new Queue<>();
    private float timer = 0f, alpha = 1f, scale = 1.0f;
    private boolean isRunning = true;
    private SpriteAnimationType type;

    private Image hbar;

    public Vector2 pos = new Vector2(), size = new Vector2();

    public SpriteAnimation(String id, SpriteAnimationType type) {
        this.id = id;
        this.type = type;
        generateAnimationData();
        setDefault();
        setZIndex(10);
    }

    public SpriteAnimation(String id) {
        this.id = id;
        generateUIAnimationData();
        setDefault();
    }

    private SpriteAnimation() {
        setDefault();
    }

    private final void setDefault() {
        setOrigin(Align.bottom);
        hbar = new Image();
        hbar.setSize(256, 256);
        hbar.setOrigin(Align.bottom);
        addActor(hbar);
        //add(hbar).center().bottom().pad(10);
        //add(render).bottom();
        //row();
        //추가
    }

    public void setAnimation(String key) {
        isRunning = true;
        timer = 0f;
        current = animations.get(key);
    }

    private void generateAnimationData() {
        JsonValue json = FileHandler.getInstance().jsonMap.get("animation/" + type.name() + "/"  + id);
        for(JsonValue v : json) {
            TextureAtlas atlas = FileHandler.getInstance().assetManager.get("animation/" + type + "/" + id + "/" + v.name + ".atlas");
            animations.put(v.name, new SpriteAnimationData(v.name, atlas, v.getFloat("frameDuration"), v.getBoolean("isLoop"), new Vector2(v.getInt("axisX", 0), v.getInt("axisY", 0))));
        }
    }

    private void generateUIAnimationData() {
        JsonValue json = FileHandler.getInstance().jsonMap.get("animation_ui_"  + id);
        for(JsonValue v : json) {
            TextureAtlas atlas = FileHandler.getInstance().assetManager.get("animation/ui/" + id + "/" + v.name + ".atlas");
            animations.put(v.name, new SpriteAnimationData(v.name, atlas, v.getFloat("frameDuration"), v.getBoolean("isLoop"), new Vector2(v.getInt("axisX", 0), v.getInt("axisY", 0))));
        }
    }

    public String getID() {
        return id;
    }

    public SpriteAnimationData getCurrent() {
        return current;
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

    @Override
    public void act(float delta) {
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
            Drawable frame = current.getFrame(timer);
            //hbar.setBackground(frame);
            hbar.setDrawable(frame);
            hbar.setSize(frame.getMinWidth(), frame.getMinHeight());
            hbar.setScale(scale);
            hbar.setPosition(getOriginX(), getOriginY(), Align.bottom);
            if(isRunning) tickDuration(delta);
            //throw new RuntimeException(frame.getX() + ", " + frame.getY() + ", " + frame.getScaleX());
        }
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void setScale(float s) {
        scale = s;
    }

    private void tickDuration(float delta) {
        timer += delta * current.timescale;
        if(timer >= current.duration) {
            timer -= current.duration;
        }
    }

    public SpriteAnimation cpy() {
        SpriteAnimation a = new SpriteAnimation();
        a.id = id;
        a.type = type;
        for(SpriteAnimationData data : animations.values()) {
            a.animations.put(data.key, data.cpy());
        }
        return a;
    }

    public class SpriteAnimationData {

        private final TextureAtlas skin;
        private final SpriteDrawable[] frames;
        private float duration, frameDuration, timescale = 1.0f;
        private boolean isLoop;
        private Vector2 axis;

        public final String key;

        public SpriteAnimationData(String key, TextureAtlas sprites, float duration, boolean isLoop, Vector2 axis) {
            this.key = key;
            this.skin = sprites;
            //Array<Drawable> ds = new Array<>();
            int size = sprites.getRegions().size;
            frames = new SpriteDrawable[size];
            for(int i = 0; i < size; i++) {
                Sprite s = sprites.createSprite(Integer.toString(i));
                SpriteDrawable trd = new SpriteDrawable(s);
                frames[i] = trd;
                //ds.add(trd);
            }
            //frames = ds.toArray(SpriteDrawable.class);
            frameDuration = duration;
            this.duration = frameDuration * frames.length;
            this.isLoop = isLoop;
            this.axis = new Vector2(axis);
        }

        public Drawable getFrame(float time) {
            return getFrame((int) (time / frameDuration));
        }

        public Drawable getFrame(int number) {
            return frames[number % frames.length];
        }

        public SpriteAnimationData cpy() {
            return new SpriteAnimationData(key, skin, frameDuration, isLoop, new Vector2(axis));
        }
    }

    public enum SpriteAnimationType {
        member, entity
    }
}
