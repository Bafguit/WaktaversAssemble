package com.fastcat.assemble.enemies;

import com.fastcat.assemble.abstracts.AbstractEffect;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.effects.MoveEnemyEffect;
import com.fastcat.assemble.effects.PercentAttackEffect;
import com.fastcat.assemble.effects.RangedVfxEffect;
import com.fastcat.assemble.screens.battle.EnemyButton;

public class SpecOpsCaster extends AbstractEnemy {
    public SpecOpsCaster() {
        super("enemy_1038_lunmag", 400, 4000, 250, 50);
        attackFrom.set(30, 25);
    }

    @Override
    protected AbstractEffect play(EnemyButton b, float duration) {
        if(b.target != null) {
            return new PercentAttackEffect(b.target.character, this, 100, DamageType.MAGIC, 1, RangedVfxEffect.RangedVfx.ARTS_1, true);
        } else {
            return new MoveEnemyEffect(b, duration);
        }
    }

    @Override
    public int getRange() {
        return 2;
    }
}
