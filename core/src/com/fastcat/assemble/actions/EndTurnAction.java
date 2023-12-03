package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.abstracts.AbstractSynergy;

public class EndTurnAction extends AbstractAction {
    
    private final boolean isPlayer;

    public EndTurnAction(boolean isPlayer) {
        super(0f);
        this.isPlayer = isPlayer;
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            for(AbstractSynergy s : WakTower.game.battle.synergy) {
                s.endOfTurn(isPlayer);
            }
            for(AbstractRelic relic : WakTower.game.relics) {
                relic.endOfTurn(isPlayer);
            }
            for(AbstractMember member : WakTower.game.battle.members) {
                member.endOfTurn(isPlayer);
            }
            for(AbstractStatus status : WakTower.game.player.status) {
                status.endOfTurn(isPlayer);
            }
        }
    }
}
