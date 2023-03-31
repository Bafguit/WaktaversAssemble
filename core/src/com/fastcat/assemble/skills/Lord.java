package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.PercentAttackAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.utils.Vector2i;

public class Lord extends AbstractSkill {

    private static final String ID = "Lord";

    public Lord() {
        super(ID, SkillTarget.AMOUNT, 7);
    }

    @Override
    protected void useSkill() {
        ActionHandler.top(new PercentAttackAction(targets, MousseAdventure.game.player, 80, AbstractEntity.DamageType.PHYSICAL, false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2i(0, 3);
        range[1] = new Vector2i(0, 2);
        range[2] = new Vector2i(-1, 1);
        range[3] = new Vector2i(0, 1);
        range[4] = new Vector2i(1, 1);
        range[5] = new Vector2i(-1, 0);
        range[6] = new Vector2i(1, 0);
    }
}
