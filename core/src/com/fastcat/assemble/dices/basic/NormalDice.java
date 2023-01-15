package com.fastcat.assemble.dices.basic;

import com.fastcat.assemble.abstrcts.AbstractDice;
import com.fastcat.assemble.abstrcts.AbstractGame;

public class NormalDice extends AbstractDice {
    public NormalDice() {
        super("Basic", DiceRarity.BASIC);
    }

    @Override
    protected void rollDice() {
        number = AbstractGame.diceRandom.random(1, 6);
    }
}
