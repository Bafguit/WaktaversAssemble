package com.fastcat.assemble.skills;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MouseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.IncreasedAttackAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class CatScratch extends AbstractSkill {

    public CatScratch(Sprite s) {
        super(SkillTarget.AMOUNT, 1);
        img = s;
    }

    @Override
    protected void useSkill() {
        ActionHandler.top(new IncreasedAttackAction(targets, MouseAdventure.game.player, 75, AbstractEntity.DamageType.ARTS, false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2(0, 1);
    }
}
