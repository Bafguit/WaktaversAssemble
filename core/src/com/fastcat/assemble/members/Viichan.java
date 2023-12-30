package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;

public class Viichan extends AbstractMember {

    public Viichan() {
        super("Viichan");
        setAtk(8, 0);
        setValue(2, 1);
    }


    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            //todo
        }
    }

    @Override
    public void afterUse() {
        baseAtk += value;
    }

    @Override
    public void onSummon() {
        
    }
}
