package com.fastcat.assemble.handlers;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractRoom.RoomType;
import com.fastcat.assemble.members.AmadeusChoi;
import com.fastcat.assemble.members.Angel;
import com.fastcat.assemble.members.Bujeong;
import com.fastcat.assemble.members.Bulgom;
import com.fastcat.assemble.members.BusinessKim;
import com.fastcat.assemble.members.CallyCarly;
import com.fastcat.assemble.members.Chouloky;
import com.fastcat.assemble.members.Chunsik;
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
import com.fastcat.assemble.members.Jinhe;
import com.fastcat.assemble.members.Jururu;
import com.fastcat.assemble.members.Kimchimandu;
import com.fastcat.assemble.members.Kkekki;
import com.fastcat.assemble.members.Kreaze;
import com.fastcat.assemble.members.Lilpa;
import com.fastcat.assemble.members.Ninnin;
import com.fastcat.assemble.members.Pungsin;
import com.fastcat.assemble.members.Roentgenium;
import com.fastcat.assemble.members.Wakgood;
import com.fastcat.assemble.members.Rusuk;
import com.fastcat.assemble.members.Seyong;
import com.fastcat.assemble.members.Sirian;
import com.fastcat.assemble.members.Soosemi;
import com.fastcat.assemble.members.Sophia;
import com.fastcat.assemble.members.Sullivan;
import com.fastcat.assemble.members.Victory;
import com.fastcat.assemble.members.Viichan;
import com.fastcat.assemble.members.Wakpago;
import com.fastcat.assemble.members.Yungter;

import java.util.HashMap;

public class GroupHandler {

    public static HashMap<String, EnemyGroup> monsterGroup;
    public static HashMap<String, AbstractMember> memberGroup;

    public static void initialize() {
        monsterGroup = new HashMap<>();
        memberGroup = new HashMap<>();

        addMembers();
    }

    private static void addMembers() {
        memberGroup.put("Messi", new Wakgood());
        memberGroup.put("Angel", new Angel());
        memberGroup.put("Jingburger", new Jingburger());
        memberGroup.put("Gosegu", new Gosegu());
        memberGroup.put("Lilpa", new Lilpa());
        memberGroup.put("Ine", new Ine());
        memberGroup.put("Viichan", new Viichan());
        memberGroup.put("Jururu", new Jururu());
        memberGroup.put("Victory", new Victory());
        memberGroup.put("Hikiking", new Hikiking());
        memberGroup.put("Sophia", new Sophia());
        memberGroup.put("Freeter", new Freeter());
        memberGroup.put("Duksu", new Duksu());
        memberGroup.put("Pungsin", new Pungsin());
        memberGroup.put("Dokohyeji", new Dokohyeji());
        memberGroup.put("Hodd", new Hodd());
        memberGroup.put("Yungter", new Yungter());
        memberGroup.put("Dopamine", new Dopamine());
        memberGroup.put("CallyCarly", new CallyCarly());
        memberGroup.put("Kimchimandu", new Kimchimandu());
        memberGroup.put("Haku", new Haku());
        memberGroup.put("Chunsik", new Chunsik());
        memberGroup.put("Bujeong", new Bujeong());
        memberGroup.put("Roentgenium", new Roentgenium());
        memberGroup.put("Ninnin", new Ninnin());
        memberGroup.put("Kkekki", new Kkekki());
        memberGroup.put("Bulgom", new Bulgom());
        memberGroup.put("AmadeusChoi", new AmadeusChoi());
        memberGroup.put("Seyong", new Seyong());
        memberGroup.put("Rusuk", new Rusuk());
        memberGroup.put("Wakpago", new Wakpago());
        memberGroup.put("BusinessKim", new BusinessKim());
        memberGroup.put("Gilbert", new Gilbert());
        memberGroup.put("Sullivan", new Sullivan());
        memberGroup.put("Jinhe", new Jinhe());
        memberGroup.put("Jentoo", new Jentoo());
        memberGroup.put("Soosemi", new Soosemi());
        memberGroup.put("Sirian", new Sirian());
        memberGroup.put("Kreaze", new Kreaze());
        memberGroup.put("Chouloky", new Chouloky());
    }

    public static EnemyGroup getBoss(int number) {
        return monsterGroup.get("boss_" + number + "_" + WakTower.game.mapRandom.random(0, 2));
    }

    public static AbstractMember getMember(String id) {
        AbstractMember m = memberGroup.get(id).duplicate();

        return m;
    }

    public static class EnemyGroup {
        public RoomType type;

        public EnemyGroup(RoomType type) {
            this.type = type;
        }
    }
}
