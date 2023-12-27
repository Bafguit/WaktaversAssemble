package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;

public class TurnStartEffectAction extends AbstractAction {

    private final boolean isPlayer;

    public TurnStartEffectAction(boolean isPlayer) {
        super(1f);
        this.isPlayer = isPlayer;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            //todo 이펙트 실행
            for(AbstractMember member : WakTower.game.battle.members) {
                if(!member.hasSynergy("Cat")) member.animation.setAnimation("exit");
            }
        }
        
        if(isDone) {
            WakTower.game.battle.clearMember();
        }
    }
}
