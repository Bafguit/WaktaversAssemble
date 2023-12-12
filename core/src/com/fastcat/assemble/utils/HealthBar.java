package com.fastcat.assemble.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractUI.BasisType;
import com.fastcat.assemble.abstracts.AbstractUI.TempUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.handlers.FontHandler.FontData;
import com.fastcat.assemble.interfaces.OnHealthUpdated;

public class HealthBar implements OnHealthUpdated {

    private static final FontData font = FontHandler.HEALTH;
    private TempUI line, hbMid, hbLeft, hbRight;
    private Sprite yetMid, yetRight, yetLeft;

    public AbstractEntity entity;
    public float x, y, width, yetWidth;
    public float timer, tick;

    public HealthBar(AbstractEntity entity) {
        this.entity = entity;
        line = new TempUI(FileHandler.getTexture("ui/hb_line"));
        line.basis = BasisType.CENTER_LEFT;
        hbLeft = new TempUI(FileHandler.getTexture("ui/hb_left"));
        hbLeft.basis = BasisType.CENTER_LEFT;
        hbMid = new TempUI(FileHandler.getTexture("ui/hb_mid"));
        hbMid.basis = BasisType.CENTER_LEFT;
        hbRight = new TempUI(FileHandler.getTexture("ui/hb_right"));
        hbRight.basis = BasisType.CENTER_LEFT;
        yetMid = new Sprite(FileHandler.getTexture("ui/hb_yet_mid"));
        yetRight = new Sprite(FileHandler.getTexture("ui/hb_yet_right"));
        yetLeft = new Sprite(FileHandler.getTexture("ui/hb_yet_left"));
        yetWidth = hbMid.originWidth;
        entity.healthUpdatedListener.add(this);
    }
    
    public HealthBar(AbstractEntity entity, float x, float y) {
        this(entity);
        setPosition(x, y);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        line.setPosition(x, y);
        hbLeft.setPosition(x + 2, y);
        hbMid.setPosition(x + 10, y);
    }

    public void update() {
        line.update();
        hbMid.update();
        hbLeft.update();
        hbRight.update();
    }

    public void render(SpriteBatch sb) {
        if(entity.health > 0) {
            float h = (entity.health - 1) / (entity.maxHealth - 1);
            width = hbMid.width * h;

            sb.draw(yetMid, hbMid.x, hbMid.y, yetWidth, hbMid.height);
            sb.draw(yetLeft, hbLeft.x, hbLeft.y, hbLeft.width, hbLeft.height);
            sb.draw(yetRight, hbMid.x + yetWidth, hbRight.y, hbRight.width, hbRight.height);

            sb.draw(hbLeft.img, hbLeft.x, hbLeft.y, hbLeft.width, hbLeft.height);
            sb.draw(hbMid.img, hbMid.x, hbMid.y, width, hbMid.height);
            sb.draw(hbRight.img, hbMid.x + width, hbRight.y, hbRight.width, hbRight.height);
            
            sb.draw(line.img, line.x, line.y, line.width, line.height);

            FontHandler.renderCenter(sb, font, entity.health + "/" + entity.maxHealth, line.x, line.y, line.width);

            if(timer > 0) {
                timer -= WakTower.tick / 2;
                if(timer <= 0) timer = 0;
            } else if(yetWidth > width) {
                yetWidth -= 60 * InputHandler.scaleX * WakTower.tick;
            }
            
            if(yetWidth <= width) yetWidth = width;
        }
        
    }

    private void yetReset() {
        timer = 1f;
    }

    @Override
    public void onHealthUpdated(int amount) {
        yetReset();
    }
}
