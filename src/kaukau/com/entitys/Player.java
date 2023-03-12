package kaukau.com.entitys;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import kaukau.com.game.Game;
import kaukau.com.graphics.Camera;
import kaukau.com.graphics.SpriteSheet;
import kaukau.com.world.World;

public class Player extends Entity {
	//sprites
	private BufferedImage[] sprite_no_gun;
	private BufferedImage[] sprite_have_gun;
	private BufferedImage[] sprite_jump_have_gun;
	private BufferedImage[] sprite_shoot_stop;
	private BufferedImage[] sprite_shoot_moving;
	private BufferedImage[] sprite_shoot_uping;
	private BufferedImage[] sprite_shoot_falling;
	private BufferedImage[] sprite_jump_no_gun;

	//anime info
	private int max_Frame;
	private int frame;
	private int cur_Frame;
	private int quat_sprite;

	//steps
	private boolean isSteps;

	//Shoot
	private boolean isShoot;
	private long lastTime = System.currentTimeMillis();

	//Falling 
	private boolean isFalling;

	//Move
	private boolean isMoving;
	private boolean right,left,up,down;
	private boolean isLeft;

	//Jump
	private int jumpMax, jump;
	private boolean coliddionFloor;
	private boolean isJump,isUping,isDownning;


	public Player(double x, double y, int width, int height) {
		super(x, y, width, height);
		init();
		sprite_no_gun = new BufferedImage[quat_sprite];
		sprite_have_gun = new BufferedImage[quat_sprite];
		sprite_jump_have_gun = new BufferedImage[quat_sprite];
		sprite_shoot_stop = new BufferedImage[quat_sprite];
		sprite_shoot_moving = new BufferedImage[quat_sprite];
		sprite_shoot_uping = new BufferedImage[quat_sprite];
		sprite_shoot_falling = new BufferedImage[quat_sprite];
		sprite_jump_no_gun = new BufferedImage[quat_sprite];
		getSprite();
	}

	public void init() {
		max_Frame = 6;
		frame = 0;
		quat_sprite = 2;
		cur_Frame = 0;
		jumpMax = 40;
		jump = jumpMax;
		speed = 1;
	}
	public void getSprite(){
		for(int i = 0;i<quat_sprite;i++) {
			sprite_no_gun[i] = SpriteSheet.getSubimage(i*16, 16 , width, height);
			sprite_have_gun[i] = SpriteSheet.getSubimage(i*16, 0 , width, height);
			sprite_jump_have_gun[i] = SpriteSheet.getSubimage(i*16, 32, width, height);
			sprite_shoot_stop[i] = SpriteSheet.getSubimage(i*16, 48, width, height);
			sprite_shoot_moving[i] = SpriteSheet.getSubimage(i*16, 64, width, height);
			sprite_shoot_uping[i] = SpriteSheet.getSubimage(i*16, 80, width, height);
			sprite_shoot_falling[i] = SpriteSheet.getSubimage(i*16, 96, width, height);  
			sprite_jump_no_gun[i] = SpriteSheet.getSubimage(i*16, 112, width, height);
		}

	}

	public void update() {
		death();
		gravition();
		logicOfMove();
		logicOfShoot();
		logicOfAnime();
		logicOfCamera();
	}

