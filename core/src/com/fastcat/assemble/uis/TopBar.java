package com.fastcat.assemble.uis;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.stages.deckviewer.DeckViewerStage;

public class TopBar extends Table {

    private final Table barTable;
    private final Table relicTable;

    private HashMap<AbstractRelic, RelicButton> relicMap = new HashMap<>();

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

        Drawable d = FileHandler.getUI().getDrawable("tile");
        TextButton b4 = new TextButton("DECK", new TextButtonStyle(d, d, d, FontHandler.BF_NB30));
        b4.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                WakTower.stages.add(new DeckViewerStage());
		        return true;
	        }
        });

        barTable.add(name).left().padLeft(24);
        barTable.add(b4).right().padRight(16).height(64).width(64);

        relicTable = new Table();

        this.add(barTable).expandX().height(80).align(Align.left);
        this.row();
        this.add(relicTable).expandX().height(80).align(Align.left);

        updateRelic();
    }

    public void updateRelic() {
        for(AbstractRelic r : WakTower.game.relics) {
            if(relicMap.get(r) == null) {
                RelicButton b = new RelicButton(r);
                relicTable.add(b).left();
            }
        }
    }
    
}
