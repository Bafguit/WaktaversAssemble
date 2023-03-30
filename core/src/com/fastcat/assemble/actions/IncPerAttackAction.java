package com.fastcat.assemble.actions;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.abstrcts.AbstractEntity;

public class IncPerAttackAction extends AbstractAction {

    private final float increase;
    private final float percent;
    private final AbstractEntity.DamageType type;

    public IncPerAttackAction(Array<AbstractEntity> targets, AbstractEntity source, int increase, int percent, AbstractEntity.DamageType type, boolean fast) {
        super(fast ? 0.25f : 0.5f);
        target = targets;
        this.source = source;
        this.increase = increase;
        this.percent = percent / 100.0f;
        this.type = type;
    }

    public IncPerAttackAction(AbstractEntity target, AbstractEntity source, int increase, int percent, AbstractEntity.DamageType type, boolean fast) {
        super(fast ? 0.25f : 0.5f);
        this.target.add(target);
        this.source = source;
        this.increase = increase;
        this.percent = percent / 100.0f;
        this.type = type;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(source.isAlive()) {
                AbstractEntity.DamageInfo info = new AbstractEntity.DamageInfo((int) ((source.calculatedAttack() + source.baseAttack * increase) * percent), type);

                source.attack(target, info);
            }
        }
    }
}