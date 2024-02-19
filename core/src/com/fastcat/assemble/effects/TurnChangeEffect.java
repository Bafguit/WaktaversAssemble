package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEffect;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class TurnChangeEffect extends AbstractEffect {

    private final FontHandler.FontData font;
    private final Stack stack;

    public TurnChangeEffect(boolean isPlayer) {
        super(2f);
        font = new FontHandler.FontData(100, Color.WHITE, false, true);
        font.alpha = 0;
        stack = new Stack();
        Button bg = new Button(new TextureRegionDrawable(FileHandler.getPng("ui/changeEffect")));
        Label label = new Label(isPlayer ? DataHandler.MEMBER_TURN : DataHandler.ENEMY_TURN, new LabelStyle(FontHandler.BF_NB100, Color.WHITE));
        label.setAlignment(Align.center);
        stack.setSize(1920, 300);
        stack.addActor(bg);
        stack.addActor(label);
        stack.setColor(1, 1, 1, 0);
    }

    @Override
    public void run() {
        WakTower.application.battleStage.entityEffect.addActor(stack);
        stack.setPosition(960, 540, Align.center);

        SequenceAction sa = new SequenceAction(Actions.alpha(1, 0.6f), Actions.delay(0.8f, Actions.alpha(0, 0.6f)), Actions.removeActor(stack));
        stack.addAction(sa);
    }


}
