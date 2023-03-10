package kaukau.com.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import kaukau.com.entitys.Enemy;
import kaukau.com.game.Game;
import kaukau.com.graphics.Camera;
import kaukau.com.tiles.Floor;
import kaukau.com.tiles.Sky;
import kaukau.com.tiles.Tile;

public class World {
	private BufferedImage spriteMap;
	private static int width,height;
	private static Tile[] tiles;
	private static int tileSize = 16;
	
	public World(String path) {
		getSpriteMap(path);
		width = spriteMap.getWidth();
		height = spriteMap.getHeight();
		tiles = new Tile[width*height];
		int[] pixels = new int[width*height];
		spriteMap.getRGB(0, 0, width, height, pixels, 0, width);
		for(int xx = 0;xx < width;xx++) {
			for(int yy = 0;yy < height;yy++) {
				int nowPixel = xx+(yy*width);
				if(pixels[nowPixel] == 0xFF000000) {
					//sky and background
					tiles[nowPixel] = new Sky(xx*tileSize,yy*tileSize,tileSize,tileSize,80,0);
				}else if(pixels[nowPixel] == 0xFFFFFFFF) {
					//floor
					tiles[nowPixel] = new Floor(xx*tileSize, yy*tileSize,tileSize,tileSize,80,16);
				}else if(pixels[nowPixel] == 0xFFFFD1FF){
					//SubFloor
					tiles[nowPixel] = new Sky(xx*tileSize,yy*tileSize,tileSize,tileSize,80,32);
				}else if(pixels[nowPixel] == 0xFFFF0008){
					//Enemy
					tiles[nowPixel] = new Sky(xx*tileSize,yy*tileSize,tileSize,tileSize,80,0);
					Enemy e = new Enemy(xx*tileSize, yy*tileSize,tileSize,tileSize);
					Game.getEntityList().add(e);
					Game.getEnemyList().add(e);
				}else {
					tiles[nowPixel] = new Sky(xx*tileSize,yy*tileSize,tileSize,tileSize,80,0);
				}
			}
		}
	}
	public void getSpriteMap(String path) {
		try {
			spriteMap = ImageIO.read(this.getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isColiddionTile(int xNext, int yNext) {
		int x1 = xNext/tileSize;
		int y1 = yNext/tileSize;
		
		int x2 =(xNext + tileSize-1)/tileSize;
		int y2 = yNext/tileSize;
		
		int x3 = xNext/tileSize;
		int y3 = (yNext + tileSize-1)/tileSize;
		
		int x4 = (xNext + tileSize-1)/tileSize;
		int y4 = (yNext + tileSize-1)/tileSize;
		
		return  tiles[x1+(y1*width)] instanceof Floor ||
				tiles[x2+(y2*width)] instanceof Floor ||
				tiles[x3+(y3*width)] instanceof Floor ||
				tiles[x4+(y4*width)] instanceof Floor ;
	}
	
	public void renderWorld(Graphics g) {
		int xStart = Camera.getX()/tileSize;;
		int yStart = Camera.getY()/tileSize;
		int xEnd =	xStart + Game.Width()/tileSize;
		int yEnd =	yStart + Game.Height()/tileSize;
		for(int xx = xStart ;xx <= xEnd; xx++) {
			for(int yy = yStart; yy <= yEnd; yy++) {
				if(xx < 0 || yy < 0 || xx >= width || yy >= height)
					continue;
				Tile tile = tiles[xx + (yy*width)];
				tile.render(g);
			}
		}
	}
	
	public static int getWidth() {
		return width;
	}
	
	public static int getHeight() {
		return height;
	}
	
	public static int getTileSize() {
		return tileSize;
	}
	
	
}
