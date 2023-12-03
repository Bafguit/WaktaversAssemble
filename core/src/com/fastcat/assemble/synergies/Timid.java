package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Timid extends AbstractSynergy {

    private static Timid instance;

    private Timid() {
        super("Timid");
    }

    public static Timid getInstance() {
        if(instance == null) {
            instance = new Timid();
        }
        return instance;
    }
}