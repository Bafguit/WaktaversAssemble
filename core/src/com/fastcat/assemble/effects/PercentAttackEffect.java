package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstracts.AbstractEffect;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.handlers.ActionHandler;

public class PercentAttackEffect extends AbstractEffect {

    public AbstractEntity source;
    public Array<AbstractEntity> target = new Array<>();
    private final float percent;
    private final AbstractEntity.DamageType type;
    private final int repeat;

    public PercentAttackEffect(Array<AbstractEntity> targets, AbstractEntity source, int percent, AbstractEntity.DamageType type, int repeat, boolean fast) {
        super(fast ? 0.25f : 0.5f);
        target = targets;
        this.source = source;
        this.percent = percent / 100.0f;
        this.type = type;
        this.repeat = repeat;
    }

    public PercentAttackEffect(AbstractEntity target, AbstractEntity source, int percent, AbstractEntity.DamageType type, int repeat, boolean fast) {
        super(fast ? 0.25f : 0.5f);
        this.target.add(target);
        this.source = source;
        this.percent = percent / 100.0f;
        this.type = type;
        this.repeat = repeat;
    }

    @Override
    protected void renderEffect(SpriteBatch sb) {
        if(duration == baseDuration) {
            if(source.isAlive()) {
                AbstractEntity.DamageInfo info = new AbstractEntity.DamageInfo((int) (source.calculatedAttack() * percent), type);
                ActionHandler.add(new AttackSeveralEffect(source, target, repeat, info));
            }
        }
    }
}
