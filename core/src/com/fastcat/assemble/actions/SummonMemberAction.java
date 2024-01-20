package com.fastcat.assemble.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.effects.UpColorTextEffect;
import com.fastcat.assemble.handlers.EffectHandler;
import com.fastcat.assemble.screens.battle.BattleStage;
import com.fastcat.assemble.screens.battle.MemberCardDisplay;
import com.fastcat.assemble.screens.battle.MemberDisplay;

public class SummonMemberAction extends AbstractAction {

    private final MemberCardDisplay member;

    public SummonMemberAction(MemberCardDisplay m) {
        super(1f);
        this.member = m;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            BattleStage stage = WakTower.game.battle.getStage();
            stage.removeHand(member.member);
            WakTower.game.battle.members.add(member.member);
            stage.updateMemberPosition();
            member.member.summon();
            //EffectHandler.add(new UpColorTextEffect(member.getX(), member.getY() + 100, MathUtils.random(1, 100), Color.CYAN));
        }
    }
}
