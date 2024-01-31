package com.fastcat.assemble.uis;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class TopBar extends Table {

    private final Table barTable;
    private final Table relicTable;

    public TopBar() {
        this.align(Align.topLeft);
        setOrigin(Align.topLeft);
        setPosition(0, 1080);
        this.setWidth(1920);

        barTable = new Table();
        barTable.setBackground(new TextureRegionDrawable(FileHandler.getPng("ui/topbar")));
        barTable.setTransform(false);
        barTable.align(Align.left);

        Label name = new Label("아무이름", new LabelStyle(FontHandler.BF_NB30, Color.WHITE));
        name.setAlignment(Align.left);

        barTable.add(name).left().padLeft(24);

        relicTable = new Table();

        this.add(barTable).expandX().height(96).align(Align.left);
        this.row();
        this.add(relicTable).expandX().height(96).align(Align.left);
    }
    
}
