package com.fastcat.assemble.screens.battle;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractBattle;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.members.Jingburger;

public class BattleStage extends Stage {

    private Table handTable;

    public final AbstractBattle battle;
    public LinkedList<MemberCardDisplay> memberCards = new LinkedList<>();
    
    public BattleStage(AbstractBattle battle) {
        super(WakTower.viewport);
        this.battle = battle;
        handTable = new Table();
        this.addActor(handTable);
        /*for(AbstractMember m : battle.drawPile) {
            MemberCardDisplay mcd = new MemberCardDisplay(m);
            handTable.addActor(mcd);
            memberCards.add(mcd);
        }*/
        MemberCardDisplay md = new MemberCardDisplay(new Jingburger());
        //handTable.addActor(md);
        handTable.setFillParent(true);
        handTable.add(md);
        setDebugAll(true);
        Gdx.input.setInputProcessor(this);
    }
}
