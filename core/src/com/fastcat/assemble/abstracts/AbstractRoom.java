package com.fastcat.assemble.abstracts;

import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.GroupHandler;
import com.fastcat.assemble.utils.RandomXC;

public class AbstractRoom {

    public final RoomData data;
    public final AbstractEnemy[] enemies;

    public AbstractRoom(RoomData data) {
        this.data = data;
        enemies = new AbstractEnemy[data.enemies.length];
        for(int i=0; i < enemies.length; i++) {
            enemies[i] = GroupHandler.getEnemy(data.enemies[i]).duplicate();
        }
    }

    public static class RoomData {
        public int stage;
        public String[] enemies;

        public RoomData(int stage, String[] enemies) {
            this.stage = stage;
            this.enemies = enemies;
        }
    }

    public static AbstractRoom getRoom(RandomXC random, int stage) {
        int i = random.random(0, 2);
        return new AbstractRoom(DataHandler.getInstance().roomData.get(stage + "_" + i));
    }
}
