package command.positions;

import command.Command;
import geometry.Shape;
import mvc.DrawingModel;

public class CmdBringToBack implements Command {

	private DrawingModel model;
	private Shape shape;
	private int index;
	
	
	public CmdBringToBack(DrawingModel model, Shape shape) {
		
		this.model = model;
		this.shape = shape;
		this.index=model.getShapes().indexOf(shape);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		model.remove(shape);
		model.getShapes().add(0,shape);	
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		model.remove(shape);
		model.getShapes().add(index,shape);
	}
	@Override
	public String toString() { 
		return "BringToBack:" + shape.toString();
	}
}
