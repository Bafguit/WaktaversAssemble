package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractStatus;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

import java.util.HashMap;

public class StatusIcon extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE;
    public AbstractStatus status;

    public StatusIcon(AbstractStatus status) {
        super(FileHandler.ui.get("BACK_32"));
        subWay = SubText.SubWay.DOWN;
        this.status = status;
        is3D = false;
    }

    @Override
    protected void updateButton() {
        if(over) MousseAdventure.subText = this;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            sb.draw(status.img, x, y, width, height);
        }
    }

    @Override
    public SubText getSubText() {
        return new SubText(status.name, status.getDescription());
    }
}
