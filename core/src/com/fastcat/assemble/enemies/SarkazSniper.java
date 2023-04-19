package com.fastcat.assemble.enemies;

import com.fastcat.assemble.abstracts.AbstractEffect;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.effects.MoveEnemyEffect;
import com.fastcat.assemble.effects.PercentAttackEffect;
import com.fastcat.assemble.screens.battle.EnemyButton;

public class SarkazSniper extends AbstractEnemy {
    public SarkazSniper() {
        super("enemy_1012_dcross", 450, 6000, 200, 50);
    }

    @Override
    protected AbstractEffect play(EnemyButton b, float duration) {
        if(b.target != null) {
            return new PercentAttackEffect(b.target.character, this, 100, DamageType.PHYSICAL, 1, false);
        } else {
            return new MoveEnemyEffect(b, duration);
        }
    }

    @Override
    public int getRange() {
        return 2;
    }

    @Override
    public void walk() {
        animation.set("Move_Loop", true);
    }
}
