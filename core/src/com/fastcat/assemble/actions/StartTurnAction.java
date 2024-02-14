package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.abstracts.AbstractBattle.BattlePhase;
import com.fastcat.assemble.handlers.ActionHandler;

public class StartTurnAction extends AbstractAction {
    
    private final boolean isPlayer, isNew;

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
            if(isPlayer) ActionHandler.next(new DrawCardAction(WakTower.game.drawAmount));
            
            for(AbstractRelic relic : WakTower.game.relics) {
                relic.startOfTurn(isPlayer);
            }
            for(AbstractMember member : WakTower.game.battle.members) {
                member.startOfTurn(isPlayer);
                for(AbstractStatus status : member.status) {
                    status.startOfTurn(isPlayer);
                }
            }
            for(AbstractEnemy enemy : WakTower.game.battle.enemies) {
                enemy.startOfTurn(isPlayer);
                for(AbstractStatus status : enemy.status) {
                    status.startOfTurn(isPlayer);
                }
            }

            if(isPlayer && !isNew) ActionHandler.next(new ExitMembersAction());
            
            ActionHandler.bot(new ChangePhaseAction(isPlayer ? BattlePhase.playerTurn : BattlePhase.enemyTurn));
            if(!isPlayer) ActionHandler.bot(new EnemyTurnAction());
        }
    }
}
