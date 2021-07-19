package analizador;

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
			Tabla tabla_Sintactica = new Tabla(analizador.tokenRC);
			Intermedio_datos datos_Intermedio = new Intermedio_datos(analizador.tokenRC, tabla_Sintactica.valoresTab);
			
			new Intermedio(datos_Intermedio.tokens_intermedionoifinal);

//			Intermedio cod_Intermedio = new Intermedio(analizador.tokenRC);
		}
	}
}
