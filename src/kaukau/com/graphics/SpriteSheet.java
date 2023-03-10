package kaukau.com.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private static BufferedImage spriteSheet;
	
	public SpriteSheet(String path) {
		try {
			spriteSheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			
		}
	}
	public static BufferedImage getSubimage(int x ,int y, int width, int height) {
		return spriteSheet.getSubimage(x, y, width, height);
	}
	
}
