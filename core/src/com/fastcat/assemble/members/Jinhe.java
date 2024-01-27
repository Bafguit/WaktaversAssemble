package com.fastcat.assemble.members;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.actions.IncreaseDefAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;

public class Jinhe extends AbstractMember {

    public Jinhe() {
        super("Jinhe");
        setAtk(7, 0);
        setValue(2, 1);
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
        next(new GainBlockAction(WakTower.game.player, this));
        next(new IncreaseDefAction(this, value));
    }
}
