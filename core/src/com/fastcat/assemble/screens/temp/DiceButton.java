package com.fastcat.assemble.screens.temp;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstrcts.AbstractDice;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.dices.basic.NormalDice;
import com.fastcat.assemble.dices.legend.Fraud3;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class DiceButton extends AbstractUI {

    public DiceRollScreen screen;
    public AbstractDice dice;
    public TileSquare tile;
    public int index;

    public DiceButton(DiceRollScreen screen, int index) {
        this(screen, new NormalDice(), index);
    }

    public DiceButton(DiceRollScreen screen, AbstractDice dice, int index) {
        super(FileHandler.dice.get("Dice"));
        pix();
        this.dice = dice;
        this.index = index;
        this.screen = screen;
        trackable = TrackType.CLICKED;
    }

    @Override
    protected void updateButton() {

    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled && dice != null) {
            if (overable && !over) sb.setColor(Color.LIGHT_GRAY);
            if (showImg) sb.draw(dice.img, x, y, width, height);
        }
    }

    @Override
    protected void onClickEnd() {
        if(tracking && screen.overTile != null) {
            if(screen.overTile.dice == null) {
                setPosition(screen.overTile.originX, screen.overTile.originY);
                if (tile != null) {
                    tile.dice = null;
                }
                tile = screen.overTile;
                tile.dice = this;
            } else if(tile != null) {
                setPosition(tile.originX, tile.originY);
            } else {
                setPosition(150 * (index + 1), 810);
                if(tile != null) {
                    tile.dice = null;
                    tile = null;
                }
            }
        } else {
            setPosition(150 * (index + 1), 810);
            if(tile != null) {
                tile.dice = null;
                tile = null;
            }
        }
    }
}
