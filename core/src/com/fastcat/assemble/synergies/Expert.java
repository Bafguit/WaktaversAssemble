package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Expert extends AbstractSynergy {

    private static Expert instance;

    private Expert() {
        super("Expert");
    }

    public float increaseExpertEffect() {
        return ((float) (25 * grade + (grade < 4 ? 25 : 50))) * 0.01f;
    }

    public static Expert getInstance() {
        if(instance == null) {
            instance = new Expert();
        }
        return instance;
    }
}