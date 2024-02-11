package com.fastcat.assemble.actions.member;

import java.util.LinkedList;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.members.Yungter;

public class MemberYungterAction extends AbstractAction {

    public Yungter member;

    public MemberYungterAction(Yungter member) {
        super(0.5f);
        this.member = member;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            member.animation.setAnimation("skill");
            member.animation.addAnimation("idle");
            LinkedList<AbstractMember> ms = WakTower.game.battle.members;
            for(int i = 0; i < ms.size(); i++) {
                AbstractMember m = ms.get(i);
                if(m == member && i + 1 < ms.size()) {
                    AbstractMember next = ms.get(i + 1);
                    next.tempClone.effect += (float) member.tempClone.calculateValue() / 100f;
                    break;
                }
            }
        }
    }
}
