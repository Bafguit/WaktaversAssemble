package com.fastcat.assemble.actions;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.effects.AttackEffect;
import com.fastcat.assemble.effects.AttackSeveralEffect;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.EffectHandler;

public class BaseAttackAction extends AbstractAction {

    private final AbstractEntity.DamageType type;
    private final int repeat;

    public BaseAttackAction(Array<AbstractEntity> targets, AbstractEntity source, AbstractEntity.DamageType type, int repeat, boolean fast) {
        super(fast ? 0.25f : 0.5f);
        target = targets;
        this.source = source;
        this.type = type;
        this.repeat = repeat;
    }

    public BaseAttackAction(AbstractEntity target, AbstractEntity source, AbstractEntity.DamageType type, int repeat, boolean fast) {
        super(fast ? 0.25f : 0.5f);
        this.target.add(target);
        this.source = source;
        this.type = type;
        this.repeat = repeat;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(source.isAlive()) {
                AbstractEntity.DamageInfo info = new AbstractEntity.DamageInfo(source.calculatedAttack(), type);
                ActionHandler.add(new AttackSeveralEffect(source, target, repeat, info));
            }
        }
    }
}
