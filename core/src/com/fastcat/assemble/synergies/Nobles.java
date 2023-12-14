package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Nobles extends AbstractSynergy {

    private static Nobles instance;

    private Nobles() {
        super("Nobles");
    }

    public static Nobles getInstance() {
        if(instance == null) {
            instance = new Nobles();
        }
        return instance;
    }
}