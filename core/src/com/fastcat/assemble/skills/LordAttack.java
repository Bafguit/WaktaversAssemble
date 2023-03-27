package com.fastcat.assemble.skills;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MouseAdventure;
import com.fastcat.assemble.abstrcts.AbstractBattle;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractGame;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.PercentAttackAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class LordAttack extends AbstractSkill {

    public LordAttack(Sprite s) {
        super(SkillTarget.AMOUNT, 7);
        img = s;
    }

    @Override
    protected void useSkill() {
        ActionHandler.top(new PercentAttackAction(targets, MouseAdventure.game.player, 200, AbstractEntity.DamageType.PHYSICAL, false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2(0, 3);
        range[1] = new Vector2(0, 2);
        range[2] = new Vector2(-1, 1);
        range[3] = new Vector2(0, 1);
        range[4] = new Vector2(1, 1);
        range[5] = new Vector2(-1, 0);
        range[6] = new Vector2(1, 0);
    }
}