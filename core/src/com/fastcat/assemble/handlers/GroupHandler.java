package com.fastcat.assemble.handlers;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractRoom.RoomType;

import java.util.HashMap;

public class GroupHandler {

    public static HashMap<String, EnemyGroup> monsterGroup;

    public static void initialize() {
        monsterGroup = new HashMap<>();
    }

    public static EnemyGroup getBoss(int number) {
        return monsterGroup.get("boss_" + number + "_" + WakTower.game.mapRandom.random(0, 2));
    }

    public static class EnemyGroup {
        public RoomType type;

        public EnemyGroup(RoomType type) {
            this.type = type;
        }
    }
}
