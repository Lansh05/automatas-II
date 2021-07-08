package analizador_lex_sint;

public class Mainn {

	public static void main(String[] args) 
	{
		Lexico analizador = new Lexico("file1.txt");
		
		if (!analizador.flag)
		{
			System.out.println("Hubo errores Léxicos\n");
			for(int i = 0; i < analizador.VoF.size(); i++)
			{ 
				System.out.println( analizador.VoF.get(i) );
			}	    	 
			System.exit(0);
		}
		// Cuando no hay errores Léxicos permite avanzar y mostrar este mensaje
		System.out.println("Sin errores Léxicos!!");
		if( analizador.VoF.get(0).equals("Sin errores lexicos"))
		{
			new Sintactico(analizador.tokenRC);
		}
		// Cuando no hay errores Sintácticos permite avanzar y mostrar este mensaje
		System.out.println("Sin errores Sintácticos!!");
	}
}
