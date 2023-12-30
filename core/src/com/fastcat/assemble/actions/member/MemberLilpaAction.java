package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.members.Lilpa;

public class MemberLilpaAction extends AbstractAction {

    public Lilpa lilpa;

    public MemberLilpaAction(Lilpa lilpa) {
        super(1f);
        this.lilpa = lilpa;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            lilpa.animation.setAnimation("skill");
            lilpa.animation.addAnimation("idle");
            int cnt = 0;
            for(AbstractMember m : WakTower.game.battle.members) {
                if(m == lilpa) break;
                else cnt++;
            }
            //todo attack
        }
    }
}
