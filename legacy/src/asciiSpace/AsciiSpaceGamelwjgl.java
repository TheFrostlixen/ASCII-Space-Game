package asciiSpace;

import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import objLoader.ObjLoader;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import part.Part;

import camera.Camera;

/**
 * @author Matt F.
 * @author Matt P.
 * @author Cave Johnson
 * */

/*
 * General Notes: <expression> ? <if true>:<if false>
 */

/*
 * General Ideas: 1. Allow base to cast to any class that extends it. So that
 * ship could act as a planet for defense or offense
 */

public class AsciiSpaceGamelwjgl {

	// Array Of all entities
	public static ArrayList<Star> ent = new ArrayList<Star>();

	// Ints
	static final int screenWidth = 800;
	static final int screenHeight = 600;

	// Longs
	static long lstFrame = 0l;

	// bools
	public static volatile boolean running = true;
	public static boolean save = false;

	public static World w;
	
	public static Ship pl = null;
	public static long seed = 0l;
	
	public static boolean g0dm0de = false;

	// Custom classes
	Random r = new Random();
	// static player p;
	private static Camera cam;

	public static void main(String[] args) {
		AsciiSpaceGamelwjgl as = new AsciiSpaceGamelwjgl();
		running = true;
		as.init();
	}

	public void init() {
		// TODO load shit in
		System.out.println("Load shit here.");
		try {
			Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
			Display.setVSyncEnabled(true);
			Display.create();
			Display.setTitle("Ascii Space");
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		
			// init OpenGL here
			InitGL();
			w = new World();
			if (SaveLoad.load(new File("test.sav"), w) == 0)
			{
				System.out.println("n00b");
				pl = new Ship(World.MODEL_LOCATION_SHIP, 0f, 0f, 0f);
				w = new World(pl);
			} else
				w.initPlayer(pl);
			setUpCamera();
			getDelta();
			System.out.println("OpenGL Render() shit");
			try {
			while (!Display.isCloseRequested() && running) {
				float delta = getDelta();
				checkInput(delta);
				// render OpenGL here
				render();
				Display.update();
				if (save) {
//					SaveLoad.save(w);
					save = false;
				}
			}
		} finally { // must use or alt f4 or cmd q will freeze application
//			SaveLoad.save(w);
			cleanUp();
		}
	}
	
	public void generateSeed()
	{
		r.setSeed(Sys.getTime());
		seed = r.nextLong();
	}

	public void InitGL() {
		// TODO OPENGL INIT SHIT HERE
		System.out.println("OpenGL Init() shit");
		GL11.glEnable(GL11.GL_DEPTH_TEST); // enable z layering
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glEnable(GL11.GL_BLEND); // we like aplha
		GL11.glCullFace(GL11.GL_BACK); // makes it so we do not render the back
										// of objects
		GL11.glShadeModel(GL11.GL_SMOOTH); // smooth is better
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); // alpha
																			// anyone?
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glViewport(0, 0, screenWidth, screenHeight);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void render() {
		// TODO OPENGL RENDER SHIT HERE
		/*
		 * General OpenGL Notes that are useful OpenGL is a reverse state
		 * machine so you have to send farthest object for render 1st and
		 * closest last
		 * 
		 * If you have glScalef(...) then you need a glPushMatrix and
		 * glPopMatrix around that block or else the entire render is scaled
		 */
		Display.sync(60);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear everything
		cam.applyModelviewMatrix(true);
		w.pl.updateCords(cam);
		w.pl.draw();
		w.draw(g0dm0de);
		GL11.glFlush();
	}

	private static void cleanUp() {
		w.destroy();
		Display.destroy();
	}

	private static void checkInput(float delta) {
		cam.processMouse(.5f, 80, -80);
		g0dm0de = g0dm0de ^ Keyboard.isKeyDown(Keyboard.KEY_T);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_COMMA))
		{
			cam.setFov(181);
			cam.applyPerspectiveMatrix();
			cam.processKeyboard(delta, 0.3f, 0.3f, 0.3f);
		}
		else
		{
			cam.setFov(90);
			cam.applyPerspectiveMatrix();
			cam.processKeyboard(delta, 0.003f, 0.003f, 0.003f);
		}
		
		
		if (Mouse.isButtonDown(0))
			Mouse.setGrabbed(true);
		else if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			Mouse.setGrabbed(false);
	}

	private static void setUpCamera() {
		cam = new Camera((float) Display.getWidth()
				/ (float) Display.getHeight(), 0f, 0f, 0f, -10f, 180f, 0.0f);
		cam.setFov(90); // use 181 for loading screen animation
		cam.setzFar(1500f);
		cam.applyPerspectiveMatrix();
	}

	public void drawCenteredString(Graphics leaf, String str, int x, int y) {
		leaf.drawString(str, x - (leaf.getFontMetrics().stringWidth(str) / 2),
				y);
	}

	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public float getDelta() {
		long time = getTime();
		float delta = (float) (time - lstFrame);
		lstFrame = time;

		return delta;
	}

	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

}
