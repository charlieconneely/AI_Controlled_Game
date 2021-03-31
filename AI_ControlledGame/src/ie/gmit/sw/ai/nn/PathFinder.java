package ie.gmit.sw.ai.nn;

public interface PathFinder {
	public Path findPath(char enemyID, int srow, int scol, int drow, int dcol);
}
