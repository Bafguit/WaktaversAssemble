package com.fastcat.assemble.uis;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.handlers.FontHandler;

public class RelicButton extends Table {

    private final Button button;
    private final Label label;

    public final AbstractRelic relic;

    public boolean isUsed = false;

    public RelicButton(AbstractRelic relic) {
        this.relic = relic;

        setTransform(false);

        button = new Button(relic.img);

        label = new Label(Integer.toString(relic.counter), new LabelStyle(FontHandler.BF_B24, Color.WHITE)) {
            public void act(float delta) {
                if(relic.counter < 0) this.setText("");
                else setText(Integer.toString(relic.counter));
                super.act(delta);
            }
        };
        //label.setWidth(76);
        label.setAlignment(Align.bottomRight);

        Button b = new Button(relic.img);
        b.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //TODO Open RelicInfoStage
		        return true;
	        }
        });

        add(button).center();
        row();
        add(label).align(Align.bottomRight).padTop(-label.getMinHeight() - 2).padRight(2);
        row();
        add(b).center().padTop(-b.getMinHeight());
    }
    
}
