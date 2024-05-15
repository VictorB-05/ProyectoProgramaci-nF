package concesionario;

public enum EtiquetaEco {
	ECO, C, B, A,CERO;
	
	@Override
	public String toString() {
		
		if(this==CERO) return "0";
		
		return super.toString();
	}
	
}