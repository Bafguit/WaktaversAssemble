package com.fastcat.assemble.stages.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip.TextTooltipStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class StatusDisplay extends Table {

    private static final BitmapFont FONT_NAME = FontHandler.BF_SYN_NAME;
    private static final BitmapFont FONT_DESC = FontHandler.BF_SYN_DESC;
    private static final String HINT = FontHandler.getHexColor(Color.GRAY);
    private static final String WHITE = FontHandler.getHexColor(Color.WHITE);
    private static final String NAME = FontHandler.getColorKey("y");

    private final TextTooltipStyle tts;

    public final AbstractStatus status;
    
    private TextTooltip tooltip;

    public StatusDisplay(AbstractStatus status) {
        super();
        this.status = status;

        Button b = new Button(status.img);

        Button b2 = new Button(status.img);

        tts = new TextTooltipStyle(new LabelStyle(FontHandler.BF_SUB_DESC, Color.WHITE), FileHandler.getUI().getDrawable("tile"));
        tts.wrapWidth = 240;

        String txt = NAME + status.name + "\n" + WHITE + status.desc;

        tooltip = new TextTooltip(txt, tts);
        tooltip.setInstant(true);
        tooltip.getContainer().pad(14);
        b2.addListener(tooltip);
        b2.setColor(1 ,1 ,1 ,0);

        Label l = new Label("", new LabelStyle(FONT_NAME, Color.YELLOW)) {
            public void act(float delta) {
                String t = "";
                if(status.amount > 0) t += status.amount;
                else if(status.amount < 0 && status.canGoNegative) t += -status.amount;
                setText(t);
            }
        };
        l.setAlignment(Align.bottomRight);

        this.add(b).width(32).height(32);
        this.row();
        this.add(l).bottom().right().padTop(-l.getMinHeight());
        this.add(b2).width(32).height(32).padTop(-32);
    }

    @Override
    public void act(float delta) {
        String txt = NAME + status.name + "\n" + WHITE + status.getDesc();
        
        Label l = new Label(txt, tts.label);
        l.setWrap(true);
        tooltip.getContainer().setActor(l);
        
        super.act(delta);
    }
}
