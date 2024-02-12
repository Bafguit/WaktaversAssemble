package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.members.AmadeusChoi;
import com.fastcat.assemble.members.Angel;
import com.fastcat.assemble.members.Bujeong;
import com.fastcat.assemble.members.Bulgom;
import com.fastcat.assemble.members.BusinessKim;
import com.fastcat.assemble.members.Butterus;
import com.fastcat.assemble.members.CallyCarly;
import com.fastcat.assemble.members.Charlotte;
import com.fastcat.assemble.members.Chouloky;
import com.fastcat.assemble.members.Chunsik;
import com.fastcat.assemble.members.Dandap;
import com.fastcat.assemble.members.Dokohyeji;
import com.fastcat.assemble.members.Dopamine;
import com.fastcat.assemble.members.Duksu;
import com.fastcat.assemble.members.Freeter;
import com.fastcat.assemble.members.Gilbert;
import com.fastcat.assemble.members.Gosegu;
import com.fastcat.assemble.members.Haku;
import com.fastcat.assemble.members.Hikiking;
import com.fastcat.assemble.members.Hodd;
import com.fastcat.assemble.members.Ine;
import com.fastcat.assemble.members.Jentoo;
import com.fastcat.assemble.members.Jingburger;
import com.fastcat.assemble.members.Jururu;
import com.fastcat.assemble.members.Kimchimandu;
import com.fastcat.assemble.members.Kkekki;
import com.fastcat.assemble.members.Kreaze;
import com.fastcat.assemble.members.Lilpa;
import com.fastcat.assemble.members.Ninnin;
import com.fastcat.assemble.members.Pungsin;
import com.fastcat.assemble.members.Roentgenium;
import com.fastcat.assemble.members.Wakgood;
import com.fastcat.assemble.members.Sullivan;
import com.fastcat.assemble.members.Valentine;
import com.fastcat.assemble.members.Rusuk;
import com.fastcat.assemble.members.Seyong;
import com.fastcat.assemble.members.Sirian;
import com.fastcat.assemble.members.Soosemi;
import com.fastcat.assemble.members.Sophia;
import com.fastcat.assemble.members.Victory;
import com.fastcat.assemble.members.Viichan;
import com.fastcat.assemble.members.Jinhe;
import com.fastcat.assemble.members.Wakpago;
import com.fastcat.assemble.members.Yungter;

public class AbstractPlayer extends AbstractEntity {
    public AbstractPlayer() {
        super("Wakgood", true);
    }

    public Array<AbstractMember> getStartDeck() {
        Array<AbstractMember> a = new Array<>();
        
        a.add(new Wakgood());
        a.add(new Angel());
        a.add(new Ine());
        a.add(new Jingburger());
        a.add(new Lilpa());
        a.add(new Jururu());
        a.add(new Gosegu());
        a.add(new Viichan());
        a.add(new Kimchimandu());
        a.add(new Freeter());
        a.add(new BusinessKim());
        a.add(new Haku());
        a.add(new Hikiking());
        a.add(new Sullivan());
        a.add(new Rusuk());
        a.add(new Gilbert());
        a.add(new Duksu());
        a.add(new Chunsik());
        a.add(new Bujeong());
        a.add(new Roentgenium());
        a.add(new Ninnin());
        a.add(new Kkekki());
        a.add(new Hodd());
        a.add(new Bulgom());
        a.add(new AmadeusChoi());
        a.add(new Seyong());
        a.add(new Sophia());
        a.add(new Victory());
        a.add(new Jinhe());
        a.add(new Jentoo());
        a.add(new Wakpago());
        a.add(new Yungter());
        a.add(new Dopamine());
        a.add(new CallyCarly());
        a.add(new Dokohyeji());
        a.add(new Pungsin());
        a.add(new Dandap());
        a.add(new Soosemi());
        a.add(new Sirian());
        a.add(new Kreaze());
        a.add(new Chouloky());
        a.add(new Valentine());
        a.add(new Charlotte());
        a.add(new Butterus());

        return a;
    }
}
