package com.fastcat.assemble.abstracts;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Isedol extends AbstractSynergy {

    private static Isedol instance;

    private Isedol() {
        super("Isedol");
    }

    public int increaseWakDamage() {
        return grade == 1 ? 2 : grade == 2 ? 5 : 10;
    }

    public static Isedol getInstance() {
        if(instance == null) {
            instance = new Isedol();
        }
        return instance;
    }
}