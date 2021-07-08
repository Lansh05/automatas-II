package analizador_lex_sin_entrega_1;

public class Mainn {

	public static void main(String[] args) 
	{
	     Lexico analizador = new Lexico("file.txt");
	     
	     if (!analizador.flag)
	     {
	    	for(int i = 0; i < analizador.VoF.size(); i++)
	    	{ 
				System.out.println( analizador.VoF.get(i) );
			}	    	 
	    	System.exit(0);
	     }
	 	if( analizador.VoF.get(0).equals("Sin errores lexicos"))
		{
			new Sintactico(analizador.tokenRC);
			new Tabla(analizador.tokenRC);
		}
	}
}
