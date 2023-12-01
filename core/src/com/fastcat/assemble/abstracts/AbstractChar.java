package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.fastcat.assemble.actions.SynergyFlashAction;
import com.fastcat.assemble.handlers.ActionHandler;


public class AbstractChar {

    public final CharData data;

    public String id;
    public String name;
    public String desc;
    public String flavor;
    public Sprite img;
    public final Synergy[] synergy;

    public AbstractChar(String id) {
        this.id = id;
        data = new CharData(id);
        name = data.name;
        desc = data.desc;
        flavor = data.flavor;
        img = new Sprite(data.img);
        synergy = data.synergy;
    }

    public enum Synergy {
        isedol, badass, family, kiddo, oldman,
        villain, crazy, guardian, doormat, mindmaster,
        magician, nobles, sister, competitor, expert,
        cat, cutey, timid, machinery, mainvocal;

        public int count, grade;

        public void flash() {
            ActionHandler.next(new SynergyFlashAction(this));
        }

        public int getNumber(int sCount, int sGrade) {
            if(this == crazy) return sGrade;
            else if(this == guardian) {
                if(sGrade < 3) return sGrade * 5;
                else return 20;
            } else if(this == kiddo) return 5 + sGrade * 15;
            else if(this == doormat) return  sGrade;
            else if(this == villain) return sCount * sGrade;

            else return 0;
        }
    }

    public static class CharData {
        public  String id;
        public  String name;
        public  String desc;
        public  String flavor;
        public  Texture img;
        public  Synergy[] synergy;

        public CharData(String id) {

        }
    }
}
