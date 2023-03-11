package kaukau.com.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import kaukau.com.graphics.Camera;
import kaukau.com.graphics.SpriteSheet;

public class Tile {
	protected int x,y;
	protected int width,height;
	protected int spriteX,spriteY;
	protected BufferedImage sprite;
	
	public Tile(int x, int y, int width, int height, int spriteX, int spriteY) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.spriteX = spriteX;
		this.spriteY = spriteY;
		sprite = SpriteSheet.getSubimage(spriteX, spriteY, width, height);
		
	}
	public void render(Graphics g) {
		g.drawImage(sprite, x-Camera.getX(), y-Camera.getY(), width, height, null);
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
	
}
