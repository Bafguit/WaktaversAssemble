package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.abstracts.AbstractStatus;

public class GainBlockAction extends AbstractAction {

    public AbstractMember from;

    public GainBlockAction(AbstractEntity target, int blockAmount) {
        this(target, blockAmount, false);
    }

    public GainBlockAction(AbstractEntity target, int blockAmount, boolean isFast) {
        super(target, isFast ? 0.15f : 0.5f);
        amount = blockAmount;
    }

    public GainBlockAction(AbstractEntity target, AbstractMember member) {
        this(target, member, false);
    }

    public GainBlockAction(AbstractEntity target, AbstractMember member, boolean isFast) {
        super(target, isFast ? 0.15f : 0.5f);
        from = member;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(target.size > 0) {
                int cnt = 0;
                if(from != null) amount = from.calculatedDef();
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
