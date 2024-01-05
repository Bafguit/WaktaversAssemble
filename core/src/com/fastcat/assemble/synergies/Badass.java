package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.utils.DamageInfo;

public class Badass extends AbstractSynergy {

    private static Badass instance;

    private Badass() {
        super("Badass");
    }

    public boolean ignoreDef(DamageInfo info) {
        if(info.member != null) {
            if((grade == 1 && info.member.hasSynergy(this)) || grade == 2) return true;
        }
        return false;
    }

    public static Badass getInstance() {
        if(instance == null) {
            instance = new Badass();
        }
        return instance;
    }
}