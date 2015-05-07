package part;

import java.util.Random;

import asciiSpace.Ship;
import asciiSpace.World;

public class Chunk {
	public static final int numPart = 125;
	public static Part[] p = new Part[numPart];
	Random r = new Random();
	final int OFFSETX = 4000;
	final int OFFSETY = 4000;
	final int OFFSETZ = 4000;
	public static final int PARTSIZE = 2000;
	
	public Chunk(){}
	
	public Chunk(int x, int y, int z , World w)
	{
		for(int k = 0; k < 5; k++)
		{
			for(int j = 0; j < 5; j++)
			{
				for(int i = 0; i< 5; i++)
				{
					p[i+j*5+k*25] = new Part(World.starModelID,(i-2)*PARTSIZE,(j-2)*PARTSIZE,(k-2)*PARTSIZE,r.nextLong());
					w.parts.add(p[i].toString());
				}
			}
		}
	}
	
	/**
	 * Never forget.
	 * */
	float notUgly(float threeTime)
	{
		return (float)((int)threeTime/PARTSIZE)*PARTSIZE;
	}
	
	public void update(int dir, Ship pl, World w)
	{
		//int direction 1 = x 2 = y 3 = z
		if(dir == 1)
		{
			float[][] placementY = new float[5][5];
			float[][] placementZ = new float[5][5];
			for(int i = 0; i < 125; i+=5)
			{
				placementY[(i/5)%5][i/25] = p[i].y;
				placementZ[(i/5)%5][i/25] = p[i].z;
			}
			// x neg
			for (int i = 0; i < 25; i++)
			{
				swap( (i*5)+4, (i*5)+3 );
				swap( (i*5)+3, (i*5)+2 );
				swap( (i*5)+2, (i*5)+1 );
				swap( (i*5)+1, i*5 );
			}
			for(int i = 0; i < 125; i+=5)
			{
				p[i] = new Part(World.starModelID,notUgly(pl.getX())-OFFSETX,notUgly(pl.getY())-placementY[(i/5)%5][i/25],notUgly(pl.getZ())-placementZ[(i/5)%5][i/25],r.nextLong());
				w.parts.add(p[i].toString());
			}
		}
		else if(dir == 2)
		{
			float[][] placementX = new float[5][5];
			float[][] placementZ = new float[5][5];
			
			int a = 0, b= 0;
			for(int j = 20; j < 124; j+=25)
			{
				for(int i = 0; i < 5; i++)
				{
					System.out.println(a+" "+b);
					placementX[a][b] = p[j + i].x;
					placementZ[a][b] = p[j + i].z;
					b++;
				}
				a++;
				b = 0;
			}
			a = 0; b= 0;
			
			
			// y negative
			for(int i = 0; i < 5; i++)
			{
				swap( i,i+5 );
				swap( i+25,i+30 );
				swap( i+50, i+55 );
				swap( i+75, i+80 );
				swap( i+100, i+105 );
			}
			for(int i = 0; i < 5; i++)
			{
				swap( i+5,i+10 );
				swap( i+30,i+35 );
				swap( i+55, i+60 );
				swap( i+80, i+85 );
				swap( i+105, i+110 );
			}
			for(int i = 0; i < 5; i++)
			{
				swap( i+10,i+15 );
				swap( i+35,i+40 );
				swap( i+60, i+65 );
				swap( i+85, i+90 );
				swap( i+110, i+115 );
			}
			for(int i = 0; i < 5; i++)
			{
				swap( i+15,i+20 );
				swap( i+40,i+45 );
				swap( i+65, i+70 );
				swap( i+90, i+95 );
				swap( i+115, i+120 );
			}

			for(int j = 20; j < 124; j+=25)
			{
				for(int i = 0; i < 5; i++)
				{
					p[i+j] = new Part(World.starModelID,notUgly(pl.getX())-placementX[a][b],notUgly(pl.getY())-OFFSETY,notUgly(pl.getZ())-placementZ[a][b],r.nextLong());
					w.parts.add(p[i].toString());
					b++;
				}
				a++; b= 0;
			}
		}
		else if(dir == 3)
		{
			float[][] placementX = new float[5][5];
			float[][] placementY = new float[5][5];
			
			for(int i = 0; i < 25; i++)
			{
				placementX[i%5][i/5] = p[i].x;
				placementY[i%5][i/5] = p[i].y;
			}
			
			// z negative
			for (int i = 0; i < 25; i++)
			{
				swap(i+75, i+100);
				swap(i+50, i+75);
				swap(i+25, i+50);
				swap(i, i+25);
			}
			for(int i = 0; i < 25; i++)
			{
				p[i] = new Part(World.starModelID,notUgly(pl.getX())-placementX[i%5][i/5],notUgly(pl.getY())-placementY[i%5][i/5],notUgly(pl.getZ())-OFFSETZ,r.nextLong());
				w.parts.add(p[i].toString());
			}
		}
		else if(dir == -1)
		{
			// x positive
			
			float[][] placementY = new float[5][5];
			float[][] placementZ = new float[5][5];
			for(int i = 4; i < 128; i+=5)
			{
				placementY[((i-4)/5)%5][i/25] = p[i].y;
				placementZ[((i-4)/5)%5][i/25] = p[i].z;
			}
			
			for (int i = 0; i < 25; i++)
			{
				swap( (i*5)+1, i*5 );
				swap( (i*5)+2, (i*5)+1 );
				swap( (i*5)+3, (i*5)+2 );
				swap( (i*5)+4, (i*5)+3 );
			}
			for(int i = 4; i < 128; i+=5)
			{
				p[i] = new Part(World.starModelID,notUgly(pl.getX())+OFFSETX,notUgly(pl.getY())-placementY[((i-4)/5)%5][i/25],notUgly(pl.getZ())-placementZ[((i-4)/5)%5][i/25],r.nextLong());
				w.parts.add(p[i].toString());
			}
		}
		else if(dir == -2)
		{
			float[][] placementX = new float[5][5];
			float[][] placementZ = new float[5][5];
			
			int a = 0, b= 0;
			for(int k = 0; k < 103; k+=25)
			{
				for(int l = 0; l < 5; l++)
				{
					placementX[a][b] = p[k + l].x;
					placementZ[a][b] = p[k + l].z;
					b++;
				}
				a++;
				b = 0;
			}
			a = 0; b= 0;
			
			// y positive
			for(int i = 0; i < 5; i++)
			{
				swap( i+15,i+20 );
				swap( i+40,i+45 );
				swap( i+65, i+70 );
				swap( i+90, i+95 );
				swap( i+115, i+120 );
			}
			for(int i = 0; i < 5; i++)
			{
				swap( i+10,i+15 );
				swap( i+35,i+40 );
				swap( i+60, i+65 );
				swap( i+85, i+90 );
				swap( i+110, i+115 );
			}
			for(int i = 0; i < 5; i++)
			{
				swap( i+5,i+10 );
				swap( i+30,i+35 );
				swap( i+55, i+60 );
				swap( i+80, i+85 );
				swap( i+105, i+110 );
			}
			for(int i = 0; i < 5; i++)
			{
				swap( i,i+5 );
				swap( i+25,i+30 );
				swap( i+50, i+55 );
				swap( i+75, i+80 );
				swap( i+100, i+105 );
			}
			
			for(int j = 0; j < 103; j+=25)
			{
				for(int i = 0; i < 5; i++)
				{
					p[i+j] = new Part(World.starModelID,notUgly(pl.getX())-placementX[a][b],notUgly(pl.getY())+OFFSETY,notUgly(pl.getZ())-placementZ[a][b],r.nextLong());
					b++;
					w.parts.add(p[i].toString());
				}
				a++; b= 0;
			}
		}
		else if(dir == -3)
		{
			float[][] placementX = new float[5][5];
			float[][] placementY = new float[5][5];
			
			for(int i = 100; i < 125; i++)
			{
				placementX[(i-100)%5][(i-100)/5] = p[i].x;
				placementY[(i-100)%5][(i-100)/5] = p[i].y;
			}
			
			// z positive
			for (int i = 0; i < 25; i++)
			{
				swap(i, i+25);
				swap(i+25, i+50);
				swap(i+50, i+75);
				swap(i+75, i+100);
			}
			for(int i = 100; i < 125; i++)
			{
				p[i] = new Part(World.starModelID,notUgly(pl.getX())-placementX[(i-100)%5][(i-100)/5],notUgly(pl.getY())-placementY[(i-100)%5][(i-100)/5],notUgly(pl.getZ())+OFFSETZ,r.nextLong());
				w.parts.add(p[i].toString());
			}
		}
	}
	
	private void swap(int indexA, int indexB)
	{
		Part temp = p[indexA];
		p[indexA] = p[indexB];
		p[indexB] = temp;
		
	}
	
	public void draw(Ship pl)
	{
		for(int i = 0; i< numPart; i++)
		{
			if(p[i].isLoaded)
			{
				for(int j = 0; j< p[i].s.size(); j++)
				{
					if(pl.distanceTo(p[i].s.get(j)) < 1500f)
						p[i].s.get(j).draw(pl);
				}
			}
		}
	}
}
