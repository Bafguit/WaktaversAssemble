package com.fastcat.assemble.utils;

import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.screens.battle.TileSquare;
import com.fastcat.assemble.screens.battle.TileSquare.TileStatus;
import org.ksdev.jps.Graph;
import org.ksdev.jps.JPSDiagOneObstacle;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BulkPathFinder {

    private static int counter = 0;
    private static Supplier<Void> callback;

    public static void performPathfinding(Vector2i start, Vector2i end, Consumer<Queue<Vector2i>> callback) {
        counter++;
        findPath(start, end).thenAccept((result) -> {
           synchronized (BulkPathFinder.class) {
               counter--;
               handleCallback();
           }
           callback.accept(result);
        });
    }

    public static void setFinishCallback(Supplier<Void> callback) {
        BulkPathFinder.callback = callback;
        handleCallback();
    }

    private static void handleCallback() {
        if(counter == 0 && callback != null) {
            callback.get();
            callback = null;
        }
    }

    public static void clear() {
        counter = 0;
        BulkPathFinder.callback = null;
    }

    private static CompletableFuture<Queue<Vector2i>> findPath(Vector2i start, Vector2i end) {
        return new JPSDiagOneObstacle(new Graph(BulkPathFinder::isWalkable)).findPath(start, end);
    }

    private static boolean isWalkable(int x, int y) {
        if(x < 0 || x >= MousseAdventure.battleScreen.wSize)
            return false;
        if(y < 0 || y >= MousseAdventure.battleScreen.hSize)
            return false;

        TileSquare s = MousseAdventure.battleScreen.tiles[x][y];
        return s.status == TileStatus.NORMAL || s.status == TileStatus.INVALID ||
                (s.character != null || (s.enemy != null && s.enemy.target == null));
    }
}
