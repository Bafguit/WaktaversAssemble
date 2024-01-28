package com.fastcat.assemble.screens.battle;

import java.util.HashMap;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractBattle;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.battles.TestBattle;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.SynergyHandler;

public class BattleStage extends Stage {

    private Table handTable;
    private Table fieldTable;
    private Table synergyTable;

    public SynergyDisplay[] synergies;

    public final AbstractBattle battle;
    public HashMap<AbstractMember, MemberCardDisplay> memberCards = new HashMap<>();
    public HashMap<AbstractMember, MemberFieldDisplay> memberFields = new HashMap<>();

    private HashMap<AbstractSynergy, SynergyDisplay> synergyMap = new HashMap<>();

    public BattleStage() {
        this(WakTower.game.battle);
    }
    
    public BattleStage(AbstractBattle battle) {
        super(WakTower.viewport);

        this.battle = battle;

        synergies = new SynergyDisplay[19];

        int c = 0;
        for(JsonValue v : FileHandler.getInstance().jsonMap.get("synergy")) {
            SynergyDisplay s = new SynergyDisplay(SynergyHandler.getSynergyInstance(v.name));
            synergies[c++] = s;
            synergyMap.put(s.getSynergy(), s);
        }

        battle.setStage(this);

        handTable = new Table();
        handTable.bottom();
        handTable.setSize(1920, 350);

        fieldTable = new Table();
        fieldTable.setFillParent(true);

        synergyTable = new Table();
        synergyTable.setOrigin(Align.topLeft);
        synergyTable.align(Align.topLeft);
        synergyTable.setPosition(0, 810);

        updateSynergy();

        Drawable d = FileHandler.getUI().getDrawable("tile");

        Table buttons = new Table();
        buttons.align(Align.topRight);

        TextButton b = new TextButton("DRAW", new TextButtonStyle(d, d, d, FontHandler.BF_NB30));
        b.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                battle.draw(1);
		        return true;
	        }
        });
        buttons.add(b).right();

        TextButton b2 = new TextButton("RESET", new TextButtonStyle(d, d, d, FontHandler.BF_NB30));
        b2.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                WakTower.game.battle = new TestBattle();
                WakTower.stage = new BattleStage();
		        return true;
	        }
        });
        buttons.add(b2).right();

        TextButton b3 = new TextButton("EXIT", new TextButtonStyle(d, d, d, FontHandler.BF_NB30));
        b3.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
		        return true;
	        }
        });
        buttons.add(b3).right();
        buttons.row();

        TextArea label = new TextArea("", new TextFieldStyle(FontHandler.BF_NB16, Color.WHITE, null, null, null)) {
            @Override
            public void act(float delta) {
                setText(WakTower.getLog());
                super.act(delta);
            }
        };
        label.setSize(300, 200);
        buttons.add(label).bottom().colspan(3);
        buttons.setPosition(1920, 1080, Align.topRight);

        this.addActor(fieldTable);
        this.addActor(synergyTable);
        this.addActor(handTable);
        this.addActor(buttons);

        battle.turnDraw();
        setDebugAll(true);
        Gdx.input.setInputProcessor(this);
    }

    public void addMemberCardInHand(AbstractMember member) {

    }

    public void updateAll() {
        updateSynergy();
        updateMemberPosition();
        updateHandPosition();
    }

    public void updateSynergy() {
        synergyTable.clearChildren();

        LinkedList<SynergyDisplay> list = new LinkedList<>();
        for(SynergyDisplay s1 : synergyMap.values()) {
            if(s1.getSynergy().memberCount > 0) {
                int j = 0;
                for(j = 0; j < list.size(); j++) {
                    SynergyDisplay sn = list.get(j);
                    if(s1.getSynergy().priority > sn.getSynergy().priority) break;
                }
                list.add(j, s1);
            }
        }

        for(SynergyDisplay sd : list) {
            synergyTable.add(sd).padTop(4);
            synergyTable.row();
        }
    }

    public void updateMemberPosition() {
        int ii = 0, jj = 0;
        for(int i = 0; i < battle.members.size; i++) {
            AbstractMember m = battle.members.get(i);
            MemberFieldDisplay md = memberFields.get(m);
            if(md == null) {
                md = new MemberFieldDisplay(m);
                memberFields.put(m, md);
                fieldTable.addActor(md);
            }
            md.setPosition(840 - (70 * jj) - 210 * ii, 600 - (90 * jj), Align.bottom);
            ii++;
            if(ii == 4) {
                jj++;
                ii = 0;
            }
        }
    }

    public void updateHandPosition() {
        int s = battle.hand.size();
        if(s == 1) {
            AbstractMember m = battle.hand.get(0);
            MemberCardDisplay md = memberCards.get(m);
            if(md == null) {
                md = new MemberCardDisplay(m);
                memberCards.put(m, md);
                md.setScale(0.5f);
                md.setRotation(-70);
                handTable.addActor(md);
                md.setPosition(0, 0, Align.bottom);
            }
            md.setBase(960, -70, 0, 0.85f);
            md.resetPosition();
        } else if(s > 1) {
            float bx, by, br, bs;
            float g = (float)(s - 2) / (float)(WakTower.game.maxHand - 2);
            float pad = Interpolation.linear.apply(90, 130, g);
            float r = 7 - Interpolation.pow2InInverse.apply(0, 4, g);
            float startRotation = r * (s - 1) / 2, startX = 960 - ((285 - pad) * (s - 1)) / 2;
            final float hMax = Interpolation.pow2In.apply(0, 80, g);
            for(int i = 0; i < battle.hand.size(); i++) {
                AbstractMember m = battle.hand.get(i);
                MemberCardDisplay mcd = memberCards.get(m);
                if(mcd == null) {
                    mcd = new MemberCardDisplay(m);
                    memberCards.put(m, mcd);
                    mcd.setScale(0.5f);
                    mcd.setRotation(-70);
                    handTable.addActor(mcd);
                    mcd.setPosition(0, 0, Align.bottom);
                }
                br = startRotation - (r * i);
                bx = startX + (285 - pad) * i;
                int sf = s / 2;
                float pd = -60, alpha = 0.5f;
                if(s > 2) {
                    if(s % 2 == 0) {
                        if(i < sf) {
                            alpha = ((float) i) / (float)(sf - 1);
                            pd -= (hMax - Interpolation.pow2Out.apply(0, hMax, alpha));
                        } else {
                            alpha = ((float)i - sf) / (float)(sf - 1);
                            pd -= Interpolation.pow2In.apply(0, hMax, alpha);
                        }
                    } else {
                        if(i < sf) {
                            alpha = ((float) i) / (float)sf;
                            pd -= (hMax - Interpolation.pow2Out.apply(0, hMax, alpha));
                        } else {
                            alpha = ((float)i - sf) / (float)sf;
                            pd -= Interpolation.pow2In.apply(0, hMax, alpha);
                        }
                    }
                }
                by = pd;
                bs = 0.85f;
                mcd.setBase(bx, by, br, bs);
                mcd.resetPosition();
            }
        }
    }

    public void removeHand(AbstractMember m) {
        battle.hand.remove(m);
        handTable.removeActor(memberCards.remove(m));
        updateHandPosition();
    }
    
    public static class HandDisplayData {
        public Vector2 position;
        public float scale;
        public float rotation;
    }
}
