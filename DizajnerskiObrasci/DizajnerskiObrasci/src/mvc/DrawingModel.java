package mvc;

import java.util.ArrayList;
import java.util.List;
import geometry.Shape;


public class DrawingModel {
	
	private List<Shape> shapes = new ArrayList<Shape>();
	private List<Shape> selectedShapes = new ArrayList<Shape>();
	
	private int numOfSelected;
	private int numOfElements;

	public List<Shape> getShapes() {
		return shapes;
	}
	
	public void addSelected(Shape selected) {
		selectedShapes.add(selected);
		setNumOfSelected(selectedShapes.size());
	}

	public void removeSelected(Shape selected) {
		selectedShapes.remove(selected);
		setNumOfSelected(selectedShapes.size());
	}
	
	public List<Shape> getAllSelected() {
		return selectedShapes;
	}
	
	public Shape getSelected(int i) {
		return selectedShapes.get(i);
	}
	
	

	public List<Shape> getSelectedShapes() {
		return selectedShapes;
	}

	public void setSelectedShapes(List<Shape> selectedShapes) {
		this.selectedShapes = selectedShapes;
	}

	public void setShapes(List<Shape> shapes) {
		this.shapes = shapes;
	}

	public void add(Shape shape) {
		shapes.add(shape);
		setNumOfElements(shapes.size());
	}
	
	public void remove(Shape shape) {
		shapes.remove(shape);
		setNumOfElements(shapes.size());
	}
	
	public Shape get(int i) {
		return shapes.get(i);
	}
	
	public int getShapeIndex(Shape shape) {
		return shapes.indexOf(shape);
	}

	public void addOnIndex(int shapeIndex, Shape shape) {
		this.shapes.add(shapeIndex, shape);
		setNumOfElements(this.shapes.size());
	}
	
	public int getNumOfElements() {
		return numOfElements;
	}

	public void setNumOfElements(int numOfElements) {
		this.numOfElements = numOfElements;
	}
	
	
	private void setNumOfSelected(int numOfSelected) {
		this.numOfSelected = numOfSelected;
	}

	public int getNumOfSelected() {
		return numOfSelected;
	}

}
