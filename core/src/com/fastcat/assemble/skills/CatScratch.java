package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.IncreasedAttackAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class CatScratch extends AbstractSkill {

    private static final String ID = "CatScratch";

    public CatScratch() {
        super(ID, SkillTarget.AMOUNT, 1);
    }

    @Override
    protected void useSkill() {
        ActionHandler.top(new IncreasedAttackAction(targets, MousseAdventure.game.player, 75, AbstractEntity.DamageType.MAGIC, false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2(0, 1);
    }
}
