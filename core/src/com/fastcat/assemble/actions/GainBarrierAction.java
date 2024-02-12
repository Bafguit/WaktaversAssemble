package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.utils.TargetType;

public class GainBarrierAction extends AbstractAction {

    public GainBarrierAction(TargetType target, AbstractMember member) {
        this(target, member, false);
    }

    public GainBarrierAction(TargetType target, AbstractMember member, boolean isFast) {
        super(target, isFast ? 0.15f : 0.5f);
        source = member;
    }

    public GainBarrierAction(TargetType target, int blockAmount) {
        this(target, blockAmount, false);
    }

    public GainBarrierAction(TargetType target, int blockAmount, boolean isFast) {
        super(target, isFast ? 0.15f : 0.5f);
        amount = blockAmount;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(target.size > 0) {
                int cnt = 0;
                if(source != null && source instanceof AbstractMember) amount = ((AbstractMember)source).tempClone.calculatedDef();
                for(AbstractEntity e : target) {
                    int amt = amount;
                    if(e.isPlayer) {
                        for(AbstractRelic item : WakTower.game.relics) {
                            amt = item.onGainBarrier(amt);
                        }
                        for(AbstractMember c : WakTower.game.battle.members) {
                            amt = c.onGainBarrier(amt);
                        }
                    }
                    for(AbstractStatus s : e.status) {
                        amt = s.onGainBarrier(amt);
                    }
                    if(amt > 0) {
                        cnt++;
                        //todo 이펙트
                        e.gainBarrier(amt);
                    }
                }
                if(cnt == 0) isDone = true;
            } else isDone = true;
        }
    }
}
