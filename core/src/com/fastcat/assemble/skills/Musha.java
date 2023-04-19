package com.fastcat.assemble.skills;

import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.actions.PercentAttackAndHealAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.utils.Vector2i;

public class Musha extends AbstractSkill {

    private static final String ID = "Musha";

    public Musha() {
        super(ID, SkillTarget.AMOUNT, 1);
    }

    @Override
    protected void useSkill() {
        ActionHandler.top(new PercentAttackAndHealAction(targets, MousseAdventure.game.player, 100, AbstractEntity.DamageType.PHYSICAL, 1, 70, false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2i(0, 1);
    }
}
