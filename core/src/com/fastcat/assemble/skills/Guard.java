package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.BaseAttackAction;

public class Guard extends AbstractSkill {

    private static final String ID = "Guard";

    public Guard() {
        super(ID, SkillTarget.AMOUNT, 1);
    }

    @Override
    protected void useSkill() {
        top(new BaseAttackAction(targets, MousseAdventure.game.player, AbstractEntity.DamageType.PHYSICAL, false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2(0, 1);
    }
}
