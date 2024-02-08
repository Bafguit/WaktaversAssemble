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

public class HealthBar extends Table implements OnHealthUpdated {

    private static final String BLUE = FontHandler.getColorKey("b");
    private static final String GREEN = FontHandler.getColorKey("g");

    private ProgressBar hb, yet;
    private Label text;

    public AbstractEntity entity;

    public HealthBar(AbstractEntity entity) {
        this(entity, 280);
    }

    public HealthBar(AbstractEntity entity, final float width) {
        this.entity = entity;
        Button bg = new Button(new TextureRegionDrawable(FileHandler.getPng("ui/hb")));
        hb = new ProgressBar(0, this.entity.maxHealth, 1, false, new ProgressBarStyle(new TextureRegionDrawable(FileHandler.getPng("ui/hb")), null));
        hb.setWidth(width);
        hb.setTouchable(Touchable.disabled);
        yet = new ProgressBar(0, this.entity.maxHealth, 1, false, new ProgressBarStyle(new TextureRegionDrawable(FileHandler.getPng("ui/hb_yet")), null));
        yet.setWidth(width);
        yet.setTouchable(Touchable.disabled);
        yet.setAnimateDuration(1);
        Button block = new Button(new TextureRegionDrawable(FileHandler.getPng("ui/hb_block"))) {
            AbstractEntity ent = entity;
            
            public void act(float delta) {
                if(ent.block > 0) setColor(1,1,1,1);
                else setColor(1,1,1,0);
            }
        };
        Button barrier = new Button(new TextureRegionDrawable(FileHandler.getPng("ui/hb_barrier"))) {
            AbstractEntity ent = entity;
            
            public void act(float delta) {
                if(ent.barrier > 0) setColor(1,1,1,1);
                else setColor(1,1,1,0);
            }
        };
        bg.setColor(0.5f, 0.5f, 0.5f, 1);
        entity.healthBar = this;

        String t = entity.health + "/" + entity.maxHealth;
        if(entity.barrier > 0) t += GREEN + " +" + entity.barrier;
        if(entity.block > 0) t += BLUE + " +" + entity.block;

        text = new Label(t, new LabelStyle(FontHandler.BF_HEALTH, Color.WHITE));
        text.setAlignment(Align.center);

        this.add(bg).width(width);
        this.row();
        this.add(yet).width(width).padTop(-8);
        this.row();
        this.add(hb).width(width).padTop(-8);
        this.row();
        this.add(block).width(width + 2).padTop(-9).padLeft(-1);
        this.row();
        this.add(barrier).width(width).padTop(-9);
        this.row();
        this.add(text).height(16).padTop(-13).width(width).center();
    }

    @Override
    public void onHealthUpdated(int amount) {
        hb.setValue(entity.health);
        //hb.updateVisualValue();
        yet.setValue(entity.health);
        String t = entity.health + "/" + entity.maxHealth;
        if(entity.barrier > 0) t += GREEN + " +" + entity.barrier;
        if(entity.block > 0) t += BLUE + " +" + entity.block;
        text.setText(t);
    }
}
