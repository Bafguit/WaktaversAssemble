package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.utils.SpineAnimation;

public class AbstractPlayer extends AbstractEntity{

    public AbstractPlayer(String id, int attack, int health, int def, int res) {
        super(id, attack, health, def, res, EntityRarity.OPERATOR);
        isPlayer = true;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!isDead) {
            Color c = sb.getColor();
            sb.setColor(animColor);
            animation.render(sb, pos.x, pos.y - 35, isFlip);
            sb.setColor(c);
        }
    }
}
