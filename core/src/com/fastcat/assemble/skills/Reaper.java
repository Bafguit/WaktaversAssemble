package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.PercentAttackAndHealAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.utils.Vector2i;

public class Reaper extends AbstractSkill {

    private static final String ID = "Reaper";

    public Reaper() {
        super(ID, SkillTarget.AMOUNT, 3);
        targetAmount = 3;
    }

    @Override
    protected void useSkill() {
        ActionHandler.top(new PercentAttackAndHealAction(targets, MousseAdventure.game.player, 100, AbstractEntity.DamageType.PHYSICAL, 1, 50, false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2i(-1, 1);
        range[1] = new Vector2i(0, 1);
        range[2] = new Vector2i(1, 1);
    }
}
