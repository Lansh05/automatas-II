package analizador_lex_sint;

public class Mainn {

	public static void main(String[] args) 
	{
		Lexico analizador = new Lexico("file1.txt");
		
		if (!analizador.flag)
		{
			System.out.println("Hubo errores L�xicos\n");
			for(int i = 0; i < analizador.VoF.size(); i++)
			{ 
				System.out.println( analizador.VoF.get(i) );
			}	    	 
			System.exit(0);
		}
		// Cuando no hay errores L�xicos permite avanzar y mostrar este mensaje
		System.out.println("Sin errores L�xicos!!");
		if( analizador.VoF.get(0).equals("Sin errores lexicos"))
		{
			new Sintactico(analizador.tokenRC);
		}
		// Cuando no hay errores Sint�cticos permite avanzar y mostrar este mensaje
		System.out.println("Sin errores Sint�cticos!!");
	}
}
