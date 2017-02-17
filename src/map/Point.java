package map;

public class Point {

	private int coordX = 0;
	private int coordY = 0;
	
	public Point(int x, int y) {
		this.coordX = x;
		this.coordY = y;
	}
	
	public int getCoordX() {
		return this.coordX;
	}
	
	public int getCoordY() {
		return this.coordY;
	}
	
	public float getDistance(Point p) {
		double diffX = (double)this.coordX - (double)p.getCoordX();
		double diffY = (double)this.coordY - (double)p.getCoordY();
		return (float)Math.sqrt(diffX*diffX + diffY*diffY);
	}
	
}
