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
			Star sh = null;
			int rx = r.nextInt(6) + 1;
			int ry = r.nextInt(6) + 1;
			int rz = r.nextInt(6) + 1;
			float xS = rx == 1 || rx == 3 ? -1 : 1;
			float yS = ry == 5 || ry == 2 ? -1 : 1;
			float zS = rz == 4 || rz == 6 ? -1 : 1;
			float x = ((r.nextInt(STARDIST) + 1) * xS)+x1;
			float y = ((r.nextInt(STARDIST) + 1) * yS)+y1;
			float z = ((r.nextInt(STARDIST) + 1) * zS)+z1;
			sh = new Star(loc, x, y, z);
			
			for (Star b : s) {
				while (b != null && b.distanceTo(sh) < 20000f && b.distanceTo(sh) > 100000f) {
					rx = r.nextInt(6) + 1;
					ry = r.nextInt(6) + 1;
					rz = r.nextInt(6) + 1;
					xS = rx == 6 || rx == 4 ? -1 : 1;
					yS = ry == 2 || ry == 5 ? -1 : 1;
					zS = rz == 1 || rz == 3 ? -1 : 1;
					x = ((r.nextInt(10000) + 1) * xS) + x1;
					y = ((r.nextInt(10000) + 1) * yS) + y1;
					z = ((r.nextInt(10000) + 1) * zS) + z1;

					sh = new Star(loc, x, y, z);
				}

			}
			s.add(new Star(loc,x,y,z));
		}
		this.x = x1;
		this.y = y1;
		this.z = z1;
		s.add(new Star(loc,x,y,z));
		isLoaded = true;
	}
	
	public String toString()
	{
		return "Part at ("+x+","+y+","+z+")";
	}
}
