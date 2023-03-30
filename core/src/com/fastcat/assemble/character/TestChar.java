package com.fastcat.assemble.character;

import com.fastcat.assemble.abstrcts.AbstractEntity;

public class TestChar extends AbstractEntity {
    public TestChar() {
        super("Mousse", 705, 2345, 392, 15, EntityRarity.OPERATOR);
        name = "테스트";
        desc = "피해 3";
        isPlayer = true;
    }
}
