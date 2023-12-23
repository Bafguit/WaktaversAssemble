package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.abstracts.AbstractUI.SubText.SubWay;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class SynergyDisplay extends AbstractUI implements Comparable<SynergyDisplay> {

    private static final String HINT = FontHandler.getHexColor(Color.LIGHT_GRAY);
    private static final String WHITE = FontHandler.getHexColor(Color.WHITE);

    public final SynergyDisplayType type;
    public AbstractSynergy synergy;

    public MemberDisplay member;

    public SynergyDisplay(SynergyDisplayType type) {
        super(FileHandler.getTexture("ui/synergyIcon"));
        clickable = false;
        this.type = type;
        if(type == SynergyDisplayType.grade) overOnlyOne = false;
    }

    public SynergyDisplay(AbstractSynergy s, SynergyDisplayType type) {
        this(type);
        setSynergy(s);
    }
    
    public void setSynergy(AbstractSynergy s) {
        synergy = s;
        subs = new SubText(synergy.name, synergy.desc);
    }

    @Override
    protected void updateButton() {
        if(over) {
            subTexts = getSubText();
        } else subTexts = null;
    }

    @Override
    protected SubText getSubText() {
        subs.name = synergy.name + " | " + synergy.memberCount;
        String s = synergy.desc + (synergy.gradeAmount[0] > 0 ? "\n" : "");
        for(int i = 0; i < synergy.gradeAmount.length; i++) {
            int amt = synergy.gradeAmount[i];
            if(amt == 0) break;
            else if(synergy.grade == i + (amt == 1 ? 0 : 1)) s += "\n(" + amt + ") " + synergy.gradeDesc[i];
            else s += HINT + "\n(" + amt + ") " + synergy.gradeDesc[i] + WHITE;
        }
        subs.desc = s;
        return subs;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if(synergy != null) {
            if(type == SynergyDisplayType.card) {
                subWay = SubWay.UP;
                sb.draw(synergy.img, x, y, width, height);
            } else if(type == SynergyDisplayType.grade) {
                subWay = SubWay.RIGHT;
                sb.draw(synergy.gradeImg[synergy.grade], x, y, width, height);
            }
        }
    }

    @Override
    public int compareTo(SynergyDisplay sd) {
        return sd.synergy.grade - this.synergy.grade;
    }

    public enum SynergyDisplayType {
        card, grade
    }
}
