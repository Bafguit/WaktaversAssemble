package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Isedol extends AbstractSynergy {

    private static Isedol instance;

    private Isedol() {
        super("Isedol");
    }

    @Override
    public int repeatAmount(AbstractMember m) {
        if(grade == 1) return 2;
        else if(grade == 2) return 3;
        else return 1;
    }

    public static Isedol getInstance() {
        if(instance == null) {
            instance = new Isedol();
        }
        return instance;
    }
}