package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.members.Dopamine;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class MemberDopamineAction extends AbstractAction {

    public Dopamine dopamine;

    public MemberDopamineAction(Dopamine dopamine) {
        super(0.5f);
        this.dopamine = dopamine;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            dopamine.animation.setAnimation("skill");
            dopamine.animation.addAnimation("idle");
            int cnt = 0;
            for(AbstractSynergy s : WakTower.game.battle.synergy) {
                if(s.gradeAmount[0] > 1 && s.getGrade() > 0) cnt++;
                else if(s.gradeAmount[0] == 1 && s.getGrade() >= 0) cnt++;
            }
            for(int i = 0; i < cnt; i++) {
                boolean b = WakTower.game.battleRandom.randomBoolean();
                if(b) ActionHandler.set(new GainBlockAction(dopamine, dopamine.tempClone.calculateValue(), true));
                else ActionHandler.set(new DamageAction(new DamageInfo(dopamine.tempClone.calculatedAtk(), dopamine, DamageType.NORMAL), TargetType.RANDOM_ENEMY));
            }
            //todo block effect
        }
    }
}
