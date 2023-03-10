package kaukau.com.entitys;

import java.awt.Color;
import java.awt.Graphics;

import kaukau.com.game.Game;
import kaukau.com.graphics.Camera;
import kaukau.com.world.World;

public class Bullet extends Entity{
	private boolean side;
	private Entity parent;
	public Bullet(double x, double y, int width, int height, boolean side, Entity parent) {
		super(x, y, width, height);
		this.side = side;
		speed = 2.5;
		this.parent = parent;
	}
	
	public void update() {
		autoDestruction();
		if(side) {
			x+=speed;
		}else {
			x-=speed;
		} 
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(getX()-Camera.getX(), getY()-Camera.getY(), width, height);
	}
	
	public void autoDestruction(){
		if(side && (World.isColiddionTile((int)(x+speed)-14, getY()-9))){
			deleteBullet();
		}else if(!side && World.isColiddionTile((int)(x-speed)+1, getY()-9)) {
			deleteBullet();
		}
		if((Camera.getX()+Game.Width()) <=x || Camera.getX() >= x) {
			deleteBullet();
		}
	}
	
	public void deleteBullet() {
		Game.getEntityList().remove(this);
		Game.getBulletList().remove(this);
	}

	public Entity getParent() {
		return parent;
	}
	
}
