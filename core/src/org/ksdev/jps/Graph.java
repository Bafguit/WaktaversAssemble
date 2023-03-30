package org.ksdev.jps;


import com.fastcat.assemble.utils.Vector2i;

import java.util.*;
import java.util.function.BiFunction;

/**
 * @author Kevin
 */
public class Graph {
    public enum Diagonal {
        ALWAYS,
        NO_OBSTACLES,
        ONE_OBSTACLE,
        NEVER
    }

    private BiFunction<Integer, Integer, Boolean> checkIfWalkable;

    private BiFunction<Vector2i, Vector2i, Double> distance = euclidean;
    private BiFunction<Vector2i, Vector2i, Double> heuristic = euclidean;

    public Graph(BiFunction<Integer, Integer, Boolean> checkIfWalkable) {
        this.checkIfWalkable = checkIfWalkable;
    }

    /**
     * Given two adjacent nodes, returns the distance between them.
     * @return The distance between the two nodes given.
     */
    public double getDistance(Vector2i a, Vector2i b) { return distance.apply(a, b); }

    /**
     * Given two nodes, returns the estimated distance between them. Optimizing this is the best way to improve
     * performance of your search time.
     * @return Estimated distance between the two given nodes.
     */
    public double getHeuristicDistance(Vector2i a, Vector2i b) { return heuristic.apply(a, b); }

    /**
     * By default, we return all reachable diagonal neighbors that have no obstacles blocking us.
     * i.e.
     * O O G
     * O C X
     * O O O
     *
     * In this above example, we could not go diagonally from our (C)urrent position to our (G)oal due to the obstacle (X).
     *
     * Please use {@link #getNeighborsOf(Vector2i, Diagonal)} method if you would like to specify different diagonal functionality.
     *
     * @return All reachable neighboring nodes of the given node.
     */
    public Collection<Vector2i> getNeighborsOf(Vector2i node) {
        return getNeighborsOf(node, Diagonal.NO_OBSTACLES);
    }

    /**
     * @return All reachable neighboring nodes of the given node.
     */
    public Set<Vector2i> getNeighborsOf(Vector2i node, Diagonal diagonal) {
        int x = (int) node.x;
        int y = (int) node.y;
        Set<Vector2i> neighbors = new HashSet<>();

        boolean n = false, s = false, e = false, w = false, ne = false, nw = false, se = false, sw = false;

        // ?
        if (isWalkable(x, y - 1)) {
            neighbors.add(new Vector2i(x, y - 1));
            n = true;
        }
        // ?
        if (isWalkable(x + 1, y)) {
            neighbors.add(new Vector2i(x + 1, y));
            e = true;
        }
        // ?
        if (isWalkable(x, y + 1)) {
            neighbors.add(new Vector2i(x, y+1));
            s = true;
        }
        // ?
        if (isWalkable(x - 1, y)) {
            neighbors.add(new Vector2i(x-1, y));
            w = true;
        }

        switch (diagonal) {
            case NEVER:
                return neighbors;
            case NO_OBSTACLES:
                ne = n && e;
                nw = n && w;
                se = s && e;
                sw = s && w;
                break;
            case ONE_OBSTACLE:
                ne = n || e;
                nw = n || w;
                se = s || e;
                sw = s || w;
                break;
            case ALWAYS:
                ne = nw = se = sw = true;
        }

        // ?
        if (nw && isWalkable(x - 1, y - 1)) {
            neighbors.add(new Vector2i(x - 1, y - 1));
        }
        // ?
        if (ne && isWalkable(x + 1, y - 1)) {
            neighbors.add(new Vector2i(x + 1, y - 1));
        }
        // ?
        if (se && isWalkable(x + 1, y + 1)) {
            neighbors.add(new Vector2i(x + 1, y + 1));
        }
        // ?
        if (sw && isWalkable(x - 1, y + 1)) {
            neighbors.add(new Vector2i(x - 1, y + 1));
        }

        return neighbors;
    }

    public boolean isWalkable(int x, int y) {
        return checkIfWalkable.apply(x, y);
    }

    public boolean isWalkable(Vector2i node) {
        return isWalkable(node.x, node.y);
    }

    /**
     * If you would like to define your own Distance Algorithm not included.
     */
    public void setDistanceAlgo(BiFunction<Vector2i, Vector2i, Double> distance) {
        this.distance = distance;
    }

    /**
     * If you would like to define your own Heuristic Algorithm not included.
     */
    public void setHeuristicAlgo(BiFunction<Vector2i, Vector2i, Double> heuristic) {
        this.heuristic = heuristic;
    }

    public enum DistanceAlgo {
        MANHATTAN(manhattan),
        EUCLIDEAN(euclidean),
        OCTILE(octile),
        CHEBYSHEV(chebyshev);

        BiFunction<Vector2i, Vector2i, Double> algo;

        DistanceAlgo(BiFunction<Vector2i, Vector2i, Double> algo) {
            this.algo = algo;
        }
    }
    private static BiFunction<Vector2i, Vector2i, Double> manhattan = (a, b) -> (double) Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    private static BiFunction<Vector2i, Vector2i, Double> euclidean = (a, b) -> Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    private static BiFunction<Vector2i, Vector2i, Double> octile = (a, b) -> {
        double F = Math.sqrt(2) - 1;
        double dx = Math.abs(a.x - b.x);
        double dy = Math.abs(a.y - b.y);
        return (dx < dy) ? F * dx + dy : F * dy + dx;
    };
    private static BiFunction<Vector2i, Vector2i, Double> chebyshev = (a, b) -> (double) Math.max(Math.abs(a.x - b.x), Math.abs(a.y - b.y));
}
