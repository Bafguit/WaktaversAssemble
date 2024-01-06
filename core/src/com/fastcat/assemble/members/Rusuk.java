package com.fastcat.assemble.members;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;

public class Rusuk extends AbstractMember {

    private AbstractMember nextMember;

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
    protected void useMember() {
        Array<AbstractMember> ms = WakTower.game.battle.members;
        for(int i = 0; i < ms.size; i++) {
            AbstractMember m = ms.get(i);
            if(m == this && i > 0) {
                ms.get(i - 1).upgradeTemp(value);
                break;
            }
        }
    }
}
