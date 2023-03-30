package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.ApplyStatusAction;
import com.fastcat.assemble.actions.PercentAttackAction;
import com.fastcat.assemble.status.IncreaseDefenseStatus;

public class ShellDefense extends AbstractSkill {

    private static final String ID = "ShellDefense";

    public ShellDefense() {
        super(ID, SkillTarget.SELF, 1);
    }

    @Override
    protected void useSkill() {
        //TODO 공격불가 추가
        top(new ApplyStatusAction(MousseAdventure.game.player, new IncreaseDefenseStatus(ID, 130, 1), true));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2(0, 1);
    }
}
