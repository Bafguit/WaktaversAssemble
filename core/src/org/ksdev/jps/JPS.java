package org.ksdev.jps;

import com.fastcat.assemble.utils.Vector2i;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author Kevin
 */
public abstract class JPS {
    protected final Graph graph;

    public JPS(Graph graph) {
        this.graph = graph;
    }

    public CompletableFuture<Queue<Vector2i>> findPath(Vector2i start, Vector2i goal) {
        return findPath(start, goal, false, false);
    }

    public CompletableFuture<Queue<Vector2i>> findPath(Vector2i start, Vector2i goal, boolean adjacentStop) {
        return findPath(start, goal, adjacentStop, true);
    }

    public CompletableFuture<Queue<Vector2i>> findPath(Vector2i start, Vector2i goal, boolean adjacentStop, boolean diagonalStop) {
        return CompletableFuture.supplyAsync(() -> findPathSync(start, goal, adjacentStop, diagonalStop));
    }

    public Queue<Vector2i> findPathSync(Vector2i start, Vector2i goal) {
        return findPathSync(start, goal, false, false);
    }

    public Queue<Vector2i> findPathSync(Vector2i start, Vector2i goal, boolean adjacentStop) {
        return findPathSync(start, goal, adjacentStop, true);
    }

    public Queue<Vector2i> findPathSync(Vector2i start, Vector2i goal, boolean adjacentStop, boolean diagonalStop) {
        Map<Vector2i, Double> fMap = new HashMap<>(); // distance to start + estimate to end
        Map<Vector2i, Double> gMap = new HashMap<>(); // distance to start (parent's g + distance from parent)
        Map<Vector2i, Double> hMap = new HashMap<>(); // estimate to end

        Queue<Vector2i> open = new PriorityQueue<>((a, b) -> {
            // we want the nodes with the lowest projected F value to be checked first
            return Double.compare(fMap.getOrDefault(a, 0d), fMap.getOrDefault(b, 0d));
        });
        Set<Vector2i> closed = new HashSet<>();
        Map<Vector2i, Vector2i> parentMap = new HashMap<>();
        Set<Vector2i> goals = new HashSet<>();

        if (adjacentStop) {
            if (!diagonalStop)
                goals = graph.getNeighborsOf(goal, Graph.Diagonal.NEVER);
            else
                goals = findNeighbors(goal, parentMap);
        }
        if (graph.isWalkable(goal)) {
            goals.add(goal);
        }
        if (goals.isEmpty()) {
            return null;
        }

        System.out.println("Start: " + start.x + "," + start.y);
        // push the start node into the open list
        open.add(start);

        // while the open list is not empty
        while (!open.isEmpty()) {
            //System.out.println(open.size());
            // pop the position of node which has the minimum `f` value.
            Vector2i node = open.poll();
            // mark the current node as checked
            closed.add(node);

            if (goals.contains(node)) {
                return backtrace(node, parentMap);
            }
            // add all possible next steps from the current node
            identifySuccessors(node, goal, goals, open, closed, parentMap, fMap, gMap, hMap);
        }

        // failed to find a path
        return null;
    }

    /**
     * Identify successors for the given node. Runs a JPS in direction of each available neighbor, adding any open
     * nodes found to the open list.
     * @return All the nodes we have found jumpable from the current node
     */
    private void identifySuccessors(Vector2i node, Vector2i goal, Set<Vector2i> goals, Queue<Vector2i> open, Set<Vector2i> closed, Map<Vector2i, Vector2i> parentMap,
                                    Map<Vector2i, Double> fMap, Map<Vector2i, Double> gMap, Map<Vector2i, Double> hMap) {
        // get all neighbors to the current node
        Collection<Vector2i> neighbors = findNeighbors(node, parentMap);

        double d;
        double ng;
        for (Vector2i neighbor : neighbors) {
            // jump in the direction of our neighbor
            Vector2i jumpNode = jump(neighbor, node, goals);

            // don't add a node we have already gotten to quicker
            if (jumpNode == null || closed.contains(jumpNode)) continue;

            // determine the jumpNode's distance from the start along the current path
            d = graph.getDistance(jumpNode, node);
            ng = gMap.getOrDefault(node, 0d) + d;

            // if the node has already been opened and this is a shorter path, update it
            // if it hasn't been opened, mark as open and update it
            if (!open.contains(jumpNode) || ng < gMap.getOrDefault(jumpNode, 0d)) {
                gMap.put(jumpNode, ng);
                hMap.put(jumpNode, graph.getHeuristicDistance(jumpNode, goal));
                fMap.put(jumpNode, gMap.getOrDefault(jumpNode, 0d) + hMap.getOrDefault(jumpNode, 0d));
                //System.out.println("jumpNode: " + jumpNode.x + "," + jumpNode.y + " f: " + fMap.get(jumpNode));
                parentMap.put(jumpNode, node);

                if (!open.contains(jumpNode)) {
                    open.offer(jumpNode);
                }
            }
        }
    }

    /**
     * Find all neighbors for a given node. If node has a parent then prune neighbors based on JPS algorithm,
     * otherwise return all neighbors.
     */
    protected abstract Set<Vector2i> findNeighbors(Vector2i node, Map<Vector2i, Vector2i> parentMap);

    /**
     * Search towards the child from the parent, returning when a jump point is found.
     */
    protected abstract Vector2i jump(Vector2i neighbor, Vector2i current, Set<Vector2i> goals);

    /**
     * Returns a path of the parent nodes from a given node.
     */
    private Queue<Vector2i> backtrace(Vector2i node, Map<Vector2i, Vector2i> parentMap) {
        LinkedList<Vector2i> path = new LinkedList<>();
        path.add(node);

        int previousX, previousY, currentX, currentY;
        int dx, dy;
        int steps;
        Vector2i temp;
        while (parentMap.containsKey(node)) {
            previousX = parentMap.get(node).x;
            previousY = parentMap.get(node).y;
            currentX = node.x;
            currentY = node.y;
            steps = Integer.max(Math.abs(previousX - currentX), Math.abs(previousY - currentY));
            dx = Integer.compare(previousX, currentX);
            dy = Integer.compare(previousY, currentY);

            temp = node;
            for (int i = 0; i < steps; i++) {
                temp = new Vector2i(temp.x + dx, temp.y + dy);
                path.addFirst(temp);
            }

            node = parentMap.get(node);
        }
        return path;
    }
}
