package concesionario;

public enum Etiqueta_Eco {
	ECO, C, B, A,CERO;
	
	@Override
	public String toString() {
		
		if(this==CERO) return "0";
		
		return super.toString();
	}
	
}