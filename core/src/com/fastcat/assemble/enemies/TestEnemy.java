package com.fastcat.assemble.enemies;

import com.fastcat.assemble.abstrcts.AbstractEnemy;

public class TestEnemy extends AbstractEnemy {
    public TestEnemy() {
        super("Test", 350, 2000, 100, 0);
        name = "테스트";
        desc = "피해 3";
    }
}
