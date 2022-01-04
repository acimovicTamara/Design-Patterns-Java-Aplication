package command;

import geometry.Shape;
import mvc.DrawingModel;

public class CmdSelectShape implements Command{
	private Shape shape;
	private DrawingModel model;
	
	public CmdSelectShape(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		shape.setSelected(true);
		model.addSelected(shape);
		
		System.out.println("Successfully selected shape" + shape.toString());
	}

	@Override
	public void unexecute() {
		shape.setSelected(false);
		model.removeSelected(this.shape);		
	}

}