package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Expert extends AbstractSynergy {

    private static Expert instance;

    private Expert() {
        super("Expert");
    }

    public float muliplyExpertEffect() {
        if(grade == 1) return 1.5f;
        else if(grade == 2) return 1.75f;
        else if(grade == 3) return 2.25f;
        else if(grade == 4) return 3f;
        else return 1f;
    }

    public static Expert getInstance() {
        if(instance == null) {
            instance = new Expert();
        }
        return instance;
    }
}