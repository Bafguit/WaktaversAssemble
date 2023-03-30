package com.fastcat.assemble.abstrcts;

public class AbstractEnemy extends AbstractEntity{

    public AbstractEnemy(String id, int attack, int health, int def, int res) {
        super(id, attack, health, def, res, EntityRarity.NORMAL);
    }
}
