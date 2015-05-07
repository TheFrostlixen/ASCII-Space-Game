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
import asciiSpace.Ship;

/**
 * A camera set in 3D perspective.
 */
public final class Camera {

	public float x = 0f;
	public float y = 0f;
	public float z = 0f;
	public float pitch = 0;
	public float yaw = 0;
	float prevSYaw = 0f;
	public float roll = 0;
	public float fov = 90;
	public float aspectRatio;
	public float zNear = 0.00001f;
	public float zFar = 150000f;
	public boolean wombat = false;

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

	public void processMouse(float mouseSpeed, float maxLookUp,
			float maxLookDown) {
		if (!Mouse.isGrabbed())
			return;
		float mouseDX = Mouse.getDX() * mouseSpeed * 0.16f;
		float mouseDY = Mouse.getDY() * mouseSpeed * 0.16f;
		yaw = (yaw + mouseDX) % 360;
		if (pitch - mouseDY >= maxLookDown && pitch - mouseDY <= maxLookUp) {
			pitch += -mouseDY;
		} else if (pitch - mouseDY < maxLookDown) {
			pitch = maxLookDown;
		} else if (pitch - mouseDY > maxLookUp) {
			pitch = maxLookUp;
		}
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
	
	public void updateCords(Ship pl)
	{
		x = pl.getX();
		y = pl.getY() + 0.15f;
		z = pl.getZ();
		this.z -= (0.65f) * (float)Math.cos( Math.toRadians(pl.yaw) );
		this.x += (0.65f) * (float)Math.sin( Math.toRadians(pl.yaw) );
	}
}