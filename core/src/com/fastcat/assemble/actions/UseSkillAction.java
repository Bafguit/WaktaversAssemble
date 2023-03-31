package com.fastcat.assemble.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.abstrcts.AbstractSkill.SkillTarget;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.screens.battle.BattleScreen.BattlePhase;

import static com.fastcat.assemble.MousseAdventure.battleScreen;

public class UseSkillAction extends AbstractAction {

    private final AbstractSkill skill;

    public UseSkillAction(AbstractSkill s) {
        super(2);
        this.skill = s;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(skill.target == SkillTarget.MOVE) {
                battleScreen.phase = BattlePhase.MOVE;
                battleScreen.dirSkill = skill;
            } else if(skill.target == SkillTarget.AMOUNT || skill.target == SkillTarget.ALL) {
                battleScreen.phase = BattlePhase.DIRECTION;
                battleScreen.dirSkill = skill;
            } else {
                skill.beforeUse();
                skill.use();
                isDone = true;
            }
        }

        if(battleScreen.phase == BattlePhase.DIRECTION || battleScreen.phase == BattlePhase.MOVE) {
            duration = 1;
            if(InputHandler.isLeftClick) {
                skill.direction = battleScreen.curDir;
                skill.beforeUse();
                if(battleScreen.phase == BattlePhase.MOVE && skill.toTile != null) {
                    skill.use();
                } else if(skill.targets.size > 0) {
                    skill.use();
                }
                battleScreen.phase = BattlePhase.SKILL;
                isDone = true;
            } else if(InputHandler.isRightClick) {
                battleScreen.phase = BattlePhase.SKILL;
                isDone = true;
            }
        }
    }

    @Override
    protected void renderAction(SpriteBatch sb) {

    }
}
