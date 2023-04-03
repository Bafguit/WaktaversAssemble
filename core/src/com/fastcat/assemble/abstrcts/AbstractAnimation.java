package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.AnimationState;

public abstract class AbstractAnimation {
        public abstract void render(SpriteBatch sb, float x, float y);
        public abstract void setAndIdle(String key);
        public abstract void setAndIdle(String key, AnimationState.AnimationStateAdapter adapter);
        public abstract void resetAnimation();
        public abstract void setSkin(String key);
        public abstract void setDie();
}