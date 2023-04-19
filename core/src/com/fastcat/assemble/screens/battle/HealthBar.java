package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;

public class HealthBar extends AbstractUI {

    private final Sprite back;
    public AbstractUI ui;
    public AbstractEntity entity;

    public HealthBar(AbstractEntity entity, AbstractUI ui) {
        super(FileHandler.ui.get("HB"), -1000, -1000, 70, 5);
        back = FileHandler.ui.get("HB_B");
        clickable = false;
        overable = false;
        this.ui = ui;
        this.entity = entity;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        setPosition(entity.pos.x, entity.pos.y - 40);
        update();
        if(entity.isAlive() && entity.health < entity.maxHealth) {
            sb.draw(back, x, y, width, height);
            sb.draw(img, x, y, 0, 0, width, height, ((float) entity.health) / ((float) entity.maxHealth), 1, 0);
        }
    }
}
