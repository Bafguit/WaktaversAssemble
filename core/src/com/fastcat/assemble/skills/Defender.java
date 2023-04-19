package com.fastcat.assemble.skills;

import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.actions.ApplyStatusAction;
import com.fastcat.assemble.actions.PercentAttackAction;
import com.fastcat.assemble.status.IncreaseDefenseStatus;
import com.fastcat.assemble.utils.Vector2i;

public class Defender extends AbstractSkill {

    private static final String ID = "Defender";

    public Defender() {
        super(ID, SkillTarget.AMOUNT, 1);
    }

    @Override
    protected void useSkill() {
        top(new PercentAttackAction(targets, MousseAdventure.game.player, 50, AbstractEntity.DamageType.PHYSICAL, 1, false));
        top(new ApplyStatusAction(MousseAdventure.game.player, new IncreaseDefenseStatus(ID, 40, 1), true));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2i(0, 1);
    }
}
