package analizador;

import java.util.ArrayList;

public class Intermedio {
	
	ArrayList<Obj_Intermedio> datos = new ArrayList<Obj_Intermedio>();
	
	public Intermedio(ArrayList<Obj_Intermedio> datosP)
	{
		datos = datosP;
		int cont1 = 0, contT = 0	, blanco = 15;
		System.out.println("\n-----------------------Cuadrúplos-----------------------\n");
		System.out.println(
				"Operador" + blancos("Operador", blanco)+
				"Operando1" + blancos("Operando1", blanco)+
				"Operando2" + blancos("Operando2", blanco)+
				"Resultado" + blancos("Resultado", blanco));

		String dato_asignado = "";
		for (int i = 0; i < datos.size(); i++) 
		{
			boolean bandera = true;
			
			if(datos.get(i).getToken().equals("=")) //0 -> a , 1 -> = , 2 -> 12, 
			{
				String operando1 = "0", operando2 = "0";
				String operador = " ";

				dato_asignado =  datos.get(i-1).getToken();
				i++;
				
				operando1 = datos.get(i).getToken();
				i++;
				//si entra al while es que trae suma,resta, etc
				String VarTemp = "";
				cont1 = 0;
				while( i < datos.size()-2 && bandera && (datos.get(i).getToken().equals("+") || datos.get(i).getToken().equals("-") 
				|| datos.get(i).getToken().equals("/")|| datos.get(i).getToken().equals("*")) )
				{
					if(cont1>0)
						operando1 = VarTemp;
						
					cont1++;
					contT++;
					VarTemp = "T"+contT;

					operador = datos.get(i).getToken();
					i++;
					
					operando2 = datos.get(i).getToken();
					i++;
						if(datos.get(i).getToken().equals("+") || datos.get(i).getToken().equals("-") 
								|| datos.get(i).getToken().equals("/")|| datos.get(i).getToken().equals("*"))
						{
							System.out.println(
									operador + blancos(" ", blanco)+
									operando1 + blancos(operando1, blanco)+
									operando2 + blancos(operando2, blanco)+
									VarTemp + blancos("T"+String.valueOf(contT), blanco));
							bandera = true;
						}
						else
						{
							bandera = false;
						}
					}
				if(i == datos.size()-2)
				{
					operador = datos.get(i).getToken();
					operando1 = VarTemp;
					operando2 = datos.get(i+1).getToken();
				}
				System.out.println(
				operador + blancos(" ", blanco)+
				operando1 + blancos(String.valueOf(operando1), blanco)+
				operando2 + blancos(String.valueOf(operando2), blanco)+
				dato_asignado + blancos(dato_asignado, blanco));
				}
		}
	}

	public String blancos(String cadena, int cantidad) {
		
		String blancos = "";
		
		for (int i = cadena.length(); i < cantidad; i++) {
			blancos += " ";
		}
		
		return blancos;
	}
}
