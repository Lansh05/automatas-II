package analizador;

import java.util.ArrayList;

public class Sintactico {
	
	final int els = 0, ifs = 1, then = 2, print = 3, begin = 4, end = 5 , eq = 6, coma = 7,
			  menor = 8, mayor = 9, parizq = 10, parder = 11, ex = 12, mas = 13, menos = 14, 
			  div = 15, mult = 16, d2eq = 17, meneq = 18, mayeq = 19, diff = 20, verdad = 21,
			  falso = 22, booleano = 23, entero = 24, num = 50, id = 51;
	
	String tokenRealString;
	int tokenReal = -1, indice = 0, renglon = 0, columna = 0;
//	public ArrayList<Integer> tipos, renglones, columnas;
//	public ArrayList<String> tokens;
	public ArrayList<Token> tokenRC;
	 
	
	public Sintactico(ArrayList<Token> tokenRC) 
	{
		this.tokenRC = tokenRC;
//		this.tipos = tipos;
//		this.renglones = renglones;
//		this.columnas = columnas;
//		this.tokens = tokens;
		
		tokenReal = tokenRC.get(indice).getTipo();			// Número
		tokenRealString = tokenRC.get(indice).getToken();	//Palabra
		renglon = tokenRC.get(indice).getRenglon();
		columna = tokenRC.get(indice).getColumna();
//		
//		tipos.get(indice); 
//		tokenRealString = tokens.get(indice);
//		renglon = renglones.get(indice);
//		columna = columnas.get(indice);
		
		Programa();
		
//		System.out.println("fin del programa, pasó las pruebas!!");

	}
	
	public void eat(int t)
	{
		int tokenAnterior = -1;
		if(indice > 0)
		 tokenAnterior = tokenRC.get(indice-1).getTipo();
	    int tokenEsperado = t; //ifs = 1
//	    System.out.println("tipos.size = " + tipos.size() + " //// indice = " + indice);
        if(tokenReal == tokenEsperado) // 2 = 8?
        {
        	if(tokenRC.size()-1 == indice)
        	{
        		indice++; //para saber que no hay nada después del final como indica la sintaxis
        		return;   //size abarca la cantidad que hay en la lista, el -1 es para que abarque
        				  //todo contando desde el indice 0
        	}
        }
        else{
        	if(tokenAnterior != -1) {
        		if(tokenAnterior == tokenReal)
        			error("Se esperaba otro token al lado \""+ tokenRealString + "\" en renglon : " + renglon + ", No. de token: " + columna + ", su tipo es: " + ValoresInversos(tokenReal));
        		else
        			error("renglon : " + renglon + ", columna: " + columna + ", token \""+ tokenRealString + "\" tipo esperado: " + ValoresInversos(tokenEsperado) +", llegó tipo: " + ValoresInversos(tokenReal));
        	}
        }
        indice++;
        if(indice > tokenRC.size()-1 )
            error("Faltó 1 token al lado de token \""+ tokenRealString + "\", llegó tipo: " + ValoresInversos(tokenReal) + " en renglon : " + renglon + ", No. de token: " + columna );
		tokenReal = tokenRC.get(indice).getTipo();			// Número
		tokenRealString = tokenRC.get(indice).getToken();	//Palabra
		renglon = tokenRC.get(indice).getRenglon();
		columna = tokenRC.get(indice).getColumna();        
	}
	
	private void error(String string) 
	{
		System.out.println(""
				 		 + string);
		System.exit(0);
	}
	private void error() 
	{
		System.out.println("Error sintáctico\n"
						 + "Token \"" + tokenRealString + "\" en renglón \"" + renglon + "\", No. de token \"" + columna + "\", tipo: \""+ValoresInversos(tokenReal)+"\" no se esperaba");
		System.exit(0);
	}
	
	public void Programa() {
		
		eat(id);
		eat(parizq);
		eat(parder);
		End();
	
//		else error();
//		System.out.println("tamaño indice = " + indice + ", tamaño lista = " + tokens.size());
//		eat(end);
		if(tokenRC.size() > indice)
		{
			System.out.println("Error sintáctico, error en los límites del código almenos un token extra \"" + tokenRC.get(indice).getToken() + "\"");
			System.exit(0);
		}
	
	}
	public void End() {
		int tr = tokenReal;
		eat(begin);
		while(tr == ifs || tr == begin || tr == print || tr == entero || tr == booleano || tr == id) {
			tr = tokenReal;
			
		if(tr == entero || tr == booleano)
			Declaracion();
		if(tokenReal == ifs || tokenReal == print ||  tr == id)
			S();
		if( tokenReal == begin)
			End();
		}
		 eat(end);
	}
	
