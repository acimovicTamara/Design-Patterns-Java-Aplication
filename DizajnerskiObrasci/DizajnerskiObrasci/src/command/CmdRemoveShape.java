package command;

import java.util.ArrayList;
import java.util.List;

import geometry.Shape;
import mvc.DrawingModel;

public class CmdRemoveShape implements Command {

	private DrawingModel model;
	private Shape shape;
	private List<Shape> shapes;

	public CmdRemoveShape(List<Shape> shapes, DrawingModel model) {
		this.shapes=new ArrayList<>(shapes);
		this.model=model;
	}
	
	public CmdRemoveShape(Shape shape, DrawingModel model)
	{
		this.shape = shape;
		this.model=model;
	}
	

	@Override
	public void execute() {
		
		for(Shape s : shapes)
		{
			model.remove(s);
		}
		
		
	}

	@Override
	public void unexecute() {
		for(Shape s : shapes)
		{
		model.add(s);
		}
	}

	@Override
	public String toString() {
		return "DeleteShape shapes=" + shapes.toString() + "]";
	}


}
