package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip.TextTooltipStyle;
import com.badlogic.gdx.utils.Align;
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

    private Image image;
    private TextTooltip tooltip;

    public SynergyDisplay(AbstractSynergy s) {
        synergy = s;

        align(Align.left);

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

        if(tooltip != null) image.removeListener(tooltip);
        TextTooltipStyle tts = new TextTooltipStyle(new LabelStyle(FontHandler.BF_SUB_DESC, Color.WHITE), FileHandler.getUI().getDrawable("tile"));
        tooltip = new TextTooltip(txt, tts);
        tooltip.setInstant(true);
        tooltip.getContainer().pad(14);
        image.addListener(tooltip);

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
        
        text.add(name).expand().center().left().pad(2);
        text.row();
        text.add(desc).expand().center().left().pad(2);

        this.add(image);
        this.add(text);
    }

    public AbstractSynergy getSynergy() {
        return synergy;
    }

    
}
