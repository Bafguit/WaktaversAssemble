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

    private MemberCardDisplay normal;
    private MemberCardDisplay upgraded;

    private CheckBox checkBox;

    private Table root;

    public MemberViewerStage(AbstractMember member) {
        super(FileHandler.getTexture("bg/half"));

        checkBox = new CheckBox("강화 보기", new CheckBoxStyle(new TextureRegionDrawable(FileHandler.getPng("ui/checkbox_off")), new TextureRegionDrawable(FileHandler.getPng("ui/checkbox_on")), FontHandler.BF_NB30, Color.WHITE));
        checkBox.addListener(new InputListener() {
	        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                checkBox.toggle();
                toggle();
                return true;
	        }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        checkBox.setChecked(member.upgradeCount > 0);
        checkBox.setPosition(960, 160, Align.center);

        if(member.upgradeCount > 0) {
            normal = new MemberCardDisplay(GroupHandler.getMember(member.id), true);
            upgraded = new MemberCardDisplay(member, true);
        } else {
            normal = new MemberCardDisplay(member, true);
            AbstractMember up = member.cpy();
            up.upgrade();
            upgraded = new MemberCardDisplay(up, true);
        }
        normal.isTemp = false;
        upgraded.isTemp = false;
        normal.setPosition(960, 440, Align.center);
        upgraded.setPosition(960, 440, Align.center);
        normal.setScale(1.5f);
        upgraded.setScale(1.5f);

        //view.setScale(2);
        
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
        toggle();

        this.addActor(root);
        this.addActor(b);
        this.addActor(topBar);
    }

    private void toggle() {
        root.clearChildren();
        root.addActor(checkBox.isChecked() ? upgraded : normal);
        root.addActor(checkBox);
    }
    
}
