package com.fastcat.assemble.members;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.actions.PlayAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;

public class Sirian extends AbstractMember {

    public Sirian() {
        super("Sirian");
        setDef(2, 1);
        passive = true;
    }

    @Override
    public void onAttack(DamageInfo info, Array<AbstractEntity> target) {
        if(info.source != null && info.source.isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        set(new PlayAnimationAction(this));
        set(new GainBlockAction(TargetType.SELF, this, true));
    }
}
