package com.fastcat.assemble.utils;

import com.badlogic.gdx.math.Vector2;

public class Vector2i {
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

    public static Vector2i getVector2i(Vector2 v2) {
        return new Vector2i((int) v2.x, (int) v2.y);
    }
}
