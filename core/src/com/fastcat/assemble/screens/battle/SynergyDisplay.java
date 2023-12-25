package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.abstracts.AbstractUI.SubText.SubWay;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.FontHandler.FontData;

public class SynergyDisplay extends AbstractUI {

    private static final String HINT = FontHandler.getHexColor(Color.GRAY);
    private static final String HINT_2 = FontHandler.getHexColor(Color.LIGHT_GRAY);
    private static final String WHITE = FontHandler.getHexColor(Color.WHITE);
    private static final FontData NAME = FontHandler.SYN_NAME;
    private static final FontData AMOUNT = FontHandler.SYN_DESC;

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
        subs.name = synergy.name;
        int l = synergy.gradeAmount.length;
        subs.desc = synergy.desc;
        if(l > 1) {
            subs.desc += "\n";
            for(int i = 0; i < synergy.gradeAmount.length; i++) {
                int amt = synergy.gradeAmount[i];
                if(synergy.grade == i + (amt == 1 ? 0 : 1)) subs.desc += "\n(" + amt + ") " + synergy.gradeDesc[i];
                else subs.desc += HINT + "\n(" + amt + ") " + synergy.gradeDesc[i] + WHITE;
            }
        }
        return subs;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if(synergy != null) {
            if(type == SynergyDisplayType.card) {
                subInt = 10;
                subWay = SubWay.UP;
                sb.draw(synergy.img, x, y, width, height);
            } else if(type == SynergyDisplayType.grade) {
                subInt = originWidth * 1.6f;
                subWay = SubWay.RIGHT;
                sb.draw(synergy.gradeImg[synergy.grade], x, y, width, height);
                FontHandler.renderLineLeft(sb, NAME, synergy.name, x + width * 1.1f, y + height * 0.75f, width * 3);
                int next;
                if(synergy.grade < synergy.gradeImg.length - 1) {
                    next = synergy.gradeAmount[synergy.gradeAmount[0] < 2 ? synergy.grade + 1 : synergy.grade];
                } else {
                    next = synergy.gradeAmount[synergy.gradeAmount.length - 1];
                }
                FontHandler.renderLineLeft(sb, AMOUNT, synergy.memberCount + " / " + next, x + width * 1.1f, y + height * 0.25f, width * 3);
            }
        }
    }

    public float compareTo(SynergyDisplay sd) {
        int cg = synergy.gradeAmount[0], ng = sd.synergy.gradeAmount[0];
        int g1 = synergy.getGrade(), g2 = sd.synergy.getGrade();
        float cc = 0, nc = 0;

        if(cg == 1) cc = ((g1 + 1) / synergy.gradeAmount.length);
        else if(cg >= 2) cc = ((g1) / synergy.gradeAmount.length);

        if(ng == 1) nc = ((g2 + 1) / sd.synergy.gradeAmount.length);
        else if(ng >= 2) nc = ((g2) / sd.synergy.gradeAmount.length);

        return nc - cc;
    }

    public enum SynergyDisplayType {
        card, grade
    }
}
