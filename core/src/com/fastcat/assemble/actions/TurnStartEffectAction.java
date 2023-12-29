package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.effects.TurnChangeEffect;
import com.fastcat.assemble.handlers.EffectHandler;

public class TurnStartEffectAction extends AbstractAction {

    private final boolean isPlayer;
    private boolean done;

    public TurnStartEffectAction(boolean isPlayer) {
        super(2.5f);
        this.isPlayer = isPlayer;
        done = false;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            EffectHandler.add(new TurnChangeEffect(isPlayer));
        } else if(duration <= 1 && !done) {
            for(AbstractMember member : WakTower.game.battle.members) {
                if(!member.hasSynergy("Cat")) member.animation.setAnimation("exit");
            }
            done = true;
        }
        
        if(isDone) {
            WakTower.game.battle.clearMember();
        }
    }
}
