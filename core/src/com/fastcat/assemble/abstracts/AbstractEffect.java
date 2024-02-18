package com.fastcat.assemble.abstracts;

import com.fastcat.assemble.abstracts.AbstractUI.UIData;

public abstract class AbstractEffect {

    protected UIData data;

    public float baseDuration;
    public float duration;
    public float x, y;
    public boolean isDone;

    public AbstractEffect(float x, float y, float duration) {
        this.duration = duration;
        this.baseDuration = this.duration;
    }

    public AbstractEffect(float duration) {
        this(-10000, -10000, duration);
    }

    public abstract void run();
}
