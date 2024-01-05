package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.interfaces.OnAnimationFinished;
import com.fastcat.assemble.members.Hikiking;
import com.fastcat.assemble.utils.DamageInfo;

public class MemberHikikingAction extends AbstractAction implements OnAnimationFinished {

    public Hikiking member;

    private int count = 0;

    public MemberHikikingAction(Hikiking member) {
        super(2f);
        this.member = member;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            member.animation.setAnimation("skill_0");
            member.animation.setSingleAnimationListener(this, 0.06f);
            amount = WakTower.game.battle.enemies.size();
            for(int i = 0; i < member.value; i++) {
                AbstractEnemy e = WakTower.game.battle.enemies.get(WakTower.game.battleRandom.random(0, amount - 1));
                target.add(e);
            }
        }
    }

    @Override
    public void onAnimationFinished(String key) {
        if(count < amount) {
            duration += 0.15f;
            if(key.equals("skill_0") || key.equals("skill_1b")) {
                member.animation.addAnimation("skill_1a");
                member.animation.setSingleAnimationListener(this, 0.06f);
            } else {
                member.animation.addAnimation("skill_1b");
                member.animation.setSingleAnimationListener(this, 0.06f);
            }
        } else {
            member.animation.addAnimation("skill_2");
            member.animation.addAnimation("idle");
        }
    }

    @Override
    public void onSingleFinished(String key) {
        DamageInfo info = new DamageInfo(member.calculatedAtk(), WakTower.game.player, DamageInfo.DamageType.NORMAL);
        target.get(count++).takeDamage(info);
        member.animation.addAnimationFinishedListener(this);
    }
}
