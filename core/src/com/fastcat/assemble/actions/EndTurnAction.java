package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractBattle.BattlePhase;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.handlers.ActionHandler;

public class EndTurnAction extends AbstractAction {
    
    private final boolean isPlayer;

    public EndTurnAction(boolean isPlayer) {
        super(0f);
        this.isPlayer = isPlayer;
        WakTower.game.battle.phase = BattlePhase.INTERMISSION;
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            if(isPlayer) WakTower.application.battleStage.discardAll();

            for(AbstractSynergy s : WakTower.game.battle.synergy) {
                s.endOfTurn(isPlayer);
            }
            for(AbstractRelic relic : WakTower.game.relics) {
                relic.endOfTurn(isPlayer);
            }
            for(AbstractMember member : WakTower.game.battle.members) {
                member.endOfTurn(isPlayer);
                for(AbstractStatus s : member.status) {
                    s.endOfTurn(isPlayer);
                }
            }
            for(AbstractEnemy e : WakTower.game.battle.enemies) {
                for(AbstractStatus s : e.status) {
                    s.endOfTurn(isPlayer);
                }
            }

            ActionHandler.bot(new TurnChangeEffectAction(!isPlayer));
            ActionHandler.bot(new StartTurnAction(!isPlayer));
        }
    }
}
