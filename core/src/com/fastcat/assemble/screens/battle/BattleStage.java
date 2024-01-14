package com.fastcat.assemble.screens.battle;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractBattle;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.members.Gosegu;
import com.fastcat.assemble.members.Ine;
import com.fastcat.assemble.members.Jingburger;
import com.fastcat.assemble.members.Victory;
import com.fastcat.assemble.utils.FastCatUtils;

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
/*
        battle.turnDraw();
        int i = 0, s = battle.hand.size();
        float pad = Interpolation.linear.apply(30, 60, (s + 1) / (WakTower.game.maxHand + 1));
        float r = 3 * s;
        for(AbstractMember m : battle.hand) {
            MemberCardDisplay mcd = new MemberCardDisplay(m);
            mcd.setRotation(-FastCatUtils.mirrorInterpolation(Interpolation.pow2InInverse, Interpolation.pow2InInverse, 0, r, r * 2, (i + 1) / (s + 1)) - r);
            Cell<MemberCardDisplay> c = handTable.add(mcd).bottom();
            if(i > 0) c.padLeft(-pad);
            float pb = 100 * Interpolation.pow2In.apply(0f, 1, (s + 1) / (WakTower.game.maxHand + 1));
            c.padBottom(FastCatUtils.returnInterpolation(Interpolation.pow2InInverse, 0, pb, i / (s - 1)) - (100 + pb));
            memberCards.add(mcd);
            i++;
        }
*/
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

        setDebugAll(true);
        Gdx.input.setInputProcessor(this);
    }
}
