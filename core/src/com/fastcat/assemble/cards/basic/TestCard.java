package com.fastcat.assemble.cards.basic;

import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractCard;
import com.fastcat.assemble.actions.RollRandomDiceAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.screens.battle.DiceButton;

public class TestCard extends AbstractCard {

    public TestCard() {
        super("Test", CardRarity.BASIC);
        name = "리롤";
        desc = "무작위 주사위를 다시 굴립니다.";
    }

    @Override
    protected void useCard() {
        ActionHandler.top(new RollRandomDiceAction());
    }

    @Override
    public boolean canUse() {
        for(DiceButton d : MousseAdventure.battleScreen.dice) {
            if(d.tile == null) return true;
        }
        return false;
    }
}
