package com.fastcat.assemble.members;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.synergies.Isedol;

public class Jururu extends AbstractMember {

    public Jururu() {
        super("Jururu");
        setValue(1, 1);
        passive = true;
    }


    @Override
    public void onSummon(AbstractMember m) {
        use();
    }

    @Override
    protected void onSummoned() {
        use();
    }

    @Override
    protected void useMember() {
        Array<AbstractMember> ms = WakTower.game.battle.members;
        for(int i = 0; i < ms.size; i++) {
            AbstractMember m = ms.get(i);
            if(m == this && i + 1 < ms.size) {
                AbstractMember next = ms.get(i + 1);
                next.upgradeTemp(tempClone.calculateValue());
                break;
            }
        }
    }
}
