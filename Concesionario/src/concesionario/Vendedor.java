package concesionario;

import java.time.LocalDateTime;

public class Vendedor extends Persona implements IVendedor{
	private String caEmpresa;
	private String departamento;
	private int sueldo;
	/*
	 * constructor con todos los valores de persona
	 */
	public Vendedor(String nombre, String apellidos, String dni, LocalDateTime fechaNac, int edad, char sexo,
			String direccion, String localidad, String provincia, int codPostal, String telefono, String correoElec,
			String caEmpresa, String departamento, int sueldo) {
		super(nombre, apellidos, dni, fechaNac, edad, sexo, direccion, localidad, provincia, codPostal, telefono,
				correoElec);
		this.caEmpresa = caEmpresa;
		this.departamento = departamento;
		this.sueldo = sueldo;
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

}
