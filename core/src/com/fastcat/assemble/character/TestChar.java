package com.fastcat.assemble.character;

import com.fastcat.assemble.abstrcts.AbstractEntity;

public class TestChar extends AbstractEntity {
    public TestChar() {
        super("Test", 10, EntityRarity.OPERATOR);
        name = "테스트";
        desc = "피해 3";
        isPlayer = true;
        baseAttack = 705;
        baseMaxHealth = 2345;
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
