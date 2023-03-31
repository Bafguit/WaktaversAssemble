package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.BaseAttackAction;
import com.fastcat.assemble.utils.Vector2i;

public class ArtsFighter extends AbstractSkill {

    private static final String ID = "ArtsFighter";

    public ArtsFighter() {
        super(ID, SkillTarget.AMOUNT, 1);
    }

    @Override
    protected void useSkill() {
        top(new BaseAttackAction(targets, MousseAdventure.game.player, AbstractEntity.DamageType.MAGIC, false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2i(0, 1);
    }
}
