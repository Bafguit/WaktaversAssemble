package com.fastcat.assemble.screens.mainmenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractChar;
import com.fastcat.assemble.abstracts.AbstractGame;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.battles.TestBattle;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.screens.battle.BattleScreen;
import com.fastcat.assemble.utils.Vector2i;

import static com.fastcat.assemble.WakTower.battleScreen;

public class CardDisplay extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE;

    public AbstractChar member;
    public Vector2i originPos;

    public CardDisplay() {
        super(FileHandler.getTexture("ui/temp"));
        trackable = TrackType.CENTER;
        fluid = true;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if (overable && !over) sb.setColor(Color.LIGHT_GRAY);
            sb.draw(img, x, y, width, height);
            FontHandler.renderCenter(sb, font, "턴 종료", x + width * 0.5f, y + height * 0.5f);
        }
    }

    @Override
    public void onClick() {
        
    }
}
