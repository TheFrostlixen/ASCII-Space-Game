package asciiSpace;

import java.util.Random;

public class Color {
	String sym = "c";
	static float blue[] = colorToFloat(57, 157, 216);
	
	public Color(){}
	
	public short[] colorToRGB(float r, float g, float b, short[] t) {
		t[0] = (short) (r / 255);
		t[1] = (short) (g / 255);
		t[2] = (short) (b / 255);
		System.out.println(t[0] + ":" + t[1] + ":" + t[2]);
		return t;
	}

	public static float[] colorToFloat(int r, int g, int b) {
		float t[] = new float[3];
		t[0] = ((float) r / 255);
		t[1] = ((float) g / 255);
		t[2] = ((float) b / 255);
		return t;
	}
	
	public static float[] randomColors()
	{
		float[] temp = new float[3];
		Random rnd = new Random();
		temp = colorToFloat(rnd.nextInt()%256,rnd.nextInt()%256,rnd.nextInt()%256);
		return temp;
	}
}
