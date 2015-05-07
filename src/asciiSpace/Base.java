package asciiSpace;

import org.lwjgl.opengl.GL11;

import camera.Camera;

public class Base
{
	float x = 0;
	float y = 0;
	float z = 0;
	float yaw = 0f;
	int ID = 0;
	int ship = 0;
	int star = 0;
	String sym = "b";
	Color c = new Color();
	boolean wombat = false;
	// TODO Add shit
	public Base() {}
	
	public Base(float x,float y,float z,int ID,int ship,int star, String sym) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.ID = ID;
		this.ship = ship;
		this.star = star;
		this.sym = sym;
	}

	public float getX() 
	{
		return x;
	}
	public float getY() 
	{
		return y;
	}
	public float getZ() 
	{
		return z;
	}
	public void setX(float q) 
	{
		x = q;
	}
	public void setY(float q) 
	{
		y = q;
	}
	public void setZ(float q) 
	{
		z = q;
	}
	public void setId(int i)
	{
		ID = i;
	}
	public int getId()
	{
		return ID;
	}
	
	public float distanceTo(Base a)
	{
		return (float) Math.sqrt((Math.pow(this.x-a.x, 2)+Math.pow(this.y-a.y, 2)+Math.pow(this.z-a.z, 2)));
	}
	
	public void draw(){}
	
	public void updateCords(Camera cam){
		x = cam.getX();
		y = cam.getY();
		z = cam.getZ();
		wombat = cam.getWombat();
		yaw = cam.getYaw();
	}
	public void destroy(){
		GL11.glDeleteLists(ID, 1);
	}
	
	public Base toBase() {
		return this;
	}
	public Ship toShip() {
		if(ship < 1)
			return (Ship)this;
		return new Ship(x,y,z,ID,ship,star,"v");
	}
	public Star toStar() {
		if(star < 1)
			return (Star)this;
		return new Star(x,y,z,ID,ship,star,"*");
	}
}