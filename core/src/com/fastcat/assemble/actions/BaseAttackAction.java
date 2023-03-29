package com.fastcat.assemble.actions;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.abstrcts.AbstractEntity;

public class BaseAttackAction extends AbstractAction {

    private final AbstractEntity.DamageType type;

    public BaseAttackAction(Array<AbstractEntity> targets, AbstractEntity source, AbstractEntity.DamageType type, boolean fast) {
        super(fast ? 0.25f : 0.5f);
        target = targets;
        this.source = source;
        this.type = type;
    }

    public BaseAttackAction(AbstractEntity target, AbstractEntity source, AbstractEntity.DamageType type, boolean fast) {
        super(fast ? 0.25f : 0.5f);
        this.target.add(target);
        this.source = source;
        this.type = type;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(source.isAlive()) {
                AbstractEntity.DamageInfo info = new AbstractEntity.DamageInfo(source.calculatedAttack(), type);

                if(target.size > 0) {
                    for(AbstractEntity t : target) {
                        if(t.isAlive()) {
                            t.takeDamage(info);
                        }
                    }
                }
            }
        }
    }
}
