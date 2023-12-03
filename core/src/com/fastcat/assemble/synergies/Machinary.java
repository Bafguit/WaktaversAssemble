package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Machinary extends AbstractSynergy {

    private static Machinary instance;

    private Machinary() {
        super("Machinary");
    }

    public static Machinary getInstance() {
        if(instance == null) {
            instance = new Machinary();
        }
        return instance;
    }
}