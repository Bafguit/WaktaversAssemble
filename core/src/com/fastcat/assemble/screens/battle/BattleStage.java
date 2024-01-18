package com.fastcat.assemble.screens.battle;

import java.util.HashMap;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractBattle;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class BattleStage extends Stage {

    private Table handTable;

    public final AbstractBattle battle;
    public HashMap<AbstractMember, MemberCardDisplay> memberCards = new HashMap<>();
    
    public BattleStage(AbstractBattle battle) {
        super(WakTower.viewport);
        this.battle = battle;
        handTable = new Table();
        handTable.bottom();
        handTable.setFillParent(true);
        Drawable d = FileHandler.getUI().getDrawable("tile");
        TextButton b = new TextButton("DRAW", new TextButtonStyle(d, d, d, FontHandler.BF_NB30));
        b.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                battle.draw(1);
                updateHandPosition();
		        return true;
	        }
        });
        b.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
        this.addActor(b);
        this.addActor(handTable);

        battle.turnDraw();
        updateHandPosition();
        /*
        int i = 0, s = battle.hand.size();
        if(s == 1) {
            AbstractMember m = battle.hand.get(0);
            MemberCardDisplay mcd = new MemberCardDisplay(m);
            handTable.add(mcd).bottom();
            memberCards.put(m, mcd);
        } else {
            float g = (float)(s - 2) / (float)(WakTower.game.maxHand - 2);
            float pad = Interpolation.linear.apply(80, 120, g);
            float r = 7 - Interpolation.pow2InInverse.apply(0, 4, g);
            float startRotation = r * (s - 1) / 2;
            final float hMax = Interpolation.pow2In.apply(0, 80, g);
            for(AbstractMember m : battle.hand) {
                MemberCardDisplay mcd = new MemberCardDisplay(m);
                mcd.setOrigin(Align.bottom);
                if(s > 1) mcd.setRotation(startRotation - (r * i));
                Cell<MemberCardDisplay> c = handTable.add(mcd).bottom();
                if(i > 0) c.padLeft(-pad);
                int sf = s / 2;
                float pd = -70, alpha = 0.5f;
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
                c.padBottom(pd);
                mcd.memberName.setText(pd + ", " + alpha);
                memberCards.put(m, mcd);
                i++;
            }
        }

        MemberCardDisplay md = new MemberCardDisplay(new Gosegu());
        md.setOrigin(Align.bottom);
        md.setRotation(9);
        handTable.add(md).bottom().padBottom(-75);

        md = new MemberCardDisplay(new Jingburger());
        md.setOrigin(Align.bottom);
        md.setRotation(3);
        handTable.add(md).bottom().padBottom(-50).padLeft(-30);

        md = new MemberCardDisplay(new Victory());
        md.setOrigin(Align.bottom);
        md.setRotation(-3);
        handTable.add(md).bottom().padBottom(-50).padLeft(-30);

        md = new MemberCardDisplay(new Ine());
        md.setOrigin(Align.bottom);
        md.setRotation(-9);
        handTable.add(md).bottom().padBottom(-75).padLeft(-30);
*/
        setDebugAll(true);
        Gdx.input.setInputProcessor(this);
    }

    public void addMemberCardInHand(AbstractMember member) {

    }

    public void updateHandPosition() {
        int s = battle.hand.size();
        if(s == 1) {
            AbstractMember m = battle.hand.get(0);
            MemberCardDisplay md = memberCards.get(m);
            if(md == null) {
                md = new MemberCardDisplay(m);
                memberCards.put(m, md);
                md.setPosition(0, 0, Align.bottom);
                md.setScale(0.9f);
                md.setRotation(-70);
                handTable.add(md).maxSize(285, 428);
            }
            md.setBase(960, -70, 0, 0.9f);
            md.resetPosition();
        } else if(s > 1) {
            float bx, by, br, bs;
            float g = (float)(s - 2) / (float)(WakTower.game.maxHand - 2);
            float pad = Interpolation.linear.apply(80, 120, g);
            float r = 7 - Interpolation.pow2InInverse.apply(0, 4, g);
            float startRotation = r * (s - 1) / 2, startX = 960 - ((285 - pad) * (s - 1)) / 2;
            final float hMax = Interpolation.pow2In.apply(0, 80, g);
            for(int i = 0; i < battle.hand.size(); i++) {
                AbstractMember m = battle.hand.get(i);
                MemberCardDisplay mcd = memberCards.get(m);
                if(mcd == null) {
                    mcd = new MemberCardDisplay(m);
                    memberCards.put(m, mcd);
                    mcd.setPosition(0, 0, Align.bottom);
                    mcd.setScale(0.9f);
                    mcd.setRotation(-70);
                    handTable.add(mcd).maxSize(285, 428);
                }
                br = startRotation - (r * i);
                bx = startX + (285 - pad) * i;
                int sf = s / 2;
                float pd = -70, alpha = 0.5f;
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
                by = pd;
                bs = 0.9f;
                mcd.setBase(bx, by, br, bs);
                mcd.resetPosition();
            }
        }
    }
    
    public static class HandDisplayData {
        public Vector2 position;
        public float scale;
        public float rotation;
    }
}
