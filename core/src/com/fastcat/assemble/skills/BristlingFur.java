package com.fastcat.assemble.skills;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.abstrcts.AbstractSkill;

public class BristlingFur extends AbstractSkill {

    public BristlingFur(Sprite s) {
        super(SkillTarget.SELF, 1);
        img = s;
    }

    @Override
    protected void useSkill() {

    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2(0, 1);
    }
}
