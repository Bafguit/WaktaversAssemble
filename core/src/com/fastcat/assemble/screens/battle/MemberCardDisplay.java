package com.fastcat.assemble.screens.battle;

import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.fastcat.assemble.handlers.FontHandler.BF_CARD_DESC;
import static com.fastcat.assemble.handlers.FontHandler.BF_CARD_NAME;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class MemberCardDisplay extends ImageButton {

    private final Image memberImage;
    private final Image memberFrame;
    private final Label memberDesc;
    private final Label memberName;

    private float timer = 0;

    public AbstractMember member;

    public MemberCardDisplay(AbstractMember member) {
        super(FileHandler.getUI(), "cardBg");
        this.member = member;
        memberImage = new Image(FileHandler.getMember(), member.id);
        memberFrame = new Image(FileHandler.getUI(), "cardFrame");
        LabelStyle ls = new LabelStyle(BF_CARD_DESC, WHITE);
        ls.background = FileHandler.getUI().getDrawable("cardDesc");
        memberDesc = new Label(member.desc, ls);
        memberName = new Label(member.getName(), new LabelStyle(BF_CARD_NAME, WHITE));
        addActor(memberImage);
        addActor(memberFrame);
        addActor(memberDesc);
        addActor(memberName);
    }

    @Override
	public void act (float delta) {
        if(isOver()) {
            timer += delta;
            if(timer > 1) timer = 1;
        } else {
            timer -= delta;
            if(timer < 0) timer = 0;
        }
		super.act(delta);
	}

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = memberDesc.getColor();
        memberDesc.setColor(c.r, c.b, c.g, c.a * timer);
    }
}
