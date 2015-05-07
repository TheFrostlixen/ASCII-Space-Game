package objLoader;

import org.lwjgl.util.vector.Vector3f;

public class Face {
	public Vector3f vertIndicies = new Vector3f();
	public Vector3f normalsIndicies = new Vector3f();
	
	public Face(Vector3f in , Vector3f norm)
	{
		vertIndicies = in;
		normalsIndicies = norm;
	}

}
