package part;

public class GenPart {

	public GenPart(){}
	
	public Part[] genPart(int num, int loc)
	{
		Part[] p = new Part[num];
		for (int i = 0 ; i< num; i++)
		{
			//p[i] = new Part(loc);
		}
		return p;
	}
	
	public Part genPart(int loc, float x , float y, float z)
	{
		return new Part(loc,x,y,z,0);
	}
	
}
