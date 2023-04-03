package com.fastcat.assemble.enemies;

import com.fastcat.assemble.abstrcts.AbstractEffect;
import com.fastcat.assemble.abstrcts.AbstractEnemy;
import com.fastcat.assemble.effects.MoveEnemyEffect;
import com.fastcat.assemble.effects.PercentAttackEffect;
import com.fastcat.assemble.screens.battle.CharacterButton;
import com.fastcat.assemble.screens.battle.EnemyButton;

public class TestEnemy extends AbstractEnemy {
    public TestEnemy() {
        super("Test", 350, 2000, 100, 0);
        name = "테스트";
        desc = "피해 3";
    }

    @Override
    public AbstractEffect playAction(EnemyButton b, float duration) {
        CharacterButton c = getPlayerInRange(b, 3);
        if(c != null) {
            return new PercentAttackEffect(c.character, this, 100, DamageType.MAGIC, true);
        } else {
            return new MoveEnemyEffect(b, duration);
        }
    }
}
