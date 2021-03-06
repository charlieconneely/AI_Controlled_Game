package ie.gmit.sw.ai.pathfinding;

import java.util.ArrayList;

import ie.gmit.sw.ai.GameModel;

import  java.lang.Math;

public class PathFinder {
	
	private ArrayList<Node> closed = new ArrayList<Node>();
	private SortedList open = new SortedList();
	private GameModel model;
	private NodeMap nodeMap;
	private int maxSearchDistance = 500;
	
	public PathFinder(GameModel model) {
		this.model = model;
		nodeMap = new NodeMap(model.getModel());
	}
	
	public Path findPath(int sx, int sy, int tx, int ty) {
		Node startNode = nodeMap.getNode(sx,sy);
		Node endNode = nodeMap.getNode(tx,ty);
		
		// initialization
		startNode.setCost(0);
		startNode.setDepth(0);

		closed.clear();
		open.clear();
		open.add(startNode);
		
		startNode.setParentNull();
		
		int maxDepth = 0;
		
		while((open.size() != 0) && (maxDepth < maxSearchDistance)) {
			Node current = (Node) open.first();
	
			if (current == endNode) {
				// found destination node
				break;
			}
			open.remove(current);
			closed.add(current);

			for (int x = -1; x < 2; x++) {
				for (int y=-1;y<2;y++) {
					if ((x == 0) && (y == 0)) continue;
					if ((x != 0) && (y != 0)) continue;
					
					int xp = x + current.getX();
					int yp = y + current.getY();

					if (isValidLocation(xp, yp)) {
						int nextStepCost = current.getCost() + 1;
						Node neighbour = nodeMap.getNode(xp, yp);
						nodeMap.pathFinderVisited(xp, yp);
						
						if (nextStepCost < neighbour.getCost()) {
							if (open.contains(neighbour)) {
								open.remove(neighbour);
							}
							if (closed.contains(neighbour)) {
								closed.remove(neighbour);
							}
						}
		
						if (!open.contains(neighbour) && !closed.contains(neighbour)) {
							neighbour.setCost(nextStepCost);
							neighbour.setHeuristic(getHeuristicCost(xp, yp, tx, ty));
							maxDepth = Math.max(maxDepth, neighbour.setParent(current));
							open.add(neighbour);
						}
					}
				}
			}
		}
		
		if (nodeMap.getNode(tx, ty).getParent() == null) {
			nodeMap.resetVisited();
			return null;
		}	
				
		Path path = new Path();
		Node target = endNode;
		while (target != startNode) {
			path.prependStep(target.getX(), target.getY());
			target = target.getParent();
		}

		path.prependStep(sx, sy);
		nodeMap.resetVisited();
		return path;
	}
	
	private float getHeuristicCost(int xp, int yp, int tx, int ty) {
		return (Math.abs(xp - tx) + Math.abs(yp - ty));
	}
	
	private boolean isValidLocation(int x, int y) {
		if (model.isInBounds(x, y)) {
			if (model.get(x, y) == ' ') {
				return true;
			}
		}
		return false;
	}
	
}
