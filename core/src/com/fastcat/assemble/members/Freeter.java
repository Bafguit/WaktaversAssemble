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
        setValue(3, 1);
    }

    public void damageTaken(DamageInfo info) {
        if(info.source != null) {
            this.info = info;
            use();
        }
    }

    @Override
    protected void useMember() {
        if(info.source != null) {
            set(new MemberSkillAnimationAction(this));
            set(new DamageAction(new DamageInfo(this.tempClone.calculateValue(), DamageType.REFLECT), info.source, true));
        }
    }
}
