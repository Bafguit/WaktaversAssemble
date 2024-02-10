package com.fastcat.assemble.stages.battle;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.uis.SpriteAnimation;

public class EnemyDisplay extends Table {

    private final AbstractEnemy enemy;

    private SpriteAnimation animation;

    public EnemyDisplay(AbstractEnemy enemy) {
        this.enemy = enemy;

        animation = this.enemy.animation;

        this.addActor(animation);

        this.enemy.animation.setAnimation("idle");
    }
}
