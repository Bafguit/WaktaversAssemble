package com.fastcat.assemble.enemies;

import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.PlayAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.EnemyAction;
import com.fastcat.assemble.utils.TargetType;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class Enemy1 extends AbstractEnemy {

    public Enemy1() {
        super("Enemy1");
    }

    @Override
    protected boolean actTurn() {
        next(new PlayAnimationAction(this, "attack"));
        next(new DamageAction(new DamageInfo(action.amount, this, DamageType.NORMAL), action.target));
        return true;
    }

    @Override
    public EnemyAction getAction() {
        return new EnemyAction(EnemyAction.ActionType.ATTACK).setAmount(5).setTarget(TargetType.RANDOM_MEMBER);
    }
    
}
