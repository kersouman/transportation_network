package map;

public class Point 
{

	private final int EARTH_RADIUS = 6371000;
	
	private int coordX = 0;
	private int coordY = 0;
	
	public Point(int x, int y) 
	{
		this.coordX = x;
		this.coordY = y;
	}
	
	public Point(float lat, float lng) 
	{
		float latRad = (float)(lat*Math.PI/180.0);
		float lngRad = (float)(lng*Math.PI/180.0);
		
		this.coordX = (int)(((float)this.EARTH_RADIUS)*lngRad);
		this.coordY = (int)(((float)this.EARTH_RADIUS)
					*Math.log(Math.tan(Math.PI/4.0 + latRad/2.0)));	
	}
	
	public float getDistance(Point p) 
	{
		double diffX = (double)this.coordX - (double)p.getCoordX();
		double diffY = (double)this.coordY - (double)p.getCoordY();
		return (float)Math.sqrt(diffX*diffX + diffY*diffY);
	}
	
	public int getCoordX() 
	{
		return this.coordX;
	}
	
	public int getCoordY() 
	{
		return this.coordY;
	}
	
}
