package com.fastcat.assemble.character;

import com.fastcat.assemble.abstrcts.AbstractEntity;

public class TestChar extends AbstractEntity {
    public TestChar() {
        super("Test", 10, EntityRarity.BASIC);
    }

    @Override
    public String getDesc(int num) {
        return null;
    }

    @Override
    public void useCard(int num) {

    }
}
