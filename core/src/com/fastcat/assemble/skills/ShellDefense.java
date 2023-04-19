package com.fastcat.assemble.skills;

import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.actions.ApplyStatusAction;
import com.fastcat.assemble.status.IncreaseDefenseStatus;
import com.fastcat.assemble.utils.Vector2i;

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
        range[0] = new Vector2i(0, 1);
    }
}
