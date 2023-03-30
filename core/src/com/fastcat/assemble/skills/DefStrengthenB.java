package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.ApplyStatusAction;
import com.fastcat.assemble.status.IncreaseDefenseStatus;

public class DefStrengthenB extends AbstractSkill {

    private static final String ID = "DefStrengthenB";

    public DefStrengthenB() {
        super(ID, SkillTarget.SELF, 1);
    }

    @Override
    protected void useSkill() {
        top(new ApplyStatusAction(MousseAdventure.game.player, new IncreaseDefenseStatus(ID, 80, 1), false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2(0, 1);
    }
}
