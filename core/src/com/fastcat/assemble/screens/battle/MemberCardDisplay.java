package com.fastcat.assemble.screens.battle;

import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.fastcat.assemble.handlers.FontHandler.BF_CARD_DESC;
import static com.fastcat.assemble.handlers.FontHandler.BF_CARD_NAME;
import static com.fastcat.assemble.handlers.FontHandler.BF_SUB_DESC;

import java.util.regex.Matcher;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
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
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.handlers.FontHandler.FontData;

public class MemberCardDisplay extends Button {
    private final Table root;

    private final Table memberImage;
    private final Image memberFrame;
    private final Table imageRoot;
    private final Image descBg;
    private final Label memberDesc;
    private final Label memberName;

    private float rotation;

    private SynergyIcon[] synergies;

    private float timer = 0;

    public AbstractMember member;

    private DragListener dragListener;
    private boolean over = false;

    public MemberCardDisplay(AbstractMember member) {
		super(FileHandler.getUI().getDrawable("cardBg"));
        setTransform(true);
        root = new Table();
        root.setFillParent(true);
        root.align(Align.center);
        add(root);

        this.member = member;

        memberImage = new Table(FileHandler.getMember());
        memberImage.setBackground(member.id);
        memberImage.setFillParent(true);

        memberFrame = new Image(FileHandler.getUI(), "cardFrame");

        descBg = new Image(FileHandler.getUI(), "cardDesc");

        memberDesc = new Label(member.desc, new LabelStyle(BF_CARD_DESC, WHITE));
        memberDesc.setWrap(true);
        memberDesc.setAlignment(Align.center);

        Stack s = new Stack(descBg, memberDesc);

        imageRoot = new Table();
        imageRoot.center().bottom();

        imageRoot.add(s).expandX().bottom();

        memberImage.add(imageRoot).expand().bottom();

        memberName = new Label(member.getName(), new LabelStyle(BF_CARD_NAME, WHITE));
        memberName.setAlignment(Align.left);

        Stack stack = new Stack(memberImage, memberFrame);


        root.add(memberName).expandX().center().left().padTop(14).padLeft(14);
        Cell<SynergyIcon> c = null;
        for(int i = 0; i < member.synergy.length; i++) {
            c = root.add(new SynergyIcon(member, i)).center().right().width(33.6f).height(33.6f).padTop(14);
        }
        if(c != null) c.padRight(14);
        root.row();
        root.add(stack).expand().pad(14).colspan(4);

        MemberCardDisplay md = this;

        dragListener = new DragListener() {
	        public void dragStart (InputEvent event, float sx, float sy, int pointer) {
                rotation = getRotation();
                setRotation(0);
                setDragStartX(md.getX());
                setDragStartY(md.getY());
	        }

	        public void drag (InputEvent event, float sx, float sy, int pointer) {
                moveBy(sx - getWidth() / 2, sy - getHeight() / 2);
                if(!member.canUse() && sy > (getHeight() * 0.75f)) {
                    cancel();
                }
	        }

	        public void dragStop (InputEvent event, float sx, float sy, int pointer) {
                if(sy > getHeight()) {
                    addAction(Actions.fadeOut(0.25f));
                } else {
                    addAction(Actions.parallel(Actions.moveTo(getDragStartX(), getDragStartY(), 0.1f, Interpolation.circle), Actions.rotateTo(rotation, 0.1f, Interpolation.circle)));
                }
	        }
        };
        addListener(dragListener);
        addListener(new InputListener() {
	        public void enter (InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                over = true;
	        }

	        public void exit (InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
                over = false;
	        }
        });
    }

    @Override
	public void act (float delta) {
        if(over) {
            timer += delta * 4;
            if(timer > 1) timer = 1;
        } else {
            timer -= delta * 4;
            if(timer < 0) timer = 0;
        }

        String text = member.data.desc;
        Matcher matcher = FontHandler.COLOR_PATTERN.matcher(text);
        while (matcher.find()) {
            String mt = matcher.group(1);
            String mmt = matcher.group(2);
            text = matcher.replaceFirst(FontHandler.getColorKey(mt) + mmt + FontHandler.getHexColor(Color.WHITE));
            matcher = FontHandler.COLOR_PATTERN.matcher(text);
        }
        matcher = FontHandler.VAR_PATTERN.matcher(text);
        while (matcher.find()) {
            String mt = matcher.group(1);
            text = matcher.replaceFirst(member.getKeyValue(mt));
            matcher = FontHandler.VAR_PATTERN.matcher(text);
        }
        memberDesc.setText(text);
        
		super.act(delta);
	}

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = memberDesc.getColor();
        memberDesc.setColor(c.r, c.b, c.g, timer);
        c = imageRoot.getColor();
        imageRoot.setColor(c.r, c.b, c.g, timer);
        super.draw(batch, parentAlpha);
    }

    private static class SynergyIcon extends Image {

        private static final String HINT = FontHandler.getHexColor(Color.GRAY);
        private static final String HINT_2 = FontHandler.getHexColor(Color.LIGHT_GRAY);
        private static final String WHITE = FontHandler.getHexColor(Color.WHITE);
        private static final String NAME = FontHandler.getColorKey("y");

        private AbstractMember owner;
        private AbstractSynergy synergy;
        private int index;
        private TextTooltip tooltip;
        private boolean over = false;

        public SynergyIcon(AbstractMember m, int index) {
            super(FileHandler.getSynergy().getDrawable("Isedol"));
            owner = m;
            this.index = index;
            setSynergy(owner.synergy[index]);
        }

        @Override
        public void act(float delta) {
            if(synergy != owner.synergy[index]) setSynergy(owner.synergy[index]);
            super.act(delta);
        }

        public void setSynergy(AbstractSynergy s) {
            synergy = s;
            setDrawable(FileHandler.getSynergy().getDrawable(synergy.id));

            String text = NAME + synergy.name + WHITE + "\n\n" + synergy.desc;
            if(synergy.gradeAmount.length > 1) {
                text += "\n";
                for(int i = 0; i < synergy.gradeAmount.length; i++) {
                    int amt = synergy.gradeAmount[i];
                    if(synergy.grade == i + (amt == 1 ? 0 : 1)) text += "\n(" + amt + ") " + synergy.gradeDesc[i];
                    else text += HINT + "\n(" + amt + ") " + synergy.gradeDesc[i] + WHITE;
                }
            }

            if(tooltip != null) removeListener(tooltip);
            TextTooltipStyle tts = new TextTooltipStyle(new LabelStyle(BF_SUB_DESC, Color.WHITE), FileHandler.getUI().getDrawable("tile"));
            tooltip = new TextTooltip(text, tts);
            tooltip.setInstant(true);
            tooltip.getManager().initialTime = 0.5f;
            tooltip.getContainer().pad(14);
            addListener(tooltip);
        }
    }
}
