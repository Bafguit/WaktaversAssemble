package com.fastcat.assemble.skills;

import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.actions.ApplyStatusAction;
import com.fastcat.assemble.status.IncreaseNextAttackStatus;
import com.fastcat.assemble.utils.Vector2i;

public class AssaultOrderAttack extends AbstractSkill {

    private static final String ID = "AssaultOrderAttack";

    public AssaultOrderAttack() {
        super(ID, SkillTarget.SELF, 1);
    }

    @Override
    protected void useSkill() {
        top(new ApplyStatusAction(MousseAdventure.game.player, new IncreaseNextAttackStatus(id, 70), true));
        //top(new PercentAttackAction(targets, MousseAdventure.game.player, 70, AbstractEntity.DamageType.PHYSICAL, 1, false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2i(0, 1);
    }
}
