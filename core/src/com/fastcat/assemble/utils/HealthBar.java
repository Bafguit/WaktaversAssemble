package com.fastcat.assemble.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractUI.BasisType;
import com.fastcat.assemble.abstracts.AbstractUI.TempUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.handlers.FontHandler.FontData;
import com.fastcat.assemble.interfaces.OnHealthUpdated;

public class HealthBar extends Table implements OnHealthUpdated {

    private static final FontData font = FontHandler.HEALTH;
    private Button hbMid, hbLeft, hbRight;
    private Button yetMid, yetRight, yetLeft;
    private Label text;

    private final float maxWidth;

    public AbstractEntity entity;
    public float x, y, width, yetWidth;
    public float timer, tick;

    public HealthBar(AbstractEntity entity) {
        this(entity, 280);
    }

    public HealthBar(AbstractEntity entity, final float width) {
        this.entity = entity;
        Button bgLeft = new Button(new TextureRegionDrawable(FileHandler.getTexture("ui/hb_left")));
        Button bgMid = new Button(new TextureRegionDrawable(FileHandler.getTexture("ui/hb_mid")));
        Button bgRight = new Button(new TextureRegionDrawable(FileHandler.getTexture("ui/hb_right")));
        bgLeft.setColor(0.5f, 0.5f, 0.5f, 1);
        bgMid.setColor(0.5f, 0.5f, 0.5f, 1);
        bgRight.setColor(0.5f, 0.5f, 0.5f, 1);

        hbLeft = new Button(new TextureRegionDrawable(FileHandler.getTexture("ui/hb_left")));
        hbMid = new Button(new TextureRegionDrawable(FileHandler.getTexture("ui/hb_mid")));
        hbRight = new Button(new TextureRegionDrawable(FileHandler.getTexture("ui/hb_right")));
        yetMid = new Button(new TextureRegionDrawable(FileHandler.getTexture("ui/hb_yet_mid")));
        yetRight = new Button(new TextureRegionDrawable(FileHandler.getTexture("ui/hb_yet_right")));
        yetLeft = new Button(new TextureRegionDrawable(FileHandler.getTexture("ui/hb_yet_left")));
        float h = (entity.health - 1) / (entity.maxHealth - 1);
        this.width = width * h;
        yetWidth = width;
        entity.healthBar = this;

        maxWidth = width;

        text = new Label(entity.health + "/" + entity.maxHealth, new LabelStyle(FontHandler.BF_HEALTH, Color.WHITE));
        text.setAlignment(Align.center);

        this.add(bgLeft).left();
        this.add(bgMid).width(width).left();
        this.add(bgRight).left();
        this.row();
        this.add(yetLeft).left().padTop(-16);
        this.add(yetMid).width(width).left().padTop(-16);
        this.add(yetRight).left().padTop(-16);
        this.row();
        this.add(hbLeft).left().padTop(-16);
        this.add(hbMid).width(width).left().padTop(-16);
        this.add(hbRight).left().padTop(-16);
        this.row();
        this.add(text).colspan(3).height(16).padTop(-16).width(width).center();
    }

    @Override
    public void act(float delta) {
        if(entity.health > 0) {
            float h = (entity.health - 1) / (entity.maxHealth - 1);
            width = maxWidth * h;

            hbMid.setWidth(width);
            yetMid.setWidth(yetWidth);

            if(timer > 0) {
                timer -= delta / 2;
                if(timer <= 0) timer = 0;
            } else if(yetWidth > width) {
                yetWidth -= 60 * InputHandler.scaleX * delta;
            }
            
            if(yetWidth <= width) yetWidth = width;
        }
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
    }

    private void yetReset() {
        timer = 1f;
    }

    @Override
    public void onHealthUpdated(int amount) {
        text.setText(entity.health + "/" + entity.maxHealth);
        yetReset();
    }
}
