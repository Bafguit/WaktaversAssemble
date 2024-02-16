package com.fastcat.assemble.abstracts;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.actions.EndBattleAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.utils.EnemyAction;

public abstract class AbstractEnemy extends AbstractEntity implements Cloneable {

    public EnemyAction action;
    protected int turn = 0;

    public AbstractEnemy(String id) {
        super(id, false);
    }

    public int calculateDamage(int damage) {
        for(AbstractStatus s : status) {
            damage = s.calculateAtk(damage);
        }

        float m = 1;
        for(AbstractStatus s : status) {
            m *= s.multiplyAtk();
        }

        damage *= m;
        return damage;
    }

    public int calculateDef(int def) {
        for(AbstractStatus s : status) {
            def = s.calculateDef(def);
        }

        float m = 1;
        for(AbstractStatus s : status) {
            m *= s.multiplyDef();
        }

        def *= m;
        return def;
    }

    public final void action() {
        if(actTurn()) setTurn();
    }

    protected void setTurn() {
        turn++;
    }

    @Override
    public void startOfTurn(boolean isPlayer) {
        if(isPlayer) action = getAction();
    }

    @Override
    public void die() {
        super.die();
        for(AbstractEnemy e : WakTower.game.battle.enemies) {
            if(e.isAlive()) return;
        }
        ActionHandler.bot(new EndBattleAction());
    }

    protected abstract boolean actTurn();

    public abstract EnemyAction getAction();

    public AbstractEnemy cpy() {
        AbstractEnemy m;
        try {
            m = (AbstractEnemy) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        m.animation = this.animation;
        return m;
    }

    public AbstractEnemy duplicate() {
        AbstractEnemy m = cpy();
        m.animation = this.animation.cpy();

        return m;
    }
}
