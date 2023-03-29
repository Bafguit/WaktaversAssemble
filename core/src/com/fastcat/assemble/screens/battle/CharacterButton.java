package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;

public class CharacterButton extends AbstractUI {

    private final Array<SubText> sub = new Array<>();

    public BattleScreen screen;
    public AbstractEntity character;
    public TileSquare tile;

    public CharacterButton(BattleScreen screen) {
        this(screen, MousseAdventure.game.player);
    }

    public CharacterButton(BattleScreen screen, AbstractEntity dice) {
        super(FileHandler.character.get("Test"));
        pix();
        this.character = dice;
        this.screen = screen;
        clickable = false;
        sub.add(new SubText(dice.name, dice.desc));
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
        character.resetAttributes();
        setPosition(-10000, -10000);
        pos.set(-100, -100);
        if(tile != null) {
            tile.character = null;
            tile = null;
        }
    }
}
