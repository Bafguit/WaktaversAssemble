package com.fastcat.assemble.dices.legend;

import com.fastcat.assemble.abstrcts.AbstractDice;
import com.fastcat.assemble.abstrcts.AbstractGame;

public class Fraud3 extends AbstractDice {
    public Fraud3() {
        super("Fraud3", DiceRarity.LEGEND);
    }

    @Override
    protected void rollDice() {
        number = 3;
    }
}
