package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;

public class SynergyDisplay extends AbstractUI implements Comparable<SynergyDisplay> {

    public final SynergyDisplayType type;
    public AbstractSynergy synergy;

    public MemberDisplay member;

    public SynergyDisplay(SynergyDisplayType type) {
        super(FileHandler.getTexture("ui/synergyIcon"));
        clickable = false;
        this.type = type;
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
    protected void renderUi(SpriteBatch sb) {
        if(synergy != null) {
            if(type == SynergyDisplayType.card) {
                if(member.over) subTexts = getSubText();
                else subTexts = null;
                sb.draw(synergy.img, x, y, width, height);
            } else if(type == SynergyDisplayType.grade) {
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
