package com.fastcat.assemble.members;

import java.util.LinkedList;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;

public class Rusuk extends AbstractMember {

    public Rusuk() {
        super("Rusuk");
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
        LinkedList<AbstractMember> ms = WakTower.game.battle.members;
        for(int i = 0; i < ms.size(); i++) {
            AbstractMember m = ms.get(i);
            if(m == this && i > 0) {
                AbstractMember pre = ms.get(i - 1);
                pre.upgradeTemp(tempClone.calculateValue());
                break;
            }
        }
    }
}
