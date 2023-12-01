package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEffect;

public class HitEffect extends AbstractEffect {

    private final AbstractEntity e;
    private float a = 0;

    public HitEffect(AbstractEntity e) {
        super(0.25f);
        this.e = e;
    }

    @Override
    protected void renderEffect(SpriteBatch sb) {
        if(a < 1) {
            a += WakTower.tick / baseDuration;
            if(a >= 1) a = 1;
            e.animColor.set(1, a, a, 1);
        }

        if(isDone) {
            e.animColor.set(e.baseColor);
        }
    }
}
