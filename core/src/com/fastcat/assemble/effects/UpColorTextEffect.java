package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEffect;
import com.fastcat.assemble.handlers.FontHandler;


public class UpColorTextEffect extends AbstractEffect {

    private final String text;
    private final Color color;

    public UpColorTextEffect(float x, float y, String text, Color color) {
        super(x, y, 0.7f);
        this.color = color;
        this.text = text;
    }

    @Override
    public void run() {
        Label l = new Label(text, new LabelStyle(FontHandler.BF_B24, color));

        WakTower.application.battleStage.entityEffect.addActor(l);
        l.setPosition(x, y, Align.center);
        ParallelAction pa = new ParallelAction(Actions.moveBy(0, 200, baseDuration), Actions.alpha(0, baseDuration));
        SequenceAction sa = new SequenceAction(pa, Actions.removeActor(l));
        l.addAction(sa);
    }


}
