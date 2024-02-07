package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.utils.DamageInfo;

public class Doormat extends AbstractSynergy {

    private static Doormat instance;

    private Doormat() {
        super("Doormat");
    }

    public int damageTake(DamageInfo info) {
        if(grade == 1) return info.damage - 1;
        else if(grade == 2) return info.damage - 3;
        else if(grade == 3) return info.damage - 6;
        else return info.damage;
    }

    public static Doormat getInstance() {
        if(instance == null) {
            instance = new Doormat();
        }
        return instance;
    }
}