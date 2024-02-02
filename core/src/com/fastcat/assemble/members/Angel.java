package com.fastcat.assemble.members;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;

public class Angel extends AbstractMember {

    public Angel() {
        super("Angel");
        setDef(5, 3);
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new MemberSkillAnimationAction(this));
        next(new GainBlockAction(WakTower.game.player, this.tempClone));
    }
}
