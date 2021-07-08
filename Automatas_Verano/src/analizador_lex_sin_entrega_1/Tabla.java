package analizador_lex_sin_entrega_1;

import java.util.ArrayList;

public class Tabla 
{
	final int els = 0, ifs = 1, then = 2, print = 3, begin = 4, end = 5 , eq = 6, coma = 7,
			  menor = 8, mayor = 9, parizq = 10, parder = 11, ex = 12, mas = 13, menos = 14, 
			  div = 15, mult = 16, d2eq = 17, meneq = 18, mayeq = 19, diff = 20, verdad = 21,
			  falso = 22, booleano = 23, entero = 24, num = 50, id = 51;
	
	public static boolean pruebas = true, pruebaVarRep = true;
	
	ArrayList<Token> tokenRC, tokenAsignado = new ArrayList<Token>();;
	ArrayList<ValoresTabla> valoresTab = new ArrayList<ValoresTabla>();

	public Tabla(ArrayList<Token> tokenrc) 
	{
		tokenRC = tokenrc;
		String nombre[] = new String[tokenRC.size()];
		int tipo[] = new int[tokenRC.size()];
		String nombreTipo = "";
		String renglon[] = new String[tokenRC.size()];
		String columna[] = new String[tokenRC.size()];
		
		//desmonto valores del arraylist en arreglos para su uso más fácil
		
		for (int i = 0; i < tokenrc.size(); i++) 
		{
			nombre[i] = tokenrc.get(i).getToken();
			tipo[i] = tokenrc.get(i).getTipo();
			renglon[i] = String.valueOf(tokenrc.get(i).getRenglon());
			columna[i] = String.valueOf(tokenrc.get(i).getColumna());
		}
		
		//Asigna valores al arraylist que desplegará la tabla
		for (int i = 0; i < tokenrc.size(); i++) {
			
			if(tipo[i] == entero || tipo[i] == booleano) 
			{
				
				if(tipo[i] == entero)
					nombreTipo = "int";
				else
					nombreTipo = "boolean";


				if(nombreTipo.equals("int"))
					ValoresHaciaTabla(nombreTipo, nombre[i+1],     "0", renglon[i+1], columna[i+1]);
				else
					ValoresHaciaTabla(nombreTipo, nombre[i+1], "false", renglon[i+1], columna[i+1]);
			}	
		}
		//Aquí a las variables declaradas se les asignan lo valores correspondientes al código en el .txt
		for (int i = 0; i < tokenRC.size(); i++){
			boolean si = false;
			for (int j = 0; j < valoresTab.size(); j++) 
			{
				if(tokenRC.get(i).getToken().equals(valoresTab.get(j).nombre))
					if(tipo[i] == id && tipo[i+1] == eq)
					{
						if(tipo[i-1] == entero)
							si = true;
						else
							si = false;
						int indice = i + 3;
						String valor = tokenRC.get(i+2).getToken();
						tokenAsignado.add(new Token(tipo[i-1],
										nombre[i],
										valor,
										tokenRC.get(indice - 1).getTipo(),
										tokenRC.get(indice - 1).getRenglon(),
										tokenRC.get(indice - 1).getColumna()	)	);

						while(tipo[indice] == mas || tipo[indice]== menos || tipo[indice] == div || tipo[indice] == mult)
						{
							valor = valor + " " + tokenRC.get(indice).getToken() + " " + tokenRC.get(indice + 1).getToken();
							tokenAsignado.add(new Token(tipo[i-1], 
											nombre[i],
											tokenRC.get(indice + 1).getToken(),
											tokenRC.get(indice + 1).getTipo(),
											tokenRC.get(indice + 1).getRenglon(),
											tokenRC.get(indice + 1).getColumna()	)	);
							indice = indice + 2;
						}
//						if(tipo[indice] != mas || tipo[indice] != menos || tipo[indice] != div || tipo[indice] != mult)
//						{
							valoresTab.set(
									j,
									new ValoresTabla(
												valoresTab.get(j).tipo,
												valoresTab.get(j).nombre,
												valor,
												valoresTab.get(j).renglon,
												valoresTab.get(j).columna					
									)							
							);
//						}
					}
			}
		}
		String nombres[] = new String[valoresTab.size()];
		String tipos[] = new String[valoresTab.size()];
		String renglons[] = new String[valoresTab.size()];
		String columnas[] = new String[valoresTab.size()];
		for (int j = 0; j < valoresTab.size(); j++) 
		{
			nombres[j] =valoresTab.get(j).nombre;
			tipos[j] =valoresTab.get(j).tipo;
			renglons[j] =valoresTab.get(j).renglon;
			columnas[j] =valoresTab.get(j).columna;
		}
		
//		duplicidad de variables
		DatosSemanticosDubVar.DubVariables(nombres,tipos,renglons, columnas);
		if(pruebas)
		{
			pruebas = DatosSemanticosDubVar.pruebas;
			pruebaVarRep = pruebas;
		}

		//variables antes de declarar solo si no hay duplicaciones saltará este
		//debido a que si hay duplicaciones no tiene sentido del todo.
		if(pruebaVarRep) 
		{
			for (int i = 1; i < tokenRC.size(); i++) 
			{
				String tokenActual = "";
				int indiceLimite = 0;
				if(tokenRC.get(i-1).getTipo() == entero && tokenRC.get(i).getTipo() == id)
				{
					tokenActual = tokenRC.get(i).getToken();
					indiceLimite = i;
				}
				for (int j = 0; j < tokenRC.size(); j++) 
				{
					if(j < indiceLimite)
					{
						if(tokenActual.equals(tokenRC.get(j).getToken()))
						{
							System.out.println("Variable asignada antes de haberla declarado, nombre \"" + tokenRC.get(j).getToken() + "\", renglon \"" + tokenRC.get(j).getRenglon() + "\", No. de token \"" + tokenRC.get(j).getColumna()+ "\"");
							pruebas = false;
							pruebaVarRep = true;
						}
					}
				}
				
				if((tokenRC.get(i-1).getTipo() == booleano || tokenRC.get(i-1).getTipo() == booleano)  && tokenRC.get(i).getTipo() == id)
				{
					tokenActual = tokenRC.get(i).getToken();
					indiceLimite = i;
				}
				for (int j = 0; j < tokenRC.size(); j++) 
				{
					if(j < indiceLimite)
					{
						if(tokenActual.equals(tokenRC.get(j).getToken()))
						{
							System.out.println("Variable asignada antes de haberla declarado, nombre \"" + tokenRC.get(j).getToken() + "\", renglon \"" + tokenRC.get(j).getRenglon() + "\", No. de token \"" + tokenRC.get(j).getColumna()+ "\"");
							pruebas = false;
							pruebaVarRep = true;
						}
					}
				}
			}
		}
		//tokenAsignado.get(i).getColumna() es el token asignado
		if(pruebaVarRep) 
		{
			for (int i = 0; i < tokenAsignado.size(); i++)
			{
				for (int j = 0; j < valoresTab.size(); j++) 
				{
					if((tokenAsignado.get(i).getToken().equals(valoresTab.get(j).nombre)  && valoresTab.get(j).tipo.equals("int")))
					{
						for (int j2 = 0; j2 < valoresTab.size(); j2++) 
						{					
							//si imprime más de uno es porque está duplicada la declaración
							if(tokenAsignado.get(i).getTokenAsig().equals(valoresTab.get(j2).nombre)  && valoresTab.get(j2).tipo.equals("boolean"))
							{
								System.out.println(
										"Estás tratando de asignar \"" + tokenAsignado.get(i).getTokenAsig() + "\" de tipo Booleano en "
									+ 	"\"" + tokenAsignado.get(i).getToken() + "\" declarado como Entero" 
									+  	" en renglon \"" + tokenAsignado.get(i).getRenglon() + "\""
									+ 	" No. de token \"" + tokenAsignado.get(i).getColumna() + "\".");
							pruebas = false;
							}
						}
	
					}
					if((tokenAsignado.get(i).getToken().equals(valoresTab.get(j).nombre)  && valoresTab.get(j).tipo.equals("boolean")))
					{
						for (int j2 = 0; j2 < valoresTab.size(); j2++) 
						{					
							//si imprime más de uno es porque está duplicada la declaración
							if(tokenAsignado.get(i).getTokenAsig().equals(valoresTab.get(j2).nombre)  && valoresTab.get(j2).tipo.equals("int"))
							{
								System.out.println(
										"Estás tratando de asignar \"" + tokenAsignado.get(i).getTokenAsig() + "\" de tipo Entero en "
									+ 	"\"" + tokenAsignado.get(i).getToken() + "\" declarado como Booleano" 
									+  	" en renglon \"" + tokenAsignado.get(i).getRenglon() + "\""
									+ 	" No. de token \"" + tokenAsignado.get(i).getColumna() + "\".");
							pruebas = false;
	
							}
						}
	
					}				
				}
	//			System.out.println("tokens asignados " +tokenAsignado.get(i).getEntBool() + ", " + tokenAsignado.get(i).getToken() + ", tipo = " + tokenAsignado.get(i).getTipo());
				
			}
		}
		//usar variables no declaradas
		DatosSemanticosVarNoDec.VarNoDeclarada(tipos,nombres, tokenRC);
		if(pruebas)
		{
			pruebas = DatosSemanticosVarNoDec.pruebas;
		}
		
		if(pruebas) 
		{	
			//Imprime la tabla de simbolos con sus datos
			int mayorN = 0;
			int mayorV = 0;
			int blanco = 12;
			for (int i = 0; i < valoresTab.size(); i++)
			{
			
				if(valoresTab.get(i).nombre.length() > mayorN)
					mayorN = valoresTab.get(i).nombre.length() + 7;
				if(valoresTab.get(i).valor.length() > mayorV)
					mayorV= valoresTab.get(i).valor.length() + 7;
						
			}
			System.out.println("\n"+
					"No."				+ blancos("no.",blanco)				+
					" Tipo" 			+ blancos("tipo",blanco)			+
					" Nombre"			+ blancos("nombre",mayorN) 			+
					" Valor"			+ blancos("valor",mayorV) 			+
					" Renglon" 			+ blancos("renglon",blanco) 		+
					" No. de token" 	+ blancos("No. de token",blanco) 	+
					"\n"
					);
			for (int i = 0; i < valoresTab.size(); i++)
			{
				System.out.println(
								(i+1) 							+ blancos(String.valueOf((i+1)),blanco)		+
								" "+valoresTab.get(i).tipo 		+ blancos(valoresTab.get(i).tipo,blanco) 		+
								" "+valoresTab.get(i).nombre	+ blancos(valoresTab.get(i).nombre,mayorN) 	+
								" "+valoresTab.get(i).valor 	+ blancos(valoresTab.get(i).valor,mayorV) 		+
								" "+valoresTab.get(i).renglon 	+ blancos(valoresTab.get(i).renglon,blanco) 	+
								" "+valoresTab.get(i).columna 	+ blancos(valoresTab.get(i).columna,blanco) 
						);
			}
		}
		else
			System.out.println("\n¡¡¡Hay uno o más errores, por lo tanto no se mostrará la tabla.!!!");
	}

	public void ValoresHaciaTabla(String tip, String nom, String val, String reng, String col) 
	{
		valoresTab.add(new ValoresTabla(tip, nom, val, reng, col));
	}
	
	public String blancos(String cadena, int cantidad) {
		
		String blancos = "";
		
		for (int i = cadena.length(); i < cantidad; i++) {
			blancos += " ";
		}
		
		return blancos;
	}
}
