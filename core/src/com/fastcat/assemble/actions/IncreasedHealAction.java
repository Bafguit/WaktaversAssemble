package com.fastcat.assemble.actions;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.abstrcts.AbstractEntity;

public class IncreasedHealAction extends AbstractAction {

    private final float percent;

    public IncreasedHealAction(Array<AbstractEntity> targets, AbstractEntity source, int percent, boolean fast) {
        super(fast ? 0.25f : 0.5f);
        target = targets;
        this.source = source;
        this.percent = percent / 100.0f;
    }

    public IncreasedHealAction(AbstractEntity target, AbstractEntity source, int percent, boolean fast) {
        super(fast ? 0.25f : 0.5f);
        this.target.add(target);
        this.source = source;
        this.percent = percent / 100.0f;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(source.isAlive()) {
                int amount = (int) (source.calculatedAttack() + source.baseAttack * percent);

                if(target.size > 0) {
                    for(AbstractEntity t : target) {
                        if(t.isAlive()) {
                            t.heal(amount);
                        }
                    }
                }
            }
        }
    }
}
