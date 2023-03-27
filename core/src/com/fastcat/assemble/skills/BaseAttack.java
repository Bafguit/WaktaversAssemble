package com.fastcat.assemble.skills;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MouseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.BaseAttackAction;

public class BaseAttack extends AbstractSkill {

    public BaseAttack(Sprite s) {
        super(SkillTarget.AMOUNT, 1);
        img = s;
    }

    @Override
    protected void useSkill() {
        top(new BaseAttackAction(targets, MouseAdventure.game.player, AbstractEntity.DamageType.PHYSICAL, false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2(0, 1);
    }
}
