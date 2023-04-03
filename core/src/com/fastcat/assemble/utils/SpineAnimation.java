package com.fastcat.assemble.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.*;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractAnimation;
import com.fastcat.assemble.handlers.InputHandler;

public class SpineAnimation {
        public TextureAtlas atlas;
        public Skeleton skeleton;
        public AnimationState state;
        public AnimationStateData stateData;

        private final float initialScale;

        public SpineAnimation(TextureAtlas atlas, FileHandle skel) {
            this.atlas = atlas;
            SkeletonBinary json = new SkeletonBinary(atlas);
            SkeletonData skeletonData = json.readSkeletonData(skel);
            skeleton = new Skeleton(skeletonData);
            skeleton.setColor(Color.WHITE);
            skeleton.setPosition(-10000, -10000);
            skeleton.setScale(0.3f * InputHandler.scaleA, 0.3f * InputHandler.scaleA);
            stateData = new AnimationStateData(skeletonData);
            state = new AnimationState(stateData);
            initialScale = InputHandler.scaleA;
            resetAnimation();
        }

        public void render(SpriteBatch sb, float x, float y, boolean flip) {
            state.update(MousseAdventure.tick);
            state.apply(skeleton);
            state.getCurrent(0).setTimeScale(1.0f);
            float sx = Math.abs(skeleton.getScaleX());
            skeleton.setScaleX(flip ? -sx : sx);
            skeleton.setPosition(x * initialScale, y * initialScale);
            skeleton.updateWorldTransform();
            skeleton.setColor(sb.getColor());
            sb.end();
            MousseAdventure.psb.begin();
            MousseAdventure.sr.draw(MousseAdventure.psb, skeleton);
            MousseAdventure.psb.end();
            sb.begin();
        }

        public final void setAndIdle(String key) {
            AnimationState.TrackEntry e = state.setAnimation(0, key, false);
            state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(1.0f);
        }

    public final void setAndIdle(String key, AnimationState.AnimationStateAdapter adapter) {
        AnimationState.TrackEntry e = state.setAnimation(0, key, false);
        state.addListener(adapter);
        state.addAnimation(0, "Idle", true, 0.0F);
        e.setTimeScale(1.0f);
    }

    public final void set(String key, boolean loop) {
        AnimationState.TrackEntry e = state.setAnimation(0, key, loop);
        e.setTimeScale(1.0f);
    }

        public final void resetAnimation() {
            AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
            e.setTimeScale(1.0f);
        }

        public void setSkin(String key) {
            skeleton.setSkin(key);
        }

    public void setDie() {
        AnimationState.TrackEntry e = state.setAnimation(0, "Die", false);
        e.setTimeScale(1.0f);
    }
}