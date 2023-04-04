package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.esotericsoftware.spine.AnimationState;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEffect;
import com.fastcat.assemble.abstrcts.AbstractEnemy;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.screens.battle.EnemyButton;

import java.util.Iterator;

public class DieEffect extends AbstractEffect {

    private final AbstractEntity e;
    private float a = 1;

    public DieEffect(AbstractEntity e) {
        super(2);
        this.e = e;
    }

    @Override
    protected void renderEffect(SpriteBatch sb) {
        if(duration == baseDuration) {
            e.isDie = true;
            e.dieAnimation(new AnimationState.AnimationStateAdapter() {
                @Override
                public void complete(AnimationState.TrackEntry entry) {
                    e.isDead = true;
                    if(e instanceof AbstractEnemy) {
                        Iterator<EnemyButton> itr = MousseAdventure.battleScreen.enemies.iterator();
                        while(itr.hasNext()) {
                            EnemyButton b = itr.next();
                            if(b.entity == e) {
                                b.tile.removeEntity();
                                e.animation.state.removeListener(this);
                                itr.remove();
                                break;
                            }
                        }
                    }
                }
            });
        }

        if(!e.isDead) {
            if(a > 0) {
                a -= MousseAdventure.tick;
                if(a <= 0) {
                    a = 0;
                    isDone = true;
                }
            }
            float i = Interpolation.circleOut.apply(a);
            //if(i < 0) i = 0;
            e.animColor.set(i, i, i, i);
        }

        if(!isDone) {
            duration = 1;
        }
    }
}
