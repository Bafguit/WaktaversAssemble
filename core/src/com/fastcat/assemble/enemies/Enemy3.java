package com.fastcat.assemble.enemies;

import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.PlayAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.EnemyAction;
import com.fastcat.assemble.utils.TargetType;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class Enemy3 extends AbstractEnemy {

    public Enemy3() {
        super("Enemy3");
    }

    @Override
    protected boolean actTurn() {
        next(new PlayAnimationAction(this, "attack"));
        next(new DamageAction(new DamageInfo(action.amount, this, DamageType.NORMAL), action.target, true));
        next(new DamageAction(new DamageInfo(action.amount, this, DamageType.NORMAL), action.target, true));
        next(new DamageAction(new DamageInfo(action.amount, this, DamageType.NORMAL), action.target, true));
        return true;
    }

    @Override
    public EnemyAction getAction() {
        return new EnemyAction(EnemyAction.ActionType.ATTACK).setAmount(2).setMultiple(3).setTarget(TargetType.RANDOM_MEMBER);
    }
    
}
