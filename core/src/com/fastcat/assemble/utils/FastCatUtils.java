package com.fastcat.assemble.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstracts.AbstractEntity;

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

    public static ProjectionData calcProjection(Vector3 camPos, Vector3 camLookVector, Vector3 drawTargetPos, double near) {
        Vector3 normalizedCamLookVector = camLookVector.cpy().nor();
        Vector3 camTargetVector = drawTargetPos.cpy().sub(camPos);

        // Find the projection vector v3 of camTargetVector onto the plane K, which is perpendicular to normalizedCamLookVector
        Vector3 v3 = camTargetVector.cpy().sub(normalizedCamLookVector.cpy().scl(camTargetVector.dot(normalizedCamLookVector)));

        // Find the x and y coordinates of P5 with respect to P4 in plane K
        Vector3 u2 = normalizedCamLookVector.cpy().crs(new Vector3(0, 0, 1)).nor(); // Unit vector in the x direction of plane K
        Vector3 u3 = normalizedCamLookVector.cpy().crs(u2).nor(); // Unit vector in the y direction of plane K
        float x = v3.dot(u2);
        float y = v3.dot(u3);

        float dist = camTargetVector.cpy().sub(u2.cpy().scl((float) (camTargetVector.dot(u2) / (u2.len() * u2.len())))).len();

        return new ProjectionData(MathUtils.round(x), -MathUtils.round(y), 1 / dist);
    }

    public static class ProjectionData {
        public final int drawX;
        public final int drawY;
        public final float scale;

        public ProjectionData(int drawX, int drawY, float scale) {
            this.drawX = drawX;
            this.drawY = drawY;
            this.scale = scale;
        }
    }
}
