package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.scene2d.SpriteAnimation;

public class MemberFieldDisplay extends Table {

    private AbstractMember member;

    private SpriteAnimation animation;
    private Table tile;
    private Label label;

    private boolean showTile = false;

    public MemberFieldDisplay(AbstractMember m) {
        member = m;
        setOrigin(Align.bottom);
        setTransform(true);
        animation = member.animation;
        tile = new Table();
        tile.setBackground(new TextureRegionDrawable(FileHandler.getTexture("ui/memberTile")));
        //tile.setFillParent(false);

        add(animation).center().bottom();
        row();

        add(tile).center().bottom().padTop(-(tile.getHeight() / 2));
        row();
        
        label = new Label(member.name, new LabelStyle(FontHandler.BF_NB16, Color.WHITE));
        add(label).center().bottom().expandX();
    }

    @Override
    public void act(float delta) {
        //Drawable d = animation.getBackground();
        label.setText(member.getName());
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = tile.getColor();
        //tile.setColor(c.r, c.b, c.g, showTile ? 1 : 0);
        super.draw(batch, parentAlpha);
    }
    
}
