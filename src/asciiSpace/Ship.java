package asciiSpace;

import static org.lwjgl.opengl.GL11.glRotatef;

import javax.print.attribute.standard.MediaSize.Engineering;

import objLoader.ObjLoader;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import camera.Camera;

/*
 * IDEAS: 
 * 	  - energy bar that decreases based on what you're doing
 * 		(eg: speed, hyperspace, shields, shooting)
 * 	  - if shields are up, you CANNOT SHOOT.
 * */

public class Ship extends Base {
	
	public float pitch = 0f;
	public float yaw = 0f;
	public float prevYaw = 0f;
	public float roll = 0f;
	public float dx = 0f;
	public float dy = 0f;
	public float dz = 0f;
	private float MAXSPD = 5f;
	int ship = 1;
	String sym = "v";
	public int target = 0;
	private float energy = 100f;
	
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
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glPushMatrix(); // save prev buffer so we dont mess it up
			GL11.glBegin(GL11.GL_MODELVIEW); // start drawing
			GL11.glColor3f(1f, 0f, 0f); // chose red for target
			GL11.glScalef(1.2f, 1.2f, 1.2f);
			GL11.glCallList(target); // render ship
			GL11.glEnd(); // end drawing
			GL11.glPopMatrix(); // retrive buffer
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
		else
		{
			// remember that we render backwards so that furthest objects are 1st to render
			GL11.glPushMatrix(); // save prev buffer so we dont mess it up
			GL11.glBegin(GL11.GL_MODELVIEW); // start drawing
			GL11.glColor3f(0.3f, 0.3f, 0.3f); // chose grey for ship
			GL11.glTranslatef(x, y, z);
			glRotatef(-yaw, 0, 1, 0);
			glRotatef(pitch, 1, 0, 0);
			glRotatef(roll, 0, 0, 1);
			GL11.glScalef(0.01f, 0.01f, 0.01f);
			GL11.glCallList(ID); // render ship
			GL11.glEndList(); // end drawing
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
	
	public void moveFromLook(float dx, float dy, float dz)
	{
		float nX = this.x;
		float nY = this.y;
		float nZ = this.z;

		float hypotenuseX = dx;
		float adjacentX = hypotenuseX
				* (float) Math.cos(Math.toRadians(yaw - 90));
		float oppositeX = (float) Math.sin(Math.toRadians(yaw - 90))
				* hypotenuseX;
		nZ += adjacentX;
		nX -= oppositeX;

		nY += dy;

		float hypotenuseZ = dz;
		float adjacentZ = hypotenuseZ * (float) Math.cos(Math.toRadians(yaw));
		float oppositeZ = (float) Math.sin(Math.toRadians(yaw)) * hypotenuseZ;
		nZ += adjacentZ;
		nX -= oppositeZ;

		this.x = nX;
		this.y = nY;
		this.z = nZ;
	}
	
	public void reverseMoveFromLook (float dz)
	{
		//neg z is 0 deg
		this.z += (dz) * (float)Math.cos( Math.toRadians(yaw) );
		this.x -= (dz) * (float)Math.sin( Math.toRadians(yaw) );
	}
	
	public void processKeyboard(float delta, float speedX, float speedY,
			float speedZ, Camera cam)
	{
		boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_UP)
				|| Keyboard.isKeyDown(Keyboard.KEY_W);
		boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_DOWN)
				|| Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_LEFT)
				|| Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_RIGHT)
				|| Keyboard.isKeyDown(Keyboard.KEY_D);
		boolean flyUp = Keyboard.isKeyDown(Keyboard.KEY_Q);
		boolean flyDown = Keyboard.isKeyDown(Keyboard.KEY_E);
		boolean save = Keyboard.isKeyDown(Keyboard.KEY_P);
		boolean quit = Keyboard.isKeyDown(Keyboard.KEY_0);
		boolean decel = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		boolean coast = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
		boolean rm = Keyboard.isKeyDown(Keyboard.KEY_Z);
		boolean rp = Keyboard.isKeyDown(Keyboard.KEY_X);
		/*
		 * Good for after hyper space!
		 */
		if(rm){
			cam.roll --;
		}
		if(rp){
			cam.roll ++;
		}
		
		if(cam.roll % 360 != 0 && !rm && !rp){
			float angle = cam.roll % 360;
			if(angle >= 180 || angle <= -180){
				cam.roll = cam.roll > 0 ? cam.roll+1 : cam.roll-1;
			}
//			cam.roll = cam.roll > 0 ? cam.roll-1 : cam.roll+1;
		}
		if(coast && ! decel)
		{
			speedX = speedX/100;
			speedY = speedY/100;
			speedZ = speedZ/100;
		}
		
		if (decel && !keyDown && !keyRight && !keyLeft && !keyUp && !flyUp && !flyDown
				|| decel && wombat)
		{
			speedX = speedX*50;
			speedY = speedY*50;
			speedZ = speedZ*50;
		}
		
		if(!wombat)
		{
			// Forward/Backwards motion
			if (keyUp || keyDown)
			{
				if (keyUp)
					dz += speedZ * delta;
				if (keyDown)
					dz -= speedZ * delta;
				dz = ((dz > MAXSPD) || (dz < -MAXSPD)) ? ((dz > 0) ? MAXSPD : -MAXSPD): dz;
			}
			else
			{
				// apply decay factor to dz
				if(dz > -speedZ*10 && dz < speedZ*10)
					dz = 0;
				else
					dz += (dz > 0) ? -speedZ*10 : speedZ*10;
			}
			
			// Left/Right motion (now used for changing the ship's yaw)
			if (keyLeft || keyRight)
			{
				if (keyLeft)
					dx -= speedX * delta / 10;
				if (keyRight)
					dx += speedX * delta / 10;
				dx = ((dx > MAXSPD-4.5f) || (dx < (-MAXSPD)+4.5f)) ? ((dx > 0) ? MAXSPD-4.5f : (-MAXSPD)+4.5f): dx;
			}
			else
			{
				// decay dx
				if(dx > -speedX*10 && dx < speedX*10)
					dx = 0;
				else
					dx += (dx > 0) ? -speedX*10 : speedX*10;
			}
			
			// Up/Down motion
			if (flyUp || flyDown)
			{
				if (flyUp)
					dy += speedY * delta;
				if (flyDown)
					dy -= speedY * delta;
				dy = ((dy > MAXSPD) || (dy < -MAXSPD)) ? ((dy > 0) ? MAXSPD : -MAXSPD): dy;
			}
			else
			{
				// decay dy
				if(dy > -speedY*10 && dy < speedY*10)
					dy = 0;
				else
					dy += (dy > 0) ? -speedY*10 : speedY*10;
			}
			
			prevYaw = yaw;
			yaw = (yaw + dx) % 360;
			cam.yaw = (cam.yaw + dx) % 360;
			energy -= dz *10 + dx*10;
			reverseMoveFromLook(dz);
			this.y += dy;
		} else {
			if (keyUp && keyRight && !keyLeft && !keyDown) {
				moveFromLook(speedX * delta, 0, -speedZ * delta);
			}
			if (keyUp && keyLeft && !keyRight && !keyDown) {
				moveFromLook(-speedX * delta, 0, -speedZ * delta);
			}
			if (keyDown && keyLeft && !keyRight && !keyUp) {
				moveFromLook(-speedX * delta, 0, speedZ * delta);
			}
			if (keyDown && keyRight && !keyLeft && !keyUp) {
				moveFromLook(speedX * delta, 0, speedZ * delta);
			}
			if (keyUp && !keyLeft && !keyRight && !keyDown) {
				moveFromLook(0, 0, -speedZ * delta);
			}
			if (keyDown && !keyUp && !keyLeft && !keyRight) {
				moveFromLook(0, 0, speedZ * delta);
			}
			if (keyLeft && !keyRight && !keyUp && !keyDown) {
				moveFromLook(-speedX * delta, 0, 0);
			}
			if (keyRight && !keyLeft && !keyUp && !keyDown) {
				moveFromLook(speedX * delta, 0, 0);
			}

		}
		if(save)
			AsciiSpaceGamelwjgl.save = true;
		if(quit)
			AsciiSpaceGamelwjgl.running = false;
	}

	public float getYaw() {
		return yaw;
	}
	
	public float getPrevYaw(){
		return prevYaw;
	}
}
