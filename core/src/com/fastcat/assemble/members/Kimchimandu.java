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
        return info.damage - tempClone.calculateValue();
    }

    @Override
    protected void useMember() {}
}
