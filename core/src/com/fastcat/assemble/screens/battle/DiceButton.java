package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstrcts.AbstractDice;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.actions.UseSkillAction;
import com.fastcat.assemble.dices.basic.NormalDice;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;

import static com.fastcat.assemble.abstrcts.AbstractUI.TrackType.CENTER;
import static com.fastcat.assemble.abstrcts.AbstractUI.TrackType.NONE;

public class DiceButton extends AbstractUI {

    public BattleScreen screen;
    public AbstractDice dice;
    public TileSquare tile;
    public int index;

    public DiceButton(BattleScreen screen, int index) {
        this(screen, new NormalDice(), index);
    }

    public DiceButton(BattleScreen screen, AbstractDice dice, int index) {
        super(FileHandler.dice.get("Dice"));
        pix();
        this.dice = dice;
        this.index = index;
        this.screen = screen;
        clickable = screen.phase == BattleScreen.BattlePhase.SKILL;
    }

    @Override
    protected void updateButton() {
        overable = screen.phase != BattleScreen.BattlePhase.DIRECTION;
        clickable = screen.phase == BattleScreen.BattlePhase.SKILL && dice.getSkill().canUse();
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled && dice != null) {
            if (!dice.getSkill().canUse()) sb.setColor(Color.GRAY);
            if (showImg) sb.draw(dice.getSkill().img, x, y, width, height);
        }
    }

    @Override
    protected void onClick() {
        ActionHandler.bot(new UseSkillAction(dice.getSkill()));
    }

    @Override
    protected void onClickEnd() {
        if(tracking && screen.overTile != null) {
            if(screen.overTile.character != null && screen.overTile.dice == null) {
                setPosition(screen.overTile.originX, screen.overTile.originY);
                if (tile != null) {
                    tile.dice = null;
                }
                tile = screen.overTile;
                tile.dice = this;
            } else if(tile != null) {
                setPosition(tile.originX, tile.originY);
            } else {
                setPosition(60, 920 - 100 * index);
                if(tile != null) {
                    tile.dice = null;
                    tile = null;
                }
            }
        } else {
            setPosition(60, 920 - 100 * index);
            if(tile != null) {
                tile.dice = null;
                tile = null;
            }
        }
    }

    public void reset() {
        dice.reset();
    }
}
