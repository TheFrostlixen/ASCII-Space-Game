package asciiSpace;

import objLoader.ObjLoader;

import org.lwjgl.opengl.GL11;

public class Star extends Base
{
	int star = 1;
	String sym = "*";
	
	/**
	 * The variable mass determines the color and size of the star.
	 * the size is controlled by an equations and by scaling.
	 * the color is simple.
	 * NOTE: NOT USED YET
	 * */
	float mass = 0.0F;
	
	float[] color = new float[3];
	
	public Star(String loc)
	{
		ID = ObjLoader.glLoadModel(loc);
		this.color = Color.blue;
	}
	
	public Star(float x,float y,float z,int ID,int ship,int star,String sym) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.ID = ID;
		this.ship = ship;
		this.star = star;
		this.sym = sym;
		this.color = Color.blue;
	}
	
	public Star(String loc,float f,float g,float h)
	{
		ID = ObjLoader.glLoadModel(loc);
		this.x = f;
		this.y = g;
		this.z = h;
		this.color = Color.blue;
	}
	
	public Star(int loc,float f,float g,float h,float size)
	{
		System.out.println(size);
		ID = loc;
		this.x = f;
		this.y = g;
		this.z = h;
		this.mass = size;
		if(mass < 0.25)
			this.color = Color.white;
		if(mass < 0.50 && mass > 0.25 )
			this.color = Color.red;
		if(mass < 0.65 && mass > 0.50)
			this.color = Color.yellow;
		if(mass < 0.75 && mass > 0.65)
			this.color = Color.blue;
		if(mass < 0.85 && mass > 0.75)
			this.color = Color.blue;
		if(mass < 0.95 && mass > 0.85)
			this.color = Color.blue;
		if(mass < 1.0 && mass > 0.95)
			this.color = Color.bRed;
	}
	
	public void draw(Ship pl)
	{
		/*
		 * How to calc < 15000f > 1000f
		 * to get upper number take 0.1 and - from >= 15000f (34.9)
		 * then use cross mult to get x (429.770) (14999 = 34.9)
		 * 										   ( x   =  1 )
		 * then use slope fomula to get the divisor (60.9)
		 * 
		 * for <= 100f
		 * go from 100 = 5f and 1 = 100
		 * 
		 * */
//		float[] color = Color.randomColors(); // LSD
		
		//remember that we render backwards so that furthest objects are 1st to render
		GL11.glPushMatrix(); // save prev buffer so we dont mess it up
		GL11.glBegin(GL11.GL_MODELVIEW); // start drawing
		GL11.glColor3f(color[0], color[1], color[2]);
		GL11.glTranslatef(x, y, z);
		if(pl.distanceTo(this) <= 1500f && pl.distanceTo(this) > 1000f)
			GL11.glScalef((float)(3.5f - (1500-pl.distanceTo(this))*0.003), (float)(3.5f - (1500-pl.distanceTo(this))*0.003), (float)(3.5f - (1500-pl.distanceTo(this))*0.003));
//		if(pl.distanceTo(this) < 15000f && pl.distanceTo(this) > 1000f)
//			GL11.glScalef(pl.distanceTo(this)/(429.770f-((14999 - pl.distanceTo(this))/60.9f)),
//						  pl.distanceTo(this)/(429.770f-((14999 - pl.distanceTo(this))/60.9f)),
//						  pl.distanceTo(this)/(429.770f-((14999 - pl.distanceTo(this))/60.9f)));
		if(pl.distanceTo(this) <= 1000f && pl.distanceTo(this) > 10f)
			GL11.glScalef(1.5f, 1.5f, 1.5f);
//		if(pl.distanceTo(this) <= 10f) // this is cuz we go to solar scale when we get this close
//			GL11.glScalef(	(float)(100f/(Math.sqrt(pl.distanceTo(this)))),
//							(float)(100f/(Math.sqrt(pl.distanceTo(this)))),
//							(float)(100f/(Math.sqrt(pl.distanceTo(this)))) );
//		GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
		GL11.glCallList(ID); // render ship
		GL11.glEnd(); // end drawing
		GL11.glPopMatrix(); // retrive buffer
		
	}
	public Base toBase()
	{
		return new Base(x,y,z,ID,ship,star,"*");
	}
}
