package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.abstrcts.AbstractEffect;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.handlers.EffectHandler;

public class PercentAttackEffect extends AbstractEffect {

    public AbstractEntity source;
    public Array<AbstractEntity> target = new Array<>();
    private final float percent;
    private final AbstractEntity.DamageType type;

    public PercentAttackEffect(Array<AbstractEntity> targets, AbstractEntity source, int percent, AbstractEntity.DamageType type, boolean fast) {
        super(fast ? 0.25f : 0.5f);
        target = targets;
        this.source = source;
        this.percent = percent / 100.0f;
        this.type = type;
    }

    public PercentAttackEffect(AbstractEntity target, AbstractEntity source, int percent, AbstractEntity.DamageType type, boolean fast) {
        super(fast ? 0.25f : 0.5f);
        this.target.add(target);
        this.source = source;
        this.percent = percent / 100.0f;
        this.type = type;
    }

    @Override
    protected void renderEffect(SpriteBatch sb) {
        if(duration == baseDuration) {
            if(source.isAlive()) {
                AbstractEntity.DamageInfo info = new AbstractEntity.DamageInfo((int) (source.calculatedAttack() * percent), type);
                EffectHandler.add(new AttackEffect(source, target, info));
            }
        }
    }
}
