package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.IncPerAttackAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class ExecutionMode extends AbstractSkill {

    private static final String ID = "ExecutionMode";

    public ExecutionMode() {
        super(ID, SkillTarget.AMOUNT, 1);
    }

    @Override
    protected void useSkill() {
        ActionHandler.top(new IncPerAttackAction(targets, MousseAdventure.game.player, 80, 70, AbstractEntity.DamageType.PHYSICAL, true));
        ActionHandler.top(new IncPerAttackAction(targets, MousseAdventure.game.player, 80, 30, AbstractEntity.DamageType.TRUE, true));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2(0, 1);
    }
}
