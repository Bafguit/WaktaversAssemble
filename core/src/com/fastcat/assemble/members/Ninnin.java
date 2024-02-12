package com.fastcat.assemble.members;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class Ninnin extends AbstractMember {

    private DamageInfo info;

    public Ninnin() {
        super("Ninnin");
        setAtk(3, 1);
        passive = true;
    }

    @Override
    public void onAttack(DamageInfo info, Array<AbstractEntity> target) {
        if(info.source != null && !info.source.isPlayer) {
            this.info = info;
            use();
        }
    }

    @Override
    protected void useMember() {
        set(new MemberSkillAnimationAction(this));
        set(new DamageAction(new DamageInfo(tempClone, DamageType.NORMAL), info.source, true));
    }
}
