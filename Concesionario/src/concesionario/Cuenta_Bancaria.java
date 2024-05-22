package concesionario;


public class Cuenta_Bancaria {
	private String nombre;
	private String apellidos;
	private String dni;
	private String banco;
	private int cuentaCorre;
	private String provincia;
	
	public Cuenta_Bancaria(String nombre, String apellidos, String dni, String banco, int cuentaCorre, String provincia) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.banco = banco;
		this.cuentaCorre = cuentaCorre;
		this.provincia = provincia;
	}
	
	public Cuenta_Bancaria(String nombre, String apellidos, String dni, String provincia) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.provincia = provincia;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public int getCuentaCorre() {
		return cuentaCorre;
	}

	public void setCuentaCorre(int cuentaCorre) {
		this.cuentaCorre = cuentaCorre;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	
	
}
