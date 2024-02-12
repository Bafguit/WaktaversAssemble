package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;

public class ExitMembersAction extends AbstractAction {

    public ExitMembersAction() {
        super(1f);
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            for(AbstractMember member : WakTower.game.battle.members) {
                member.block = 0;
                if(!member.hasSynergy("Cat")) {
                    member.animation.setAnimation("exit");
                    member.onExit();
                }
            }
        }
        
        if(isDone) {
            WakTower.game.battle.clearMember();
            WakTower.application.battleStage.updateMemberPosition();
        }
    }
}
