package com.fastcat.assemble.skills;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MouseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.BaseAttackAction;
import com.fastcat.assemble.actions.IncreasedHealAction;

public class BaseHeal extends AbstractSkill {

    public BaseHeal(Sprite s) {
        super(SkillTarget.SELF, 1);
        img = s;
    }

    @Override
    protected void useSkill() {
        top(new IncreasedHealAction(targets, MouseAdventure.game.player, 100, false));
    }

    @Override
    protected void defineRange() {}
}
