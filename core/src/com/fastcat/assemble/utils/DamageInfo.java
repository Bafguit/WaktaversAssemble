package com.fastcat.assemble.utils;

import com.fastcat.assemble.abstracts.AbstractEntity;

public class DamageInfo {
    public int damage;
    public AbstractEntity source;
    public DamageType type;

    public enum DamageType {
        normal, reflect, lose
    }
}
