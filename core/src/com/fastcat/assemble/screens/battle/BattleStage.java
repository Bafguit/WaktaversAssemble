package com.fastcat.assemble.screens.battle;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

public class BattleStage extends Stage {

    private Table handTable;

    public final AbstractBattle battle;
    public LinkedList<MemberCardDisplay> memberCards = new LinkedList<>();
    
    public BattleStage(AbstractBattle battle) {
        super(WakTower.viewport);
        this.battle = battle;
        handTable = new Table();
        handTable.bottom();
        this.addActor(handTable);
        /*for(AbstractMember m : battle.drawPile) {
            MemberCardDisplay mcd = new MemberCardDisplay(m);
            handTable.addActor(mcd);
            memberCards.add(mcd);
        }*/
        //handTable.addActor(md);
        handTable.setFillParent(true);

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
