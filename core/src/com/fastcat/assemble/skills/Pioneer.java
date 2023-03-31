package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.PercentAttackAction;
import com.fastcat.assemble.utils.Vector2i;

public class Pioneer extends AbstractSkill {

    private static final String ID = "Pioneer";

    public Pioneer() {
        super(ID, SkillTarget.AMOUNT, 1);
    }

    @Override
    protected void useSkill() {
        top(new PercentAttackAction(targets, MousseAdventure.game.player, 70, AbstractEntity.DamageType.PHYSICAL, false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2i(0, 1);
    }
}
