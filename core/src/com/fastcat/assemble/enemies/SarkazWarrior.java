package com.fastcat.assemble.enemies;

import com.esotericsoftware.spine.AnimationState;
import com.fastcat.assemble.abstrcts.AbstractEffect;
import com.fastcat.assemble.abstrcts.AbstractEnemy;
import com.fastcat.assemble.effects.MoveEnemyEffect;
import com.fastcat.assemble.effects.PercentAttackEffect;
import com.fastcat.assemble.screens.battle.EnemyButton;

public class SarkazWarrior extends AbstractEnemy {
    public SarkazWarrior() {
        super("enemy_1010_demon", 600, 7500, 230, 50);
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

    @Override
    public void attackAnimation(int target, AnimationState.AnimationStateAdapter adapter) {
        animation.set("Attack", adapter, false);
        animation.state.addAnimation(0, "Attack_End", false, 0.0F);
        animation.state.addAnimation(0, "Idle", true, 0.0F);
    }

    @Override
    public void walk() {
        animation.set("Move_Loop", true);
    }
}
