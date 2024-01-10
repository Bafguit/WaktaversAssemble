package com.fastcat.assemble.screens.battle;

import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.fastcat.assemble.handlers.FontHandler.BF_CARD_DESC;
import static com.fastcat.assemble.handlers.FontHandler.BF_CARD_NAME;
import static com.fastcat.assemble.handlers.FontHandler.BF_SUB_DESC;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip.TextTooltipStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.FontHandler.FontData;

public class MemberCardDisplay extends ImageButton {

    private final Image memberImage;
    private final Image memberFrame;
    private final Label memberDesc;
    private final Label memberName;

    private ImageButton[] synergies;

    private float timer = 0;

    public AbstractMember member;

    private DragListener dragListener;

    public MemberCardDisplay(AbstractMember member) {
        super(FileHandler.getUI().getDrawable("cardBg"));
        //align(Align.center);
        this.member = member;
        memberImage = new Image(FileHandler.getMember(), member.id);
        memberImage.setPosition(13.5f, 14);
        memberFrame = new Image(FileHandler.getUI(), "cardFrame");
        memberFrame.setPosition(13.5f, 14);
        LabelStyle ls = new LabelStyle(BF_CARD_DESC, WHITE);
        ls.background = FileHandler.getUI().getDrawable("cardDesc");
        memberDesc = new Label(member.desc, ls);
        memberDesc.setWrap(true);
        memberDesc.setAlignment(Align.left);
        memberDesc.setPosition(13.5f, 14);
        memberName = new Label(member.getName(), new LabelStyle(BF_CARD_NAME, WHITE));
        memberName.setPosition(13.5f, 385);
        memberName.setAlignment(Align.left);
        addActor(memberImage);
        addActor(memberFrame);
        addActor(memberDesc);
        addActor(memberName);
        dragListener = new DragListener() {
	        public void drag (InputEvent event, float sx, float sy, int pointer) {
                if(!member.canUse() && sy > (getHeight() * 0.75f)) {
                    cancel();
                }
	        }

	        public void dragStop (InputEvent event, float sx, float sy, int pointer) {
                if(sy > getHeight()) {
                    addAction(Actions.fadeOut(0.25f));
                } else {
                    MoveToAction mv = Actions.action(MoveToAction.class);
                    mv.setPosition(getDragStartX(), getDragStartY());
                    addAction(Actions.moveTo(getDragStartX(), getDragStartY(), 0.1f, Interpolation.circle));
                }
	        }
        };
        addListener(dragListener);
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
        super.draw(batch, parentAlpha);
    }

    private static class SynergyIcon extends ImageButton {

        private static final String HINT = FontHandler.getHexColor(Color.GRAY);
        private static final String HINT_2 = FontHandler.getHexColor(Color.LIGHT_GRAY);
        private static final String WHITE = FontHandler.getHexColor(Color.WHITE);
        private static final FontData NAME = FontHandler.SYN_NAME;
        private static final FontData AMOUNT = FontHandler.SYN_DESC;

        private AbstractSynergy synergy;
        private TextTooltip tooltip;

        public SynergyIcon() {
            super(FileHandler.getSynergy().getDrawable("Isedol"));
        }

        public void setSynergy(AbstractSynergy s) {
            synergy = s;
            Drawable d = FileHandler.getSynergy().getDrawable(synergy.id);
            setStyle(new ImageButtonStyle(d, d, d, d, d, d));

            String text = synergy.name + "\n" + synergy.desc;
            for(int i = 0; i < synergy.gradeAmount.length; i++) {
                int amt = synergy.gradeAmount[i];
                if(synergy.grade == i + (amt == 1 ? 0 : 1)) text += "\n(" + amt + ") " + synergy.gradeDesc[i];
                else text += HINT + "\n(" + amt + ") " + synergy.gradeDesc[i] + WHITE;
            }

            TextTooltipStyle tts = new TextTooltipStyle(new LabelStyle(BF_SUB_DESC, Color.WHITE), FileHandler.getUI().getDrawable("tile"));
            tooltip = new TextTooltip(text, tts);
            
            tooltip.setInstant(true);
        }
    }
}
