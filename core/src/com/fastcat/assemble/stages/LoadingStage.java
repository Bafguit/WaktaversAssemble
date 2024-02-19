package com.fastcat.assemble.stages;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractStage;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.stages.mainmenu.MainMenuStage;

public class LoadingStage extends AbstractStage {

    private final Table root;
    private final Label label;
    
    public LoadingStage() {
        super();
        root = new Table();
        this.addActor(root);
        root.setFillParent(true);
        label = new Label(DataHandler.LOADING, new LabelStyle(FontHandler.BF_NB30, Color.WHITE.cpy()));
        label.setAlignment(Align.center);
        root.add(label).expand().center();
    }

    @Override
    public void act(float delta) {
        float p = FileHandler.getProcess();
        AssetManager m = FileHandler.getInstance().assetManager;
        String name = m.getAssetNames().size > 0 ? m.getAssetNames().peek() : "";
        label.setText(DataHandler.LOADING + "\n\n" + name + "\n" + String.format("%.1f", p * 100) + "%");
        super.act(delta);
        if(p >= 1) {
            clear();
            WakTower.stage = new MainMenuStage();
        }
    }
}
