package com.fastcat.assemble.stages.cardviewer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractStage;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.GroupHandler;
import com.fastcat.assemble.stages.battle.MemberCardDisplay;

public class MemberViewerStage extends AbstractStage {

    private final Container<MemberCardDisplay> view;

    private MemberCardDisplay normal;
    private MemberCardDisplay upgraded;

    private Table root;

    public MemberViewerStage(AbstractMember member) {
        super(FileHandler.getTexture("bg/half"));
        view = new Container<>();

        CheckBox checkBox = new CheckBox("강화 보기", new CheckBoxStyle(new TextureRegionDrawable(FileHandler.getPng("ui/checkbox_off")), new TextureRegionDrawable(FileHandler.getPng("ui/checkbox_on")), FontHandler.BF_NB30, Color.WHITE));
        checkBox.addListener(new InputListener() {
	        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
	        }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                checkBox.toggle();
                if(checkBox.isChecked()) view.setActor(upgraded);
                else view.setActor(normal);
            }
        });
        checkBox.setChecked(member.upgradeCount > 0);

        if(member.upgradeCount > 0) {
            normal = new MemberCardDisplay(GroupHandler.getMember(member.id), true);
            upgraded = new MemberCardDisplay(member, true);
            normal.isTemp = false;
            upgraded.isTemp = false;
            //normal.setScale(2);
            //upgraded.setScale(2);
            view.setActor(upgraded);
        } else {
            normal = new MemberCardDisplay(member, true);
            AbstractMember up = member.cpy();
            up.upgrade();
            upgraded = new MemberCardDisplay(up, true);
            normal.isTemp = false;
            upgraded.isTemp = false;
            //normal.setScale(2);
            //upgraded.setScale(2);
            view.setActor(normal);
        }

        view.setScale(2);
        
        MemberViewerStage ss = this;
        Drawable d = FileHandler.getUI().getDrawable("tile");
        TextButton b = new TextButton("CLOSE", new TextButtonStyle(d, d, d, FontHandler.BF_NB30));
        b.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                WakTower.stages.remove(ss);
		        return true;
	        }
        });
        b.setPosition(1920, 0, Align.bottomRight);

        root = new Table();
        root.setSize(1920, 1000);
        root.add(view).center();
        root.row();
        root.add(checkBox).center().padTop(30);

        this.addActor(root);
        this.addActor(b);
        this.addActor(topBar);
        setDebugAll(true);
    }
    
}
