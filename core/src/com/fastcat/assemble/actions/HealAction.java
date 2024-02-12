package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.utils.TargetType;

public class HealAction extends AbstractAction {

    public HealAction(AbstractEntity target, int blockAmount) {
        this(target, blockAmount, false);
    }

    public HealAction(AbstractEntity target, int blockAmount, boolean isFast) {
        super(target, isFast ? 0.15f : 0.5f);
        amount = blockAmount;
    }

    public HealAction(TargetType target, int blockAmount) {
        this(target, blockAmount, false);
    }

    public HealAction(TargetType target, int blockAmount, boolean isFast) {
        super(target, isFast ? 0.15f : 0.5f);
        amount = blockAmount;
    }

    public HealAction(TargetType target, AbstractEntity source, int amount) {
        this(target, source, amount, false);
    }

    public HealAction(TargetType target, AbstractEntity source, int amount, boolean isFast) {
        super(target, isFast ? 0.15f : 0.5f);
        this.source = source;
        this.amount = amount;
    }

    public HealAction(TargetType target, AbstractMember source) {
        this(target, source, false);
    }

    public HealAction(TargetType target, AbstractMember source, boolean isFast) {
        super(target, isFast ? 0.15f : 0.5f);
        this.source = source;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(target.size > 0) {
                if(source != null) {
                    if(source instanceof AbstractMember) {
                        amount = ((AbstractMember) source).tempClone.calculateValue();
                        for(AbstractRelic item : WakTower.game.relics) {
                            amount = item.onHeal(amount);
                        }
                        for(AbstractMember c : WakTower.game.battle.members) {
                            amount = c.onHeal(amount);
                        }
                    }
                    for(AbstractStatus s : source.status) {
                        amount = s.onHeal(amount);
                    }
                }
                if(amount > 0) {
                    for(AbstractEntity e : target) {
                        e.heal(amount);
                    }
                }
            } else cancel();
        }
    }
}
