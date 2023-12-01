package com.fastcat.assemble.battles;

import com.fastcat.assemble.abstracts.AbstractBattle;

public class TestBattle extends AbstractBattle {

    public TestBattle() {
        super(BattleType.WEAK, 5, 4);
    }

    @Override
    public void setEnemies() {
        addEnemy(new SpecOpsCaster(), 0, 1);
        addEnemy(new SarkazWarrior(), 3, 3);
        //addEnemy(new SarkazSniper(), 2, 3);
    }
}
