package ie.gmit.sw.ai.pathfinding;

import java.util.Deque;
import java.util.LinkedList;

public class Path {
	private Deque<Step> path = new LinkedList<Step>();
	
	public Deque<Step> getPath() {
		return path;
	}
			
	public void prependStep(int r, int c) {
		path.addFirst(new Step(r, c));
	}
	
}
