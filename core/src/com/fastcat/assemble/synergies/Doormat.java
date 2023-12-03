package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Doormat extends AbstractSynergy {

    private static Doormat instance;

    private Doormat() {
        super("Doormat");
    }

    public static Doormat getInstance() {
        if(instance == null) {
            instance = new Doormat();
        }
        return instance;
    }
}