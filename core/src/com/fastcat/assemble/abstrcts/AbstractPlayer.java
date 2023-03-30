package com.fastcat.assemble.abstrcts;

public class AbstractPlayer extends AbstractEntity{

    public AbstractPlayer(String id, int attack, int health, int def, int res) {
        super(id, attack, health, def, res, EntityRarity.OPERATOR);
    }
}
