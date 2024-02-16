package com.fastcat.assemble.utils;

import java.util.LinkedList;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;

public class FastCatUtils {

    public static <T> Array<T> arrayShuffle(Array<T> array, RandomXC r, Class<T> cls) {
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

    public static <T> Queue<T> queueShuffle(Queue<T> queue, RandomXC r, Class<T> cls) {
        Array<T> array = new Array<>();
        for(T t : queue) {
            array.add(t);
        }
        T[] items = array.toArray(cls);
        for (int i = array.size - 1; i >= 0; --i) {
            int ii = r.random(i);
            T temp = items[i];
            items[i] = items[ii];
            items[ii] = temp;
        }
        queue.clear();
        for(T m : array) {
            queue.addLast(m);
        }
        return queue;
    }
    
    public static <T> LinkedList<T> linkedlistShuffle(LinkedList<T> linked, RandomXC r, Class<T> cls) {
        Array<T> array = new Array<>();
        for(T t : linked) {
            array.add(t);
        }
        T[] items = array.toArray(cls);
        for (int i = array.size - 1; i >= 0; --i) {
            int ii = r.random(i);
            T temp = items[i];
            items[i] = items[ii];
            items[ii] = temp;
        }
        linked.clear();
        for(T m : array) {
            linked.addLast(m);
        }
        return linked;
    }

    public static float getAngle(float fromX, float fromY, float toX, float toY) {
        return (float) Math.toDegrees(Math.atan2(toY - fromY, toX - fromX)) - 90;
    }

    public static float distance(float fromX, float fromY, float toX, float toY) {
        return (float) Math.sqrt(Math.abs(fromX - toX) + Math.abs(fromY - toY));
    }

    public static float returnInterpolation(Interpolation ip, Interpolation ip2, float start, float max, float a) {
        if(a <= 0.5f) return ip.apply(start, max, a / 0.5f);
        else return ip2.apply(max, start, (a - 0.5f) / 0.5f);
    }

    public static float mirrorInterpolation(Interpolation ip, Interpolation ip2, float start, float center, float end, float a) {
        if(a < 0.5f) return ip.apply(start, center, a / 0.5f);
        else if(a > 0.5f) return ip2.apply(center, end, (a - 0.5f) / 0.5f);
        else return center;
    }
}
