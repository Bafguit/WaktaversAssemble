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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractBattle;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.handlers.FileHandler;

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
        Button b = new Button(FileHandler.getUI().getDrawable("tile")) {
            @Override
            public boolean fire (Event event) {
                battle.draw(1);
                //updateHandPosition();
                return super.fire(event);
            }
        };
        b.setPosition(getWidth() / 2, getHeight() / 2);
        this.addActor(b);
        this.addActor(handTable);

        battle.turnDraw();
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
/*
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

    private void updateHandPosition() {
        int s = battle.hand.size();
        HashMap<AbstractMember, HandDisplayData> map = new HashMap<>();
        for(int i = 0; i < s; i++) {
            AbstractMember m = battle.hand.get(i);
            MemberCardDisplay c = memberCards.get(m);
            HandDisplayData data = new HandDisplayData();
            if(c != null) {
                data.position = new Vector2(c.getX(), c.getY());
                data.scale = c.getScaleX();
                data.rotation = c.getRotation();
            } else {
                data.position = new Vector2(0, 0);
                data.scale = 0.5f;
                data.rotation = -70;
            }
            map.put(m, data);
        }

        int i = 0;
        handTable.clearChildren();
        memberCards.clear();
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

        ParallelAction action = Actions.parallel();
        for(int j = 0; j < s; j++) {
            Actor c = handTable.getChild(j);
            Vector2 vector = new Vector2(c.getX(), c.getY());
            float scale = c.getScaleX();
            float rotation = c.getRotation();

            HandDisplayData data = map.get(battle.hand.get(j));
            if(data != null) {
                c.setScale(data.scale);
                c.setRotation(data.rotation);
                Vector2 bv = data.position;
                c.setPosition(bv.x, bv.y);

                action.addAction(Actions.moveTo(vector.x, vector.y, 0.1f));
                action.addAction(Actions.scaleTo(scale, scale, 0.1f));
                action.addAction(Actions.rotateTo(rotation, 0.1f));
            }
        }
        handTable.addAction(action);
    }
    
    public static class HandDisplayData {
        public Vector2 position;
        public float scale;
        public float rotation;
    }
}
