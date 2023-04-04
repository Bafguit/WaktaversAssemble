package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.IncPerAttackAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.utils.Vector2i;

public class ExecutionMode extends AbstractSkill {

    private static final String ID = "ExecutionMode";

    public ExecutionMode() {
        super(ID, SkillTarget.AMOUNT, 1);
    }

    @Override
    protected void useSkill() {
        ActionHandler.top(new IncPerAttackAction(targets, MousseAdventure.game.player, 80, 70, AbstractEntity.DamageType.PHYSICAL, 1, true));
        ActionHandler.top(new IncPerAttackAction(targets, MousseAdventure.game.player, 80, 30, AbstractEntity.DamageType.TRUE, 1, true));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2i(0, 1);
    }
}
