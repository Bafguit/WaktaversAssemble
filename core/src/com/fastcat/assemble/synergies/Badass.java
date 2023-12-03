package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Badass extends AbstractSynergy {

    private static Badass instance;

    private Badass() {
        super("Badass");
    }

    public boolean isOnlyWak() {
        return grade == 1;
    }

    public static Badass getInstance() {
        if(instance == null) {
            instance = new Badass();
        }
        return instance;
    }
}