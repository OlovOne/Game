package kaukau.com.entitys;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.random.RandomGeneratorFactory;

import kaukau.com.game.Game;
import kaukau.com.graphics.Camera;
import kaukau.com.graphics.SpriteSheet;
import kaukau.com.world.World;

public class Enemy extends Entity{
	//Sprite
	private BufferedImage[] sprite_no_gun;
	private BufferedImage[] sprite_have_gun;
	private BufferedImage[] sprite_shoot_stop;
	private BufferedImage[] sprite_shoot_walk;
	private BufferedImage[] sprite_death;

	//anime info
	private int max_Frame;
	private int frame;
	private int cur_Frame;
	private int quat_sprite;

	//death 
	private int xMax;

	//Move
	private double speed;
	private double lastX;

	//Shoot
	private long lastTime = System.currentTimeMillis();
	private boolean isShoot;
	private Random random;

	public Enemy(double x, double y, int width, int height) {
		super(x, y, width, height);
		init();
		sprite_no_gun = new BufferedImage[quat_sprite];
		sprite_have_gun = new BufferedImage[quat_sprite];
		sprite_shoot_stop = new BufferedImage[quat_sprite];
		sprite_shoot_walk = new BufferedImage[quat_sprite];
		sprite_death = new BufferedImage[quat_sprite];
		getSprite();
		random = new Random();
	}

	private void init() {
		lastX = x;
		speed = 0.7;
		max_Frame = 10;
		frame = 0;
		cur_Frame = 0;
		quat_sprite = 2;
		xMax = 10;
	}

	private void getSprite() {
		int aux=0;
		for(int i = 0; i<quat_sprite;i++) {
			aux++;
			sprite_no_gun[i] = SpriteSheet.getSubimage((aux*16)+16, 0, width, height);
			sprite_have_gun[i] = SpriteSheet.getSubimage((aux*16)+16, 16, width, height);
			sprite_shoot_stop[i] = SpriteSheet.getSubimage((aux*16)+16, 32, width, height);
			sprite_shoot_walk[i] = SpriteSheet.getSubimage((aux*16)+16, 48, width, height);
			sprite_death[i] = SpriteSheet.getSubimage((aux*16)+16, 64, width, height);
		}
	}

	public void update() { 
		//System.out.println();
		lastX = x;
		if(isColiddionBullet(this) || isDeath) {
			isDeath = true;
			deathAnime();
		}else{

			if(distanciOfPlayer() >= 60 || !isRaio() || haveTile() ) {
				isShoot = false;
				if(World.isColiddionSteps_left(getX(),getY())) {
					if(speed <0) {
						if(!World.isColiddionTile(getX(),(int)(y-0.7)) 
								|| World.isColiddionTile((int)(x-0.7), (int)(y-0.7))) {
							y-=0.7;
						}
					}else {
						if(!World.isColiddionTile(getX(),(int)(y+0.7))) {
							y+=0.7;
						}
					}
					x+=speed;
				}else if(World.isColiddionSteps_left(getX(),(int)(y+1)) &&
						!World.isColiddionTile(getX(), getY()+1)) {
					y+=0.7;
					x+=speed;
				}else if(World.isColiddionSteps(getX(),getY())) {
					if(speed <0) {
						if(!World.isColiddionTile(getX(),(int)(y+0.7)) 
								|| World.isColiddionTile((int)(x-0.7), (int)(y+0.7))) {
							y+=0.7;
						}
					}else {
						if(!World.isColiddionTile(getX(),(int)(y-0.7))) {
							y-=0.7;
						}
					}
					x+=speed;
				}else if(World.isColiddionSteps(getX(),(int)(y+1)) &&
						!World.isColiddionTile(getX(), getY()+1)) {
					y+=0.7;
					x+=speed;
				}else if((!World.isColiddionTile((int)(x+World.getTileSize()), (int)(y+World.getTileSize()))
						||!World.isColiddionTile((int)(x-World.getTileSize()), (int)(y+World.getTileSize())))
						&&(!World.isColiddionSteps_left((int)(x+World.getTileSize()),(int)(y+World.getTileSize()))
								&&!World.isColiddionSteps_left((int)(x-World.getTileSize()),(int)(y+World.getTileSize())))
						&&(!World.isColiddionSteps((int)(x+World.getTileSize()),(int)(y+World.getTileSize()))
								&&!World.isColiddionSteps((int)(x-World.getTileSize()),(int)(y+World.getTileSize())))) {
					speed*=-1; 
					x+=speed;
				} else if((World.getWidth()*World.getTileSize())-16 <= x || 0>x || World.isColiddionTile((int)(x+speed), getY())) {
					speed*=-1;
					x+=speed;
				}else {
					x+=speed;
				}
			}else{
				long nowTime = System.currentTimeMillis();
				if(nowTime - lastTime >= 100) {
					lastTime = nowTime;
					if(random.nextInt(100) > 50 ) {
						isShoot = true;
						if(x > Game.getPlayer().getX()) {
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

			if(lastX != x) {
				animeSprite();
			}else if(isShoot){
				animeSprite();
			}else {
				cur_Frame = 0;
			}
		}
	}
	private boolean isRaio() {
		if(Game.getPlayer().getY()<y+8 && Game.getPlayer().getY() > y-16) {
			return true;
		}
		return false;
	}

	public void deathAnime() {
		if(xMax >=0) {
			x-=speed;
			y+=0.3;
			xMax--;
			cur_Frame = 0;
		}else {
			cur_Frame = 1;
		}
	}

	private boolean haveTile() {
		int aux = 0;
		if(x > Game.getPlayer().getX()) {
			int maxTile = ((getX()-Game.getPlayer().getX())/16);
			while((maxTile >= aux && maxTile > 0)) {
				aux++;
				if(World.isColiddionTile(Game.getPlayer().getX()+(aux*16), getY())) {
					return true;
				}
				maxTile = ((getX()-Game.getPlayer().getX())/16);
			}
		}else {
			int maxTile = (Game.getPlayer().getX()-getX())/16;
			while((maxTile >= aux && maxTile > 0)) {
				aux++;
				if(World.isColiddionTile(Game.getPlayer().getX()-(aux*16), getY())) {
					return true;
				}
				maxTile = (Game.getPlayer().getX()-getX())/16;
			}
		}
		return false;
	}
	private int distanciOfPlayer() {
		return (int) Math.sqrt(Math.pow(getX()-Game.getPlayer().getX(), 2) + Math.pow(getY()-Game.getPlayer().getY(), 2));
	}

	public void animeSprite() {
		frame++;
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
		if(isDeath) {
			g.drawImage(sprite_death[cur_Frame],getX() - Camera.getX(),getY() - Camera.getY(), null);
		}else {
			if(isShoot) {
				if(x > Game.getPlayer().getX()) {
					g.drawImage(flip(sprite_shoot_stop[cur_Frame]),getX() - Camera.getX(),getY() - Camera.getY(), null);
				}else {
					g.drawImage(sprite_shoot_stop[cur_Frame],getX() - Camera.getX(),getY() - Camera.getY(), null);
				}
			}else {
				if(speed >0) {
					g.drawImage(sprite_no_gun[cur_Frame],getX() - Camera.getX(),getY() - Camera.getY(), null);
				}else {
					g.drawImage(flip(sprite_no_gun[cur_Frame]),getX() - Camera.getX(),getY() - Camera.getY(), null);
				}
			}
		}
	}
}
