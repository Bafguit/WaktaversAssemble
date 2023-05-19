package com.fastcat.assemble.actions;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractDice;
import com.fastcat.assemble.screens.battle.BattleScreen;
import com.fastcat.assemble.screens.battle.DiceButton;

public class BattleReadyAction extends AbstractAction {

    public BattleReadyAction() {
        super(1f);
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            MousseAdventure.battleScreen.phase = BattleScreen.BattlePhase.DEPLOY;
        }
    }
}
