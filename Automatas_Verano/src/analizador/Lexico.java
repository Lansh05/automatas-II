package analizador;
import java.io.*;
import java.util.*;

public class Lexico
{		
    boolean flag = true;
    ArrayList<String> VoF = new ArrayList<String>(), tokens = new ArrayList<String>();
    public ArrayList<Integer> tipos = new ArrayList<Integer>(), renglones = new ArrayList<Integer>(),
    		columnas = new ArrayList<Integer>(); 
    int ren = 1;
    int col = 1;
    int retEQ = 0, retMayEQ = 0, retMenEQ = 0, retDif = 0,  contador = -1;;
	ArrayList<Token> tokenRC = new ArrayList<Token>();

	public Lexico(String archivo) 
	{
		FileReader file;
		BufferedReader a;
		String oracion = null;
		StringTokenizer tokenizer;
		
		try{
			file = new FileReader(archivo);
			a = new BufferedReader(file);
			oracion = a.readLine();
			
			while (oracion != null)
			{	
				for (String cadena : Arrays.asList("=", ";", "(", ")", "*", "-", "+", "<", "/", ">", "!"))
				{
					if(oracion.contains(cadena)) 
						oracion = oracion.replace(cadena, " "+cadena+" ");
				}
				tokenizer = new StringTokenizer(oracion); 
				col = 0;
				while(tokenizer.hasMoreTokens()) // 
				{				
					col++;
					tipifica(tokenizer.nextToken());
				}
				oracion = a.readLine();
				ren++;
			}
		}catch(IOException e)
		{
			System.out.println("Error: "+e.getMessage());		
		}
		if(flag) /*{*/
			VoF.add("Sin errores lexicos"); 
	}
	public void tipifica(String token) //if,
	{
		
		token = Junta(token); //método tipo String
		if(token.equals("==") || token.equals(">=") || token.equals("<=") || token.equals("!="))
		{
			return;
		}
		int tipo = -1;
		//^ = inicio de palabra "expresión regular" / $ = fin de palabra / ? = indica 0 o 1 vez
		if(token.matches("^[a-zA-Z]+[[a-zA-Z]*[0-9]*]*$")) //
			tipo = 51;
		if(token.matches("([0-9])+"))
			tipo = 50;
//							  0,     1,     2,      3,        4,      5,    6,   7,  8,   9
		String cadenas[] = {"else", "if", "then", "print", "begin", "end", "=", ";","<", ">",
//							10,  11,  12,  13,  14, 15, 16,   17,   18,   19,   20,    21,    22
							"(", ")", "!", "+", "-","/","*", "==", "<=", ">=", "!=", "true","false",
//							   23,       24
							"boolean", "int"};
		
		for (int i = 0; i < cadenas.length; i++) 
		{
			if(token.equals(cadenas[i]))
				tipo = i;
		}
		if(tipo == -1) 
		{
			VoF.add("Error de compilación en Léxico, reglón " + ren + " columna " + col + ", nombre ** " + token + " ** un token con estas características no está permitido en la grámática.");
			flag = false;
		}
//		if(tipo == -2) 
//		{
//			VoF.add("Error de compilación en Léxico, el programa no acepta identificadores, reglón " + ren + " columna " + col + ", nombre ** " + token + " **");
//			flag = false;
//			return;
//		}
//		System.out.println("token = "+ token);
		tokenRC.add(new Token(token, ren, col, tipo));

//		tokens.add(token);
//		tipos.add(tipo);
//		renglones.add(ren);
//		columnas.add(col);
	}
	public String Junta(String token) { //hicimos esto ya que se buggeaba cuando trataba de poner un token tipo != <= >= ==
		contador++; //guarda equivalencia con las posiciones de los datos añadidos en el ArrayList TokenRC (renxcol)
	
		if(token.equals("<"))
			retMenEQ++;
		else if(token.equals(">"))
			retMayEQ++;
		else if(token.equals("!"))
			retDif++;
		else if(token.equals("="))
		{
			retEQ++;
			retDif++;
			retMayEQ++;
			retMenEQ++;

		}
		else {
			retEQ = 0;
			retDif = 0;
			retMayEQ = 0;
			retMenEQ = 0;
		}

		if(retEQ == 2 || retMenEQ == 2 || retMayEQ == 2 || retDif == 2)
		{
			contador--;
//			tokenRC.remove(contador-1);
			tokenRC.remove(contador);
			
			if(retEQ == 2)
			{	
				tokenRC.add(new Token("==", ren, col, 17));
				token = "==";
			}
			else if(retMayEQ == 2)
			{	
				tokenRC.add(new Token(">=", ren, col, 19));
				token = ">=";
			}
			else if(retMenEQ == 2)
			{	
				tokenRC.add(new Token("<=", ren, col, 18));
				token = "<=";
			}
			else if(retDif == 2)
			{	
				tokenRC.add(new Token("!=", ren, col, 20));
				token = "!=";
			}
			retEQ = 0;
			retDif = 0;
			retMayEQ = 0;
			retMenEQ = 0;
//			contador--;
		}
//		System.out.println("Antes de madar token, token vale = " + token);
		return token;
	}
}