package com.fastcat.assemble.screens.battle;

import java.util.LinkedList;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractBattle;
import com.fastcat.assemble.abstracts.AbstractMember;

public class BattleStage extends Stage {

    private Table handTable;

    public final AbstractBattle battle;
    public LinkedList<MemberCardDisplay> memberCards = new LinkedList<>();
    
    public BattleStage(AbstractBattle battle) {
        super(new ScreenViewport(WakTower.camera));
        this.battle = battle;
        handTable = new Table();
        handTable.setDebug(true);
        handTable.align(Align.bottom);
        for(AbstractMember m : battle.drawPile) {
            MemberCardDisplay mcd = new MemberCardDisplay(m);
            handTable.addActor(mcd);
            memberCards.add(mcd);
        }
    }
}
