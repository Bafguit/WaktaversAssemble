package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MouseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;

public class CharacterButton extends AbstractUI {

    private final Array<SubText> sub = new Array<>();

    public BattleScreen screen;
    public AbstractEntity character;
    public TileSquare tile;

    public CharacterButton(BattleScreen screen) {
        this(screen, MouseAdventure.game.player);
    }

    public CharacterButton(BattleScreen screen, AbstractEntity dice) {
        super(FileHandler.character.get("Test"));
        pix();
        this.character = dice;
        this.screen = screen;
        clickable = false;
        sub.add(new SubText(dice.desc));
        setPosition(-10000, -10000);
    }

    @Override
    protected void updateButton() {
        clickable = screen.phase != BattleScreen.BattlePhase.DIRECTION;
        if(tile != null) {
            setPosition(tile.originX, tile.originY);
        }
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled && character != null) {
            if (showImg) sb.draw(character.img, x, y, width, height);
        }
    }

    public void reset() {
        setPosition(-10000, -10000);
        pos.set(-100, -100);
        if(tile != null) {
            tile.character = null;
            tile = null;
        }
    }
}
