package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Magician extends AbstractSynergy {

    private static Magician instance;

    private Magician() {
        super("Magician");
    }

    public int increaseMemberDamage() {
        return grade == 1 ? 2 : grade == 2 ? 4 : grade == 3 ? 8 : 0;
    }

    public static Magician getInstance() {
        if(instance == null) {
            instance = new Magician();
        }
        return instance;
    }
}