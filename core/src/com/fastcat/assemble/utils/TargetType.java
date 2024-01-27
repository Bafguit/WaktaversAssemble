package com.fastcat.assemble.utils;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractEntity;

public enum TargetType {
    RANDOM, ALL, ALL_ENEMY, HP_HIGH, HP_LOW, PLAYER, NONE;

    public Array<AbstractEntity> getTargets() {
        Array<AbstractEntity> targets = new Array<>();

        if(this == RANDOM) {
            Array<AbstractEntity> entities = new Array<>();
            for(AbstractEnemy e : WakTower.game.battle.enemies) {
                if(e.isAlive()) entities.add(e);
            }
            int r = WakTower.game.battleRandom.random(entities.size - 1);
            targets.add(entities.get(r));
        } else if(this == ALL_ENEMY) {
            for(AbstractEnemy e : WakTower.game.battle.enemies) {
                if(e.isAlive()) targets.add(e);
            }
        } else if(this == ALL) {
            for(AbstractEnemy e : WakTower.game.battle.enemies) {
                if(e.isAlive()) targets.add(e);
            }
            targets.add(WakTower.game.player);
        } else if(this == HP_HIGH) {
            int h = 0;
            for(AbstractEnemy e : WakTower.game.battle.enemies) {
                if(e.isAlive()) {
                    if(e.health > h) {
                        targets.clear();
                        targets.add(e);
                        h = e.health;
                    } else if(e.health == h) {
                        targets.add(e);
                    }
                }
            }
        } else if(this == HP_LOW) {
            int h = 0;
            for(AbstractEnemy e : WakTower.game.battle.enemies) {
                if(e.isAlive() && e.health > h) {
                    h = e.health;
                }
            }

            for(AbstractEnemy e : WakTower.game.battle.enemies) {
                if(e.isAlive()) {
                    if(e.health < h) {
                        targets.clear();
                        targets.add(e);
                        h = e.health;
                    } else if(e.health == h) {
                        targets.add(e);
                    }
                }
            }
        } else if(this == PLAYER) {
            targets.add(WakTower.game.player);
        }

        return targets;
    }
}
