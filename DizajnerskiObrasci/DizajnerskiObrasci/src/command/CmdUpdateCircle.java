package command;

import geometry.Circle;
import geometry.Point;

public class CmdUpdateCircle implements Command {
	
	private Circle oldState;
	private Circle newState;
	private Circle originalState = new Circle(new Point(), 0);

	public CmdUpdateCircle(Circle oldState, Circle newState) {
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
