package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class MindMaster extends AbstractSynergy {

    private static MindMaster instance;

    private MindMaster() {
        super("MindMaster");
    }

    public static MindMaster getInstance() {
        if(instance == null) {
            instance = new MindMaster();
        }
        return instance;
    }
}