package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.scene2d.SpriteAnimation;
import com.fastcat.assemble.scene2d.SpriteAnimation.SpriteAnimationType;

public class MemberFieldDisplay extends Table {

    private AbstractMember member;

    private SpriteAnimation animation;
    private Image tile;

    private boolean showTile = false;

    public MemberFieldDisplay(AbstractMember m) {
        member = m;
        setOrigin(Align.bottom);
        animation = member.animation;
        tile = new Image(FileHandler.getTexture("ui/memberTile"));
        add(animation).bottom();
        row();
        add(tile).bottom().padTop(-(tile.getHeight() / 2));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = tile.getColor();
        tile.setColor(c.r, c.b, c.g, showTile ? 1 : 0);
        super.draw(batch, parentAlpha);
    }
    
}
