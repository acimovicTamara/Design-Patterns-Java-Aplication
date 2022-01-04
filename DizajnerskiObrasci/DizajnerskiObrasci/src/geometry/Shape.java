package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shape implements Serializable, Moveable, Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean selected;
	private Color color;
	
	public Shape() {
		
	}
	
	public Shape(boolean selected) {
		this.selected = selected;
	}
	
	public abstract boolean contains(Point p);
	public abstract void draw(Graphics g);
	public abstract boolean contains(int x, int y);
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
}