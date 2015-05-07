package asciiSpace;

import static org.lwjgl.opengl.GL11.glRotatef;
import objLoader.ObjLoader;
import asciiSpace.TextureLoader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/*
 * IDEAS: 
 * 	  - energy bar that decreases based on what you're doing
 * 		(eg: speed, hyperspace, shields, shooting)
 * 	  - if shields are up, you CANNOT SHOOT.
 * */

public class Ship extends Base {
	
	float pitch = 0f;
	float yaw = 0f;
	float roll = 0f;
	
	int ship = 1;
	String sym = "v";
	public int target = 0;
	
	public Ship(String loc) {
		ID = ObjLoader.glLoadModel(loc);
		target= ObjLoader.glLoadModel("rsc/crosshair.obj");
		System.out.println(target);
	}
	
	public Ship(float x,float y,float z,int ID,int ship,int star,String sym) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.ID = ID;
		this.ship = ship;
		this.star = star;
		this.sym = sym;
		target= ObjLoader.glLoadModel("rsc/crosshair.obj");
		System.out.println(target);
	}
	
	public Ship(String loc,float f,float g,float h)
	{
		ID = ObjLoader.glLoadModel(loc);
		this.x = f;
		this.y = g;
		this.z = h;
		target= ObjLoader.glLoadModel("rsc/crosshair.obj");
		System.out.println(target);
	}
	
	public Ship(int loc,float f,float g,float h)
	{
		ID = loc;
		this.x = f;
		this.y = g;
		this.z = h;
		target= ObjLoader.glLoadModel("rsc/crosshair.obj");
		System.out.println(target);
	}

	public void draw()
	{
		if(wombat)
		{
			/*GL11.glPushMatrix(); // save prev buffer so we dont mess 
			GL11.glBegin(GL11.GL_QUADS); // start drawing
			GL11.glVertex2f(370,270);
			GL11.glVertex2f(430,270);
			GL11.glVertex2f(430,330);
			GL11.glVertex2f(370,330);
			GL11.glEnd();
			GL11.glPopMatrix(); // retrive buffer
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,target);*/
			// remember that we render backwards so that furthest objects are 1st to render
			GL11.glPushMatrix(); // save prev buffer so we dont mess it up
			GL11.glBegin(GL11.GL_MODELVIEW); // start drawing
			GL11.glColor3f(1f, 0f, 0f); // chose grey for ship
			GL11.glTranslatef(x, y - 0.15f, z);
			glRotatef(pitch, 1, 0, 0);
			glRotatef(yaw, 0, 1, 0);
			glRotatef(roll, 0, 0, 1);
			GL11.glScalef(1.2f, 1.2f, 1.2f);
			GL11.glCallList(target); // render ship
			GL11.glEnd(); // end drawing
			GL11.glPopMatrix(); // retrive buffer
		}
		else
		{
			// remember that we render backwards so that furthest objects are 1st to render
			GL11.glPushMatrix(); // save prev buffer so we dont mess it up
			GL11.glBegin(GL11.GL_MODELVIEW); // start drawing
			GL11.glColor3f(0.3f, 0.3f, 0.3f); // chose grey for ship
			GL11.glTranslatef(x, y - 0.15f, 0.65f + z);
			glRotatef(pitch, 1, 0, 0);
			glRotatef(yaw, 0, 1, 0);
			glRotatef(roll, 0, 0, 1);
			GL11.glScalef(0.01f, 0.01f, 0.01f);
			GL11.glCallList(ID); // render ship
			GL11.glEnd(); // end drawing
			GL11.glPopMatrix(); // retrive buffer
		}
	}
	
	public Base toBase()
	{
		return new Base(x,y,z,ID,ship,star,"v");
	}
	
	public void destroy()
	{
		GL11.glDeleteLists(target,1);
	}
}
