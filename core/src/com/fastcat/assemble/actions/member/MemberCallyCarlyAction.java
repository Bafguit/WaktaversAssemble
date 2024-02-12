package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.members.CallyCarly;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class MemberCallyCarlyAction extends AbstractAction {

    public CallyCarly callycarly;

    public MemberCallyCarlyAction(CallyCarly callycarly) {
        super(0.35f);
        this.callycarly = callycarly;
        tar = TargetType.RANDOM_ENEMY;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            callycarly.animation.setAnimation("skill");
            callycarly.animation.addAnimation("idle");
            DamageInfo info = new DamageInfo(callycarly.tempClone, DamageType.NORMAL);
            info.damage = info.member.calculatedAtk();
            for(AbstractRelic item : WakTower.game.relics) {
                item.onAttack(info, target);
            }
            for(AbstractMember c : WakTower.game.battle.members) {
                c.onAttack(info, target);
            }
            for(AbstractStatus s : callycarly.status) {
                s.onAttack(info, target);
            }

            int damage = target.get(0).takeDamage(info);

            if(damage > 0) ActionHandler.set(new GainBlockAction(callycarly, damage, true));
            //todo block effect
        }
    }
}
