package com.fastcat.assemble.actions.member;

import java.util.LinkedList;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.ApplyStatusAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.members.Roentgenium;
import com.fastcat.assemble.status.IncreaseDamageStatus;

public class MemberRoentgeniumAction extends AbstractAction {

    public Roentgenium roent;

    public MemberRoentgeniumAction(Roentgenium roent) {
        super(0.2f);
        this.roent = roent;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            LinkedList<AbstractMember> ms = WakTower.game.battle.members;
            for(int i = 0; i < ms.size(); i++) {
                AbstractMember m = ms.get(i);
                if(m == roent && i + 1 < ms.size()) {
                    AbstractMember next = ms.get(i + 1);
                    ActionHandler.set(new ApplyStatusAction(next, new IncreaseDamageStatus(roent.tempClone.calculateValue(), false), true));
                    break;
                }
            }
        }
    }
}
