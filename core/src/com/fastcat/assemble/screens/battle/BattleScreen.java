package com.fastcat.assemble.screens.battle;

import java.util.Arrays;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractScreen;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.SynergyHandler;
import com.fastcat.assemble.members.Hikiking;
import com.fastcat.assemble.members.Victory;
import com.fastcat.assemble.screens.battle.SynergyDisplay.SynergyDisplayType;

public class BattleScreen extends AbstractScreen {

    public PlayerDisplay player;
    public HashMap<AbstractEnemy, EnemyDisplay> enemies;
    public HashMap<AbstractMember, MemberDisplay> hand;
    public HashMap<AbstractMember, MemberDisplay> members;
    public HashMap<AbstractSkill, SkillDisplay> skills;
    public HashMap<AbstractSynergy, SynergyDisplay> synergyMap;
    public SynergyDisplay[] synergies;
    public TurnEndButton turnEnd;

    public MemberDisplay testMember;
    public DrawButton drawButton;

    public BattleScreen() {
        super(ScreenType.BASE);
        enemies = new HashMap<>();
        hand = new HashMap<>();
        members = new HashMap<>();
        skills = new HashMap<>();
        synergyMap = new HashMap<>();
        synergies = new SynergyDisplay[19];
        turnEnd = new TurnEndButton();
        initialize();
        for(int i = 0; i < 3; i++) {
            addHand(new Victory());
            addHand(new Hikiking());
        }

        testMember = new MemberDisplay(new Victory());
        testMember.setPosition(200, 540);
        drawButton = new DrawButton();
    }

    public void initialize() {
        player = new PlayerDisplay(WakTower.game.player);
        player.setPosition(500, 600);
        hand.clear();
        members.clear();
        skills.clear();
        for(int i = 0; i < WakTower.game.skills.length; i++) {
            AbstractSkill s = WakTower.game.skills[i];
            SkillDisplay sd = new SkillDisplay(s);
            sd.setPosition(960 - 100 * i, 800);
            skills.put(s, sd);
        }

        int c = 0;
        for(JsonValue v : FileHandler.getInstance().jsonMap.get("synergy")) {
            SynergyDisplay s = new SynergyDisplay(SynergyHandler.getSynergyInstance(v.name), SynergyDisplayType.grade);
            synergies[c++] = s;
            synergyMap.put(s.synergy, s);
        }
        Arrays.sort(synergies);
    }

    @Override
    public void update() {
        testMember.update();

        for(MemberDisplay h : hand.values()) {
            if(h.over) {
                h.update();
                break;
            }
        }
        for(MemberDisplay h : hand.values()) {
            if(!h.over) h.update();
        }

        drawButton.update();

        int c = 0;
        for(MemberDisplay m : members.values()) {
            if(c < 4) {
                m.setPosition(940 - 103 - 210 * c, 500);
            } else {
                m.setPosition(940 - 170 - 210 * (c - 4), 400);
            }
            c++;
            m.update();
        }
        c = 0;
        for(SynergyDisplay s : synergies) {
            if(s.synergy.memberCount > 0) {
                s.setPosition(70, 810 - c * 66);
                s.update();
                c++;
            }
        }
        /*for(SkillDisplay s : skills.values()) {
            s.update();
        }
        turnEnd.update();
        player.update();
        for(EnemyDisplay e : enemies.values()) {
            e.update();
        }*/
    }

    public void addHand(AbstractMember member) {
        MemberDisplay m = new MemberDisplay(member);
        m.forcePosition(50, 50);
        hand.put(member, m);
        updateHandPosition();
    }

    public void updateHandPosition() {
        int c = 0, hs = hand.size();
        float hw = 960 + (140 * hs + 145) * 0.5f;
        for(MemberDisplay h : hand.values()) {
            if(h.over) {
                h.setPosition(hw - 145 - c * 140, 214);
            } else h.setPosition(hw - 145 - c * 140, 107);
        }
    }

    @Override
    protected void render(SpriteBatch sb) {
        /*if(WakTower.game.battle.isPlayerTurn()) {
            for(EnemyDisplay e : enemies.values()) {
                e.render(sb);
            }
            player.render(sb);
        } else {
            player.render(sb);
            for(EnemyDisplay e : enemies.values()) {
                e.render(sb);
            }
        }*/

        for(MemberDisplay m : members.values()) {
            if(!m.isCard) m.render(sb);
        }

        /*effectHandler.render(sb);

        turnEnd.render(sb);

        for(SkillDisplay s : skills.values()) {
            s.render(sb);
        }*/

        for(SynergyDisplay s : synergies) {
            s.render(sb);
        }

        drawButton.render(sb);

        for(MemberDisplay m : members.values()) {
            if(m.isCard) m.render(sb);
        }
        testMember.render(sb);
    }
}
