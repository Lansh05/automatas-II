package analizador;

import java.util.ArrayList;

public class Intermedio_datos 
{
	final static int els = 0, ifs = 1, then = 2, print = 3, begin = 4, end = 5 , eq = 6, coma = 7,
			  menor = 8, mayor = 9, parizq = 10, parder = 11, ex = 12, mas = 13, menos = 14, 
			  div = 15, mult = 16, d2eq = 17, meneq = 18, mayeq = 19, diff = 20, verdad = 21,
			  falso = 22, booleano = 23, entero = 24, num = 50, id = 51;
	ArrayList<Token> tokenRC;
	ArrayList<Integer> numeros = new ArrayList<Integer>();
	ArrayList<Obj_Intermedio> tokens_intermedionoif = new ArrayList<Obj_Intermedio>(), tokens_intermedionoifinal = new ArrayList<Obj_Intermedio>();
	
	ArrayList<ValoresTabla> valoresTab = new ArrayList<ValoresTabla>();
	public Intermedio_datos(ArrayList<Token> tokenRc,ArrayList<ValoresTabla> valoresTAB) 
//	public Intermedio(ArrayList<Token> tokenRc) 
	{
		valoresTab = valoresTAB;
		tokenRC = tokenRc;
		int indicePos = 0;
		//declaraciones y asignaciones, no asignaciones en if ni valores o ids dentro de print
		for (int i = 4; i < tokenRC.size()-1; i++) //empezamos desde aquí para optimizar un poco, ya que lo requerido no serán los primeros datos metodo() begin y end
		{
			if(tokenRC.get(i-2).getTipo() == print)
			{
				while(tokenRC.get(i).getTipo() != coma)
				{
					i++;
				}
			}
			if(tokenRC.get(i-2).getTipo() == ifs)
			{
				indicePos++;
				while(tokenRC.get(i).getTipo() != parder)
				{
					i++;
				}
			}
			if((tokenRC.get(i-1).getTipo() == then || tokenRC.get(i-1).getTipo() == els))
				while(tokenRC.get(i).getTipo() != coma)
				{
					i++;
				}
			if((tokenRC.get(i).getTipo() == id))// || tokenRC.get(i).getTipo() == num || tokenRC.get(i).getTipo() == verdad || tokenRC.get(i).getTipo() == falso))
			{
				indicePos++;
				
				while(Condicion(tokenRC.get(i).getTipo()))
				{	
					tokens_intermedionoif.add(new Obj_Intermedio(indicePos,tokenRC.get(i).getToken()));
					i++;
				}
			}
		}
		//si la variable está sola entonces preguntar que tipo de dato es desde la lista
		//de la tabla de simbolos es y asignarle 1 por defecto
		for (int i = 0; i < tokens_intermedionoif.size()-2; i++)
		{
			if(tokens_intermedionoif.get(i).getPos() != tokens_intermedionoif.get(i+1).getPos())
			{
				for (int j = 0; j < valoresTab.size(); j++) 
				{
					if(tokens_intermedionoif.get(i).getToken().equals(valoresTab.get(j).nombre)) 
					{
						if(valoresTab.get(j).tipo.equals("int"))
						{
							tokens_intermedionoif.add(new Obj_Intermedio(tokens_intermedionoif.get(i).getPos(),"="));
							tokens_intermedionoif.add(new Obj_Intermedio(tokens_intermedionoif.get(i).getPos(),"0"));
						}
						else
						{
							tokens_intermedionoif.add(new Obj_Intermedio(tokens_intermedionoif.get(i).getPos(),"="));
							tokens_intermedionoif.add(new Obj_Intermedio(tokens_intermedionoif.get(i).getPos(),"false"));
						}
					}
				}
			}
			else
//				---------------------------------------------------------------------------------------
				while(tokens_intermedionoif.get(i).getPos() == tokens_intermedionoif.get(i+1).getPos() 
				)//&& i < tokens_intermedionoif.size()-1)
				{
						i++;
						if(i > tokens_intermedionoif.size()-2)
							break;
				}
		}
		//como estas asignaciones quedarán al final entonces hay que acomodar con método de intercambio
		int temp = 0, cont_remover = 0, vuelta = 0;
//		System.out.println("tamaño tokens_intermedionoif = " + tokens_intermedionoif.size());
		for (int i = 0; i < tokens_intermedionoif.size()-1; i++) 
		{
			boolean siguiente = true;
			vuelta++;
			if(i>0)
				for (int j = 0; j <numeros.size(); j++) 
				{
					if(numeros.get(j) == tokens_intermedionoif.get(i).getPos())
						siguiente = false;
				}
			
			if(siguiente)
			{
				numeros.add(tokens_intermedionoif.get(i).getPos());
				while(i < tokens_intermedionoif.size()-1 && (tokens_intermedionoif.get(i).getPos() == tokens_intermedionoif.get(i+1).getPos()))
				{	
					tokens_intermedionoifinal.add(tokens_intermedionoif.get(i));
					i++;
				
				}
				tokens_intermedionoifinal.add(tokens_intermedionoif.get(i)); //para añadir el restante
				
				temp = tokens_intermedionoif.get(i).getPos();
//				System.out.println("Valor temp = " + temp);
				for (int j = i+1; j < tokens_intermedionoif.size(); j++) 
				{ 
					if(tokens_intermedionoif.get(j).getPos() == temp)
					{	
//					System.out.println("está entrando aqui?");
//						cont_remover++;
						tokens_intermedionoifinal.add(tokens_intermedionoif.get(j));
					}	
				}
			}
		}
		
//		for (int i = 0; i < numeros.size(); i++)
//		{
//			System.out.println(numeros.get(i));
//		}
//		System.out.println("\nMis tokens para el código intermedio.\n");
//		for (int i = 0; i < tokens_intermedionoifinal.size(); i++)
//		{
//			System.out.println(tokens_intermedionoifinal.get(i).getPos() + ", " + tokens_intermedionoifinal.get(i).getToken());
//		}
		
	}
	private boolean Condicion(int tipo)
	{
		if(tipo == id || tipo == num || tipo == menos || tipo == mas || tipo == div || tipo == mult || tipo == eq
		|| tipo == verdad || tipo == falso)// || tipo == d2eq || tipo == meneq || tipo == mayeq || tipo == diff || tipo == verdad || tipo == falso)
			return true;
		else 
			return false;
	}
}
