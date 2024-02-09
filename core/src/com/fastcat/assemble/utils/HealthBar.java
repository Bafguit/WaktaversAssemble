package com.fastcat.assemble.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
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

public class HealthBar extends Widget implements OnHealthUpdated {

    private static final String BLUE = FontHandler.getColorKey("b");
    private static final String GREEN = FontHandler.getColorKey("g");

    private TextureRegionDrawable bg, hb, yet, block, barrier;
    private Label text;
    private float yetHealth, health, timer = 0, wScale = 1;

    public AbstractEntity entity;

    public HealthBar(AbstractEntity entity) {
        this(entity, 148);
    }

    public HealthBar(AbstractEntity entity, final float width) {
        this.entity = entity;
        bg = new TextureRegionDrawable(FileHandler.getPng("ui/hb"));
        hb = new TextureRegionDrawable(FileHandler.getPng("ui/hb"));
        yet = new TextureRegionDrawable(FileHandler.getPng("ui/hb_yet"));
        block = new TextureRegionDrawable(FileHandler.getPng("ui/hb_block"));
        barrier = new TextureRegionDrawable(FileHandler.getPng("ui/hb_barrier"));

        health = (float) entity.health / (float) entity.maxHealth;
        setWidth(width);
        setHeight(12);
        wScale = (float) width / 148;

        entity.healthBar = this;

        String t = entity.health + "/" + entity.maxHealth;
        if(entity.barrier > 0) t += GREEN + " +" + entity.barrier;
        if(entity.block > 0) t += BLUE + " +" + entity.block;

        text = new Label(t, new LabelStyle(FontHandler.BF_HEALTH, Color.WHITE));
        text.setAlignment(Align.center);
        text.setWidth(width);
    }

    @Override
    public void act(float delta) {
        if(timer == 0) {
            if(yetHealth > health) yetHealth -= delta;
            if(yetHealth < health) yetHealth = health;
        } else {
            timer -= delta;
            if(timer < 0) timer = 0;
        }
        text.act(delta);
    }
    
    @Override
	public void draw (Batch batch, float parentAlpha) {
        float x = getX(), y = getY(), width = getWidth(), height = getHeight();
        Color c = batch.getColor().cpy();
        batch.setColor(c.r * 0.5f, c.g * 0.5f, c.b * 0.5f, c.a);
        bg.draw(batch, x, y, 0, 0, width, height, 1, 1, 0);
        batch.setColor(c);
        yet.draw(batch, x, y, 0, 0, width, height, yetHealth, 1, 0);
        hb.draw(batch, x, y, 0, 0, width, height, health, 1, 0);
        if(entity.block <= 0) batch.setColor(c.r, c.g, c.b, 0);
        block.draw(batch, x - (1.5f * wScale), y - (1.5f * wScale), 0, 0, width, height, 1, 1, 0);
        if(entity.barrier <= 0) batch.setColor(c.r, c.g, c.b, 0);
        else batch.setColor(c.r, c.g, c.b, c.a);
        barrier.draw(batch, x, y, 0, 0, width, height, 1, 1, 0);
        batch.setColor(c);
        String t = entity.health + "/" + entity.maxHealth;
        if(entity.barrier > 0) t += GREEN + " +" + entity.barrier;
        if(entity.block > 0) t += BLUE + " +" + entity.block;
        text.setText(t);
        text.setPosition(x, y - 2);
        text.draw(batch, parentAlpha);
    }

    @Override
    public void onHealthUpdated(int amount) {
        health = (float) entity.health / (float) entity.maxHealth;
        timer = 1;
    }
}
