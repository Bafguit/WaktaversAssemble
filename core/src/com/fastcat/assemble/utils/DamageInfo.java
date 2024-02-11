package com.fastcat.assemble.utils;

import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractMember;

public class DamageInfo {
    public int damage;
    public AbstractEnemy source;
    public DamageType type;
    public AbstractMember member;

    public DamageInfo(int damage, DamageType type) {
        this.damage = damage;
        this.type = type;
    }

    public DamageInfo(int damage, AbstractEnemy source, DamageType type) {
        this.damage = damage;
        this.source = source;
        this.type = type;
    }

    public DamageInfo(AbstractMember member, DamageType type) {
        this.member = member;
        this.type = type;
    }

    public DamageInfo cpy() {
        if(member != null) {
            DamageInfo ni = new DamageInfo(member, type);
            ni.damage = this.damage;
            return ni;
        }
        else return new DamageInfo(damage, source, type);
    }

    public enum DamageType {
        NORMAL, REFLECT, LOSE
    }
}
