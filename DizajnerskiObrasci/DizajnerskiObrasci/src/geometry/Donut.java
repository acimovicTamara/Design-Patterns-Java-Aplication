package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle {

	private int innerRadius;
	private Color sEdgeColor;

	public Donut() {
		
	}
	
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		this.innerRadius = innerRadius;
	}
	
	public Donut(Point center, int radius, int innerRadius, Color sEdgeColor, Color fillColor) {
		super(center, radius, fillColor);
		this.innerRadius = innerRadius;
		this.sEdgeColor = sEdgeColor;
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);
	}
	
	public void draw(Graphics gr) {
		Ellipse2D outerCircle = new Ellipse2D.Double(this.getCenter().getX() - this.getRadius(), this.getCenter().getY() - this.getRadius(), this.getRadius()*2, this.getRadius()*2);
		Ellipse2D innerCircle = new Ellipse2D.Double(this.getCenter().getX() - this.getInnerRadius(), this.getCenter().getY() - this.getInnerRadius(), this.getInnerRadius()*2, this.getInnerRadius()*2);
		Area outerArea = new Area(outerCircle);
		outerArea.subtract(new Area(innerCircle));
		
		Graphics2D g = (Graphics2D) gr;
		if(super.getFillColor() != null)
		{
			g.setColor(super.getFillColor());
			g.fill(outerArea);
		}
		g.setColor(Color.black);
		if(sEdgeColor != null)
		{
			g.setColor(sEdgeColor);
		}
		g.draw(outerArea);		
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			//g.drawRect(getCenter().getX() - 3, getCenter().getY() - 3, 6, 6);
			g.drawRect(this.getCenter().getX() + getInnerRadius() - 3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX() - getInnerRadius() - 3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() + getInnerRadius() -3, 6, 6);
			g.drawRect(this.getCenter().getX()  - 3, this.getCenter().getY() - getInnerRadius() -3, 6, 6);
			
			g.drawRect(this.getCenter().getX() + getRadius() - 3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX() - getRadius() - 3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() + getRadius() -3, 6, 6);
			g.drawRect(this.getCenter().getX()  - 3, this.getCenter().getY() - getRadius() -3, 6, 6);
			g.setColor(Color.BLACK);
		}
	}
	
	public int compareTo(Object o) {
		if (o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}
	
	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) &&
				dFromCenter > innerRadius;
	}
	
	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) &&
				dFromCenter > innerRadius;
	}
	
	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut d = (Donut) obj;
			if (this.getCenter().equals(d.getCenter()) &&
					this.getRadius() == d.getRadius() &&
					innerRadius == d.getInnerRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public int getInnerRadius() {
		return innerRadius;
	}
	
	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}
	
	public Color getsEdgeColor() {
		return sEdgeColor;
	}

	public void setsEdgeColor(Color sEdgeColor) {
		this.sEdgeColor = sEdgeColor;
	}
	
	public String toString() {
		String sEdgeColor = Integer.toString(getsEdgeColor().getRGB());
		String fillColor = Integer.toString(getFillColor().getRGB());
		return "Donut: Center: [x=" + getCenter().getX() + ",y=" + getCenter().getY() +"], radius=" + this.getRadius() + ", selected=" +this.isSelected()+", edgeColor:" + sEdgeColor + ", fillColor:" + fillColor + ", inner radius=" + innerRadius; 
	}

	
	public Donut clone(Shape s) {
		Donut donutClone = new Donut(new Point(), 0, 0);
		
		if (s instanceof Donut) {
			donutClone = (Donut) s;
		}
		donutClone.getCenter().setX(this.getCenter().getX());
		donutClone.getCenter().setY(this.getCenter().getY());
		donutClone.setRadius(this.getRadius());
		donutClone.setsEdgeColor(this.getsEdgeColor());
		donutClone.setFillColor(this.getFillColor());

		return donutClone;
	}
	
}