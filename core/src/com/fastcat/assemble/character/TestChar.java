package com.fastcat.assemble.character;

import com.fastcat.assemble.abstrcts.AbstractEntity;

public class TestChar extends AbstractEntity {
    public TestChar() {
        super("Test", 705, 2345, 392, 15, EntityRarity.OPERATOR);
        name = "테스트";
        desc = "피해 3";
        isPlayer = true;
    }

    @Override
    public String getDesc(int num) {
        return switch (num) {
            case 1 -> "피해 3";
            case 2 -> "피해 2";
            case 3 -> "쉴드 3";
            case 4 -> "쉴드 2";
            case 5 -> "쇠약 1";
            case 6 -> "취약 1";
            default -> "";
        };
    }
}
