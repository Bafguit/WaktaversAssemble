package com.fastcat.assemble.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.abstracts.AbstractSkill.SkillTarget;
import com.fastcat.assemble.screens.battle.BattleScreen.BattlePhase;

import static com.fastcat.assemble.MousseAdventure.battleScreen;

public class SetDirectionAction extends AbstractAction {

    private final AbstractSkill skill;

    public SetDirectionAction(AbstractSkill s) {
        super(2);
        this.skill = s;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(skill.target == SkillTarget.AMOUNT || skill.target == SkillTarget.ALL) {
                battleScreen.phase = BattlePhase.DIRECTION;
                battleScreen.dirSkill = skill;
            }
        }

        if(battleScreen.phase == BattlePhase.DIRECTION) {
            duration = 1;
        } else {
            skill.use();
            isDone = true;
        }
    }

    @Override
    protected void renderAction(SpriteBatch sb) {

    }
}
