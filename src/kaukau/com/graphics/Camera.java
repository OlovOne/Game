package kaukau.com.graphics;

public class Camera {
	
	private static int x;
	private static int y;
	
	public static int clamp(int now,int min, int max) {
		if(now < min) {
			now = min;
		}
		if(max < now) {
			now = max;
		}
			
		return now;
	}

	public static int getX() {
		return x;
	}

	public static void setX(int x) {
		Camera.x = x;
	}

	public static int getY() {
		return y;
	}

	public static void setY(int y) {
		Camera.y = y;
	}
		
}
