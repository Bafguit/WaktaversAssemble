package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.actions.SynergyFlashAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;

public abstract class AbstractSynergy {

    private final SynergyData data;

    public final String id, name, desc;
    public final String[] gradeDesc;
    public final int[] gradeAmount;
    public final int maxGrade;
    public final Sprite img;
    public final Sprite[] gradeImg;
    public int baseMemCount, memberCount, grade, counter, globalGrade;

    public AbstractSynergy(String id) {
        this.id = id;
        data = DataHandler.getInstance().synergyData.get(id);
        name = data.name;
        desc = data.desc;
        gradeDesc = data.gradeDesc;
        gradeAmount = data.gradeAmount;
        img = new Sprite(data.img);
        maxGrade = gradeDesc.length;
        gradeImg = new Sprite[maxGrade];
        for(int i = 0; i < maxGrade; i++) {
            gradeImg[i] = new Sprite(data.gradeImg[i]);
        }
        globalGrade = 0;
        baseMemCount = memberCount = grade = counter = 0;
    }

    public void endOfTurn(boolean isPlayer) {}
    public void gradeUp() {}

    public void flash() {
        ActionHandler.next(new SynergyFlashAction(this));
    }

    public static class SynergyData {
        public final String id, name, desc;
        public final String[] gradeDesc;
        public final int[] gradeAmount;
        public final Texture img;
        public final Texture[] gradeImg;

        public SynergyData(String id, JsonValue json) {
            this.id = id;
            name = json.getString("name");
            desc = json.getString("desc");
            gradeDesc = json.get("gradeDesc").asStringArray();
            gradeAmount = json.get("gradeAmount").asIntArray();
            img = FileHandler.getTexture("synergy/" + id);
            gradeImg = new Texture[gradeDesc.length];
            for(int i = 1; i <= gradeDesc.length; i++) {
                gradeImg[i - 1] = FileHandler.getTexture("synergy/" + id + "_" + i);
            }
        }
    }
}