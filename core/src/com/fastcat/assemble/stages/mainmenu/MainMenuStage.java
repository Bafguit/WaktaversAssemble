package com.fastcat.assemble.stages.mainmenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractGame;
import com.fastcat.assemble.abstracts.AbstractStage;
import com.fastcat.assemble.battles.TestBattle;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.stages.battle.BattleStage;

public class MainMenuStage extends AbstractStage {

    private Table root;

    public MainMenuStage() {
        super(FileHandler.getTexture("bg/cat"));

        root = new Table();
        root.center();
        root.setFillParent(true);

        Label logo = new Label("WAKTOWER", new LabelStyle(FontHandler.BF_NB100, Color.WHITE));
        root.add(logo).center();
        root.row();
        
        TextButton b = new TextButton("게임 시작", new TextButtonStyle(null, null, null, FontHandler.BF_NB60));
        b.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                WakTower.game = new AbstractGame();
                WakTower.game.battle = new TestBattle();
                WakTower.application.battleStage = new BattleStage();
                WakTower.stage = WakTower.application.battleStage;
		        return true;
	        }
        });
        root.add(b).center().padTop(200);
        root.row();
        
        TextButton b2 = new TextButton("불러오기", new TextButtonStyle(null, null, null, FontHandler.BF_NB60));
        b2.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //불러오기 추가
		        return false;
	        }
        });
        root.add(b2).center().padTop(20);
        root.row();
        
        TextButton b3 = new TextButton("도감", new TextButtonStyle(null, null, null, FontHandler.BF_NB60));
        b3.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //도감 추가
		        return false;
	        }
        });
        root.add(b3).center().padTop(20);
        root.row();
        
        TextButton b4 = new TextButton("설정", new TextButtonStyle(null, null, null, FontHandler.BF_NB60));
        b4.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //설정 추가
		        return false;
	        }
        });
        root.add(b4).center().padTop(20);

        this.addActor(root);
    }
    
}
