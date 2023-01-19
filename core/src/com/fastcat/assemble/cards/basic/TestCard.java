package com.fastcat.assemble.cards.basic;

import com.fastcat.assemble.abstrcts.AbstractCard;

public class TestCard extends AbstractCard {

    public TestCard() {
        super("Test", CardRarity.BASIC);
        name = "테스트";
        desc = "아무튼 설명 매우엄청난 설명";
    }

    @Override
    protected void useCard() {

    }
}
