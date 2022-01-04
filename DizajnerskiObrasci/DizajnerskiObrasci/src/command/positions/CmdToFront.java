package command.positions;

import java.util.Collections;

import command.Command;
import geometry.Shape;
import mvc.DrawingModel;

public class CmdToFront implements Command{
	
	private DrawingModel model;
	private Shape shape;
	private int index;
	
	public CmdToFront(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		this.index = model.getShapes().indexOf(shape);
		Collections.swap(model.getShapes(), index, index+1);
	}


	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		this.index = model.getShapes().indexOf(shape);
		Collections.swap(model.getShapes(), index, index-1);
	}
	@Override
	public String toString() {
		return "ToFront:" + shape.toString();
	}

}
