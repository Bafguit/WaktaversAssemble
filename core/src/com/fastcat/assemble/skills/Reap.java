package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.IncreasedAttackAndHealAction;

public class Reap extends AbstractSkill {

    private static final String ID = "Reap";

    public Reap() {
        super(ID, SkillTarget.AMOUNT, 3);
        targetAmount = 3;
    }

    @Override
    protected void useSkill() {
        top(new IncreasedAttackAndHealAction(targets, MousseAdventure.game.player, 70, AbstractEntity.DamageType.PHYSICAL, 50, true));
        top(new IncreasedAttackAndHealAction(targets, MousseAdventure.game.player, 70, AbstractEntity.DamageType.PHYSICAL, 50, true));
        top(new IncreasedAttackAndHealAction(targets, MousseAdventure.game.player, 70, AbstractEntity.DamageType.PHYSICAL, 50, true));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2(-1, 1);
        range[1] = new Vector2(0, 1);
        range[2] = new Vector2(1, 1);
    }
}
