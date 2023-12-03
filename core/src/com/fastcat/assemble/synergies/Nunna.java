package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Nunna extends AbstractSynergy {

    private static Nunna instance;

    private Nunna() {
        super("Nunna");
    }

    public static Nunna getInstance() {
        if(instance == null) {
            instance = new Nunna();
        }
        return instance;
    }
}