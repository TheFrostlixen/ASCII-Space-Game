package part;

import java.util.ArrayList;
import java.util.Random;

import asciiSpace.Star;

public class Part {
	public static final int NUMSTARS = 75;
	static final int STARDIST = 2000;
	static Random r = new Random();
	public ArrayList<Star> s = new ArrayList<Star>();
	public boolean isLoaded = false;
	public float x = 0;
	public float y = 0;
	public float z = 0;
	public long seed = 0;
	
	public Part(int loc, float x1, float y1, float z1, long seed)
	{
		this.seed = seed;
		r.setSeed(seed);
		
		for (int i = 0; i < NUMSTARS; i++) {
			int rx = r.nextInt(6) + 1;
			int ry = r.nextInt(6) + 1;
			int rz = r.nextInt(6) + 1;
			float xS = rx == 1 || rx == 3 ? -1 : 1;
			float yS = ry == 5 || ry == 2 ? -1 : 1;
			float zS = rz == 4 || rz == 6 ? -1 : 1;
			float x = ((r.nextInt(STARDIST) + 1) * xS)+x1;
			float y = ((r.nextInt(STARDIST) + 1) * yS)+y1;
			float z = ((r.nextInt(STARDIST) + 1) * zS)+z1;
			float size = r.nextFloat();
			s.add(new Star(loc, x, y, z, size));
		}
		this.x = x1;
		this.y = y1;
		this.z = z1;
		isLoaded = true;
	}
	
	public String toString()
	{
		return "p "+x+" "+y+" "+z+" "+seed;
	}
}
