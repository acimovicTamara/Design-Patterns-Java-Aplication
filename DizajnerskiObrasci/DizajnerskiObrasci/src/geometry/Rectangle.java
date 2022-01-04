package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends SurfaceShape {

	private Point upperLeftPoint;
	private int width;
	private int height;
	private Color edgeColor;
	private Color fillColor;
	
	public Rectangle() {
		
	}
	
	public Rectangle(Point upperLeftPoint, int height, int width) {
		this.upperLeftPoint = upperLeftPoint;
		setHeight(height);
		setWidth(width);
	}
	
	public Rectangle(Point upperLeftPoint, int height, int width, boolean selected) {
		this(upperLeftPoint, height, width);
		setSelected(selected);
	}
	
	public Rectangle(Point upperLeftPoint, int height, int width, Color edgeCol, Color fillCol) {
		this.upperLeftPoint = upperLeftPoint;
		setHeight(height);
		setWidth(width);
		edgeColor = edgeCol;
		fillColor = fillCol;
	}

	@Override
	public void draw(Graphics g) {
		if(fillColor!=null)
		{
			fill(g);
		}
		if(edgeColor!=null)
			
			g.setColor(edgeColor);
		else
			
			g.setColor(Color.black);
			g.drawRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), this.width, this.getHeight());
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getUpperLeftPoint().getX() - 3, this.getUpperLeftPoint().getY() -3, 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() + width - 3, this.getUpperLeftPoint().getY() - 3, 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() - 3, this.getUpperLeftPoint().getY() + height - 3, 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() + width - 3, this.getUpperLeftPoint().getY() + height - 3, 6, 6);
			g.setColor(Color.BLACK);
		}
	}
	
	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(fillColor);	
		g.fillRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), this.width, this.getHeight());
	}

	@Override
	public void moveBy(int byX, int byY) {
		upperLeftPoint.moveBy(byX, byY);
		
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof Rectangle) {
			return (int) (this.area() - ((Rectangle) o).area());
		}
		return 0;
	}
	
	public boolean contains(int x, int y) {
		if (this.getUpperLeftPoint().getX() <= x
				&& x <= (this.getUpperLeftPoint().getX() + width)
				&& this.getUpperLeftPoint().getY() <= y
				&& y <= (this.getUpperLeftPoint().getY() + height)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean contains(Point p) {
		if (this.getUpperLeftPoint().getX() <= p.getX()
				&& p.getX() <= (this.getUpperLeftPoint().getX() + width)
				&& this.getUpperLeftPoint().getY() <= p.getY()
				&& p.getY() <= (this.getUpperLeftPoint().getY() + height)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean equals (Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle r = (Rectangle) obj;
			if (this.upperLeftPoint.equals(r.getUpperLeftPoint()) &&
					this.height == r.getHeight() &&
					this.width == r.getWidth()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}		
	}
	
	
	public int area() {
		return width * height;
	}
	
	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}
	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height)  {
		this.height = height;
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
	public String toString() {
		String fillColor = Integer.toString(getFillColor().getRGB());
		String edgeColor = Integer.toString(getEdgeColor().getRGB());
		return "Rectangle: Upper left point: [x=" + upperLeftPoint.getX() + ", y="+ upperLeftPoint.getY() + "], height=" + height + ", width=" + width+", selected=" + this.isSelected()+ ", fillColor=" + fillColor + ", edgeColor=" + edgeColor;
	}
	
	public Rectangle clone(Shape s) {
		Rectangle rectangleClone = new Rectangle(new Point(), 0, 0);
		
		if(s instanceof Rectangle) {
			rectangleClone = (Rectangle) s;
		}
		
		rectangleClone.getUpperLeftPoint().setX(this.getUpperLeftPoint().getX());
		rectangleClone.getUpperLeftPoint().setY(this.getUpperLeftPoint().getY());
		rectangleClone.setHeight(this.getHeight());
		rectangleClone.setWidth(this.getWidth());
		rectangleClone.setEdgeColor(this.getEdgeColor());
		rectangleClone.setFillColor(this.getFillColor());
		
		return rectangleClone;
	}

}
