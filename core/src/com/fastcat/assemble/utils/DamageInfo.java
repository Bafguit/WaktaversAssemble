package com.fastcat.assemble.utils;

import com.fastcat.assemble.abstracts.AbstractEntity;

public class DamageInfo {
    public int damage;
    public AbstractEntity source;
    public DamageType type;

    public DamageInfo(int damage, AbstractEntity source, DamageType type) {
        this.damage = damage;
        this.source = source;
        this.type = type;
    }

    public enum DamageType {
        wak, member, reflect, lose
    }
}
