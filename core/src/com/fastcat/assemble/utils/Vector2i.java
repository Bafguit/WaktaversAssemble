package com.fastcat.assemble.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.Objects;

public class Vector2i implements Cloneable {
    public int x;
    public int y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i(Vector2i v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void add(Vector2i v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2i v) {
        this.x = v.x;
        this.y = v.y;
    }

    @Override
    public final Vector2i clone() {
        return new Vector2i(this.x, this.y);
    }

    public static Vector2i getVector2i(Vector2 v2) {
        return new Vector2i((int) v2.x, (int) v2.y);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vector2i) {
            return ((Vector2i) obj).x == x && ((Vector2i) obj).y == y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "{x: " + x + ", y: " + y + "}";
    }
}
