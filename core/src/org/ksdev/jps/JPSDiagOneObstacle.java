package org.ksdev.jps;

import com.fastcat.assemble.utils.Vector2i;

import java.util.*;

/**
 * @author Kevin
 */
public class JPSDiagOneObstacle extends JPS {
    public JPSDiagOneObstacle(Graph graph) { super(graph); }

    @Override
    protected Set<Vector2i> findNeighbors(Vector2i node, Map<Vector2i, Vector2i> parentMap) {
        Set<Vector2i> neighbors = new HashSet<>();

        Vector2i parent = parentMap.get(node);
        if (parent != null) {
            final int x = node.x;
            final int y = node.y;
            // get normalized direction of travel
            final int dx = (x - parent.x) / Math.max(Math.abs(x - parent.x), 1);
            final int dy = (y - parent.y) / Math.max(Math.abs(y - parent.y), 1);

            // search diagonally
            if (dx != 0 && dy != 0) {
                if (graph.isWalkable(x, y + dy))
                    neighbors.add(new Vector2i(x, y + dy));
                if (graph.isWalkable(x + dx, y))
                    neighbors.add(new Vector2i(x + dx, y));
                if ((graph.isWalkable(x, y + dy) || graph.isWalkable(x + dx, y)) && graph.isWalkable(x + dx, y + dy))
                    neighbors.add(new Vector2i(x + dx, y + dy));
                if ((!graph.isWalkable(x - dx, y) && graph.isWalkable(x, y + dy)) && graph.isWalkable(x - dx, y + dy))
                    neighbors.add(new Vector2i(x - dx, y + dy));
                if ((!graph.isWalkable(x, y - dy) && graph.isWalkable(x + dx, y)) && graph.isWalkable(x + dx, y - dy))
                    neighbors.add(new Vector2i(x + dx, y - dy));
            } else { // search horizontally/vertically
                if (dx == 0) {
                    if (graph.isWalkable(x, y + dy)) {
                        neighbors.add(new Vector2i(x, y + dy));
                        if (!graph.isWalkable(x + 1, y))
                            neighbors.add(new Vector2i(x + 1, y + dy));
                        if (!graph.isWalkable(x - 1, y))
                            neighbors.add(new Vector2i(x - 1, y + dy));
                    }
                } else {
                    if (graph.isWalkable(x + dx, y)) {
                        neighbors.add(new Vector2i(x + dx, y));
                        if (!graph.isWalkable(x, y + 1))
                            neighbors.add(new Vector2i(x + dx, y + 1));
                        if (!graph.isWalkable(x, y - 1))
                            neighbors.add(new Vector2i(x + dx, y - 1));
                    }
                }
            }
        } else {
            // no parent, return all neighbors
            neighbors.addAll(graph.getNeighborsOf(node, Graph.Diagonal.ONE_OBSTACLE));
        }

        return neighbors;
    }

    @Override
    protected Vector2i jump(Vector2i neighbor, Vector2i current, Set<Vector2i> goals) {
        if (neighbor == null || !graph.isWalkable(neighbor)) return null;
        if (goals.contains(neighbor)) return neighbor;

        int dx = neighbor.x - current.x;
        int dy = neighbor.y - current.y;

        // check for forced neighbors
        // check along diagonal
        if (dx != 0 && dy != 0) {
            if ((graph.isWalkable(neighbor.x - dx, neighbor.y + dy) && !graph.isWalkable(neighbor.x - dx, neighbor.y)) ||
                    (graph.isWalkable(neighbor.x + dx, neighbor.y - dy) && !graph.isWalkable(neighbor.x, neighbor.y - dy))) {
                return neighbor;
            }
            // when moving diagonally, must check for vertical/horizontal jump points
            if (jump(new Vector2i(neighbor.x + dx, neighbor.y), neighbor, goals) != null ||
                    jump(new Vector2i(neighbor.x, neighbor.y + dy), neighbor, goals) != null) {
                return neighbor;
            }
        } else { // check horizontally/vertically
            if (dx != 0) {
                if ((graph.isWalkable(neighbor.x + dx, neighbor.y + 1) && !graph.isWalkable(neighbor.x, neighbor.y + 1)) ||
                        (graph.isWalkable(neighbor.x + dx, neighbor.y - 1) && !graph.isWalkable(neighbor.x, neighbor.y - 1))) {
                    return neighbor;
                }
            } else {
                if ((graph.isWalkable(neighbor.x + 1, neighbor.y + dy) && !graph.isWalkable(neighbor.x + 1, neighbor.y)) ||
                        (graph.isWalkable(neighbor.x - 1, neighbor.y + dy) && !graph.isWalkable(neighbor.x - 1, neighbor.y))) {
                    return neighbor;
                }
            }
        }

        // moving diagonally, must make sure one of the vertical/horizontal
        // neighbors is open to allow the path
        if (graph.isWalkable(neighbor.x + dx, neighbor.y) || graph.isWalkable(neighbor.x, neighbor.y + dy)) {
            return jump(new Vector2i(neighbor.x + dx, neighbor.y + dy), neighbor, goals);
        } else {
            return null;
        }
    }
}
