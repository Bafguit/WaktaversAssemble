package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;

public class DrawCardAction extends AbstractAction {

    private AbstractMember member;

    public DrawCardAction(int amount) {
        super(0.5f);
        this.amount = amount;
    }

    public DrawCardAction(AbstractMember m) {
        super(0.5f);
        member = m;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(member != null) amount = member.tempClone.calculateValue();
            WakTower.game.battle.draw(amount);
        }
    }
}
