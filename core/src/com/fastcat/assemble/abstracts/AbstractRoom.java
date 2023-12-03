package com.fastcat.assemble.abstracts;

import com.fastcat.assemble.handlers.GroupHandler.EnemyGroup;

public class AbstractRoom {

    public RoomType type;

    public AbstractRoom(RoomType type) {
        this.type = type;
    }

    public AbstractRoom(EnemyGroup group) {
        type = group.type;
    }
    
    public enum RoomType {
        weakBattle, normalBattle, bossBattle, mystery, rest, shop, ending, reward
    }
}
