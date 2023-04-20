package com.fastcat.assemble.battles;

import com.fastcat.assemble.abstracts.AbstractBattle;
import com.fastcat.assemble.enemies.SarkazSniper;
import com.fastcat.assemble.enemies.SarkazWarrior;
import com.fastcat.assemble.enemies.SpecOpsCaster;
import com.fastcat.assemble.screens.battle.TileSquare;

public class TestBattle extends AbstractBattle {

    public TestBattle() {
        super(BattleType.WEAK, 5, 4);
    }

    @Override
    protected void defineMap() {
        for(int i = 0; i < wSize; i++) {
            for(int j = 0; j < hSize; j++) {
                map[i][j] = new TileSquare(TileSquare.TileStatus.NORMAL, i, j);
            }
        }

        map[0][0] = new TileSquare(TileSquare.TileStatus.INVALID, 0, 0);
        map[1][1] = new TileSquare(TileSquare.TileStatus.INVALID, 1, 1);
    }

    @Override
    public void setEnemies() {
        addEnemy(new SpecOpsCaster(), 0, 1);
        addEnemy(new SarkazWarrior(), 3, 3);
        //addEnemy(new SarkazSniper(), 2, 3);
    }
}
