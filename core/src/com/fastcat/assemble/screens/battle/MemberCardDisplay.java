package com.fastcat.assemble.screens.battle;

import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.fastcat.assemble.handlers.FontHandler.BF_CARD_DESC;
import static com.fastcat.assemble.handlers.FontHandler.BF_CARD_NAME;
import static com.fastcat.assemble.handlers.FontHandler.BF_SUB_DESC;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.handlers.FontHandler.FontData;

public class MemberCardDisplay extends Table {

    private final Image memberImage;
    private final Image memberFrame;
    private final Image descBg;
    private final Label memberDesc;
    private final Label memberName;

    private SynergyIcon[] synergies;

    private float timer = 0;

    public AbstractMember member;

    private DragListener dragListener;

    public MemberCardDisplay(AbstractMember member) {
        setBackground(FileHandler.getUI().getDrawable("cardBg"));
        setTouchable(Touchable.enabled);
        setBounds(0, 0, 285, 428);
        align(Align.center);
        this.member = member;
        memberImage = new Image(FileHandler.getMember(), member.id);
        memberFrame = new Image(FileHandler.getUI(), "cardFrame");
        descBg = new Image(FileHandler.getUI(), "cardDescBg");
        memberDesc = new Label(member.desc, new LabelStyle(BF_CARD_DESC, WHITE));
        memberDesc.setWrap(true);
        memberName = new Label(member.getName(), new LabelStyle(BF_CARD_NAME, WHITE));
        memberName.setPosition(13.5f, 385);
        memberName.setAlignment(Align.left);

        Stack stack = new Stack(memberImage, memberFrame, descBg, memberDesc);


        add(memberName).expandX().align(Align.left).height(60).padLeft(14);
        for(int i = 0; i < member.synergy.length; i++) {
            add(new SynergyIcon(member, i)).align(Align.center).width(48).height(48);
        }
        row();
        add(stack).expand().pad(14, 14, 14, 14);

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
        if(isTouchFocusTarget()) {
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

        private AbstractMember owner;
        private AbstractSynergy synergy;
        private int index;
        private TextTooltip tooltip;

        public SynergyIcon(AbstractMember m, int index) {
            super(FileHandler.getSynergy().getDrawable("Isedol"));
            owner = m;
            this.index = index;
            synergy = m.synergy[index];
        }

        @Override
        public void act(float delta) {
            if(synergy != owner.synergy[index]) setSynergy(owner.synergy[index]);
            super.act(delta);
        }

        public void setSynergy(AbstractSynergy s) {
            synergy = s;
            setBackground(FileHandler.getSynergy().getDrawable(synergy.id));

            String text = synergy.name + "\n" + synergy.desc;
            for(int i = 0; i < synergy.gradeAmount.length; i++) {
                int amt = synergy.gradeAmount[i];
                if(synergy.grade == i + (amt == 1 ? 0 : 1)) text += "\n(" + amt + ") " + synergy.gradeDesc[i];
                else text += HINT + "\n(" + amt + ") " + synergy.gradeDesc[i] + WHITE;
            }

            clearListeners();
            TextTooltipStyle tts = new TextTooltipStyle(new LabelStyle(BF_SUB_DESC, Color.WHITE), FileHandler.getUI().getDrawable("tile"));
            tooltip = new TextTooltip(text, tts);
            
            tooltip.setInstant(true);
            addListener(tooltip);
        }
    }
}
