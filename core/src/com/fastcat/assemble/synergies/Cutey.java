package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Cutey extends AbstractSynergy {

    private static Cutey instance;

    private Cutey() {
        super("Cutey");
    }

    public static Cutey getInstance() {
        if(instance == null) {
            instance = new Cutey();
        }
        return instance;
    }
}