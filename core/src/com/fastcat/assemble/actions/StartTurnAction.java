package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractChar;

public class StartTurnAction extends AbstractAction {
    
    private final isPlayer, isNew;

    public StartTurnAction(boolean isPlayer) {
        this(isPlayer, false);
    }

    public StartTurnAction(boolean isPlayer, boolean isNew) {
        super(0f);
        this.isPlayer = isPlayer;
        this.isNew = isNew;
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            if(!isNew) ActionHandler.next(new TurnStartEffectAction(isPlayer));
            ActionHandler.next(new DrawCardAction(WakTower.game.drawAmount));
            
            for(AbstractRelic relic : WakTower.game.relics) {
                relic.startOfTurn(isPlayer);
            }
            for(AbstractChar member : WakTower.game.battle.members) {
                member.startOfTurn(isPlayer);
            }
            for(AbstractStatus status : WakTower.game.player.status) {
                status.startOfTurn(isPlayer);
            }
            
            ActionHandler.bot(new ChangePhaseAction(AbstractBattle.BattlePhase.playerTurn));
        }
    }
}
