package com.fastcat.assemble.battles;

import com.fastcat.assemble.abstracts.AbstractBattle;
import com.fastcat.assemble.enemies.Enemy1;

public class TestBattle extends AbstractBattle {

    public TestBattle() {
        super(BattleType.WEAK);
    }

    @Override
    protected void setEnemy() {
        enemies.addLast(new Enemy1());
    }
}
