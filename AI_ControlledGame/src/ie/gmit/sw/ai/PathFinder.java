package ie.gmit.sw.ai;

import java.util.LinkedList;
import  java.lang.Math;

public class PathFinder {
	
	private LinkedList<Node> closed = new LinkedList<Node>();
	private LinkedList<Node> open = new LinkedList<Node>();
	private GameModel model;
	private NodeMap nodeMap;
	private int maxSearchDistance = 50;
	
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
		
		startNode.setParent(null);
		
		int maxDepth = 0;
		while((open.size() != 0) && maxDepth < maxSearchDistance) {
			Node current = open.getFirst();
	
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
						int nextStepCost = current.getCost() + 
                                getMovementCost(current.getX(), current.getY(), xp, yp);
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
							neighbour.setParent(current);
							maxDepth++;
							neighbour.setHeuristic(getMovementCost(xp, yp, tx, ty));
							open.add(neighbour);
						}
					}
				}
			}
		}

		if (nodeMap.getNode(tx, ty).getParent() == null) {
			return null;
		}
		
		Path path = new Path();
		Node target = endNode;
		while (target != startNode) {
			path.prependStep(target.getX(), target.getY());
			target = target.getParent();
		}

		path.prependStep(sx, sy);
		return path;
	}
	
	private int getMovementCost(int sx, int sy, int tx, int ty) {
		// Manhattan distance
	    int dx = Math.abs(tx - sx);
	    int dy = Math.abs(ty - sy);
	    int heuristic = dx+dy;
		return heuristic;	
	}
	
	private boolean isValidLocation(int x, int y) {
		if (x >= 0 && y >= 0 && x < model.size() + 1 && y < model.size() + 1) {
			if (model.get(x, y) == ' ') {
				return true;
			}
		}
		return false;
	}
	
}
