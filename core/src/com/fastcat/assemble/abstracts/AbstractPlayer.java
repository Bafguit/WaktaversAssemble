package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.members.Angel;
import com.fastcat.assemble.members.Kimchimandu;
import com.fastcat.assemble.members.Messi;

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

        return a;
    }
}
