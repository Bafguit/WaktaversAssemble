package com.fastcat.assemble.members;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.utils.DamageInfo;

public class Kimchimandu extends AbstractMember {

    public Kimchimandu() {
        super("Kimchimandu");
        setValue(2, 1);
        passive = true;
    }

    public int damageTake(DamageInfo info) {
        return info.damage - value;
    }

    @Override
    protected void useMember() {}
}
