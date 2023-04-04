package com.fastcat.assemble.actions;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.effects.AttackHealEffect;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.EffectHandler;

public class PercentAttackAndHealAction extends AbstractAction {

    private final int heal;
    private final float percent;
    private final AbstractEntity.DamageType type;
    private final int repeat;

    public PercentAttackAndHealAction(Array<AbstractEntity> targets, AbstractEntity source, int percent, AbstractEntity.DamageType type, int repeat, int heal, boolean fast) {
        super(fast ? 0.1f : 0.5f);
        target = targets;
        this.source = source;
        this.percent = percent / 100.0f;
        this.type = type;
        this.heal = heal;
        this.repeat = repeat;
    }

    public PercentAttackAndHealAction(AbstractEntity target, AbstractEntity source, int percent, AbstractEntity.DamageType type, int repeat, int heal, boolean fast) {
        super(fast ? 0.1f : 0.5f);
        this.target.add(target);
        this.source = source;
        this.percent = percent / 100.0f;
        this.type = type;
        this.heal = heal;
        this.repeat = repeat;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(source != null && source.isAlive()) {
                AbstractEntity.DamageInfo info = new AbstractEntity.DamageInfo((int) (source.calculatedAttack() * percent), type);
                ActionHandler.add(new AttackHealEffect(source, target, repeat, heal, info));
            }
        }
    }
}
