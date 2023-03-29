package com.fastcat.assemble.actions;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.abstrcts.AbstractDice;
import com.fastcat.assemble.screens.battle.DiceButton;

public class RollRandomDiceAction extends AbstractAction {

    public RollRandomDiceAction() {
        super(0.5f);
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            Array<AbstractDice> dices = new Array<>();

            for(DiceButton d : MousseAdventure.battleScreen.dice) {
                if(d.tile == null) dices.add(d.dice);
            }

            if(dices.size > 0) {
                dices.get(MousseAdventure.game.battleRandom.random(0, dices.size - 1)).roll();
                System.out.println("ROLL!");
            }
        }
    }
}
