package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.actions.EndTurnAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.utils.Vector2i;

public class TurnEndButton extends AbstractUI {

    private final FontHandler.FontData enabledFont = FontHandler.TURN_CHANGE.cpy();
    private final FontHandler.FontData disabledFont = FontHandler.TURN_CHANGE.cpy();
    private final Sprite disabledImg;

    private Vector2i[] p = new Vector2i[3];

    public TurnEndButton() {
        super(FileHandler.getTexture("ui/tile"));
        setPosition(960, 400);
        disabledImg = new Sprite(FileHandler.getTexture("ui/tile"));
        data = DataHandler.getInstance().uiData.get("turnEndButton");

        p[0] = new Vector2i(800, 450);
        p[1] = new Vector2i(1280, 720);
        p[2] = new Vector2i(1600, 900);
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if (overable && !over) sb.setColor(Color.LIGHT_GRAY);
            if(clickable) {
                sb.draw(img, x, y, width, height);
                FontHandler.renderCenter(sb, enabledFont, data.text[0], localX, localY);
            } else {
                sb.draw(disabledImg, x, y, width, height);
                FontHandler.renderCenter(sb, disabledFont, data.text[0], localX, localY);
            }
        }
    }

    @Override
    protected void onClick() {
        clickable = false;
        ActionHandler.bot(new EndTurnAction(true));

        /*pc++;
        if(pc == 3) pc = 0;
        Vector2i v = p[pc];
        Gdx.graphics.setWindowedMode(v.x, v.y);
        InputHandler.getInstance().updateScale();*/
    }
}
