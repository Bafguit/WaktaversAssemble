package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.InputHandler;

public class CharacterButton extends AbstractUI {

    private final Array<SubText> sub = new Array<>();

    public final HealthBar hb;

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
        hb = new HealthBar(dice, this);
        sub.add(new SubText(dice.name, dice.desc));
        setPosition(-10000, -10000);
    }

    @Override
    protected void updateButton() {
        if(tile != null) {
            setPosition(character.pos.x, character.pos.y);
        }
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled && character != null) {
            if (showImg) character.render(sb);
        }
    }

    public void reset() {
        character.resetAttributes();
        setPosition(-10000, -10000);
        pos.set(-100, -100);
        if(tile != null) {
            tile.removeEntity();
            tile = null;
        }
    }
}
