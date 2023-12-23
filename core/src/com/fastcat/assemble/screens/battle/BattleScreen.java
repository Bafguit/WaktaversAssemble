package com.fastcat.assemble.screens.battle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractScreen;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.handlers.SynergyHandler;
import com.fastcat.assemble.members.Hikiking;
import com.fastcat.assemble.members.Victory;
import com.fastcat.assemble.screens.battle.SynergyDisplay.SynergyDisplayType;

public class BattleScreen extends AbstractScreen {

    public PlayerDisplay player;
    public HashMap<AbstractEnemy, EnemyDisplay> enemies;
    public LinkedList<MemberDisplay> hand;
    public MemberDisplay clicked;
    public HashMap<AbstractMember, MemberDisplay> members;
    public HashMap<AbstractSkill, SkillDisplay> skills;
    public HashMap<AbstractSynergy, SynergyDisplay> synergyMap;
    public SynergyDisplay[] synergies;
    public TurnEndButton turnEnd;

    public DrawButton drawButton;

    public BattleScreen() {
        super(ScreenType.BASE);
        enemies = new HashMap<>();
        hand = new LinkedList<>();
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
        drawButton = new DrawButton();
    }

    public void initialize() {
        player = new PlayerDisplay(WakTower.game.player);
        player.setPosition(500, 600);
        int sz = WakTower.game.battle.enemies.size();
        for(int i = 0; i < sz; i++) {
            AbstractEnemy e = WakTower.game.battle.enemies.get(i);
            EnemyDisplay ed = new EnemyDisplay(e);
            ed.setPosition(1420 - 150 * (sz / 2 + i), sz == 1 ? 500 : i % 2 == 0 ? 570 : 430);
            enemies.put(e, ed);
        }
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

        if(clicked != null) {
            clicked.forcePosition(InputHandler.mx / InputHandler.scaleX, InputHandler.my / InputHandler.scaleY);
            clicked.update();
        }
        
        for(MemberDisplay h : hand) {
            if(!h.clicking) h.update();
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
        Arrays.sort(synergies);
        for(SynergyDisplay s : synergies) {
            if(s.synergy.memberCount > 0) {
                s.setPosition(70, 810 - c * s.height * 1.1f);
                c++;
            } else s.setPosition(-10000, -10000);
            s.update();
        }
        for(SkillDisplay s : skills.values()) {
            s.update();
        }
        turnEnd.update();
        player.update();
        for(EnemyDisplay e : enemies.values()) {
            e.update();
        }
        updateHandPosition();
    }

    public void addHand(AbstractMember member) {
        MemberDisplay m = new MemberDisplay(member);
        m.forcePosition(50, 50);
        hand.add(m);
        updateHandPosition();
    }

    public void updateHandPosition() {
        int c = 0, cw = 0, hs = hand.size();
        boolean hasOver = false;
        for(MemberDisplay h : hand) {
            if(h != clicked) {
                if(c > 0 && h.over) {
                    hasOver = true;
                    break;
                }
                c++;
            } else {
                hs -= 1;
                break;
            }
        }
        c = 0;
        float hw = 960 + (140 * hs + (hasOver ? 290 : 145)) * 0.5f;
        if(clicked == null) {
            for(MemberDisplay h : hand) {
                if(h.over) {
                    if(c > 0) cw += 145;
                    h.setPosition(hw - 145 - cw, 214);
                } else h.setPosition(hw - 145 - cw, 107);
                cw += 140;
                c++;
            }
        } else {
            for(MemberDisplay h : hand) {
                if(h != clicked) {
                    h.setPosition(hw - 145 - c * 140, 107);
                    c++;
                }
            }
        }
    }

    @Override
    protected void render(SpriteBatch sb) {
        if(WakTower.game.battle.isPlayerTurn()) {
            for(EnemyDisplay e : enemies.values()) {
                e.render(sb);
            }
            player.render(sb);
        } else {
            player.render(sb);
            for(EnemyDisplay e : enemies.values()) {
                e.render(sb);
            }
        }

        for(MemberDisplay m : members.values()) {
            if(!m.isCard) m.render(sb);
        }

        effectHandler.render(sb);

        turnEnd.render(sb);

        for(SkillDisplay s : skills.values()) {
            s.render(sb);
        }

        for(SynergyDisplay s : synergies) {
            if(s.enabled && s.synergy.memberCount > 0) {
                s.render(sb);
            }
        }

        drawButton.render(sb);

        if(clicked != null) clicked.render(sb);

        for(int i = hand.size() - 1; i >= 0; i--) {
            MemberDisplay d = hand.get(i);
            if(!d.over) d.render(sb);
        }

        for(int i = hand.size() - 1; i >= 0; i--) {
            MemberDisplay d = hand.get(i);
            if(d.over) d.render(sb);
        }
    }

    public MemberDisplay getMemberFromHand(AbstractMember m) {
        for(MemberDisplay d : hand) {
            if(d.member == m) return d;
        }
        return null;
    }

    public int indexOfHand(AbstractMember m) {
        for(int i = 0; i < hand.size(); i++) {
            MemberDisplay d = hand.get(i);
            if(d.member == m) return i;
        }
        return -1;
    }
}
