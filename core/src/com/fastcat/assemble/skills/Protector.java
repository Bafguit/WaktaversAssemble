package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.ApplyStatusAction;
import com.fastcat.assemble.actions.PercentAttackAction;
import com.fastcat.assemble.status.IncreaseDefenseStatus;

public class Protector extends AbstractSkill {

    private static final String ID = "Protector";

    public Protector() {
        super(ID, SkillTarget.SELF, 1);
    }

    @Override
    protected void useSkill() {
        top(new ApplyStatusAction(MousseAdventure.game.player, new IncreaseDefenseStatus(ID, 60, 1), false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2(0, 1);
    }
}
