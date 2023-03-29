package com.fastcat.assemble.abstrcts;

public class AbstractEnemy extends AbstractEntity{

    public AbstractDice dice;

    public AbstractEnemy(String id, int attack, int health, int def, int res) {
        super(id, attack, health, def, res, EntityRarity.NORMAL);
    }

    @Override
    public String getDesc(int num) {
        return switch (num) {
            case 1 -> "피해 3";
            case 2 -> "피해 2";
            case 3 -> "쉴드 3";
            case 4 -> "쉴드 2";
            case 5 -> "쇠약 1";
            case 6 -> "취약 1";
            default -> "";
        };
    }
}
