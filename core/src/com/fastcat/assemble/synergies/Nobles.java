package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Nobles extends AbstractSynergy {

    private static Nobles instance;

    private Nobles() {
        super("Nobles");
    }

    @Override
    public int upgradeAmount() {
        if(grade == 1) return 1;
        else if(grade == 2) return 2;
        else if(grade == 3) return 4;
        else return 0;
    }

    @Override
    public void onSummon(AbstractMember m) {
        if(grade > 0) {
            for(AbstractMember mb : members) {
                mb.upgradeTemp(upgradeAmount());
            }
        }
    }

    public static Nobles getInstance() {
        if(instance == null) {
            instance = new Nobles();
        }
        return instance;
    }
}