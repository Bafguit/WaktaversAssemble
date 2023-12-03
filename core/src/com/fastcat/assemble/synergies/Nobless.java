package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Nobless extends AbstractSynergy {

    private static Nobless instance;

    private Nobless() {
        super("Nobless");
    }

    public static Nobless getInstance() {
        if(instance == null) {
            instance = new Nobless();
        }
        return instance;
    }
}