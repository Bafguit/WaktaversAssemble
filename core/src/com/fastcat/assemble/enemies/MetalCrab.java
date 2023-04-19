package com.fastcat.assemble.enemies;

import com.fastcat.assemble.abstracts.AbstractEffect;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.effects.MoveEnemyEffect;
import com.fastcat.assemble.effects.PercentAttackEffect;
import com.fastcat.assemble.screens.battle.EnemyButton;

public class MetalCrab extends AbstractEnemy {
    public MetalCrab() {
        super("enemy_1016_diaman", 300, 3000, 500, 85);
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
        return 1;
    }
}
