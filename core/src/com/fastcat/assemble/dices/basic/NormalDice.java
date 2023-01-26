package com.fastcat.assemble.dices.basic;

import com.fastcat.assemble.WaktaAssemble;
import com.fastcat.assemble.abstrcts.AbstractDice;
import com.fastcat.assemble.abstrcts.AbstractGame;

public class NormalDice extends AbstractDice {
    public NormalDice() {
        super("Dice", DiceRarity.BASIC);
    }

    @Override
    protected void rollDice() {
        number = WaktaAssemble.game.diceRandom.random(1, 6);
    }
}
