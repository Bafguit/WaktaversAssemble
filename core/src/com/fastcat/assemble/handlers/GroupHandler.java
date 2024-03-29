package com.fastcat.assemble.handlers;

import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.enemies.Enemy1;
import com.fastcat.assemble.enemies.Enemy2;
import com.fastcat.assemble.enemies.Enemy3;
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
import com.fastcat.assemble.members.Valentine;
import com.fastcat.assemble.members.Victory;
import com.fastcat.assemble.members.Viichan;
import com.fastcat.assemble.members.Wakpago;
import com.fastcat.assemble.members.Yungter;
import com.fastcat.assemble.relics.CommonRelic1;
import com.fastcat.assemble.relics.CommonRelic2;
import com.fastcat.assemble.relics.CommonRelic3;
import com.fastcat.assemble.relics.CommonRelic4;
import com.fastcat.assemble.relics.CommonRelic5;
import com.fastcat.assemble.relics.CommonRelic6;
import com.fastcat.assemble.relics.CommonRelic7;
import com.fastcat.assemble.relics.CommonRelic8;
import com.fastcat.assemble.relics.CompetitorRelic;
import com.fastcat.assemble.relics.CrazyRelic;
import com.fastcat.assemble.relics.DoormatRelic;
import com.fastcat.assemble.relics.ExpertRelic;
import com.fastcat.assemble.relics.GuardianRelic;
import com.fastcat.assemble.relics.KiddoRelic;
import com.fastcat.assemble.relics.LegenoRelic1;
import com.fastcat.assemble.relics.LegenoRelic2;
import com.fastcat.assemble.relics.LegenoRelic3;
import com.fastcat.assemble.relics.LegenoRelic4;
import com.fastcat.assemble.relics.LegenoRelic5;
import com.fastcat.assemble.relics.LegenoRelic6;
import com.fastcat.assemble.relics.LegenoRelic7;
import com.fastcat.assemble.relics.LegenoRelic8;
import com.fastcat.assemble.relics.LegenoRelic9;
import com.fastcat.assemble.relics.MagicianRelic;
import com.fastcat.assemble.relics.MainVocalRelic;
import com.fastcat.assemble.relics.NoblesRelic;
import com.fastcat.assemble.relics.TimidRelic;
import com.fastcat.assemble.relics.VillainRelic;

import java.util.HashMap;

public class GroupHandler {

    private static HashMap<String, AbstractMember> memberGroup;
    private static HashMap<String, AbstractEnemy> enemyGroup;
    private static HashMap<String, AbstractRelic> relicGroup;

    public static void initialize() {
        memberGroup = new HashMap<>();
        enemyGroup = new HashMap<>();
        relicGroup = new HashMap<>();

        addMembers();
        addEnemies();
        addRelics();
    }

    private static void addMembers() {
        memberGroup.put("Wakgood", new Wakgood());
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
        memberGroup.put("Valentine", new Valentine());
        memberGroup.put("Charlotte", new Charlotte());
        memberGroup.put("Butterus", new Butterus());
        memberGroup.put("Dandap", new Dandap());
    }

    private static void addEnemies() {
        enemyGroup.put("Enemy1", new Enemy1());
        enemyGroup.put("Enemy2", new Enemy2());
        enemyGroup.put("Enemy3", new Enemy3());
    }

    private static void addRelics() {

        //Common Relics
        relicGroup.put("CommonRelic1", new CommonRelic1());
        relicGroup.put("CommonRelic2", new CommonRelic2());
        relicGroup.put("CommonRelic3", new CommonRelic3());
        relicGroup.put("CommonRelic4", new CommonRelic4());
        relicGroup.put("CommonRelic5", new CommonRelic5());
        relicGroup.put("CommonRelic6", new CommonRelic6());
        relicGroup.put("CommonRelic7", new CommonRelic7());
        relicGroup.put("CommonRelic8", new CommonRelic8());

        //Rare Relics
        relicGroup.put("GuardianRelic", new GuardianRelic());
        relicGroup.put("CrazyRelic", new CrazyRelic());
        relicGroup.put("ExpertRelic", new ExpertRelic());
        relicGroup.put("MagicianRelic", new MagicianRelic());
        relicGroup.put("TimidRelic", new TimidRelic());
        relicGroup.put("DoormatRelic", new DoormatRelic());
        relicGroup.put("VillainRelic", new VillainRelic());
        relicGroup.put("KiddoRelic", new KiddoRelic());
        relicGroup.put("NoblesRelic", new NoblesRelic());
        relicGroup.put("CompetitorRelic", new CompetitorRelic());
        relicGroup.put("MainVocalRelic", new MainVocalRelic());

        //Legeno Relics
        relicGroup.put("LegenoRelic1", new LegenoRelic1());
        relicGroup.put("LegenoRelic2", new LegenoRelic2());
        relicGroup.put("LegenoRelic3", new LegenoRelic3());
        relicGroup.put("LegenoRelic4", new LegenoRelic4());
        relicGroup.put("LegenoRelic5", new LegenoRelic5());
        relicGroup.put("LegenoRelic6", new LegenoRelic6());
        relicGroup.put("LegenoRelic7", new LegenoRelic7());
        relicGroup.put("LegenoRelic8", new LegenoRelic8());
        relicGroup.put("LegenoRelic9", new LegenoRelic9());
    }

    public static AbstractMember getMember(String id) {
        return memberGroup.get(id).duplicate();
    }

    public static AbstractEnemy getEnemy(String id) {
        return enemyGroup.get(id).duplicate();
    }

    public static AbstractRelic getRelic(String id) {
        return relicGroup.get(id).duplicate();
    }
}
