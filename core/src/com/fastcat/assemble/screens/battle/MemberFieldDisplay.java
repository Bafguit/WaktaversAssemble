package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.scene2d.SpriteAnimation;

public class MemberFieldDisplay extends Table {

    private AbstractMember member;

    private SpriteAnimation animation;
    private Button tile;
    private Button overTile;
    private Label label;

    private boolean showTile = false;
    private Tooltip<MemberCardDisplay> tooltip;

    private float timer = 0;

    public MemberFieldDisplay(AbstractMember m) {
        member = m;
        setOrigin(Align.bottom);
        setTransform(true);
        animation = member.animation;
        tile = new Button(new TextureRegionDrawable(FileHandler.getTexture("ui/memberTile")));
        overTile = new Button(new TextureRegionDrawable(FileHandler.getTexture("ui/memberTile")));

        tooltip = new Tooltip<MemberCardDisplay>(new MemberCardDisplay(m, true));
        tooltip.setInstant(true);
        overTile.addListener(tooltip);
        overTile.setColor(1, 1, 1, 0);

        add(animation).center().bottom();
        row();

        add(tile).center().bottom().padTop(-(tile.getMinHeight() / 2)).width(tile.getMinWidth()).height(tile.getMinHeight());
        row();

        add(overTile).center().bottom().padTop(-overTile.getMinHeight()).width(overTile.getMinWidth()).height(overTile.getMinHeight());
        row();
        
        label = new Label(member.name, new LabelStyle(FontHandler.BF_NB16, Color.WHITE));
        add(label).center().bottom().expandX().padTop(-30);

        animation.setZIndex(10);
        label.setZIndex(11);
        overTile.setZIndex(12);
    }

    @Override
    public void act(float delta) {
        showTile = false;
        for(AbstractSynergy s : member.synergy) {
            if(s.isOver) {
                showTile = true;
                break;
            }
        }

        if(showTile) {
            timer += delta / 0.25f;
            if(timer > 1) timer = 1;
        } else {
            timer -= delta / 0.25f;
            if(timer < 0) timer = 0;
        }

        //Drawable d = animation.getBackground();
        label.setText(member.getName());
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = tile.getColor();
        tile.setColor(c.r, c.b, c.g, timer);
        super.draw(batch, parentAlpha);
    }
    
}
