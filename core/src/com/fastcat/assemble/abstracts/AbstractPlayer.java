package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.members.Angel;
import com.fastcat.assemble.members.Freeter;
import com.fastcat.assemble.members.Gosegu;
import com.fastcat.assemble.members.Hikiking;
import com.fastcat.assemble.members.Ine;
import com.fastcat.assemble.members.Jingburger;
import com.fastcat.assemble.members.Jururu;
import com.fastcat.assemble.members.Kimchimandu;
import com.fastcat.assemble.members.Lilpa;
import com.fastcat.assemble.members.Messi;
import com.fastcat.assemble.members.Rusuk;
import com.fastcat.assemble.members.Sophia;
import com.fastcat.assemble.members.Victory;
import com.fastcat.assemble.members.Viichan;
import com.fastcat.assemble.members.Wakpago;

public class AbstractPlayer extends AbstractEntity {
    public AbstractPlayer() {
        super("Wakgood", true);
    }

    public Array<AbstractMember> getStartDeck() {
        Array<AbstractMember> a = new Array<>();
        
        a.add(new Messi());
        a.add(new Messi());
        a.add(new Messi());
        a.add(new Kimchimandu());
        a.add(new Kimchimandu());
        a.add(new Kimchimandu());
        a.add(new Messi());
        a.add(new Angel());
        a.add(new Angel());
        a.add(new Angel());
        a.add(new Angel());
        a.add(new Angel());
        a.add(new Freeter());
        a.add(new Gosegu());
        a.add(new Hikiking());
        a.add(new Ine());
        a.add(new Jingburger());
        a.add(new Jururu());
        a.add(new Lilpa());
        a.add(new Rusuk());
        a.add(new Sophia());
        a.add(new Victory());
        a.add(new Viichan());
        a.add(new Wakpago());

        return a;
    }
}
