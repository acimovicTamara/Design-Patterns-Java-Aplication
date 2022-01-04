package geometry;

import java.awt.Color;
import java.awt.Graphics;


public class Point extends Shape implements Cloneable {

	private int x;
	private int y;
	private Color col;
	
	public Point() {
		
	}
	
	public Point(int x, int y) {
		this.x = x;
		setY(y);
	}
	
	public Point(int x, int y, boolean selected) {
		this(x, y);
		setSelected(selected);
	}
	
	public Point(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		col = color;
	}

	
	@Override
	public void moveBy(int byX, int byY) {
		this.x += byX;
		this.y = this.y + byY;
		
	}

	
	@Override
	public int compareTo(Object o) {
		if(o instanceof Point) {
			Point start = new Point(0, 0);
			return (int) (this.distance(start.getX(), start.getY()) - ((Point) o).distance(start.getX(),start.getY()));
		}
		return 0;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(col);
		g.drawLine(this.x-2, y, this.x+2, y);
		g.drawLine(x, this.y-2, x, this.y+2);
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.x-3, this.y-3, 6, 6);
			g.setColor(Color.BLACK);
		}
	}
	
	/*public boolean contains(int x, int y) {
		if (this.distance(x, y) <= 3 ) {
			return true;
		} else {
			return false;
		}
	}*/
	
	public boolean contains(Point p) {
		return this.distance(p.getX(), p.getY()) <=3;
	}
	
	
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point p = (Point) obj;
			if (this.x == p.getX() &&
					this.y == p.getY()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public double distance(int x2, int y2) {
		double dx = this.x - x2;
		double dy = this.y - y2;
		double d = Math.sqrt(dx * dx + dy * dy);
		return d;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Color getCol() {
		return col;
	}

	public void setCol(Color col) {
		this.col = col;
	}	

	public String toString() {
		String color = Integer.toString(getCol().getRGB());
		return "Point [x=" + x + ", y=" + y + ", selected=" + this.isSelected() + ", color:" + color + "]";
	}

	@Override
	public boolean contains(int x, int y) {
		return this.distance(x,y) <=3;
	}
	
	
	public Point clone(Shape s) {
		Point pointClone = new Point();
		
		if(s instanceof Point) {
			pointClone = (Point) s;
		}
		
		pointClone.setX(this.getX());
		pointClone.setY(this.getY());
		pointClone.setCol(this.getCol());
		
		return pointClone;
	} 
}