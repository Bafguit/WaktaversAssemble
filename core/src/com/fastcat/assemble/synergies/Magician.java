package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Magician extends AbstractSynergy {

    private static Magician instance;

    private Magician() {
        super("Magician");
    }

    public int increaseMemberDamage() {
        return grade == 1 ? 2 : grade == 2 ? 3 : grade == 3 ? 5 : grade == 4 ? 10 : 0;
    }

    public static Magician getInstance() {
        if(instance == null) {
            instance = new Magician();
        }
        return instance;
    }
}