package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip.TextTooltipStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class SynergyDisplay extends Table {

    private static final BitmapFont FONT_NAME = FontHandler.BF_SYN_NAME;
    private static final BitmapFont FONT_DESC = FontHandler.BF_SYN_DESC;
    private static final String HINT = FontHandler.getHexColor(Color.GRAY);
    private static final String WHITE = FontHandler.getHexColor(Color.WHITE);
    private static final String NAME = FontHandler.getColorKey("y");

    private final AbstractSynergy synergy;
    private final TextTooltipStyle tts;

    private Image image;
    private TextTooltip tooltip;

    public SynergyDisplay(AbstractSynergy s) {
        synergy = s;

        left();
        tts = new TextTooltipStyle(new LabelStyle(FontHandler.BF_SUB_DESC, Color.WHITE), FileHandler.getUI().getDrawable("tile"));
        tts.wrapWidth = 240;

        image = new Image(synergy.img) {
            @Override
            public void act(float delta) {
                setDrawable(synergy.gradeImg[synergy.grade]);
                super.act(delta);
            }
        };

        String txt = NAME + synergy.name + WHITE + "\n\n" + synergy.desc;

        if(synergy.gradeAmount.length > 1) {
            txt += "\n";
            for(int i = 0; i < synergy.gradeAmount.length; i++) {
                int amt = synergy.gradeAmount[i];
                if(synergy.grade == i + (amt == 1 ? 0 : 1)) txt += "\n(" + amt + ") " + synergy.gradeDesc[i];
                else txt += HINT + "\n(" + amt + ") " + synergy.gradeDesc[i] + WHITE;
            }
        }
        tooltip = new TextTooltip(txt, tts);
        tooltip.setInstant(true);
        tooltip.getContainer().pad(14);
        image.addListener(tooltip);
        image.addListener(new InputListener() {
            public void enter (InputEvent event, float mx, float my, int pointer, @Null Actor fromActor) {
                synergy.isOver = true;
	        }

	        public void exit (InputEvent event, float mx, float my, int pointer, @Null Actor toActor) {
                synergy.isOver = false;
	        }
        });

        Table text = new Table();
        text.left();

        Label name = new Label("", new LabelStyle(FONT_NAME, Color.WHITE.cpy())) {
            @Override
            public void act(float delta) {
                setText(synergy.name);
                super.act(delta);
            }
        };
        Label desc = new Label("", new LabelStyle(FONT_DESC, Color.WHITE.cpy())) {
            @Override
            public void act(float delta) {
                int next;
                if(synergy.grade < synergy.gradeImg.length - 1) {
                    next = synergy.gradeAmount[synergy.gradeAmount[0] < 2 ? synergy.grade + 1 : synergy.grade];
                } else {
                    next = synergy.gradeAmount[synergy.gradeAmount.length - 1];
                }
                setText(synergy.memberCount + " / " + next);
                super.act(delta);
            }
        };
        
        text.add(name).expand().center().left().padLeft(2);
        text.row();
        text.add(desc).expand().center().left().padLeft(2);

        this.add(image).left().width(36).height(36);
        this.add(text).left();
    }

    public AbstractSynergy getSynergy() {
        return synergy;
    }

    @Override
    public void act(float delta) {
        String txt = NAME + synergy.name + WHITE + "\n\n" + synergy.desc;
        if(synergy.gradeAmount.length > 1) {
            txt += "\n";
            for(int i = 0; i < synergy.gradeAmount.length; i++) {
                int amt = synergy.gradeAmount[i];
                if(synergy.grade == i + (amt == 1 ? 0 : 1)) txt += "\n(" + amt + ") " + synergy.gradeDesc[i];
                else txt += HINT + "\n(" + amt + ") " + synergy.gradeDesc[i] + WHITE;
            }
        }
        
        Label l = new Label(txt, tts.label);
        l.setWrap(true);
        tooltip.getContainer().setActor(l);
        
        super.act(delta);
    }
}