	public void death() {
		if(isColiddionBullet(this)) {

		}
	}	
	public void logicOfCamera() {
		Camera.setX(Camera.clamp(getX()-Game.Width()/2, 0, (World.getWidth()*World.getTileSize())-Game.Width()));
		Camera.setY(Camera.clamp(getY()-Game.Height()/2, 0, (World.getHeight()*World.getTileSize())-Game.Height()));
	}
	public void logicOfAnime(){

		if(isJump) {
			if(isShoot) {
				animeShoot();
			}else {
				animeJump();
			}
		}else if(isFalling){
			animeFalling();
		}else if(isMoving) {
			animeMoving();
		}else if(isShoot) {
			animeShoot();
		}else {
			cur_Frame = 0;
		}
	}
	public void gravition() {
		if(!up && !World.isColiddionTile(getX(), (int)(y+speed)) 
				&& !World.isColiddionSteps((int)(x+speed), getY()) 
				&& !World.isColiddionSteps(getX(), (int) (y+speed))
				&& !World.isColiddionSteps_left((int)(x+speed), getY()) 
				&& !World.isColiddionSteps_left(getX(), (int) (y+speed))) {
			y+=speed;
			isFalling = true;
		}else {
			isFalling = false;
		}
	}
	public void jump() {

		if(jump == jumpMax && World.isColiddionTile(getX(), (int)(y+speed))) {
			coliddionFloor = true;
		}

		if(World.isColiddionTile(this.getX(), (int)(y-speed)) || 0>=y-speed || jump == 0 || !coliddionFloor){
			jump = 0;
			return;
		}else {
			y-=speed;
			jump--;
		}


	}
	public void logicOfMove() {
		isMoving = false;

		if(right && !((World.getWidth()*16)-16 <= x+speed) && !World.isColiddionTile((int)(x+speed), getY())
				&& !isColiddionPlayer((int)(x+speed), getY())) {
			if(World.isColiddionSteps(getX(), getY()) && down 
					&& World.isColiddionTile(getX(), (int)(y+speed))) {	
				x+=speed;
				isLeft = false;
				isMoving = true;
				isSteps = false;
			}else if(World.isColiddionSteps(getX(), getY()) && down){
				x+=speed;
				y-=speed;
				isLeft = false;
				isMoving = true;
				isSteps = true;
			}else if(World.isColiddionSteps_left(getX(), (int) (y+speed)) && down){
				x+=speed;
				if(!World.isColiddionTile(getX(), (int)(y+speed))) {
					y+=speed;
				}
				isLeft = false;
				isMoving = true;
				isSteps = true;
			}else if(World.isColiddionSteps(getX(), getY()) && !down) {
				x+=speed;
				y-=speed;
				isLeft = false;
				isMoving = true;
				isSteps = true;
			}else if(World.isColiddionSteps_left(getX(), getY()) && !down) {
				x+=speed;
				if(!World.isColiddionTile(getX(), (int)(y+speed))) {
					y+=speed;
				}
				isLeft = false;
				isMoving = true;
				isSteps = true;
			}else if(World.isColiddionSteps_left(getX(), (int)(y+speed)) && !down) {
				x+=speed;
				if(!World.isColiddionTile(getX(), (int)(y+speed))) {
					y+=speed;
				}
				isLeft = false;
				isMoving = true;
				isSteps = true;
			}else {
				x+=speed;
				isLeft = false;
				isMoving = true;
				isSteps = false;
			}

		}else if(World.isColiddionTile((int)(x+speed), getY()) && World.isColiddionSteps(getX(), getY())) {
			y-=speed;
		} else if(left && !(x-speed<=0) && !World.isColiddionTile((int)(x-speed), getY())
				&& !isColiddionPlayer((int)(x-speed), getY())) {
			if(World.isColiddionSteps_left(getX(), getY()) && down 
					&& World.isColiddionTile(getX(), (int)(y+speed))) {	
				x-=speed;
				isLeft = true;
				isMoving = true;
				isSteps = false;
			}else if(World.isColiddionSteps_left(getX(), getY()) && down){
				x-=speed;
				y-=speed;
				isLeft = true;
				isMoving = true;
				isSteps = true;
			}else if(World.isColiddionSteps(getX(), (int) (y+speed)) && down){
				x-=speed;
				if(!World.isColiddionTile(getX(), (int)(y+speed))) {
					y+=speed;
				}
				isLeft = true;
				isMoving = true;
				isSteps = true;
			}else if(World.isColiddionSteps(getX(), getY()) && !down) {
				x-=speed;
				if(!World.isColiddionTile(getX(), (int)(y+speed))) {
					y+=speed;
				}
				isLeft = true;
				isMoving = true;
				isSteps = true;
			}else if(World.isColiddionSteps(getX(), (int)(y+speed)) && !down) {
				x-=speed;
				if(!World.isColiddionTile(getX(), (int)(y+speed))) {
					y+=speed;
				}
				isLeft = true;
				isMoving = true;
				isSteps = true;
			}else if(World.isColiddionSteps_left(getX(), getY()) && !down) {
				x-=speed;
				y-=speed;
				isLeft = true;
				isMoving = true;
				isSteps = true;
			}else {
				x-=speed;
				isLeft = true;
				isMoving = true;
				isSteps = false;
			}
		}else if(World.isColiddionTile((int)(x-speed), getY()) && World.isColiddionSteps_left(getX(), getY())) {
			y-=speed;
		}

		if(up){
			isJump = true;
			isUping = true;
			jump();
			if(jump == 0) {
				isUping = false;
				isDownning = true;
				if(!World.isColiddionTile(getX(), (int)(y+speed)) && !World.isColiddionSteps(getX(), (int)(y+speed))){
					if(!isColiddionPlayer(getX(), (int)(y+speed))) {
						y+=speed;
					}
				}else{
					isDownning = false;
					isJump = false;
					up = false;
					jump = jumpMax;
				}
			}
		}
	}
	public void logicOfShoot() {
		if(isShoot) {
			long nowTime = System.currentTimeMillis();
			if(nowTime - lastTime >= 200) {
				lastTime = nowTime;
				if(isLeft) {
					Bullet b = new Bullet(getX()+2,getY()+9,2,1,false,this);
					Game.getEntityList().add(b);
					Game.getBulletList().add(b);
				}else {
					Bullet b = new Bullet(getX()+12,getY()+9,2,1,true,this);
					Game.getEntityList().add(b);
					Game.getBulletList().add(b);
				}
			}
		}
	}
	public void animeMoving() {
		frame ++;
		if(frame == max_Frame) {
			cur_Frame++;
			frame = 0;
			if(cur_Frame == quat_sprite) {
				cur_Frame = 0;
			}
		}
	}
	public void animeFalling() {
		cur_Frame = 1;
	}
	public void animeJump() {
		if(isUping) {
			cur_Frame = 0;
		}else if(isDownning) {
			cur_Frame = 1;
		}
	}
	public void animeShoot() {
		frame ++;
		if(frame == max_Frame) {
			cur_Frame++;
			frame = 0;
			if(cur_Frame == quat_sprite) {
				cur_Frame = 0;
			}
		}
	}
	public void render(Graphics g) {
		//g.fillRect(getX() - Camera.getX(),getY() - Camera.getY(), width, height);
		if(isJump || isFalling) {
			if(isShoot) {
				if(isUping) {
					if(isLeft) {
						g.drawImage(flip(sprite_shoot_uping [cur_Frame]), getX()-Camera.getX(), getY()-Camera.getY(), null);
					}else {
						g.drawImage(sprite_shoot_uping [cur_Frame], getX()-Camera.getX(), getY()-Camera.getY(), null);
					}
				}else {
					if(isLeft) {
						g.drawImage(flip(sprite_shoot_falling [cur_Frame]), getX()-Camera.getX(), getY()-Camera.getY(), null);
					}else {
						g.drawImage(sprite_shoot_falling [cur_Frame], getX()-Camera.getX(), getY()-Camera.getY(), null);
					}
				}
			}else {
				if(isLeft) {
					g.drawImage(flip(sprite_jump_no_gun [cur_Frame]), getX()-Camera.getX(), getY()-Camera.getY(), null);
				}else {
					g.drawImage(sprite_jump_no_gun [cur_Frame], getX()-Camera.getX(), getY()-Camera.getY(), null);
				}
			}
		}else{
			if(isShoot && !isMoving) {
				if(isLeft) {
					g.drawImage(flip(sprite_shoot_stop[cur_Frame]), getX()-Camera.getX(), getY()-Camera.getY(), null);

				}else {
					g.drawImage(sprite_shoot_stop[cur_Frame], getX()-Camera.getX(), getY()-Camera.getY(), null);

				}
			}else if(isShoot){
				if(isLeft) {
					g.drawImage(flip(sprite_shoot_moving[cur_Frame]), getX()-Camera.getX(), getY()-Camera.getY(), null);
				}else {
					g.drawImage(sprite_shoot_moving[cur_Frame], getX()-Camera.getX(), getY()-Camera.getY(), null);
				}
			}else {
				if(isLeft) {
					g.drawImage(flip(sprite_no_gun[cur_Frame]), getX()-Camera.getX(), getY()-Camera.getY(), null);
				}else {
					g.drawImage(sprite_no_gun[cur_Frame], getX()-Camera.getX(), getY()-Camera.getY(), null);
				}
			}

		}

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
	public boolean isShoot() {
		return isShoot;
	}
	public void setShoot(boolean isShoot) {
		this.isShoot = isShoot;
	}

}
