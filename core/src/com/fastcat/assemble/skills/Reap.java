package com.fastcat.assemble.skills;

import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.actions.IncreasedAttackAndHealAction;
import com.fastcat.assemble.utils.Vector2i;

public class Reap extends AbstractSkill {

    private static final String ID = "Reap";

    public Reap() {
        super(ID, SkillTarget.AMOUNT, 3);
        targetAmount = 3;
    }

    @Override
    protected void useSkill() {
        top(new IncreasedAttackAndHealAction(targets, MousseAdventure.game.player, 70, AbstractEntity.DamageType.PHYSICAL, 3, 50, true));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2i(-1, 1);
        range[1] = new Vector2i(0, 1);
        range[2] = new Vector2i(1, 1);
    }
}
