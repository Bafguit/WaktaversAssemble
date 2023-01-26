package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;

import static com.fastcat.assemble.screens.battle.BattleScreen.BattlePhase.CARD;
import static com.fastcat.assemble.screens.battle.BattleScreen.BattlePhase.DEPLOY;

public class TileSquare extends AbstractUI {

    public BattleScreen screen;
    public CharacterButton character;
    public DiceButton dice;

    public TileSquare(BattleScreen screen) {
        super(FileHandler.ui.get("TILE"));
        this.screen = screen;
        clickable = false;
    }

    @Override
    protected void updateButton() {
        overable = screen.phase == CARD ? (character != null && screen.tracking != null) : (screen.phase == DEPLOY && screen.tracking != null);
        if(screen.tracking != null && over) {
            screen.overTile = this;
        }
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if (overable) {
                if(over && character != null) sb.setColor(Color.LIGHT_GRAY);
                else if(!over) sb.setColor(Color.DARK_GRAY);
            } else sb.setColor(Color.DARK_GRAY);
            if (showImg) sb.draw(img, x, y, width, height);
        }
    }
}
