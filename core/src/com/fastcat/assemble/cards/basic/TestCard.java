package com.fastcat.assemble.cards.basic;

import com.fastcat.assemble.abstracts.AbstractCard;
import com.fastcat.assemble.handlers.ActionHandler;

public class TestCard extends AbstractCard {

    public TestCard() {
        super("Test");
        name = "리롤";
        desc = "무작위 주사위를 다시 굴립니다.";
    }

    @Override
    protected void useCard() {
        ActionHandler.top(new RollRandomDiceAction());
    }
}
