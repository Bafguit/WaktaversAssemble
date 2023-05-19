package com.fastcat.assemble.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractEntity;

import static com.fastcat.assemble.MousseAdventure.cam;

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

    public static ProjectionData calcProjection(Vector3 drawTargetPos) {

        Vector3 normalizedCamLookVector = cam.direction.cpy().nor();
        Vector3 camTargetVector = drawTargetPos.cpy().sub(cam.position);

        // Find the projection vector v3 of camTargetVector onto the plane K, which is perpendicular to normalizedCamLookVector
        //Vector3 v3 = camTargetVector.cpy().sub(normalizedCamLookVector.cpy().scl(camTargetVector.dot(normalizedCamLookVector)));

        // Find the x and y coordinates of P5 with respect to P4 in plane K
        Vector3 u2 = normalizedCamLookVector.cpy().crs(new Vector3(0, 0, 1)).nor(); // Unit vector in the x direction of plane K
        //Vector3 u3 = normalizedCamLookVector.cpy().crs(u2).nor(); // Unit vector in the y direction of plane K
        //float x = v3.dot(u2);
        //float y = v3.dot(u3);

        Vector3 tmp = cam.project(drawTargetPos.cpy());
        //Vector3 tmp2 = MousseAdventure.camera.unproject(tmp);

        float dist = camTargetVector.cpy().sub(u2.cpy().scl((camTargetVector.dot(u2) / (u2.len() * u2.len())))).len();

        return new ProjectionData(tmp.x, tmp.y, 1 / dist);
    }

    public static Vector2 getProjectedPos(float x, float y) {
        Ray ray = cam.getPickRay(x, Gdx.graphics.getHeight() - y);

        float t = -ray.origin.z/ray.direction.z;
        float convertedX = ray.origin.x+ray.direction.x*t;
        float convertedY = ray.origin.y+ray.direction.y*t;

        return new Vector2(convertedX, convertedY);
    }

    public static ProjectionData getProjectionData(Vector3 unprojected) {
        // 카메라의 투영 행렬을 직접 계산합니다.
        Matrix4 projection = cam.combined.cpy();
        projection.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

// 2D 좌표를 3D 좌표로 변환합니다.
        unprojected.prj(projection.inv()); // 투영 행렬의 역행렬을 곱합니다.
        unprojected.z = 0;

        return new ProjectionData(unprojected.x / 1000, unprojected.y / 1000, 0.3f);
    }

    public static class ProjectionData {
        public final float drawX;
        public final float drawY;
        public final float scale;

        public ProjectionData(float drawX, float drawY, float scale) {
            this.drawX = drawX;
            this.drawY = drawY;
            this.scale = scale;
        }

        public String toString() {
            return "x: " + drawX + ", y: " + drawY + ", scale: " + scale;
        }
    }
}
