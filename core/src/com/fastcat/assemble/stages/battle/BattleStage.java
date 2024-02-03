package com.fastcat.assemble.stages.battle;

import java.util.HashMap;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractBattle;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractStage;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.battles.TestBattle;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.SynergyHandler;
import com.fastcat.assemble.stages.deckviewer.DeckViewerStage;
import com.fastcat.assemble.uis.TopBar;

public class BattleStage extends AbstractStage {

    private Table handTable;
    private Table fieldTable;
    private Table synergyTable;

    public SynergyDisplay[] synergies;

    public final AbstractBattle battle;
    public HashMap<AbstractMember, MemberCardDisplay> memberCards = new HashMap<>();
    public HashMap<AbstractMember, MemberFieldDisplay> memberFields = new HashMap<>();
    public HashMap<AbstractMember, Button> overTiles = new HashMap<>();

    private HashMap<AbstractSynergy, SynergyDisplay> synergyMap = new HashMap<>();

    public BattleStage() {
        this(WakTower.game.battle);
    }
    
    public BattleStage(AbstractBattle battle) {
        super(FileHandler.getPng("bg/way_lab"));

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
        synergyTable.setPosition(0, 900);
        
        for(AbstractSynergy s : WakTower.game.battle.synergy) {
            s.resetAll();
        }
        updateSynergy();

        Drawable d = FileHandler.getUI().getDrawable("tile");

        Table buttons = new Table();
        buttons.align(Align.bottomRight);

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
                WakTower.application.battleStage = new BattleStage();
                WakTower.stage = WakTower.application.battleStage;
		        return true;
	        }
        });
        buttons.add(b2).right();

        TextButton b3 = new TextButton("DEBUG", new TextButtonStyle(d, d, d, FontHandler.BF_NB30));
        b3.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                WakTower.debug = !WakTower.debug;
                setDebugAll(WakTower.debug);
		        return true;
	        }
        });
        buttons.add(b3).right();

        /*TextButton b4 = new TextButton("DECK", new TextButtonStyle(d, d, d, FontHandler.BF_NB30));
        b4.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                WakTower.stages.add(new DeckViewerStage());
		        return true;
	        }
        });
        buttons.add(b4).right();*/

        TextButton b5 = new TextButton("EXIT", new TextButtonStyle(d, d, d, FontHandler.BF_NB30));
        b5.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
		        return true;
	        }
        });
        buttons.add(b5).right();

        buttons.setPosition(1920, 0, Align.bottomRight);

        this.addActor(fieldTable);
        this.addActor(synergyTable);
        this.addActor(handTable);

        this.addActor(topBar);

        this.addActor(buttons);

        battle.turnDraw();
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
            synergyTable.add(sd).padTop(4).left();
            synergyTable.row();
        }
    }

    public void updateMemberPosition() {
        fieldTable.clearChildren();
        int ii = 0, jj = 0;
        for(int i = 0; i < battle.members.size; i++) {
            AbstractMember m = battle.members.get(i);
            MemberFieldDisplay md = memberFields.get(m);
            if(md == null) {
                md = new MemberFieldDisplay(m);
                memberFields.put(m, md);
            }
            fieldTable.addActor(md);
            md.setPosition(840 - (70 * jj) - 210 * ii, 500 - (90 * jj), Align.bottom);
            ii++;
            if(ii == 4) {
                jj++;
                ii = 0;
            }
        }

        ii = 0;
        jj = 0;
        for(int i = 0; i < battle.members.size; i++) {
            AbstractMember m = battle.members.get(i);
            Button overTile = overTiles.get(m);
            if(overTile == null) {
                Tooltip<MemberCardDisplay> tooltip = new Tooltip<MemberCardDisplay>(new MemberCardDisplay(m, true));
                //tooltip.setInstant(true);
                overTile = new Button(new TextureRegionDrawable(FileHandler.getTexture("ui/memberTile")));
                overTile.addListener(tooltip);
                overTile.setColor(1, 1, 1, 0);
                overTiles.put(m, overTile);
            }
            fieldTable.addActor(overTile);
            overTile.setPosition(840 - (70 * jj) - 210 * ii, 500 - (90 * jj), Align.bottom);
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
