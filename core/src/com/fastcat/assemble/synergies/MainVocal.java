package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class MainVocal extends AbstractSynergy {

    private static MainVocal instance;

    private MainVocal() {
        super("MainVocal");
    }

    public int upgradeAmount() {
        if(grade == 1) return 2;
        else if(grade == 2) return 3;
        else if(grade == 3) return 5;
        else if(grade == 4) return 8;
        return 0;
    }

    public static MainVocal getInstance() {
        if(instance == null) {
            instance = new MainVocal();
        }
        return instance;
    }
}