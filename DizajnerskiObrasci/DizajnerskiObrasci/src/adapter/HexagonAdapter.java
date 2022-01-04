package adapter;

import java.awt.Color;
import java.awt.Graphics;

import geometry.Point;
import geometry.Shape;
import geometry.SurfaceShape;
import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape{
	
	private Hexagon hexagon;
	
	private String color;
	private String fillColor;

	public HexagonAdapter() {
		
	}
	
	public HexagonAdapter(Hexagon hexagon) { 
		this.hexagon = hexagon; 
	}
	
	public HexagonAdapter(int x, int y, int radius) {
		hexagon = new Hexagon(x, y, radius);
		
	}
	
	public HexagonAdapter(int x, int y, int radius, Color fillColor, Color edgeColor) {
		hexagon = new Hexagon(x, y, radius);
		this.setColor(edgeColor);
		this.setFillColor(fillColor);
	}
	
	
	@Override
	public void moveBy(int byX, int byY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof Hexagon) {
			Hexagon h = (Hexagon) o;
			return (int) (hexagon.getR() - h.getR());
		}
		else
			return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return hexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		hexagon.paint(g);
		
	}
	
	public String toString(){        
		if (getColor() != null) {
			color = Integer.toString(hexagon.getBorderColor().getRGB());
		}
		if (getFillColor() !=  null)
		{
			fillColor = Integer.toString(hexagon.getAreaColor().getRGB());
		}		
		return "Heksagon:("+hexagon.getX()+","+hexagon.getY()+"), radius="+hexagon.getR()+", selected=" + this.isSelected() + ", edgeColor:"+ color+", fillColor:"+fillColor;
		
	}
	
	public boolean equals(Object obj){
		if(obj instanceof HexagonAdapter){
			HexagonAdapter hexAdapter = (HexagonAdapter) obj;
			Point p1 = new Point(hexagon.getX(),hexagon.getY());
			Point p2 = new Point(hexAdapter.hexagon.getX(),hexAdapter.hexagon.getY());
			if(p1.equals(p2) && hexagon.getR() == hexAdapter.getHexagon().getR())
				return true;
			else
				return false;

		}
		else
			return false;
	}
	
	@Override
	public boolean isSelected() {
		return hexagon.isSelected();
	}

	@Override
	public void setSelected(boolean selected) {
		hexagon.setSelected(selected);
	}

	public Color getColor() {
		return hexagon.getBorderColor();
	}
	
	public void setColor(Color color) {
		hexagon.setBorderColor(color);
	}
	
	public Color getFillColor() {
		return hexagon.getAreaColor();
	}
	
	public void setFillColor(Color color) {
		hexagon.setAreaColor(color);
	}
	
	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}

	@Override
	public boolean contains(Point p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	public HexagonAdapter clone(Shape s) {
		HexagonAdapter hexagonClone = new HexagonAdapter(0, 0, 0);
		
		if(s instanceof HexagonAdapter) {
			hexagonClone = (HexagonAdapter) s;
		}
		
		hexagonClone.getHexagon().setX(this.getHexagon().getX());
		hexagonClone.getHexagon().setY(this.getHexagon().getY());
		hexagonClone.getHexagon().setR(this.getHexagon().getR());
		hexagonClone.setColor(this.getColor());
		hexagonClone.setFillColor(this.getFillColor());

		return hexagonClone;
	}

}
