package com.fastcat.assemble.skills;

import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.utils.Vector2i;

public class AssaultOrderB extends AbstractSkill {

    private static final String ID = "AssaultOrderB";

    public AssaultOrderB() {
        super(ID, SkillTarget.NONE, 1);
    }

    @Override
    protected void useSkill() {
        //top(new PercentAttackAction(targets, MousseAdventure.game.player, 70, AbstractEntity.DamageType.PHYSICAL, 1, false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2i(0, 1);
    }
}
