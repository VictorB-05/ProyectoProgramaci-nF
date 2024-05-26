package concesionario;

/**
 * Obejeto en el que se gestionan las cuentas bancarias
 */
public class Cuenta_Bancaria {
	private String banco;	
	private String titular;
	private String dni;
	private String cuentaIban;
	private String provincia;
	
	/**
	 * 
	 * @param nombre
	 * @param apellidos
	 * @param banco
	 * @param cuentaIban
	 * @param provincia
	 */
	public Cuenta_Bancaria(String nombre, String apellidos, String dni, String banco, String cuentaIban, String provincia) {
		this.titular = nombre+" "+apellidos;
		this.dni = dni;
		this.banco = banco;
		this.cuentaIban = cuentaIban;
		this.provincia = provincia;
	}
	
	/**
	 * 
	 * @param titular
	 * @param banco
	 * @param cuentaIban
	 * @param provincia
	 */
	public Cuenta_Bancaria(String titular, String banco, String dni, String cuentaIban, String provincia) {
		this.titular = titular;
		this.dni = dni;
		this.banco = banco;
		this.cuentaIban = cuentaIban;
		this.provincia = provincia;
	}
	
	/**
	 * 
	 * @param nombre
	 * @param apellidos
	 * @param cuentaIban
	 */
	public Cuenta_Bancaria(String nombre, String apellidos, String dni, String cuentaIban) {
		this.titular = nombre+" "+apellidos;
		this.dni = dni;
		this.cuentaIban = cuentaIban;
	}
	
	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}
	
	public void setTitular(String nombre,String apellido) {
		this.titular = nombre+" "+apellido;
	}
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getCuentaIban() {
		return cuentaIban;
	}

	public void setCuentaIban(String cuentaIban) {
		this.cuentaIban = cuentaIban;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Override
	public String toString() {
		return " Entidad: " + banco + " titular: " + titular + " cuentaIban: " + cuentaIban
				+ " provincia: " + provincia;
	}
	
	
	
}
