package asciiSpace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import part.Chunk;

public class SaveLoad {

	public static final String MODEL_LOCATION = "rsc/sky.obj";
	public static final String MODEL_LOCATION_SHIP = "rsc/ship.obj";
	static Ship sh;

	public SaveLoad() {
	}

	public static void save(World w) {
		try {
			File f = new File("test.sav");
			// if(!f.exists())
			// f.createNewFile();
			PrintWriter pri = new PrintWriter(f);
			pri.println(w.pl.sym + " " + w.pl.x + " " + w.pl.y + " " + w.pl.z); // save player
			
			for(int i = 0; i< w.parts.size(); i++) // save parts (they're really chunks)
			{
				pri.println(w.parts.get(i));
			}
			pri.close();
		} catch (IOException e) {
			System.out
					.println("Aww damn, we failed to save. Player isn't going to be happy");
		}
	}

	public static byte load(File f, World w) {
		Scanner s;
		byte ret = 0;
		if(f.exists())
		{
			try {
				s = new Scanner(f);
				int partIndex = 0;
				while (s.hasNextLine())
				{
					ret = 1;
					String[] tmp = s.nextLine().split(" ");
					if (tmp[0].equals("v"))
						AsciiSpaceGamelwjgl.pl = new Ship( World.shipModelID, Float.parseFloat(tmp[1]), Float.parseFloat(tmp[2]), Float.parseFloat(tmp[3]));
					if(tmp[0].equals("p"))
					{
						w.loadChunk(partIndex,Float.parseFloat(tmp[1]), Float.parseFloat(tmp[2]), Float.parseFloat(tmp[3]),Long.parseLong(tmp[4]));
						partIndex++;
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("Well shit we couldn't load the sav file");
			}
		}
		return ret;
	}
}
