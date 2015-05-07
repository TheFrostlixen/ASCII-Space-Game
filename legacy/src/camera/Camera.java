package camera;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.RenderingHints.Key;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.glu.GLU;

import asciiSpace.AsciiSpaceGamelwjgl;

/**
 * A camera set in 3D perspective.
 */
public final class Camera {

	private float x = 0f;
	private float y = 0f;
	private float z = 0f;
	private float dx = 0f;
	private float dy = 0f;
	private float dz = 0f;
	private float MAXSPD = 5.5f;
	private float pitch = 0;
	private float yaw = 0;
	private float roll = 0;
	private float fov = 90;
	private float aspectRatio;
	private float zNear = 0.3f;
	private float zFar = 150000f;
	private boolean wombat = false;

	/**
	 * Creates a new camera with the given aspect ratio. It's located at [0 0 0]
	 * with the orientation [0 0 0]. It has a zNear of 0.3, a zFar of 100.0, and
	 * an fov of 90.
	 * 
	 * @param aspectRatio
	 *            the aspect ratio (width/height) of the camera
	 */
	public Camera(float aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	/**
	 * Creates a new camera with the given aspect ratio and location.
	 * 
	 * @param aspectRatio
	 *            the aspect ratio (width/height) of the camera
	 * @param x
	 *            the first location coordinate
	 * @param y
	 *            the second location coordinate
	 * @param z
	 *            the third location coordinate
	 */
	public Camera(float aspectRatio, double x, double y, double z) {
		this.aspectRatio = aspectRatio;
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;
	}

	/**
	 * Creates a new camera with the given aspect ratio and location.
	 * 
	 * @param aspectRatio
	 *            the aspect ratio (width/height) of the camera
	 * @param x
	 *            the first location coordinate
	 * @param y
	 *            the second location coordinate
	 * @param z
	 *            the third location coordinate
	 */
	public Camera(float aspectRatio, float x, float y, float z) {
		this.aspectRatio = aspectRatio;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Creates a new camera with the given aspect ratio, location, and
	 * orientation.
	 * 
	 * @param aspectRatio
	 *            the aspect ratio (width/height) of the camera
	 * @param x
	 *            the first location coordinate
	 * @param y
	 *            the second location coordinate
	 * @param z
	 *            the third location coordinate
	 * @param pitch
	 *            the pitch (rotation on the x-axis)
	 * @param yaw
	 *            the yaw (rotation on the y-axis)
	 * @param roll
	 *            the roll (rotation on the z-axis)
	 */
	public Camera(float aspectRatio, double x, double y, double z,
			double pitch, double yaw, double roll) {
		this.aspectRatio = aspectRatio;
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;
		this.pitch = (float) pitch;
		this.yaw = (float) yaw;
		this.roll = (float) roll;
	}

	/**
	 * Creates a new camera with the given aspect ratio, location, and
	 * orientation.
	 * 
	 * @param aspectRatio
	 *            the aspect ratio (width/height) of the camera
	 * @param x
	 *            the first location coordinate
	 * @param y
	 *            the second location coordinate
	 * @param z
	 *            the third location coordinate
	 * @param pitch
	 *            the pitch (rotation on the x-axis)
	 * @param yaw
	 *            the yaw (rotation on the y-axis)
	 * @param roll
	 *            the roll (rotation on the z-axis)
	 */
	public Camera(float aspectRatio, float x, float y, float z, float pitch,
			float yaw, float roll) {
		this.aspectRatio = aspectRatio;
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}

	/**
	 * Processes mouse input and converts it in to camera movement using the
	 * mouseSpeed value.
	 * 
	 * @param mouseSpeed
	 *            the speed (sensitity) of the mouse
	 * @param maxLookUp
	 *            the maximum angle at which you can look up
	 * @param maxLookDown
	 *            the maximum angle at which you can look down
	 */
	public void processMouse(float mouseSpeed, float maxLookUp,
			float maxLookDown) {
		if (!Mouse.isGrabbed())
			return;
		float mouseDX = Mouse.getDX() * mouseSpeed * 0.16f;
		float mouseDY = Mouse.getDY() * mouseSpeed * 0.16f;
		if (yaw + mouseDX >= 360) {
			yaw = yaw + mouseDX - 360;
		} else if (yaw + mouseDX < 0) {
			yaw = 360 - yaw + mouseDX;
		} else {
			yaw += mouseDX;
		}
		if (pitch - mouseDY >= maxLookDown && pitch - mouseDY <= maxLookUp) {
			pitch += -mouseDY;
		} else if (pitch - mouseDY < maxLookDown) {
			pitch = maxLookDown;
		} else if (pitch - mouseDY > maxLookUp) {
			pitch = maxLookUp;
		}
	}

	/**
	 * @param delta
	 *            the elapsed time since the last frame update in seconds
	 * @param speedX
	 *            the speed of the movement on the x-axis (normal = 0.003)
	 * @param speedY
	 *            the speed of the movement on the y-axis (normal = 0.003)
	 * @param speedZ
	 *            the speed of the movement on the z-axis (normal = 0.003)
	 */
	public void processKeyboard(float delta, float speedX, float speedY,
			float speedZ)
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
		wombat = Keyboard.isKeyDown(Keyboard.KEY_R);
		/*
		 * Good for after hyper space!
		 */
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
				// apply decay factor to dx
				if(dz > -speedZ*10 && dz < speedZ*10)
					dz = 0;
				else
					dz += (dz > 0) ? -speedZ*10 : speedZ*10;
			}
			
			// Left/Right motion
			if (keyLeft || keyRight)
			{
				if (keyLeft)
					dx += speedX * delta;
				if (keyRight)
					dx -= speedX * delta;
				dx = ((dx > MAXSPD) || (dx < -MAXSPD)) ? ((dx > 0) ? MAXSPD : -MAXSPD): dx;
			}
			else
			{
				// decay dz
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
			
			// get position and shit outta yo' accelerations
			procAccel();
		} else {
			System.out.println("wombat?!");
			this.setFov(75);
			this.applyPerspectiveMatrix();
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
	
	private void procAccel()
	{
		this.x += dx;
		this.y += dy;
		this.z += dz;
		System.out.println(dx+" "+dy+" "+dz);
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

	public void moveAlongAxis(float magnitude, float x, float y, float z)
	{
		this.x += x * magnitude;
		this.y += y * magnitude;
		this.z += z * magnitude;
		System.out.println(this.x + ", " + this.y + ", " + this.z);
	}

	public void setPosition(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void applyOrthographicMatrix()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-1, 1, -1, 1, zNear, zFar);
		glMatrixMode(GL_MODELVIEW);
	}

	public void applyPerspectiveMatrix()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(fov, aspectRatio, zNear, zFar);
		glMatrixMode(GL_MODELVIEW);
	}

	public void applyModelviewMatrix(boolean resetMatrix)
	{
		if (resetMatrix)
			glLoadIdentity();
		glRotatef(pitch, 1, 0, 0);
		glRotatef(yaw, 0, 1, 0);
		glRotatef(roll, 0, 0, 1);
		glTranslatef(-x, -y, -z);
	}

	public float getX()
	{
		return this.x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return this.y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getZ()
	{
		return this.z;
	}

	public void setZ(float z)
	{
		this.z = z;
	}

	public float getPitch()
	{
		return pitch;
	}

	public void setPitch(float pitch)
	{
		this.pitch = pitch;
	}

	public float getYaw()
	{
		return yaw;
	}

	public void setYaw(float yaw)
	{
		this.yaw = yaw;
	}

	public float getRoll()
	{
		return roll;
	}

	public void setRoll(float roll)
	{
		this.roll = roll;
	}

	public float getFov()
	{
		return fov;
	}

	public void setFov(float fov)
	{
		this.fov = fov;
	}

	public float getAspectRatio()
	{
		return aspectRatio;
	}

	public void setAspectRatio(float aspectRatio)
	{
		this.aspectRatio = aspectRatio;
	}

	public float getzNear()
	{
		return zNear;
	}

	public void setzNear(float zNear)
	{
		this.zNear = zNear;
	}

	public float getzFar()
	{
		return zFar;
	}

	public void setzFar(float zFar)
	{
		this.zFar = zFar;
	}
	
	public boolean getWombat()
	{
		return wombat;
	}

	@Override
	public String toString()
	{
		return "Camera [x=" + x + ", y=" + y + ", z=" + z + ", pitch=" + pitch
				+ ", yaw=" + yaw + ", roll=" + roll + ", fov=" + fov
				+ ", aspectRatio=" + aspectRatio + ", zNear=" + zNear
				+ ", zFar=" + zFar + "]";
	}

}