package concesionario;

import java.time.LocalDate;

/**
 * Objeto que gestiona los vendedores
 */
public class Vendedor extends Persona implements IVendedor{
	private String caEmpresa;
	private String departamento;
	private int sueldo;
	
	/**
	 * constructor con todos los valores de persona
	 * @param nombre
	 * @param apellidos
	 * @param dni
	 * @param fechaNac
	 * @param edad
	 * @param sexo
	 * @param direccion
	 * @param localidad
	 * @param provincia
	 * @param codPostal
	 * @param telefono
	 * @param correoElec
	 * @param caEmpresa
	 * @param departamento
	 * @param sueldo
	 */
	public Vendedor(String nombre, String apellidos, String dni, LocalDate fechaNac, int edad, char sexo,
			String direccion, String localidad, String provincia, int codPostal, String telefono, String correoElec,
			String caEmpresa, String departamento, int sueldo) {
		super(nombre, apellidos, dni, fechaNac, edad, sexo, direccion, localidad, provincia, codPostal, telefono,
				correoElec);
		this.caEmpresa = caEmpresa;
		this.departamento = departamento;
		this.sueldo = sueldo;
	}
	
	public Vendedor(String dni) {
		super(dni);
	}

	public String getCaEmpresa() {
		return caEmpresa;
	}

	public void setCaEmpresa(String caEmpresa) {
		this.caEmpresa = caEmpresa;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public int getSueldo() {
		return sueldo;
	}

	public void setSueldo(int sueldo) {
		this.sueldo = sueldo;
	}

	@Override
	public String toString() {
		return super.toString()+ " categoria: " + caEmpresa + " departamento: " + departamento + " sueldo: " + sueldo;
	}

	
	
}
