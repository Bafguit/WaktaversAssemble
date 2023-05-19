package com.fastcat.assemble.actions;

import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.screens.battle.BattleScreen;

public class RollDiceAction extends AbstractAction {

    public RollDiceAction() {
        super(1f);
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            MousseAdventure.battleScreen.rollDice();
        }
    }
}
