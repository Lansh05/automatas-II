package analizador;

public class Obj_Intermedio 
{
	private int pos;
	private String token;
	
	public Obj_Intermedio(int pos,String token)
	{
		this.pos = pos;
		this.token = token;
	}

	public int getPos()
	{
		return pos;
	}

	public String getToken()
	{
		return token;
	}
	
}
