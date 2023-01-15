package com.fastcat.assemble.cards.basic;

import com.fastcat.assemble.abstrcts.AbstractCard;

public class TestCard extends AbstractCard {

    public TestCard() {
        id = "test";
        name = "테스트";
        desc = "테스트입니다.";
    }

    @Override
    protected void useCard() {

    }
}
