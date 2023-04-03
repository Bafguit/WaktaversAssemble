package com.fastcat.assemble.abstrcts;

import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.screens.battle.CharacterButton;
import com.fastcat.assemble.screens.battle.EnemyButton;
import com.fastcat.assemble.screens.battle.TileSquare;

import static com.fastcat.assemble.MousseAdventure.battleScreen;

public abstract class AbstractEnemy extends AbstractEntity{

    private int act = 0;

    public AbstractEnemy(String id, int attack, int health, int def, int res) {
        super(id, attack, health, def, res, EntityRarity.NORMAL);
    }

    public abstract AbstractEffect playAction(EnemyButton button, float duration);

    public static CharacterButton getPlayerInRange(EnemyButton b, int r) {
        for(int i = 1; i <= r; i++) {
            for(int j = r - i; j >= 0; j--) {
                TileSquare t;
                int x = b.pos.x + i, y = b.pos.y + j;
                if (battleScreen.wSize > x && x >= 0 && battleScreen.hSize > y && y >= 0) {
                    t = battleScreen.tiles[x][y];
                    if (t.character != null && !t.character.character.invisible) return t.character;
                }

                x = b.pos.x + j;
                y = b.pos.y - i;
                if (battleScreen.wSize > x && x >= 0 && battleScreen.hSize > y && y >= 0) {
                    t = battleScreen.tiles[x][y];
                    if (t.character != null && !t.character.character.invisible) return t.character;
                }

                x = b.pos.x - i;
                y = b.pos.y - j;
                if (battleScreen.wSize > x && x >= 0 && battleScreen.hSize > y && y >= 0) {
                    t = battleScreen.tiles[x][y];
                    if (t.character != null && !t.character.character.invisible) return t.character;
                }

                x = b.pos.x - j;
                y = b.pos.y + i;
                if (battleScreen.wSize > x && x >= 0 && battleScreen.hSize > y && y >= 0) {
                    t = battleScreen.tiles[x][y];
                    if (t.character != null && !t.character.character.invisible) return t.character;
                }
            }
        }
        return null;
    }
}
