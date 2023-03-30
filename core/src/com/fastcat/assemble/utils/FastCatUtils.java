package com.fastcat.assemble.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.screens.battle.TileSquare;
import org.ksdev.jps.Graph;
import org.ksdev.jps.JPS;
import org.ksdev.jps.JPSDiagOneObstacle;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class FastCatUtils {

    public static <T> Array<T> staticShuffle(Array<T> array, RandomXC r, Class<T> cls) {
        T[] items = array.toArray(cls);
        for (int i = array.size - 1; i >= 0; --i) {
            int ii = r.random(i);
            T temp = items[i];
            items[i] = items[ii];
            items[ii] = temp;
        }
        array.clear();
        array.addAll(items);
        return array;
    }

    public static float getAngle(float fromX, float fromY, float toX, float toY) {
        return (float) Math.toDegrees(Math.atan2(toY - fromY, toX - fromX)) - 90;
    }

    public static float distance(float fromX, float fromY, float toX, float toY) {
        return (float) Math.sqrt(Math.abs(fromX - toX) + Math.abs(fromY - toY));
    }

}
