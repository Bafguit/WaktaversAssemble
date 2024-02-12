package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;

public class Kkekki extends AbstractMember {

    public Kkekki() {
        super("Kkekki");
        setValue(25, 5);
        passive = true;
    }

    @Override
    public float damageTakeMultiply(DamageInfo info) {
        float m = 1f - ((float) calculateValue() / 100f);
        return m < 0 ? 0f : m;
    }

    @Override
    protected void onSummoned() {
        use();
    }

    @Override
    protected void useMember() {
        next(new MemberSkillAnimationAction(this, 0.3f));
    }
}