	public void Declaracion () {
		String tok;
		switch (tokenReal) {
			case entero: 
				eat(entero);
				eat(id);
				if(tokenReal == coma)
					eat(coma);
				else if(tokenReal == eq)
				{
					eat(eq);
					
					if(tokenReal == num)
						eat(num);
					else if(tokenReal == id)
						eat(id);
					else if(tokenReal == falso || tokenReal == verdad)
						error("No se esperaba la asignación de un valor booleano para un dato tipo Entero.\n"
						+ "Token \""+ tokenRealString + "\" en renglón \"" + renglon + "\", No. de token \"" + columna + "\", tipo: \""+ValoresInversos(tokenReal));
					else
						error("Asignaste un valor que no es de tipo Entero.\n"
							 + "Token \""+ tokenRealString + "\" en renglón \"" + renglon + "\", No. de token \"" + columna + "\", tipo: \""+ValoresInversos(tokenReal));
					if(tokenReal == coma)
						eat(coma);
					else
						while(OperandoSimbols())
						{
							if(tokenReal == num)
								eat(num);
							else
								eat(id);
							if(tokenReal == coma)
							{
								eat(coma);
								break;
							}
						}
				}
				
				break;
			case booleano:
				eat(booleano);
				eat(id);
				if(tokenReal == coma)
					eat(coma);
				else if(tokenReal == eq)
				{
					eat(eq);
					if(tokenReal == falso)
					{
						eat(falso);
					}
					else if(tokenReal == verdad)
					{
						eat(verdad);
					}
					else  if(tokenReal == id)
						eat(id);
					else if(tokenReal == num)
						error("No se esperaba la asignación de un valor Entero para un dato tipo Boolean.\n"
						+ "Token \""+ tokenRealString + "\" en renglón \"" + renglon + "\", No. de token \"" + columna + "\", tipo: \""+ValoresInversos(tokenReal)+"\"");
					else
						error("Asignaste un valor que no es de tipo Boolean.\n"
						 + "Token \""+ tokenRealString + "\" en renglón \"" + renglon + "\", No. de token \"" + columna + "\", tipo: \""+ValoresInversos(tokenReal)+"\"");

					if(tokenReal == coma)
						eat(coma);
				}

			}
	}
	public void S() {
		
		switch (tokenReal) // 1
		{
		case ifs:			// 1 == 1?
			eat(ifs);
			eat(parizq);
			if(tokenReal == id || tokenReal == num)
			{				
					eat(tokenReal);
				if (tokenReal == menos || tokenReal == mas || tokenReal == div || tokenReal == mult /*type == mayor || type == dobleEQ ||*/ )
					while(OperandoSimbols())
					{
						if(tokenReal == num)
							eat(num);
						else
							eat(id);
						if (tokenReal == menor || tokenReal == mayor || tokenReal == meneq || tokenReal == mayeq || tokenReal == d2eq || tokenReal == diff/*type == mayor || type == dobleEQ ||*/ )
						{
						
							break;
						}
					}
				LogicSimbols();
				if(tokenReal == num)
					eat(num);
				else
					eat(id);
					
				if(tokenReal != parder)
					while(OperandoSimbols())
					{
						if(tokenReal == num)
							eat(num);
						else
							eat(id);
						if(tokenReal == parder)
						{
							break;
						}
					}
			}
			else if(tokenReal == falso)
			{
				eat(falso);
			}
			else if(tokenReal == verdad)
				eat(verdad);
			else
				error("Dentro del if debe haber un dato Boolean o una comparación.\n"
						 + "Token \""+ tokenRealString + "\" en renglón \"" + renglon + "\", No. de token \"" + columna + "\", tipo: \""+ValoresInversos(tokenReal)+"\"");


			eat(parder);
			eat(then); S(); eat(els); S();
			break;
		case print:			// 1 == 3?
			eat(print);
			eat(parizq);
			
				if(tokenReal == num)
					eat(num);
				else
					eat(id);
				if(tokenReal == parder)
					eat(parder);
				else
					while(OperandoSimbols())
					{
						if(tokenReal == num)
							eat(num);
						else
							eat(id);
						if(tokenReal == parder)
						{
							eat(parder);
							break;
						}
					}
			
			eat(coma);
			break;
		case id:			// 1 == 3?
			eat(id);

				eat(eq);
				
				if(tokenReal == num)
					eat(num);
				else if(tokenReal == id)
					eat(id);
				else if(tokenReal == falso)
				{
					eat(falso);
				}
				else if(tokenReal == verdad)
					eat(verdad);
				
				if(tokenReal == coma)
					eat(coma);
				else
					while(OperandoSimbols())
					{
						if(tokenReal == num)
							eat(num);
						else
							eat(id);
						if(tokenReal == coma)
						{
							eat(coma);
							break;
						}
					}
			
			break;
		default: error(); break;
		}
	}
	public boolean LogicSimbols() {
		if (tokenReal == menor || tokenReal == mayor || tokenReal == meneq || tokenReal == mayeq || tokenReal == d2eq || tokenReal == diff/*type == mayor || type == dobleEQ ||*/ )
		{
			eat(tokenReal);
			return true;
		}
		else 
			error();
			return false;
	}
	public boolean OperandoSimbols() {
		if (tokenReal == menos || tokenReal == mas || tokenReal == div || tokenReal == mult)
		{
			eat(tokenReal);
			return true;
		}
		else 
			error();
			return false;
	}
	public void E() 
	{
		eat(num); eat(eq); eat(num);
	}
	
	public String ValoresInversos(int type)
	{
		String devuelve, cadenas[] =
		//		  0,     1,     2,      3,        4,      5,    6,   7,  8,   9
				{"else", "if", "then", "print", "begin", "end", "=", ";","<", ">",
		//		10,  11,  12,  13,  14, 15, 16,   17,   18,   19,   20,    21,    22
				"(", ")", "!", "+", "-","/","*", "==", "<=", ">=", "!=", "true","false",
		//		   23,       24
				"boolean", "int"};
		if(type == 50) 
			return devuelve = "numérico";
		if(type == 51) 
			return devuelve = "identificador";
		devuelve = cadenas[type];
		
		return  devuelve;
	}

}
