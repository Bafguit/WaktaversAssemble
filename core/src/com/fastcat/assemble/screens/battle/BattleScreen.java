package com.fastcat.assemble.screens.battle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractScreen;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.actions.StartBattleAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.handlers.SynergyHandler;
import com.fastcat.assemble.members.Hikiking;
import com.fastcat.assemble.members.Victory;
import com.fastcat.assemble.screens.battle.SynergyDisplay.SynergyDisplayType;

public class BattleScreen extends AbstractScreen {

    public PlayerDisplay player;
    public LinkedList<EnemyDisplay> enemies;
    public LinkedList<MemberDisplay> hand;
    public MemberDisplay clicked;
    public Array<MemberDisplay> members;
    public HashMap<AbstractSynergy, SynergyDisplay> synergyMap;
    public SynergyDisplay[] synergies;
    public LinkedList<SynergyDisplay> synergyDisplays;
    public TurnEndButton turnEnd;

    public DrawButton drawButton;

    public BattleScreen() {
        super(ScreenType.BASE);
        enemies = new LinkedList<>();
        hand = new LinkedList<>();
        members = new Array<>();
        synergyMap = new HashMap<>();
        synergies = new SynergyDisplay[19];
        synergyDisplays = new LinkedList<>();
        turnEnd = new TurnEndButton();
        initialize();
        drawButton = new DrawButton();
    }

    public void initialize() {
        player = new PlayerDisplay(WakTower.game.player);
        player.setPosition(500, 650);
        int sz = WakTower.game.battle.enemies.size();
        for(int i = 0; i < sz; i++) {
            AbstractEnemy e = WakTower.game.battle.enemies.get(i);
            EnemyDisplay ed = new EnemyDisplay(e);
            ed.setPosition(1400, sz == 1 ? 600 : i % 2 == 0 ? 670 : 530);
            enemies.add(ed);
        }
        hand.clear();
        members.clear();

        int c = 0;
        for(JsonValue v : FileHandler.getInstance().jsonMap.get("synergy")) {
            SynergyDisplay s = new SynergyDisplay(SynergyHandler.getSynergyInstance(v.name), SynergyDisplayType.grade);
            synergies[c++] = s;
            synergyMap.put(s.synergy, s);
        }
        sortSynergies();
        ActionHandler.bot(new StartBattleAction());
    }

    @Override
    public void update() {

        if(clicked != null) {
            clicked.forcePosition(InputHandler.mx / InputHandler.scaleX, InputHandler.my / InputHandler.scaleY);
            clicked.update();
        }
        
        for(MemberDisplay h : hand) {
            if(!h.isUsing && !h.clicking) h.update();
        }

        drawButton.update();

        int ii = 0, jj = 0;
        for(MemberDisplay m : members) {
            m.forcePosition(840 - (70 * jj) - 210 * ii, 600 - (90 * jj));

            ii++;
            if(ii == 3) {
                jj++;
                ii = 0;
            }
        }

        for(int i = members.size - 1; i >= 0; i--) {
            MemberDisplay m = members.get(i);
            m.update();
        }

        int c = 0;
        sortSynergies();
        for(int i = 0; i < synergyDisplays.size(); i++) {
            SynergyDisplay s = synergyDisplays.get(i);
            s.setPosition(70, 810 - c * s.originHeight * 1.05f);
            c++;
            s.update();
        }
        turnEnd.update();
        player.update();
        for(EnemyDisplay e : enemies) {
            e.update();
        }
        updateHandPosition();
    }

    private void sortSynergies() {
        synergyDisplays.clear();
        for(int i = 0; i < synergies.length; i++) {
            SynergyDisplay s1 = synergies[i];
            if(s1.synergy.memberCount > 0) {
                int j = 0;
                for(j = 0; j < synergyDisplays.size(); j++) {
                    SynergyDisplay sn = synergyDisplays.get(j);
                    if(s1.synergy.priority > sn.synergy.priority) break;
                }
                synergyDisplays.add(j, s1);
            }
        }
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
            if(h != clicked && !h.isUsing) {
                if(c > 0 && h.over) {
                    hasOver = true;
                }
            } else {
                hs -= 1;
            }
            c++;
        }
        c = 0;
        float hw = 960 + (140 * hs + (hasOver ? 290 : 145)) * 0.5f;
        if(clicked == null) {
            for(MemberDisplay h : hand) {
                if(!h.isUsing) {
                    if(h.over) {
                        if(c > 0) cw += 145;
                        h.setPosition(hw - 145 - cw, 214);
                    } else h.setPosition(hw - 145 - cw, 107);
                    cw += 140;
                    c++;
                }
            }
        } else {
            for(MemberDisplay h : hand) {
                if(h != clicked && !h.isUsing) {
                    h.setPosition(hw - 145 - c * 140, 107);
                    c++;
                }
            }
        }
    }

    @Override
    protected void render(SpriteBatch sb) {
        if(WakTower.game.battle.isPlayerTurn()) {
            for(EnemyDisplay e : enemies) {
                e.render(sb);
            }
            player.render(sb);
        } else {
            player.render(sb);
            for(EnemyDisplay e : enemies) {
                e.render(sb);
            }
        }

        for(MemberDisplay m : members) {
            m.render(sb);
        }

        effectHandler.render(sb);

        turnEnd.render(sb);

        for(int i = 0; i < synergyDisplays.size(); i++) {
            SynergyDisplay s = synergyDisplays.get(i);
            if(s.enabled) {
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
