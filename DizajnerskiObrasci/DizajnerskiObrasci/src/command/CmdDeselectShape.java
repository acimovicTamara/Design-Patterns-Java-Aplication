package command;

import geometry.Shape;
import mvc.DrawingModel;

public class CmdDeselectShape implements Command{
	private Shape shape;
	private DrawingModel model;
	
	public CmdDeselectShape(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		this.shape.setSelected(false);
		model.removeSelected(this.shape);
	}

	@Override
	public void unexecute() {
		this.shape.setSelected(true);
		model.addSelected(this.shape);
	}

}
