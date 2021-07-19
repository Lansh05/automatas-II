package analizador;

import java.util.ArrayList;

public class DatosSemanticosDubVar {
	public static boolean pruebas = true;

	public static void DubVariables(String token[],String[] tipos,String renglon[],String columna[])
	{
		ArrayList<Token> datosRep = new ArrayList<Token>();
		ArrayList<String> yaRep = new ArrayList<String>();
		int cont_g = 0;
		for (int i = 0; i < token.length; i++) 
		{
			int cont_j = 0, cont_i = 0;
			boolean entra = true;
			if(cont_g > 0)
			{
				for (int j2 = 0; j2 < yaRep.size(); j2++)
				{
					if(token[i].equals(yaRep.get(j2)))
						entra = false;
				}
			}
			for (int j = i +1; j < token.length; j++) 
			{
				if(entra)
				{
					if(token[i].equals(token[j])) 
					{
	//					System.out.println(token[i] + " == " + token[j]);
						cont_g++;
						cont_j++;
						yaRep.add(token[i]);
						
						if(cont_j > 0)
						{
							if(cont_i == 0) //para que también meta la primera declaración solo 1 vez por vuelta de i
							{
								datosRep.add(new Token(token[i],tipos[i],Integer.parseInt(renglon[i]),Integer.parseInt(columna[i])));
								cont_i++;
							}
							
							datosRep.add(new Token(token[j],tipos[j],Integer.parseInt(renglon[j]),Integer.parseInt(columna[j])));
						}
					}
				}
			}
		}
		int mayorN = 0;
		int blanco = 12;
		for (int i = 0; i < datosRep.size(); i++)
		{
				if(datosRep.get(i).getToken().length() > mayorN)
					mayorN = datosRep.get(i).getToken().length() + 7;
					
		}
		if(datosRep.size()>0) //entra solo si sí hay variables repetidas
		{
			pruebas = false;

			System.out.println("\nLas variables que se duplican son las siguientes...\n\n"
							+	guion( blanco + mayorN)
							+	"\n"
							+ 	"|tipo"		+	blancos("tipo", blanco)		
							+	"|nombre"	+ 	blancos("nombre", mayorN)
							+	"|"
							+	"\n" 
							+	guion( blanco + mayorN)
						 );
			for (int i = 0; i < datosRep.size(); i++)
			{
				System.out.println("|"+datosRep.get(i).getTipos() 			+ 		blancos(datosRep.get(i).getTipos(), blanco)						+ 
								   "|"+datosRep.get(i).getToken()		+ 		blancos(datosRep.get(i).getToken(), mayorN)						+
								   "|"									+
								   "ubicado en renglón \""+datosRep.get(i).getRenglon()		+
								   "\"" 								+
								   " y número de token \""+datosRep.get(i).getColumna()		+
								   "\""	
						);
			}		
			System.out.println(guion( blanco + mayorN));
		}
	}
	public static String blancos(String cadena, int cantidad) {
		
		String blancos = "";
		
		for (int i = cadena.length(); i < cantidad; i++) {
			blancos += " ";
		}
		
		return blancos;
	}
	public static String guion(int cantidad) {
		
		String blancos = "-";
		
		for (int i = 0; i < cantidad; i++) {
			blancos += "-";
		}
		
		return blancos+="--";
	}
}
