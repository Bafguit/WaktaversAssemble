package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class SkillDisplay extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE;
    private final Sprite frame;

    public AbstractSkill skill;

    public SkillDisplay(AbstractSkill s) {
        super(FileHandler.getTexture("ui/skillBg"));
        skill = s;
        frame = new Sprite(FileHandler.getTexture("ui/skillFrame"));
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            sb.draw(img, x, y, width, height);
            if(!skill.canUse()) sb.setColor(Color.DARK_GRAY);
            sb.draw(skill.img, x, y, width, height);
            sb.setColor(Color.WHITE);
            sb.draw(frame, x, y, width, height);
        }
    }

    @Override
    protected void foreUpdate() {
        clickable = skill.canUse() && !ActionHandler.isRunning;
    }

    @Override
    protected void updateButton() {
        
    }

    @Override
    public void onClick() {
        skill.use();
    }
}
