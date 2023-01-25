package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.fastcat.assemble.WaktaAssemble;
import com.fastcat.assemble.handlers.SettingHandler;

public abstract class AbstractEffect implements Disposable {

    public float baseDuration;
    public float duration;
    public float x, y;
    public boolean isDone;

    public AbstractEffect(float x, float y, float duration) {
        this.x = x;
        this.y = y;
        this.duration = duration;
        //this.duration = SettingHandler.setting.fastMode ? duration * 0.5f : duration;
        this.baseDuration = this.duration;
    }

    public AbstractEffect(float duration) {
        this(-10000, -10000, duration);
    }

    public final void render(SpriteBatch sb) {
        if (duration <= 0) {
            isDone = true;
            duration = 0;
        }
        renderEffect(sb);
        TickDuration();
    }

    protected void TickDuration() {
        if (duration > 0) {
            duration -= WaktaAssemble.tick;
        }
    }

    protected abstract void renderEffect(SpriteBatch sb);

    public void onRemove() {}

    public void dispose() {}
}
