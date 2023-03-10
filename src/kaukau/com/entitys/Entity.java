package kaukau.com.entitys;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import kaukau.com.game.Game;

public class Entity {
	protected double x, y;
	protected int width, height;
	protected double speed;
	
	public Entity(double x, double y, int width, int height) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	public void update() {}
	
	public void render(Graphics g) {
		
	}
	
	public boolean isColiddionPlayer(int xNext, int yNext) {
		Rectangle player = new Rectangle(xNext,yNext,Game.getPlayer().getWidth(),Game.getPlayer().getHeight());
		for(int i = 0;i<Game.getEntityList().size();i++) {
			Entity e = Game.getEntityList().get(i);
			if(e instanceof Player || e instanceof Bullet)
				continue;
			Rectangle entityCurrent = new Rectangle(e.getX(),e.getY(),e.getWidth(),e.getHeight());
			if(entityCurrent.intersects(player)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isColiddionBullet(Entity e) {
		for(int i = 0;i<Game.getBulletList().size();i++) {
			Bullet b = Game.getBulletList().get(i);
			if(e == b.getParent())
				continue;
			Rectangle entityCurrent = new Rectangle(e.getX(),e.getY(),e.getWidth(),e.getHeight());
			Rectangle bulletCurrent = new Rectangle(b.getX(),b.getY(),b.getWidth(),b.getHeight());
			if(bulletCurrent.intersects(entityCurrent)) {
				Game.getBulletList().remove(b);
				Game.getEntityList().remove(b);
				return true;
			}
		}
		return false;
	}
	
	public BufferedImage flip(BufferedImage input) {

		BufferedImage flipped = new BufferedImage(input.getWidth(),input.getHeight(),BufferedImage.TYPE_INT_ARGB);
		for(int y = 0;y<height; y++) {
			for(int x = 0; x<width; x++) {
				flipped.setRGB((width-1)-x, y, input.getRGB(x, y));
			}
		}
		return flipped;

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
