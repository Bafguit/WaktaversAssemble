package com.fastcat.assemble.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.effects.UpColorTextEffect;
import com.fastcat.assemble.handlers.EffectHandler;
import com.fastcat.assemble.screens.battle.MemberDisplay;

public class SummonMemberAction extends AbstractAction {

    private final MemberDisplay member;

    public SummonMemberAction(MemberDisplay m) {
        super(1f);
        this.member = m;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            WakTower.battleScreen.hand.remove(member);
            WakTower.battleScreen.updateHandPosition();
            member.isCard = false;
            member.isUsing = false;
            WakTower.battleScreen.members.add(member);
            member.member.summon();
            WakTower.battleScreen.update();
            EffectHandler.add(new UpColorTextEffect(member.originX, member.originY + 100, MathUtils.random(1, 100), Color.CYAN));
        }
    }
}
