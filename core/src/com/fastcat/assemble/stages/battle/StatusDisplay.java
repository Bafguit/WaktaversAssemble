package com.fastcat.assemble.stages.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;

public class StatusDisplay extends AbstractUI {

    public AbstractStatus status;

    public StatusDisplay(AbstractStatus status) {
        super(FileHandler.getTexture("ui/tile"));
        clickable = false;
        this.status = status;
        basis = BasisType.BOTTOM_LEFT;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if(status != null) {
            sb.draw(status.img, x, y, width, height);
        }
    }
}
