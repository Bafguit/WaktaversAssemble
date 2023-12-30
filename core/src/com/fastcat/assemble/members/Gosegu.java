package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;

public class Gosegu extends AbstractMember {

    public Gosegu() {
        super("Gosegu");
        setValue(2, 1);
    }

    public void startOfTurn(boolean isPlayer) {
        if(isPlayer) {
            //todo 드로우 후 버리기
        }
    }

    @Override
    public void onSummon() {
        
    }
}
