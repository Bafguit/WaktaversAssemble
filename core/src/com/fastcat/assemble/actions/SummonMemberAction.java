package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.stages.battle.BattleStage;
import com.fastcat.assemble.stages.battle.MemberCardDisplay;

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
            member.member.summon();
            stage.updateMemberPosition();
            stage.updateSynergy();
            //EffectHandler.add(new UpColorTextEffect(member.getX(), member.getY() + 100, MathUtils.random(1, 100), Color.CYAN));
        }
    }
}
