package com.fastcat.assemble.relics;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.abstracts.AbstractSynergy;

public class CommonRelic1 extends AbstractRelic {

    private int sAmount = 0;

    public CommonRelic1() {
        super("CommonRelic1");
    }

    public void onSummon(AbstractMember m) {
        sAmount = 0;
        for(AbstractSynergy s : WakTower.game.battle.synergy) {
            if(s.gradeAmount[0] > 1 && s.getGrade() > 0) sAmount++;
            else if(s.gradeAmount[0] == 1 && s.getGrade() >= 0) sAmount++;
        }
    }

    @Override
    public float multiplyAtk() {
        return 1f + (0.05f * sAmount);
    }
}
