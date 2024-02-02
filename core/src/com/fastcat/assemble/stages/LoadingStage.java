package com.fastcat.assemble.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractGame;
import com.fastcat.assemble.abstracts.AbstractStage;
import com.fastcat.assemble.abstracts.AbstractUI.UIData;
import com.fastcat.assemble.battles.TestBattle;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.stages.battle.BattleStage;

public class LoadingStage extends AbstractStage {

    private final UIData data;
    private final Table root;
    private final Label label;
    
    public LoadingStage() {
        super();
        data = DataHandler.getInstance().uiData.get("loading");
        root = new Table();
        this.addActor(root);
        root.setFillParent(true);
        label = new Label(data.text[0], new LabelStyle(FontHandler.BF_NB30, Color.WHITE.cpy()));
        label.setAlignment(Align.center);
        root.add(label).expand().center();
        setDebugAll(true);
    }

    @Override
    public void act(float delta) {
        float p = FileHandler.getProcess();
        label.setText(data.text[0] + "\n" + String.format("%.1f", p * 100) + "%");
        super.act(delta);
        if(p >= 1) {
            clear();
            WakTower.game = new AbstractGame();
            WakTower.game.battle = new TestBattle();
            WakTower.application.battleStage = new BattleStage();
            WakTower.stage = WakTower.application.battleStage;
        }
    }
}
