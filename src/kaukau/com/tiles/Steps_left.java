package kaukau.com.tiles;

public class Steps_left extends Tile{

	public Steps_left(int x, int y, int width, int height, int spriteX, int spriteY) {
		super(x, y, width, height, spriteX, spriteY);
		this.sprite = flip(sprite);
	}
	
}
