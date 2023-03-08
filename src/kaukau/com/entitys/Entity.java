package kaukau.com.entitys;

import java.awt.Graphics;

public class Entity {
	protected double x, y;
	protected int width, height;
	
	public Entity(double x, double y, int width, int height) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	public void update() {}
	
	public void render(Graphics g) {
		
	}

	public int getX() {
		return (int) x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getY() {
		return (int) y;
	}

	public void setY(double y) {
		this.y = y;
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

	public void setHeight(int height) {
		this.height = height;
	}
	
}
