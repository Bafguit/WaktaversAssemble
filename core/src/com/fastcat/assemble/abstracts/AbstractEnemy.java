package com.fastcat.assemble.abstracts;

public abstract class AbstractEnemy extends AbstractEntity {
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
}
