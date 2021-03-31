package ie.gmit.sw.ai;

import java.util.LinkedList;

public class BFS {
	
	private GameModel model;
	
	public BFS(GameModel model) {
		this.model = model;
	}

	private static class CellBlock {
		int row,col;
		int dist;
		CellBlock prev;
		
		CellBlock(int row, int col, int dist, CellBlock prev) {
			this.row = row;
			this.col = col;
			this.dist = dist;
			this.prev = prev;
		}
		
		public String toString() {
			return "(" + row + "," + col + ")";
		}
	}	
	
	public int[] shortestPath(char[][] grid, int[] start, int[] end) {
		
		if (end[0] == 0 && end[1] == 0) return start;
		
		int sRow = start[0], sCol = start[1];
		int dRow = end[0], dCol = end[1];
					
		int m = grid.length;
		int n = grid[0].length;
		int searchArea = 20;
		CellBlock[][] cells = new CellBlock[m][n];
		for (int i = sCol - searchArea; i < sCol + searchArea; i++) {
			for (int j = sRow - searchArea; j < sRow + searchArea; j++) {
				if (grid[i][j] == ' ') {
					cells[i][j] = new CellBlock(i, j, Integer.MAX_VALUE, null);
				}
 			}
		}
			
		LinkedList<CellBlock> queue = new LinkedList<>();		
		
		CellBlock src = new CellBlock(sRow, sCol, 0, null);
		
		queue.add(src);
		CellBlock dest = null;
		CellBlock p;
		
		while((p = queue.poll()) != null) {
			// find destination
			if (p.row == dRow && p.col == dCol) {
				System.out.println("Found destination");
				dest = p;
				break;
			}
			
			visit(cells, queue, p.row-1, p.col, p); // move up
			
			visit(cells, queue, p.row+1, p.col, p); // move down
			
			visit(cells, queue, p.row, p.col-1, p); // move left
			
			visit(cells, queue, p.row, p.col+1, p); // move right
		}
		
		if (dest == null) {
			queue.clear();
			return start;
		} else {
			LinkedList<CellBlock> path = new LinkedList<>();
			p = dest;
			do {
				path.addFirst(p);
			} while((p = p.prev) != null);
			CellBlock lastBlock = path.pop();
			int[] firstStep = {lastBlock.row, lastBlock.col};
			
			path.clear();
			queue.clear();
			return firstStep;
		}
	}

	public void visit(CellBlock[][] cells, LinkedList<CellBlock> queue, int row, int col, CellBlock parent) {
		if (row > model.size() - 1 || col > model.size() - 1 || model.get(row, col) != ' ') {
			return;
		}
		
		int dist = parent.dist + 1;
		//CellBlock p = cells[row][col];
		CellBlock p = new CellBlock(row, col, cells[row][col].dist, null);
		if (dist < p.dist) {
			p.dist = dist;
			p.prev = parent;
			queue.add(p);
		}
	}
	
	public int[] getPlayerPos() { 
		char[][] m = model.getModel();
		for (int row = 0; row < m.length; row++){
			for (int col = 0; col < m[row].length - 1; col++){
				if (m[row][col] == '1') {
					int[] pos = {row, col};
					return pos;
				}
			}
		}
		int[] pos = {0, 0};
		return pos;
	}
}