package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.PercentAttackAndHealAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class Musha extends AbstractSkill {

    private static final String ID = "Musha";

    public Musha() {
        super(ID, SkillTarget.AMOUNT, 1);
    }

    @Override
    protected void useSkill() {
        ActionHandler.top(new PercentAttackAndHealAction(targets, MousseAdventure.game.player, 100, AbstractEntity.DamageType.PHYSICAL, 70, false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2(0, 1);
    }
}
