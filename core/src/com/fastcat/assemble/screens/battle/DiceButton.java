package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractDice;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.actions.UseSkillAction;
import com.fastcat.assemble.dices.basic.Mousse;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;

public class DiceButton extends AbstractUI {

    public BattleScreen screen;
    public AbstractDice dice;
    public TileSquare tile;
    public int index;

    public DiceButton(BattleScreen screen, int index) {
        this(screen, new Mousse(), index);
    }

    public DiceButton(BattleScreen screen, AbstractDice dice, int index) {
        super(FileHandler.dice.get("Dice"));
        pix();
        is3D = false;
        this.dice = dice;
        this.index = index;
        this.screen = screen;
        clickable = screen.phase == BattleScreen.BattlePhase.SKILL;
    }

    @Override
    protected void updateButton() {
        overable = screen.phase != BattleScreen.BattlePhase.DIRECTION && screen.phase != BattleScreen.BattlePhase.MOVE;
        clickable = !ActionHandler.isRunning && screen.phase == BattleScreen.BattlePhase.SKILL && dice.canUse();
        if(overable && over) {
            MousseAdventure.subText = this;
        }
    }

    @Override
    protected SubText getSubText() {
        AbstractSkill s = null;
        if(dice.getNumber() >= 0) s = dice.getSkill();
        return s != null ? new SubText(s.name, s.desc) : subs;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled && dice != null) {
            if (!dice.canUse()) sb.setColor(Color.GRAY);
            sb.draw(dice.getNumber() < 0 ? dice.img : dice.getSkill().img, x, y, width, height);
        }
    }

    @Override
    protected void onClick() {
        ActionHandler.bot(new UseSkillAction(dice.getSkill()));
    }

    public void reset() {
        dice.reset();
    }
}
