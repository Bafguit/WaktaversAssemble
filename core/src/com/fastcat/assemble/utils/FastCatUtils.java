package com.fastcat.assemble.utils;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;

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

    public static float returnInterpolation(Interpolation ip, float start, float max, float a) {
        if(a < 0.5f) return ip.apply(start, max, a / 0.5f);
        else if(a > 0.5f) return ip.apply(max, start, (a - 0.5f) / 0.5f);
        else return max;
    }

    public static float mirrorInterpolation(Interpolation ip, Interpolation ip2, float start, float center, float end, float a) {
        if(a < 0.5f) return ip.apply(start, center, a / 0.5f);
        else if(a > 0.5f) return ip2.apply(center, end, (a - 0.5f) / 0.5f);
        else return center;
    }
}
