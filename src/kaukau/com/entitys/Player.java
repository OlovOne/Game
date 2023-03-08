package kaukau.com.entitys;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import kaukau.com.game.Game;

public class Player extends Entity {
	//anime
	private BufferedImage[] sprite_no_gun;
	private BufferedImage[] sprite_have_gun;
	private int max_Frame;
	private int frame;
	private int cur_Frame;
	private int cur_Max_Frame;
	private int quat_tiles;

	//Move
	private boolean isMoving;
	private boolean right,left,up,down;
	private boolean isLeft;


	public Player(double x, double y, int width, int height) {
		super(x, y, width, height);
		init();
		sprite_no_gun = new BufferedImage[quat_tiles];
		sprite_have_gun = new BufferedImage[quat_tiles];
		getSprite();
	}
	public void init() {
		max_Frame = 10;
		frame = 0;
		quat_tiles = 3;
		cur_Frame = 0;
		cur_Max_Frame = 3;
	}
	public void getSprite(){
		for(int i = 0;i<3;i++) {
			sprite_no_gun[i] = Game.getSpriteSheet().getSubimage(i*16, 0 , width, height);
			sprite_have_gun[i] = Game.getSpriteSheet().getSubimage(i*16, 16 , width, height);
		}

	}

	public void update() {
		if(right) {
			x++;
			isLeft = false;
		}else if(left) {
			x--;
			isLeft = true;
		}

		if(up){
			y--;
		}else if(down) {
			y++;
		}

		if(isMoving) {
			frame ++;
			if(frame == max_Frame) {
				cur_Frame++;
				frame = 0;
				if(cur_Frame == cur_Max_Frame) {
					cur_Frame = 0;
				}
			}
		}else {
			cur_Frame = 0;
		}


	}

	public void render(Graphics g) {
		if(isLeft)
			g.drawImage(flip(sprite_no_gun[cur_Frame]), getX(), getY(), null);
		else
			g.drawImage(sprite_no_gun[cur_Frame], getX(), getY(), null);
	}

	public BufferedImage flip(BufferedImage input) {

		BufferedImage flipped = new BufferedImage(input.getWidth(),input.getHeight(),BufferedImage.TYPE_INT_RGB);
		for(int y = 0;y<height; y++) {
			for(int x = 0; x<width; x++) {
				flipped.setRGB((width-1)-x, y, input.getRGB(x, y));
			}
		}

		return flipped;
	}


	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public boolean isUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	public boolean isMoving() {
		return isMoving;
	}
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

}
