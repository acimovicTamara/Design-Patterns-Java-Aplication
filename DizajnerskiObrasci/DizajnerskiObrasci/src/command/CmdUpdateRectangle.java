package command;

import geometry.Point;
import geometry.Rectangle;

public class CmdUpdateRectangle implements Command{
	
	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle originalState = new Rectangle(new Point(), 0, 0);

	public CmdUpdateRectangle(Rectangle oldState, Rectangle newState) {
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
