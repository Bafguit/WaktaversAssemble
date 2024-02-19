package com.fastcat.assemble.abstracts;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    public final TextureRegionDrawable img;
    public final TextureRegionDrawable[] gradeImg;
    public int baseMemCount, memberCount, grade, counter, globalCount;
    public int priority = 0;
    public LinkedList<AbstractMember> members;

    public boolean isOver = false;

    public AbstractSynergy(String id) {
        this.id = id;
        data = DataHandler.getInstance().synergyData.get(id);
        name = data.name;
        desc = data.desc;
        gradeDesc = data.gradeDesc;
        gradeAmount = data.gradeAmount;
        img = new TextureRegionDrawable(data.img);
        maxGrade = gradeDesc.length;
        gradeImg = new TextureRegionDrawable[data.gradeImg.length];
        for(int i = 0; i < gradeImg.length; i++) {
            gradeImg[i] = new TextureRegionDrawable(data.gradeImg[i]);
        }
        globalCount = 0;
        baseMemCount = memberCount = grade = counter = 0;
        members = new LinkedList<>();
    }

    public void addMember(AbstractMember m) {
        members.add(m);
        update();
    }

    public void update() {
        memberCount = getActualMemberCount();
        grade = getGrade();
        priority = (int) ((((float) (grade + (gradeAmount[0] == 1 ? 1 : 0))) / ((float) gradeImg.length)) * 100);
    }

    public int getActualMemberCount() {
        int c = members.size();
        for(int i = 0; i < members.size(); i++) {
            AbstractMember m = members.get(i);
            for(int j = 0; j < i; j++) {
                if(m.id.equals(members.get(j).id)) {
                    c--;
                    break;
                }
            }
        }
        return c + baseMemCount;
    }

    public int getGrade() {
        for(int i = gradeAmount.length; i > 0; i--) {
            int a = gradeAmount[i - 1];
            if(memberCount >= a) {
                if(gradeAmount[0] == 1) return i - 1;
                else return i;
            }
        }
        return 0;
    }

    public int upgradeAmount() {
        return 0;
    }

    public void onSummon(AbstractMember m) {}

    public int repeatAmount(AbstractMember m) {
        return 1;
    }
    
    public float muliplyEffect() {
        return 1f;
    }

    public void reset() {
        members.clear();
        memberCount = getActualMemberCount();
        grade = getGrade();
    }

    public void resetAll() {
        members.clear();
        memberCount = getActualMemberCount();
        grade = getGrade();
    }

    public void endOfTurn(boolean isPlayer) {}

    public void endOfBattle() {}

    public void gradeUp() {}

    public void flash() {
        ActionHandler.next(new SynergyFlashAction(this));
    }

    public static class SynergyData {
        public final String id, name, desc;
        public final String[] gradeDesc;
        public final int[] gradeAmount;
        public final TextureRegionDrawable img;
        public final TextureRegionDrawable[] gradeImg;

        public SynergyData(String id, JsonValue json) {
            this.id = id;
            name = json.getString("name");
            desc = json.getString("desc");
            gradeDesc = json.get("gradeDesc").asStringArray();
            gradeAmount = json.get("gradeAmount").asIntArray();
            TextureAtlas a = FileHandler.getAtlas("image/synergy/synergy");
            img = new TextureRegionDrawable(a.findRegion(id, -1));
            gradeImg = new TextureRegionDrawable[gradeAmount[0] < 2 ? gradeAmount.length : gradeAmount.length + 1];
            if(gradeAmount[0] == 0) gradeImg[0] = new TextureRegionDrawable(img);
            else {
                for(int i = 0; i < gradeImg.length; i++) {
                    TextureRegionDrawable s = new TextureRegionDrawable(a.findRegion(id, i));
                    gradeImg[i] = s;
                }
            }
        }
    }
}