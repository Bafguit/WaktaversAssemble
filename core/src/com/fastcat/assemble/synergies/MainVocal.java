package com.fastcat.assemble.synergies;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractSynergy;

public class MainVocal extends AbstractSynergy {

    private static MainVocal instance;

    private MainVocal() {
        super("MainVocal");
    }

    @Override
    public int upgradeAmount() {
        if(grade == 1) return 2;
        else if(grade == 2) return 3;
        else if(grade == 3) return 5;
        else if(grade == 4) return 8;
        return 0;
    }

    @Override
    public void onSummon(AbstractMember m) {
        if(grade > 0) {
            WakTower.game.battle.members.get(0).upgradeTemp(upgradeAmount());
        }
    }

    public static MainVocal getInstance() {
        if(instance == null) {
            instance = new MainVocal();
        }
        return instance;
    }
}