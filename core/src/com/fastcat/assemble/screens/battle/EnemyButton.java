package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstrcts.AbstractEnemy;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.enemies.SpecOpsCaster;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.utils.Vector2i;

import java.util.Queue;

public class EnemyButton extends AbstractUI {

    private final Array<SubText> sub = new Array<>();
    private Queue<Vector2i> path;

    public final HealthBar hb;

    public BattleScreen screen;
    public AbstractEnemy entity;
    public TileSquare tile;

    public CharacterButton target;

    public EnemyButton(BattleScreen screen) {
        this(screen, new SpecOpsCaster());
    }

    public EnemyButton(BattleScreen screen, AbstractEnemy dice) {
        super(FileHandler.character.get("Test"));
        pix();
        this.entity = dice;
        this.screen = screen;
        clickable = false;
        hb = new HealthBar(dice, this);
        sub.add(new SubText(dice.name, dice.desc));
    }

    @Override
    protected void updateButton() {
        if(tile != null) {
            setPosition(entity.pos.x, entity.pos.y);
        }
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled && entity != null) {
            if (showImg) entity.render(sb);
        }
    }

    public void setPath(Queue<Vector2i> v) {
        if(v.size() > 1) v.remove();
        //path.clear();
        path = v;
    }

    public Vector2i getNextPath() {
        return path.element();
    }

    public Vector2i nextPath() {
        return path.poll();
    }
}
