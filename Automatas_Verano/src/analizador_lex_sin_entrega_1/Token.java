package analizador_lex_sin_entrega_1;

public class Token {
	
	private String token;
	private int renglon;
	private int columna;
	private int tipo;		//tipos pero en su valor númerico id = 51, num  =50
	private String tipos;	//tipos en tipos String "int", "boolean"
	private int entBool;	//para los errores semanticos en tipos de asignación
	private String tokAsig;	//para los errores semanticos en tipos de asignación este dato guarda la asigacion
	
	public Token(String tok, int r, int c, int tipo) 
	{
		token = tok;
		renglon = r;
		columna = c;
		this.tipo = tipo;
	}
	public Token(String tok, String tipos, int r, int c) 
	{
		token = tok;
		this.tipos = tipos;
		renglon = r;
		columna = c;
	}
	public Token(String tok, int r, int c) 
	{
		token = tok;
		renglon = r;
		columna = c;
	}
	//para los errores semanticos en tipos de asignación, comparando si el tipo de uno es igual al otro
	public Token(int entBool, String tok, String tokAsig, int tipo, int r, int c) 
	{
		this.entBool = entBool;
		token = tok;
		this.tokAsig = tokAsig;
		this.tipo = tipo;
		renglon = r;
		columna = c;
	}
	public int getEntBool() {
		return entBool;
	}
	public String getTokenAsig() {
		return tokAsig;
	}
	public String getToken() {
		return token;
	}
	public int getRenglon() {
		return renglon;
	}

	public int getColumna() {
		return columna;
	}
	public int getTipo() {
		return tipo;
	}public String getTipos() {
		return tipos;
	}

}
