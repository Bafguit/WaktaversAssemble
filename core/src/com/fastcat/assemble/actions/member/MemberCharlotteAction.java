package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.members.Charlotte;
import com.fastcat.assemble.utils.TargetType;

public class MemberCharlotteAction extends AbstractAction {

    public Charlotte charlotte;

    public MemberCharlotteAction(Charlotte charlotte) {
        super(0f);
        this.charlotte = charlotte;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            charlotte.animation.setAnimation("skill");
            charlotte.animation.addAnimation("idle");
            int cnt = 0;
            for(AbstractEnemy m : WakTower.game.battle.enemies) {
                if(m.isAlive()) cnt++;
            }
            for(int i = 0; i < cnt; i++) {
                ActionHandler.set(new GainBlockAction(TargetType.SELF, charlotte, true));
            }
        }
    }
}
