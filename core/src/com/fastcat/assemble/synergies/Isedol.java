package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Isedol extends AbstractSynergy {

    private static Isedol instance;

    private Isedol() {
        super("Isedol");
    }

    public int repeatAmount() {
        return grade == 1 ? 2 : 3;
    }

    public static Isedol getInstance() {
        if(instance == null) {
            instance = new Isedol();
        }
        return instance;
    }
}