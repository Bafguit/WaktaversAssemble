package com.fastcat.assemble.enemies;

import com.fastcat.assemble.abstrcts.AbstractEnemy;

public class TestEnemy extends AbstractEnemy {
    public TestEnemy() {
        super("Test", 350, 2000, 100, 0);
        name = "테스트";
        desc = "피해 3";
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
