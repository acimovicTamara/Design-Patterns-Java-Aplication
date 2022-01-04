package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends SurfaceShape implements Cloneable{

	private Point center;
	private int radius;
    private Color edgeColor;
	private Color fillColor;
	
	public Circle() {
		
	} 
	
	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public Circle(Point center, int radius, Color fillColor) {
		this.center = center;
		this.radius = radius;
		this.fillColor = fillColor;
	}
	
	public Circle(Point center, int radius, Color fillColor, Color edgeColor) {
		this.center = center;
		this.radius = radius;
		this.fillColor = fillColor;
		this.edgeColor = edgeColor;
	}
	
	public Circle(Point center, int radius, boolean selected) {
		this(center, radius);
		setSelected(selected);
	}

	@Override
	public void draw(Graphics g) {
		if(fillColor != null)
		{
			fill(g);
		}
		if(edgeColor != null)
		g.setColor(edgeColor);
		else 
		g.setColor(Color.BLACK);
		g.drawOval(this.getCenter().getX() - this.getRadius(), this.getCenter().getY() - this.getRadius(), this.getRadius()*2, this.getRadius()*2);
		g.setColor(Color.BLACK);
		
		if (isSelected()) {
			g.setColor(Color.blue);
			g.drawRect(this.getCenter().getX() + getRadius() - 3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX() - getRadius() - 3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() + getRadius() -3, 6, 6);
			g.drawRect(this.getCenter().getX()  - 3, this.getCenter().getY() - getRadius() -3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() - 3, 6, 6);
			g.setColor(Color.black);
		}
	}
	
	@Override
	public void fill(Graphics g) {
		g.setColor(fillColor);
		g.fillOval(this.getCenter().getX() - this.getRadius(), this.getCenter().getY() - this.getRadius(), this.getRadius()*2, this.getRadius()*2);
	}

	@Override
	public void moveBy(int byX, int byY) {
		center.moveBy(byX, byY);
		
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof Circle) {
			return (this.radius - ((Circle) o).radius);
		}
		return 0;
	}
	
	public boolean contains(int x, int y) {
		return this.getCenter().distance(x, y) <= radius;
	}
	
	public boolean contains(Point p) {
		if (p.distance(getCenter().getX(), getCenter().getY()) <= radius) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean equals (Object obj) {
		if (obj instanceof Circle) {
			Circle c = (Circle) obj;
			if (this.center.equals(c.getCenter()) &&
					this.radius == c.getRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public double area() {
		return radius * radius * Math.PI;
	}
	
	public Point getCenter() {
		return center;
	}
	
	public void setCenter(Point center) {
		this.center = center;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius){
			this.radius = radius;
	}
	
	public Color getEdgeColor() {
		return edgeColor;
	}

	public void setEdgeColor(Color edgeColor) {
		this.edgeColor = edgeColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	
	public String toString() { //ispis u logu
		String edgeColor = Integer.toString(getEdgeColor().getRGB());
		String fillColor = Integer.toString(getFillColor().getRGB());
		return "Circle: Center: [x=" + center.getX() + ",y=" + center.getY() +"], radius=" + radius + ", selected:" + this.isSelected() +", edgeColor:" + edgeColor + ", fillColor:" +fillColor; 
	}
	
	
	
	public Circle clone(Shape s) {
		Circle circleClone = new Circle(new Point(), 0);
		
		if (s instanceof Circle) {
			circleClone = (Circle) s;
		}
		circleClone.getCenter().setX(this.getCenter().getX());
		circleClone.getCenter().setY(this.getCenter().getY());
		circleClone.setRadius(this.getRadius());
		circleClone.setEdgeColor(this.getEdgeColor());
		circleClone.setFillColor(this.getFillColor());

		return circleClone;
	}
	 
}