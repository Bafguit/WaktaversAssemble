package com.fastcat.assemble.actions;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MouseAdventure;
import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.abstrcts.AbstractDice;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.screens.battle.DiceButton;

public class PercentAttackAction extends AbstractAction {

    private final float percent;
    private final AbstractEntity.DamageType type;

    public PercentAttackAction(Array<AbstractEntity> targets, AbstractEntity source, int percent, AbstractEntity.DamageType type, boolean fast) {
        super(0.5f);
        target = targets;
        this.source = source;
        this.percent = percent / 100.0f;
        this.type = type;
    }

    public PercentAttackAction(AbstractEntity target, AbstractEntity source, int percent, AbstractEntity.DamageType type, boolean fast) {
        super(0.5f);
        this.target.add(target);
        this.source = source;
        this.percent = percent / 100.0f;
        this.type = type;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(source.isAlive()) {
                AbstractEntity.DamageInfo info = new AbstractEntity.DamageInfo((int) (source.calculatedAttack() * percent), type);

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
