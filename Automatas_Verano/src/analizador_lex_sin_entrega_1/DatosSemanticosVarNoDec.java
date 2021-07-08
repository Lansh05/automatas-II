package analizador_lex_sin_entrega_1;

import java.util.ArrayList;

public class DatosSemanticosVarNoDec {
	
	final static int els = 0, ifs = 1, then = 2, print = 3, begin = 4, end = 5 , eq = 6, coma = 7,
			  menor = 8, mayor = 9, parizq = 10, parder = 11, ex = 12, mas = 13, menos = 14, 
			  div = 15, mult = 16, d2eq = 17, meneq = 18, mayeq = 19, diff = 20, verdad = 21,
			  falso = 22, booleano = 23, entero = 24, num = 50, id = 51;
	public static boolean pruebas = true;
	
	public static void VarNoDeclarada(String[] tipos, String[] nombres, ArrayList<Token> tokenrc)
	{
		ArrayList<Token> Declarado = new ArrayList<Token>();
		ArrayList<Token> noDeclarado = new ArrayList<Token>();
		ArrayList<Token> noDecFinal = new ArrayList<Token>();
	
		for (int i = 1; i < tokenrc.size(); i++) //tipos en tokenRC "51 = id, 50 = num"
		{
			int contDec = 0;
			int contNo = 0;
			if(tipos.length == 0 && tokenrc.get(i).getTipo() == id) //cuando no haces declaraciones e igual usas variables
			{
				contNo++;
				
			}
			for (int j = 0; j < tipos.length; j++) //tipos de los valores en la tabla "int, boolean"
			{
				
				if((tokenrc.get(i).getToken().equals(nombres[j])) && tokenrc.get(i).getTipo() == id && (tipos[j].equals("int") || tipos[j].equals("boolean"))) 
				{
					contDec++;
				}
				if(!(tokenrc.get(i).getToken().equals(nombres[j])) && tokenrc.get(i).getTipo() == id && (tipos[j].equals("int") || tipos[j].equals("boolean"))) 
				{
					contNo++;
				}
			}
			if(contDec > 0)
				Declarado.add(new Token(tokenrc.get(i).getToken(),tokenrc.get(i).getRenglon(), tokenrc.get(i).getColumna()));
			if(contNo > 0)
				noDeclarado.add(new Token(tokenrc.get(i).getToken(),tokenrc.get(i).getRenglon(), tokenrc.get(i).getColumna()));
		}		
		for (int i = 0; i < noDeclarado.size(); i++)
		{
			int contNo = 0;
			for (int j = 0; j < Declarado.size(); j++)
			{
				if(!noDeclarado.get(i).getToken().equals(Declarado.get(j).getToken())) 
				{
					contNo++;
				}
			}
			if(contNo == Declarado.size()) //si el contador llega al máximo quiere decir que no hay ninguna variable declarada como la actual
			{
				noDecFinal.add(new Token(noDeclarado.get(i).getToken(),noDeclarado.get(i).getRenglon(), noDeclarado.get(i).getColumna()));
			}
		}
		
		int mayorN = 0;
		int blanco = 8;
		
		for (int i = 0; i < noDecFinal.size(); i++)
		{
				if(noDecFinal.get(i).getToken().length() > mayorN)
					mayorN = noDecFinal.get(i).getToken().length() + 7;
					
		}
		if(noDecFinal.size()>0) //entra solo si sí hay variables repetidas
		{
			pruebas = false;
			System.out.println("\nVariables que están siendo utilizadas y no han sido declaradas son las siguientes...\n"
					+	guion( mayorN+ blanco)
					+	"\n"
					+	"|num"	+ 		blancos("num", blanco)
					+	"|nombre"	+ 	blancos("nombre", mayorN)
					+	"|"
					+	"\n" 
					+	guion( mayorN + blanco)
				 );
			for (int i = 0; i < noDecFinal.size(); i++)
			{
				System.out.println("|"	+			(i + 1) 			+   blancos(String.valueOf((i+1)), blanco)			+
								   "|"	+noDecFinal.get(i).getToken()	+ 	blancos(noDecFinal.get(i).getToken(), mayorN)	+
								   "|"									+
								   "ubicado en renglón \""+noDecFinal.get(i).getRenglon()		+
								   "\"" 								+
								   " y número de token \""+noDecFinal.get(i).getColumna()		+
								   "\""	
						);
			}		
			System.out.println(guion(  mayorN+ blanco));
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
