package com.fastcat.assemble.screens.temp;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.character.TestChar;
import com.fastcat.assemble.handlers.FileHandler;

import static com.fastcat.assemble.abstrcts.AbstractUI.TrackType.CENTER;
import static com.fastcat.assemble.abstrcts.AbstractUI.TrackType.NONE;

public class CharacterButton extends AbstractUI {

    public BattleScreen screen;
    public AbstractEntity character;
    public TileSquare tile;
    public int index;

    public CharacterButton(BattleScreen screen, int index) {
        this(screen, new TestChar(), index);
    }

    public CharacterButton(BattleScreen screen, AbstractEntity dice, int index) {
        super(FileHandler.character.get("Test"));
        pix();
        this.character = dice;
        this.index = index;
        this.screen = screen;
        clickEnd = screen.phase == BattleScreen.BattlePhase.DEPLOY;
        trackable = clickEnd ? CENTER : NONE;
    }

    @Override
    protected void updateButton() {
        clickEnd = screen.phase == BattleScreen.BattlePhase.DEPLOY;
        trackable = clickEnd ? CENTER : NONE;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled && character != null) {
            if (overable && !over) sb.setColor(Color.LIGHT_GRAY);
            if (showImg) sb.draw(character.img, x, y, width, height);
        }
    }

    @Override
    protected void onClickEnd() {
        if(tracking && screen.overTile != null) {
            if(screen.overTile.character == null) {
                setPosition(screen.overTile.originX, screen.overTile.originY);
                if (tile != null) {
                    tile.character = null;
                }
                tile = screen.overTile;
                tile.character = this;
            } else if(tile != null) {
                setPosition(tile.originX, tile.originY);
            } else {
                setPosition(150 * (index + 1), 270);
                if(tile != null) {
                    tile.character = null;
                    tile = null;
                }
            }
        } else {
            setPosition(150 * (index + 1), 270);
            if(tile != null) {
                tile.character = null;
                tile = null;
            }
        }
    }

    public void reset() {
        setPosition(150 * (index + 1), 270);
        if(tile != null) {
            tile.character = null;
            tile = null;
        }
    }
}
