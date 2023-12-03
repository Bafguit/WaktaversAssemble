package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class PlayerDisplay extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE;
    private final Sprite frame;

    public AbstractSkill skill;
    private float timer = 0f;

    public PlayerDisplay() {
        super(FileHandler.getTexture("ui/mediumBlank"));
        frame = new Sprite(FileHandler.getTexture("ui/skillFrame"));
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            sb.draw(img, x, y, width, height);
            sb.draw(skill.img, x, y, width, height);
            sb.draw(frame, x, y);

            if(timer >= 1f) {
                //효과 설명 출력
            }
        }
    }

    @Override
    protected void foreUpdate() {
        clickable = skill.canUse() && !ActionHandler.isRunning;
    }

    @Override
    protected void updateButton() {
        if(over) {
            if(timer < 1f) {
                timer += WakTower.tick / 0.5f;
            }
        } else timer = 0f;
    }

    @Override
    public void onClick() {
        skill.use();
    }
}
