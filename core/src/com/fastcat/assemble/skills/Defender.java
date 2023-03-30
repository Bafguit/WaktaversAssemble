package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.ApplyStatusAction;
import com.fastcat.assemble.actions.BaseAttackAction;
import com.fastcat.assemble.actions.PercentAttackAction;
import com.fastcat.assemble.status.IncreaseDefenseStatus;

public class Defender extends AbstractSkill {

    private static final String ID = "Defender";

    public Defender() {
        super(ID, SkillTarget.AMOUNT, 1);
    }

    @Override
    protected void useSkill() {
        top(new PercentAttackAction(targets, MousseAdventure.game.player, 50, AbstractEntity.DamageType.PHYSICAL, false));
        top(new ApplyStatusAction(MousseAdventure.game.player, new IncreaseDefenseStatus(ID, 40, 1), true));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2(0, 1);
    }
}
