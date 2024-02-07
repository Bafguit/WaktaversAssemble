package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Expert extends AbstractSynergy {

    private static Expert instance;

    private Expert() {
        super("Expert");
    }

    @Override
    public float muliplyEffect() {
        if(grade == 1) return 1.35f;
        else if(grade == 2) return 1.75f;
        else if(grade == 3) return 2.35f;
        else return 1f;
    }

    public static Expert getInstance() {
        if(instance == null) {
            instance = new Expert();
        }
        return instance;
    }
}