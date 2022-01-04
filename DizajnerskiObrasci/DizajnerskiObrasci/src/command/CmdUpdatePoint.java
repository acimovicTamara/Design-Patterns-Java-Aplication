package command;

import geometry.Point;

public class CmdUpdatePoint implements Command {
	private Point oldState; 
	private Point newState;
	private Point originalState = new Point();

	public CmdUpdatePoint(Point oldState, Point newState) {
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