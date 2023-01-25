package com.fastcat.assemble.cards.basic;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WaktaAssemble;
import com.fastcat.assemble.abstrcts.AbstractCard;
import com.fastcat.assemble.abstrcts.AbstractDice;
import com.fastcat.assemble.screens.battle.DiceButton;

public class TestCard extends AbstractCard {

    public TestCard() {
        super("Test", CardRarity.BASIC);
        name = "리롤";
        desc = "무작위 주사위를 다시 굴립니다.";
    }

    @Override
    protected void useCard() {
        Array<AbstractDice> dices = new Array<>();

        for(DiceButton d : WaktaAssemble.battleScreen.dice) {
            if(d.tile == null) dices.add(d.dice);
        }

        if(dices.size > 0) {
            dices.get(WaktaAssemble.game.battleRandom.random(0, dices.size - 1)).roll();
            System.out.println("ROLL!");
        }
    }

    @Override
    public boolean canUse() {
        for(DiceButton d : WaktaAssemble.battleScreen.dice) {
            if(d.tile == null) return true;
        }
        return false;
    }
}
