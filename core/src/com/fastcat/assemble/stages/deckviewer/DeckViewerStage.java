package com.fastcat.assemble.stages.deckviewer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractStage;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.stages.battle.MemberCardDisplay;
import com.fastcat.assemble.stages.cardviewer.MemberViewerStage;
import com.fastcat.assemble.uis.TopBar;

public class DeckViewerStage extends AbstractStage {

    private Table root;
    private Table deckTable;
    private ScrollPane scroll;

    public DeckViewerStage() {
        super(FileHandler.getTexture("bg/half"));
        root = new Table();
        deckTable = new Table();
        updateDeck();
        scroll = new ScrollPane(deckTable);
        setScrollFocus(scroll);
        
        root.add(scroll).center().bottom().expand();
        root.align(Align.bottom);
        root.setSize(1920, 1000);

        DeckViewerStage ss = this;

        Drawable d = FileHandler.getUI().getDrawable("tile");
        TextButton b = new TextButton("CLOSE", new TextButtonStyle(d, d, d, FontHandler.BF_NB30));
        b.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                WakTower.stages.remove(ss);
		        return true;
	        }
        });
        b.setPosition(1920, 0, Align.bottomRight);

        this.addActor(root);
        this.addActor(b);
        this.addActor(topBar);
        setDebugAll(true);
    }

    private void updateDeck() {
        deckTable.clearChildren();
        int i = 0, j = 0;
        for(AbstractMember m : WakTower.game.deck) {
            MemberCardDisplay md = new MemberCardDisplay(m, true);
            md.setScale(1);
            md.isTemp = false;
            md.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    return scroll.isDragging();
                }
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    WakTower.stages.add(new MemberViewerStage(m));
                }
            });

            Cell<MemberCardDisplay> c = deckTable.add(md);
            if(i > 0) c.padLeft(15);
            c.padTop(j == 0 ? 80 : 15);

            if(i < 4) i++;
            else {
                i = 0;
                j++;
                deckTable.row();
            }
        }
    }
    
}
