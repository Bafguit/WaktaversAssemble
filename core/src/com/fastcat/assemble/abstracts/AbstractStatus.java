package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.actions.RemoveStatusAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.utils.DamageInfo;

public abstract class AbstractStatus {

    public final String id;
    public int amount;
    public AbstractEntity owner;
    public boolean hasAmount, canGoNegative;

    public AbstractStatus(String id) {
        this.id = id;
    }

    public void apply(int amount) {}

    public void onRemove() {}

    public void onInitial() {}

    public void startOfTurn(boolean isPlayer) {}

    public void endOfTurn(boolean isPlayer) {}

    public int damageTake(DamageInfo info) {
        return info.damage;
    }

    public float damageTakeMultiply(DamageInfo info) {
        return 1f;
    }

    public void damageTaken(DamageInfo info) {}

    public void onAttack(DamageInfo info, Array<AbstractEntity> target) {}

    public void onDamage(DamageInfo info, AbstractEntity target) {}

    public int calculateAtk(int atk) {
        return atk;
    }

    public int calculateDef(int def) {
        return def;
    }

    public float multiplyAtk() {
        return 1f;
    }

    public float multiplyDef() {
        return 1f;
    }
    
    public int onGainBlock(int amount) {
        return amount;
    }
    
    public int onGainBarrier(int amount) {
        return amount;
    }
    
    public void onGainedBlock(int amount) {}
    
    public void onGainedBarrier(int amount) {}
    
    public int onHeal(int amount) {
        return amount;
    }
    
    public void onHealed(int amount) {}

    public void onSummon(AbstractMember m) {}

    public void remove() {
        ActionHandler.set(new RemoveStatusAction(owner, this));
    }
}
