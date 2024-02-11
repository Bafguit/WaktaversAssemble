package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.utils.TargetType;

public class GainBlockAction extends AbstractAction {

    public GainBlockAction(TargetType target, int blockAmount) {
        this(target, blockAmount, false);
    }

    public GainBlockAction(TargetType target, int blockAmount, boolean isFast) {
        super(target, isFast ? 0.15f : 0.5f);
        amount = blockAmount;
    }

    public GainBlockAction(TargetType target, AbstractEntity source, int amount) {
        this(target, source, amount, false);
    }

    public GainBlockAction(TargetType target, AbstractEntity source, int amount, boolean isFast) {
        super(target, isFast ? 0.15f : 0.5f);
        this.source = source;
        this.amount = amount;
    }

    public GainBlockAction(TargetType target, AbstractMember source) {
        this(target, source, false);
    }

    public GainBlockAction(TargetType target, AbstractMember source, boolean isFast) {
        super(target, isFast ? 0.15f : 0.5f);
        this.source = source;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(target.size > 0) {
                int cnt = 0;
                if(source != null) {
                    if(source instanceof AbstractMember) amount = ((AbstractMember) source).calculatedDef();
                    else if(source instanceof AbstractEnemy) amount = ((AbstractEnemy) source).calculateDef(amount);
                }
                for(AbstractEntity e : target) {
                    int amt = amount;
                    if(e.isPlayer) {
                        for(AbstractRelic item : WakTower.game.relics) {
                            amt = item.onGainBlock(amt);
                        }
                        for(AbstractMember c : WakTower.game.battle.members) {
                            amt = c.onGainBlock(amt);
                        }
                    }
                    for(AbstractStatus s : e.status) {
                        amt = s.onGainBlock(amt);
                    }
                    if(amt > 0) {
                        cnt++;
                        //todo 이펙트
                        e.gainBlock(amt);
                    }
                }
                if(cnt == 0) isDone = true;
            } else isDone = true;
        }
    }
}
