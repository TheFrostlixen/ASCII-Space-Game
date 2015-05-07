package asciiSpace;

import java.util.ArrayList;

import objLoader.ObjLoader;

import org.lwjgl.opengl.GL11;

import part.Chunk;
import part.Part;

public class World {
	// Strings
	public static final String MODEL_LOCATION = "rsc/ttstar.obj";
	public static final String MODEL_LOCATION_SHIP = "rsc/ship.obj";

	public static int shipModelID = 0;
	public static int starModelID = 0;
	public static final int PARTSIZE = 2000;
	public Ship pl;
	private int prevX = 0, prevY = 0, prevZ = 0; 
	public Chunk c = null;
	public ArrayList<String> parts = new ArrayList<String>();
	
	public World() {initModels();}
	
	public World(Ship pl)
	{
		this.pl = pl;
		System.out.println("init world");
		prevX = (int)pl.getX()/PARTSIZE;
		prevY = (int)pl.getY()/PARTSIZE;
		prevZ = (int)pl.getZ()/PARTSIZE;
		if(c == null)
			c = new Chunk((int)pl.getX()/PARTSIZE,(int)pl.getY()/PARTSIZE,(int)pl.getZ()/PARTSIZE , this);
	}
	
	public void loadChunk(int index,float x, float y, float z, long seed)
	{
		c = new Chunk();
		Chunk.p[index] = new Part(starModelID,x,y,z,seed);
	}
	
	public void destroy()
	{
		pl.destroy();
		GL11.glDeleteLists(shipModelID, 1);
		GL11.glDeleteLists(starModelID, 1);
	}
	
	public void initPlayer(Ship pl)
	{
		this.pl = pl;
	}
	
	public void addShip(Ship s)
	{
		// TODO add other ships and whatnots
	}
	
	public void initModels()
	{
		shipModelID = ObjLoader.glLoadModel(MODEL_LOCATION_SHIP);
		starModelID = ObjLoader.glLoadModel(MODEL_LOCATION);
	}
	
	public void draw()
	{
		//int direction 1 = x 2 = y 3 = z
		if(prevX != (int)pl.getX()/PARTSIZE)
		{
			System.out.println("update X");
			c.update(1 * (((int)prevX-(pl.getX()/PARTSIZE) > 0)?1:-1), pl ,this);
			prevX = (int)pl.getX()/PARTSIZE;
		}
		if(prevY != (int)pl.getY()/PARTSIZE)
		{
			System.out.println("update Y");
			c.update(2 * ((prevY-((int)pl.getY()/PARTSIZE) > 0)?1:-1), pl ,this);
			prevY = (int)pl.getY()/PARTSIZE;
		}
		if(prevZ != (int)pl.getZ()/PARTSIZE)
		{
			System.out.println("update Z");
			c.update(3 * ((prevZ-((int)pl.getZ()/PARTSIZE) > 0)?1:-1), pl ,this);
			prevZ = (int)pl.getZ()/PARTSIZE;
		}
		c.draw(pl);
	}
	
	public void setPlayer(Ship p)
	{
		pl = p;
	}
	public Ship getPlayer()
	{
		return pl;
	}
}
