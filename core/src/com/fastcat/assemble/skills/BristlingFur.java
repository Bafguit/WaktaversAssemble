package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.ApplyStatusAction;
import com.fastcat.assemble.status.IncreaseAttackStatus;
import com.fastcat.assemble.status.IncreaseDefenseStatus;
import com.fastcat.assemble.status.IncreaseNextAttackStatus;
import com.fastcat.assemble.utils.Vector2i;

public class BristlingFur extends AbstractSkill {

    private static final String ID = "BristlingFur";

    public BristlingFur() {
        super(ID, SkillTarget.SELF, 1);
    }

    @Override
    protected void useSkill() {
        top(new ApplyStatusAction(MousseAdventure.game.player, new IncreaseDefenseStatus(id, 75, 1), true));
        top(new ApplyStatusAction(MousseAdventure.game.player, new IncreaseNextAttackStatus(id, 75), true));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2i(0, 1);
    }
}
