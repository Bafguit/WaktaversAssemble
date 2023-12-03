package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class MainVocal extends AbstractSynergy {

    private static MainVocal instance;

    private MainVocal() {
        super("MainVocal");
    }

    public int upgradeWakSkill() {
        return grade - 1;
    }

    public static MainVocal getInstance() {
        if(instance == null) {
            instance = new MainVocal();
        }
        return instance;
    }
}