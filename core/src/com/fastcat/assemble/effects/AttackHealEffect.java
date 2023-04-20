package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Event;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractEffect;
import com.fastcat.assemble.abstracts.AbstractEntity;

public class AttackHealEffect extends AbstractEffect {

    private final AbstractEntity attacker;
    private final Array<AbstractEntity> target;
    private final AbstractEntity.DamageInfo info;
    private final int amount;
    private final int heal;

    private int count = 0;
    private float attackTimer = 0;
    private boolean attackDone = false;

    public AttackHealEffect(AbstractEntity attacker, Array<AbstractEntity> target, int heal, AbstractEntity.DamageInfo info) {
        this(attacker, target, 1, heal, info);
    }

    public AttackHealEffect(AbstractEntity attacker, Array<AbstractEntity> target, int amount, int heal, AbstractEntity.DamageInfo info) {
        super(2);
        this.attacker = attacker;
        this.amount = amount;
        this.target = target;
        this.info = info;
        this.heal = heal;
    }

    @Override
    protected void renderEffect(SpriteBatch sb) {
        if(duration == baseDuration) {
            attacker.attackAnimation(amount, new AnimationState.AnimationStateAdapter() {
                @Override
                public void complete(AnimationState.TrackEntry entry) {
                    isDone = true;
                    attacker.afterAttack();
                    attacker.animation.state.removeListener(this);
                }

                @Override
                public void event(AnimationState.TrackEntry entry, Event event) {
                    if (!attackDone && event.getData().getName().equals("OnAttack")) {
                        attackDone = true;
                        count++;
                        if(count >= 2 && count < amount) {
                            entry.setTrackTime(0.4f);
                        }
                        attacker.attackAndHeal(target, info, heal);
                    }
                }
            });
        }

        if(attackDone && count < amount) {
            attackTimer += MousseAdventure.tick;
            if(attackTimer >= 0.1f) {
                attackTimer = 0;
                attackDone = false;
            }
        }

        if(!isDone) {
            duration = 1;
        }
    }
}
