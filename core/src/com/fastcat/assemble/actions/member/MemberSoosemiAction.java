package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.members.Soosemi;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class MemberSoosemiAction extends AbstractAction {

    public Soosemi soosemi;

    public MemberSoosemiAction(Soosemi soosemi) {
        super(0f);
        this.soosemi = soosemi;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            soosemi.animation.setAnimation("skill");
            soosemi.animation.addAnimation("idle");
            int cnt = 0;
            for(AbstractMember m : WakTower.game.battle.members) {
                if(m == soosemi) break;
                else cnt++;
            }
            int damage = soosemi.tempClone.calculatedAtk() + soosemi.tempClone.calculateValue() * cnt;
            ActionHandler.set(new DamageAction(new DamageInfo(damage, soosemi, DamageType.NORMAL), TargetType.RANDOM_ENEMY));
        }
    }
}
