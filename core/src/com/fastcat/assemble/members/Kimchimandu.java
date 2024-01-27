package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.utils.DamageInfo;

public class Kimchimandu extends AbstractMember {

    private boolean summoned = true;

    public Kimchimandu() {
        super("Kimchimandu");
        setValue(2, 1);
        passive = true;
    }

    public int damageTake(DamageInfo info) {
        return info.damage - value;
    }

    @Override
    protected void onSummoned() {
        value = 0;
        summoned = true;
        use();
    }

    @Override
    public void onSummon(AbstractMember m) {
        value = 0;
        use();
    }

    @Override
    protected void useMember() {
        if(summoned) {
            //todo value increase action
            summoned = false;
        } else {
            value += baseValue;
        }
    }
}
