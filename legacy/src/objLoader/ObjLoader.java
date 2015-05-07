package objLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class ObjLoader
{
	private static Model objLoadModel(File f) throws IOException, FileNotFoundException
	{
		Scanner s = new Scanner(f);
		Model m = new Model();
		while(s.hasNextLine())
		{
			String line = s.nextLine();
			if(line.startsWith("v "))
			{
				String[] spl = line.split(" ");
				float x = Float.valueOf(spl[1]);
				float y = Float.valueOf(spl[2]);
				float z = Float.valueOf(spl[3]);
				m.vert.add(new Vector3f(x,y,z));
			}
			else if(line.startsWith("vn "))
			{
				String[] spl = line.split(" ");
				float x = Float.valueOf(spl[1]);
				float y = Float.valueOf(spl[2]);
				float z = Float.valueOf(spl[3]);
				m.normals.add(new Vector3f(x,y,z));
			}
			else if(line.startsWith("f "))
			{
				String[] spl = line.split(" ");
				Vector3f vertiIndi = new Vector3f(Float.valueOf(spl[1].split("/")[0]),
						Float.valueOf(spl[2].split("/")[0]),
						Float.valueOf(spl[3].split("/")[0]));
				Vector3f normalin = new Vector3f(Float.valueOf(spl[1].split("/")[2]),
						Float.valueOf(spl[2].split("/")[2]),
						Float.valueOf(spl[3].split("/")[2]));
				m.face.add(new Face(vertiIndi,normalin));
			}
		}
		s.close();
		return m;
	}
	
	public static int glLoadModel(String loc) {
		// To load an obj model use this
		int dsplist = GL11.glGenLists(1);
		GL11.glNewList(dsplist, GL11.GL_COMPILE);
		{
			Model m = null;
			try {
				m = ObjLoader.objLoadModel(new File(loc));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Display.destroy();
				System.exit(1);
			} catch (IOException e) {
				e.printStackTrace();
				Display.destroy();
				System.exit(1);
			}
			GL11.glBegin(GL11.GL_TRIANGLES);
			for (Face f : m.face) {
				Vector3f n1 = m.normals.get((int) f.normalsIndicies.x - 1);
				GL11.glNormal3f(n1.x, n1.y, n1.z);
				Vector3f v1 = m.vert.get((int) f.vertIndicies.x - 1);
				GL11.glVertex3f(v1.x, v1.y, v1.z);
				Vector3f n2 = m.normals.get((int) f.normalsIndicies.y - 1);
				GL11.glNormal3f(n2.x, n2.y, n2.z);
				Vector3f v2 = m.vert.get((int) f.vertIndicies.y - 1);
				GL11.glVertex3f(v2.x, v2.y, v2.z);
				Vector3f n3 = m.normals.get((int) f.normalsIndicies.z - 1);
				GL11.glNormal3f(n3.x, n3.y, n3.z);
				Vector3f v3 = m.vert.get((int) f.vertIndicies.z - 1);
				GL11.glVertex3f(v3.x, v3.y, v3.z);
			}
			GL11.glEnd();
		}
		GL11.glEndList();
		return dsplist;
	}
	
	
}
