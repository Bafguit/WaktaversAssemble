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
    private Table hb, yet;

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
        maxWidth = width;
        this.width = width * h;
        yetWidth = this.width;
        entity.healthBar = this;

        Table bg = new Table();
        bg.align(Align.left);
        bg.add(bgLeft).left();
        bg.add(bgMid).width(width).left();
        bg.add(bgRight).left();

        hb = new Table();
        hb.align(Align.left);
        hb.add(hbLeft).left();
        hb.add(hbMid).width(yetWidth).left();
        hb.add(hbRight).left();

        yet = new Table();
        yet.align(Align.left);
        yet.add(yetLeft).left();
        yet.add(yetMid).width(yetWidth).left();
        yet.add(yetRight).left();

        text = new Label(entity.health + "/" + entity.maxHealth, new LabelStyle(FontHandler.BF_HEALTH, Color.WHITE));
        text.setAlignment(Align.center);

        this.add(bg).left();
        this.row();
        this.add(yet).left().padTop(-16);
        this.row();
        this.add(hb).left().padTop(-16);
        this.row();
        this.add(text).height(16).padTop(-16).width(width).center();
    }

    @Override
    public void act(float delta) {
        if(entity.health > 0) {
            float h = (float) (entity.health - 1) / (float) (entity.maxHealth - 1);
            width = maxWidth * h;

            hb.clearChildren();
            hb.align(Align.left);
            hb.add(hbLeft).left();
            hb.add(hbMid).width(width).left();
            hb.add(hbRight).left();
    
            yet.clearChildren();
            yet.align(Align.left);
            yet.add(yetLeft).left();
            yet.add(yetMid).width(yetWidth).left();
            yet.add(yetRight).left();

            if(timer > 0) {
                timer -= delta;
                if(timer <= 0) timer = 0;
            } else if(yetWidth > width) {
                yetWidth -= 100 * delta;
            }
            
            if(yetWidth <= width) yetWidth = width;
        } else {
            width = 0;
            hb.clearChildren();
            yet.clearChildren();
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
