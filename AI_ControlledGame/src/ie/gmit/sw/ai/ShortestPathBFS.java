package ie.gmit.sw.ai;

import java.util.LinkedList;

public class ShortestPathBFS {
	
	public static void main(String[] args) {
		int[][] matrix = {
				{1, 1, 1, 1, 1, 1},
				{1, 1, 1, 1, 0, 1},
				{1, 0, 0, 0, 1, 1},
				{1, 1, 1, 1, 1, 1},
				{0, 0, 0, 0, 0, 0}
		};
		int[] start= {2, 1};
		int[] end = {3, 3};
		shortestPath(matrix, start, end);
	}
	
	private static class Cell {
		int x,y;
		int dist;
		Cell prev;
		
		Cell(int x, int y, int dist, Cell prev) {
			this.x = x;
			this.y = y;
			this.dist = dist;
			this.prev = prev;
		}
		
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}
	
	public static void shortestPath(int[][] matrix, int[] start, int[] end) {
		int sx = start[0], sy = start[1];
		int dx = end[0], dy = end[1];
		
		if (matrix[sx][sy] == 0 || matrix[dx][dy] == 0) {
			return;
		}
		
		int m = matrix.length;
		int n = matrix[0].length;
		
		Cell[][] cells = new Cell[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] != 0) {
					cells[i][j] = new Cell(i, j, Integer.MAX_VALUE, null);
				}
 			}
		}
		
		LinkedList<Cell> queue = new LinkedList<>();
		Cell src = cells[sx][sy];
		src.dist = 0;
		queue.add(src);
		Cell dest = null;
		Cell p;
		while((p = queue.poll()) != null) {
			// find destination
			if (p.x == dx && p.y == dy) {
				dest = p;
				break;
			}
			// move up
			visit(cells, queue, p.x-1, p.y, p);
			// move down
			visit(cells, queue, p.x+1, p.y, p);
			// move left
			visit(cells, queue, p.x, p.y-1, p);
			// move right
			visit(cells, queue, p.x, p.y+1, p);
		}
		
		if (dest == null) {
			return;
		} else {
			LinkedList<Cell> path = new LinkedList<>();
			p = dest;
			do {
				path.addFirst(p);
			} while((p = p.prev) != null);
			System.out.println(path);
			System.out.println(path.pop());
		}
	}
	
	static void visit(Cell[][] cells, LinkedList<Cell> queue, int x, int y, Cell parent) {
		if (x < 0 || x >= cells.length || y < 0 || y >= cells[0].length || cells[x][y] == null) {
			return;
		}
		
		int dist = parent.dist + 1;
		Cell p = cells[x][y];
		if (dist < p.dist) {
			p.dist = dist;
			p.prev = parent;
			queue.add(p);
		}
	}

}
