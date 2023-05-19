package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.enemies.SpecOpsCaster;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.utils.Vector2i;

import java.util.Queue;

import static com.fastcat.assemble.handlers.InputHandler.scaleX;
import static com.fastcat.assemble.handlers.InputHandler.scaleY;

public class EnemyButton extends AbstractUI {

    private final Array<SubText> sub = new Array<>();
    private Queue<Vector2i> path;

    public final HealthBar hb;

    public AbstractEnemy entity;
    public TileSquare tile;

    public CharacterButton target;

    public EnemyButton(AbstractEnemy dice) {
        super(FileHandler.character.get("Test"));
        pix();
        this.entity = dice;
        clickable = false;
        hb = new HealthBar(dice, this);
        sub.add(new SubText(dice.name, dice.desc));
    }

    @Override
    protected void updateButton() {
        if(tile != null) {
            setPosition(entity.pos.x, entity.pos.y);
            entity.animPos.set(x, y);
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
