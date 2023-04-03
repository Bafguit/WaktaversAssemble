package com.fastcat.assemble.actions;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.effects.AttackEffect;
import com.fastcat.assemble.handlers.EffectHandler;

public class IncreasedAttackAction extends AbstractAction {

    private final float percent;
    private final AbstractEntity.DamageType type;

    public IncreasedAttackAction(Array<AbstractEntity> targets, AbstractEntity source, int percent, AbstractEntity.DamageType type, boolean fast) {
        super(fast ? 0.25f : 0.5f);
        target = targets;
        this.source = source;
        this.percent = percent / 100.0f;
        this.type = type;
    }

    public IncreasedAttackAction(AbstractEntity target, AbstractEntity source, int percent, AbstractEntity.DamageType type, boolean fast) {
        super(fast ? 0.25f : 0.5f);
        this.target.add(target);
        this.source = source;
        this.percent = percent / 100.0f;
        this.type = type;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(source.isAlive()) {
                AbstractEntity.DamageInfo info = new AbstractEntity.DamageInfo((int) (source.calculatedAttack() + source.baseAttack * percent), type);
                EffectHandler.add(new AttackEffect(source, target, info));
            }
        }
    }
}
