package command;

import geometry.Donut;
import geometry.Point;

public class CmdUpdateDonut implements Command {
	
	private Donut oldState;
	private Donut newState;
	private Donut originalState = new Donut(new Point(), 0, 0);

	public CmdUpdateDonut(Donut oldState, Donut newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {

		this.originalState = oldState.clone(this.originalState);
		this.oldState = newState.clone(this.oldState);
	}

	@Override
	public void unexecute() {
		
		this.oldState = originalState.clone(this.oldState);
	}
}
