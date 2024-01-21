package com.fastcat.assemble.screens.battle;

import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.fastcat.assemble.handlers.FontHandler.BF_CARD_DESC;
import static com.fastcat.assemble.handlers.FontHandler.BF_CARD_NAME;
import static com.fastcat.assemble.handlers.FontHandler.BF_SUB_DESC;

import java.util.regex.Matcher;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip.TextTooltipStyle;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.actions.SummonMemberAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class MemberCardDisplay extends Button {
    private final Table root;

    private final Table memberImage;
    private final Image memberFrame;
    private final Table imageRoot;
    private final Image descBg;
    private final Label memberDesc;
    public final Label memberName;

    private int zIndex;
    private float baseX, baseY, baseScale, baseRotation;

    private SynergyIcon[] synergies;

    private float timer = 0;

    public AbstractMember member;

    private DragListener dragListener;
    private boolean over = false;
    private boolean overing = false;
    private boolean drag = false;
    private float overScale = 0.95f;

    public MemberCardDisplay(AbstractMember member) {
		super(FileHandler.getUI().getDrawable("cardBg"));
        setName("memberCardDisplay");
        setTransform(true);
        setScale(0.85f);
        root = new Table();
        root.setFillParent(true);
        root.align(Align.center);
        add(root);

        this.member = member;

        memberImage = new Table(FileHandler.getMember());
        memberImage.setBackground(member.id);
        memberImage.setFillParent(true);
        memberImage.setTouchable(Touchable.disabled);

        memberFrame = new Image(FileHandler.getUI(), "cardFrame");
        memberFrame.setTouchable(Touchable.disabled);

        descBg = new Image(FileHandler.getUI(), "cardDesc");
        descBg.setTouchable(Touchable.disabled);

        memberDesc = new Label(member.desc, new LabelStyle(BF_CARD_DESC, WHITE));
        memberDesc.setWrap(true);
        memberDesc.setAlignment(Align.center);
        memberDesc.setTouchable(Touchable.disabled);

        Stack s = new Stack(descBg, memberDesc);
        s.setTouchable(Touchable.disabled);

        imageRoot = new Table();
        imageRoot.center().bottom();
        imageRoot.setTouchable(Touchable.disabled);

        imageRoot.add(s).expandX().bottom();

        memberImage.add(imageRoot).expand().bottom();

        memberName = new Label(member.getName(), new LabelStyle(BF_CARD_NAME, WHITE));
        memberName.setAlignment(Align.left);
        memberName.setTouchable(Touchable.disabled);

        Stack stack = new Stack(memberImage, memberFrame);
        stack.setTouchable(Touchable.disabled);


        root.add(memberName).expandX().center().left().padTop(14).padLeft(14);
        Cell<SynergyIcon> c = null;
        synergies = new SynergyIcon[member.synergy.length];
        for(int i = 0; i < member.synergy.length; i++) {
            SynergyIcon sc = new SynergyIcon(member, i);
            c = root.add(sc).center().right().width(33.6f).height(33.6f).padTop(14);
            synergies[i] = sc;
        }
        if(c != null) c.padRight(14);
        root.row();
        root.add(stack).expand().pad(14).colspan(4);
        //root.setTouchable(Touchable.childrenOnly);

        MemberCardDisplay md = this;

        dragListener = new DragListener() {
	        public void dragStart (InputEvent event, float sx, float sy, int pointer) {
                if(!drag && !overing) drag = true;
	        }

	        public void drag (InputEvent event, float sx, float sy, int pointer) {
                if(!drag && !overing) cancelUse();
                else {
                    moveBy(sx - getWidth() / 2, sy - getHeight() / 2);
                    if(!member.canUse() && sy > (getHeight() * 0.75f)) {
                        //member.use();
                        cancelUse();
                    }
                }
	        }

	        public void dragStop (InputEvent event, float sx, float sy, int pointer) {
                if(md.getY(Align.center) > 350 && WakTower.game.battle.members.size < WakTower.game.memberLimit && !ActionHandler.isRunning) {
                    ActionHandler.bot(new SummonMemberAction(md));
                    addAction(Actions.fadeOut(0.25f));
                } else {
                    cancelUse();
                }
	        }
        };
        addListener(dragListener);
        addListener(new InputListener() {
	        public void enter (InputEvent event, float mx, float my, int pointer, @Null Actor fromActor) {
                if(!over && !overing && !drag) {
                    overing = true;
                    zIndex = getZIndex();
                    toFront();
                    MoveToAction action = new MoveToAction() {
                        protected void end () {
                            over = true;
                            overing = false;
                        }
                    };
                    action.setPosition(baseX, 0, Align.bottom);
                    action.setDuration(0.1f);
                    action.setInterpolation(Interpolation.circle);
                    addAction(Actions.parallel(action, Actions.rotateTo(0, 0.1f, Interpolation.circle), Actions.scaleTo(overScale, overScale, 0.1f)));
                }
	        }

	        public void exit (InputEvent event, float mx, float my, int pointer, @Null Actor toActor) {
                if((over || overing) && !drag) {
                    String name = getName();
                    if(name != null && name.equals("synergyIcon")) {
                        if(toActor == md) return;
                    }
                    if(toActor != null) {
                        name = toActor.getName();
                        if(name != null) {
                            if(name.equals("synergyIcon") && toActor.getParent() == root) return;
                            else if(name.equals("memberCardDisplay") && toActor.hashCode() == md.hashCode()) return;
                        }
                    }

                    overing = true;
                    setZIndex(zIndex);
                    MoveToAction action = new MoveToAction() {
                        protected void end () {
                            over = false;
                            overing = false;
                        }
                    };
                    action.setPosition(baseX, baseY, Align.bottom);
                    action.setDuration(0.1f);
                    action.setInterpolation(Interpolation.circle);
                    addAction(Actions.parallel(action, Actions.rotateTo(baseRotation, 0.1f, Interpolation.circle), Actions.scaleTo(baseScale, baseScale, 0.1f)));
                }
	        }
        });

        setOrigin(Align.bottom);
    }

    private void cancelUse() {
        setZIndex(zIndex);
        MoveToAction action = new MoveToAction() {
            protected void end () {
                drag = false;
                over = false;
            }
        };
        action.setPosition(baseX, baseY, Align.bottom);
        action.setDuration(0.1f);
        action.setInterpolation(Interpolation.circle);
        addAction(Actions.parallel(action, Actions.rotateTo(baseRotation, 0.1f, Interpolation.circle), Actions.scaleTo(baseScale, baseScale, 0.1f)));
    }

    @Override
	public void act (float delta) {
        if(over) {
            timer += delta * 10;
            if(timer > 1) timer = 1;
        } else {
            timer -= delta * 10;
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

    public void setBase(float x, float y, float rotation, float scale) {
        baseX = x;
        baseY = y;
        baseRotation = rotation;
        baseScale = scale;
    }

    public void resetPosition() {
        ParallelAction action = new ParallelAction();
        action.addAction(Actions.moveToAligned(baseX, baseY, Align.bottom, 0.1f, Interpolation.circle));
        action.addAction(Actions.scaleTo(baseScale, baseScale, 0.1f, Interpolation.circle));
        action.addAction(Actions.rotateTo(baseRotation, 0.1f, Interpolation.circle));
        addAction(action);
    }

    private static class SynergyIcon extends Image {

        private static final String HINT = FontHandler.getHexColor(Color.GRAY);
        private static final String WHITE = FontHandler.getHexColor(Color.WHITE);
        private static final String NAME = FontHandler.getColorKey("y");

        private AbstractMember owner;
        private AbstractSynergy synergy;
        private int index;
        public TextTooltip tooltip;

        public SynergyIcon(AbstractMember m, int index) {
            super();
            owner = m;
            this.index = index;
            setSynergy(owner.synergy[index]);
            setName("synergyIcon");
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
            tooltip.getContainer().pad(14);
            addListener(tooltip);
        }
    }
}
