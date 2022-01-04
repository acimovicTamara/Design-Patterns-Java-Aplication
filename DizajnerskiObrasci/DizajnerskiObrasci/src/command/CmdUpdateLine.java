package command;

import geometry.Line;
import geometry.Point;

public class CmdUpdateLine implements Command{
	private Line oldState;
	private Line newState;
	private Line originalState = new Line(new Point(), new Point());
	
	public CmdUpdateLine(Line oldState, Line newState) {
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