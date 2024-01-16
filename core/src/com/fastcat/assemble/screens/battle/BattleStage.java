package com.fastcat.assemble.screens.battle;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractBattle;
import com.fastcat.assemble.abstracts.AbstractMember;

public class BattleStage extends Stage {

    private Table handTable;

    public final AbstractBattle battle;
    public LinkedList<MemberCardDisplay> memberCards = new LinkedList<>();
    
    public BattleStage(AbstractBattle battle) {
        super(WakTower.viewport);
        this.battle = battle;
        handTable = new Table();
        handTable.bottom();
        handTable.setFillParent(true);
        this.addActor(handTable);

        battle.turnDraw();
        int i = 0, s = battle.hand.size();
        if(s == 1) {
            MemberCardDisplay mcd = new MemberCardDisplay(battle.hand.get(0));
            handTable.add(mcd).bottom();
            memberCards.add(mcd);
        } else {
            float g = (s - 2) / (WakTower.game.maxHand - 2);
            float pad = Interpolation.linear.apply(50, 120, g);
            float r = 6 - Interpolation.pow2InInverse.apply(0, 4, g);
            float startRotation = r * (s - 1) / 2;
            float hMax = Interpolation.pow2In.apply(0, 80, g);
            for(AbstractMember m : battle.hand) {
                MemberCardDisplay mcd = new MemberCardDisplay(m);
                mcd.setOrigin(Align.bottom);
                if(s > 1) mcd.setRotation(startRotation - (r * i));
                Cell<MemberCardDisplay> c = handTable.add(mcd).bottom();
                if(i > 0) c.padLeft(-pad);
                float sf = (float)s / 2f;
                float pd = -70, alpha = 0.5f;
                if(s % 2 == 0) {
                    if(i < sf) {
                        int sff = MathUtils.floor(sf);
                        alpha = ((float) i) / (float)(sff - 1);
                        pd -= (hMax - Interpolation.pow2Out.apply(0, hMax, alpha));
                    } else if(i > sf) {
                        int sff = MathUtils.ceil(sf);
                        alpha = ((float)i - sff) / (float)(sff - 1);
                        pd -= Interpolation.pow2In.apply(0, hMax, alpha);
                    }
                } else {
                    float sff = (float)MathUtils.floor(sf);
                    if(i < (sf - 1)) {
                        alpha = ((float) i) / sff;
                        pd -= (hMax - Interpolation.pow2Out.apply(0, hMax, alpha));
                    } else if(i > sf) {
                        alpha = ((float)i - sff) / sff;
                        pd -= Interpolation.pow2In.apply(0, hMax, alpha);
                    }
                }
                c.padBottom(pd);
                mcd.memberName.setText(pd + ", " + alpha);
                memberCards.add(mcd);
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
/*
    private void updateHandPosition() {
        int i = 0, s = handTable.getChildren().size;
        if(s == 1) {
            Actor md = handTable.getChild(0);
            Cell<Actor> c = handTable.getCell(md);
            md.addAction(Actions.);
            handTable.add(mcd).bottom();
            memberCards.add(mcd);
        } else {
            float g = (s - 2) / (WakTower.game.maxHand - 2);
            float pad = Interpolation.linear.apply(30, 60, g);
            float r = 12 - Interpolation.pow2InInverse.apply(0, 12, g);
            float startRotation = (r * s) / 2;
            float hMax = Interpolation.pow2In.apply(0, -100, g);
            for(AbstractMember m : battle.hand) {
                MemberCardDisplay mcd = new MemberCardDisplay(m);
                if(s > 1) mcd.setRotation(startRotation - (r * i));
                Cell<MemberCardDisplay> c = handTable.add(mcd).bottom();
                if(i > 0) c.padLeft(-pad);
                c.padBottom(-FastCatUtils.returnInterpolation(Interpolation.pow2OutInverse, 0, hMax, i / (s - 1)) - (hMax - 100));
                memberCards.add(mcd);
                i++;
            }
        }
    }*/
}
