package kaukau.com.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import kaukau.com.entitys.Bullet;
import kaukau.com.entitys.Enemy;
import kaukau.com.entitys.Entity;
import kaukau.com.entitys.Player;
import kaukau.com.graphics.SpriteSheet;
import kaukau.com.world.World;



public class Game extends Canvas implements Runnable,KeyListener{
	private static final long serialVersionUID = 1L;
	
	//Logic of the game
	private Thread thread;
	private boolean isRunning;
	
	//Frame 
	private JFrame frame;
	private final static int WIDTH = 240;
	private final static int HEIGHT = 160;
	private final int SCALE = 3;
	
	//Graphics
	private BufferedImage image;
	private static Graphics graphics;
	private static BufferStrategy bufferS;
	private static SpriteSheet spritesheet;
	
	//Graphics map
	private static World world;
	
	//Entity
	private static Player player;
	private static List<Entity> entityList;
	private static List<Bullet> bulletList;
	private static List<Enemy> enemyList;

	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		spritesheet = new SpriteSheet("/spritesheet.gif");
		player = new Player(32,32,16,16);
		entityList = new ArrayList<Entity>();
		bulletList = new ArrayList<Bullet>();
		enemyList = new ArrayList<Enemy>();
		entityList.add(player);
		world = new World("/map.gif");
		this.addKeyListener(this);
	}
	
	public void initFrame() {
		frame = new JFrame();
		frame.add(this);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			System.out.print("O jogo teve problemas para fechar");
		}
	}
	
	public void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void update() {
		updateEntity();
	}
	
	public void render() {
		bufferS = this.getBufferStrategy();
		if(bufferS == null) {
			this.createBufferStrategy(3);
			return;
		}
		graphics = image.getGraphics();
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.renderWorld(graphics);

		renderEntity(graphics);
		
		
		graphics.dispose();
		graphics = bufferS.getDrawGraphics();
		graphics.drawImage(image,0 ,0 ,WIDTH*SCALE, HEIGHT*SCALE, null);
		bufferS.show();
	}
	
	public void renderEntity(Graphics g) {
		for(int i = 0;i<entityList.size();i++) {
			Entity e = entityList.get(i);
			e.render(g);
		}
	}
	
	public void updateEntity() {
		for(int i = 0;i<entityList.size();i++) {
			Entity e = entityList.get(i);
			e.update();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfUpdate = 60.0;
		double ns = 1000000000 / amountOfUpdate;
		double delta = 0;
		this.requestFocus();
		while(isRunning){
			long nowTime = System.nanoTime();
			delta += (nowTime - lastTime) / ns;
			lastTime = nowTime;
			if(delta >= 1) {
				update();
				render();
				delta--;
			}
		}
		stop();
	}
	
	public static SpriteSheet getSpriteSheet() {
		return spritesheet;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.setLeft(true);
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.setRight(true);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.setDown(true);
		}else if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.setUp(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.setShoot(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.setLeft(false);
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.setRight(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.setDown(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.setShoot(false);
		}
	}

	public static int Width() {
		return WIDTH;
	}

	public static int Height() {
		return HEIGHT;
	}

	public static List<Entity> getEntityList() {
		return entityList;
	}

	public static Player getPlayer() {
		return player;
	}

	public static List<Bullet> getBulletList() {
		return bulletList;
	}

	public static List<Enemy> getEnemyList() {
		return enemyList;
	}

	
}
