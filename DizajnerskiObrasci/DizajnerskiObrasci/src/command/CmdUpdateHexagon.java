package command;


import adapter.HexagonAdapter;
import hexagon.Hexagon;

public class CmdUpdateHexagon implements Command{
	
	private HexagonAdapter newState;
	private HexagonAdapter oldState;
	private HexagonAdapter originalState = new HexagonAdapter(0, 0, 0);
	
	public CmdUpdateHexagon(HexagonAdapter oldState, HexagonAdapter newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		this.originalState = oldState.clone(this.originalState);
		this.oldState = newState.clone(this.oldState);
	}

	@Override
	public void unexecute() {
		
		this.oldState = originalState.clone(this.oldState);
	}

}