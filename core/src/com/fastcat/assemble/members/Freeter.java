package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class Freeter extends AbstractMember {

    private DamageInfo info;

    public Freeter() {
        super("Freeter");
        setAtk(3, 2);
    }

    public void damageTaken(DamageInfo info) {
        if(info.source != null) {
            this.info = info;
            use();
        }
    }

    @Override
    protected void useMember() {
        if(info.source != null && !info.source.isPlayer) {
            next(new MemberSkillAnimationAction(this));
            next(new DamageAction(new DamageInfo(this, DamageType.REFLECT), info.source, true));
        }
    }
}
