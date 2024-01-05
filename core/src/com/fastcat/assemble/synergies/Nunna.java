package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Nunna extends AbstractSynergy {

    private static Nunna instance;

    private Nunna() {
        super("Nunna");
    }

    public float damageMultiply() {
        if(grade == 1) return 1.25f;
        else if(grade == 2) return 1.5f;
        else if(grade == 3) return 2f;
        else return 1f;
    }

    public static Nunna getInstance() {
        if(instance == null) {
            instance = new Nunna();
        }
        return instance;
    }
}