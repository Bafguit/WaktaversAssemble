package com.fastcat.assemble.abstracts;

import com.fastcat.assemble.handlers.ActionHandler;

public abstract class AbstractSynergy {

    private final SynergyData data;

    public final String id, name, desc;
    public final String[] gradeDesc;
    public final int[] gradeAmount;
    public final int maxGrade;
    public final Sprite img;
    public int baseMemCount, memberCount, grade, counter;

    protected AbstractSynergy(String id) {
        this.id = id;
        data = DataHandler.synergyData.get(id).cpy();
        name = data.name;
        desc = data.desc;
        gradeDesc = data.gradeDesc;
        gradeAmount = data.gradeAmount;
        maxGrade = gradeDesc.length;
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

        public SynergyData(String id, JsonValue json) {
            this.id = id;
            name = json.getString("name");
            desc = json.getString("desc");
            gradeDesc = json.getStringArray("gradeDesc");
            gradeAmount = json.getIntArray("gradeAmount");
            img = FileHandler.getTexture("synergy/" + id);
        }
    }
}