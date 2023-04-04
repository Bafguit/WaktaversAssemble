package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Event;
import com.fastcat.assemble.abstrcts.AbstractEffect;
import com.fastcat.assemble.abstrcts.AbstractEntity;

public class AttackEffect extends AbstractEffect {

    private final AbstractEntity attacker;
    private final Array<AbstractEntity> target;
    private final AbstractEntity.DamageInfo info;
    private final String key;

    private boolean attackDone = false;

    public AttackEffect(AbstractEntity attacker, Array<AbstractEntity> target, AbstractEntity.DamageInfo info) {
        this(attacker, target, "Attack", info);
    }

    public AttackEffect(AbstractEntity attacker, Array<AbstractEntity> target, String key, AbstractEntity.DamageInfo info) {
        super(2);
        this.attacker = attacker;
        this.key = key;
        this.target = target;
        this.info = info;
    }

    @Override
    protected void renderEffect(SpriteBatch sb) {
        if(duration == baseDuration) {
            attacker.animation.setAndIdle(key, new AnimationState.AnimationStateAdapter() {
                @Override
                public void complete(AnimationState.TrackEntry entry) {
                    isDone = true;
                    attacker.animation.state.removeListener(this);
                }

                @Override
                public void event(AnimationState.TrackEntry entry, Event event) {
                    if (!attackDone && event.getData().getName().equals("OnAttack")) {
                        attackDone = true;
                        attacker.attack(target, info);
                    }
                }
            });
        }

        if(!isDone) {
            duration = 1;
        }
    }
}
