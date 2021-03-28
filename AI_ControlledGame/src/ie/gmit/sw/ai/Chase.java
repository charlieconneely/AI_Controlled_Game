package ie.gmit.sw.ai;

public class Chase implements Command {	
	
	private GameModel model;
	private DepthLimitedSearch dls;
	
	public Chase(GameModel model) {
		this.model = model;
		dls = new DepthLimitedSearch(model);
	}
	
	@Override
	public void execute(int row, int col) {		
		if (dls.search(row, col, 5)) System.out.println("near player");
	}
}
