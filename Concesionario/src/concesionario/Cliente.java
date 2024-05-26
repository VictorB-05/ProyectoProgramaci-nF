package concesionario;

import java.time.LocalDate;

/**
 * Objeto gestiona los clientes de la base de datos
 */
public class Cliente extends Persona implements ICliente{
	private Cuenta_Bancaria datosBan;
	private LocalDate fAlta;
	private Boolean mayorEdad;
	private Cliente representante;
		
	/**
	 * Constructor de aquellos clientes con representante no se incia la cuenta bancaria
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
	 * @param representante
	 * @param fechaAlta
	 */
	public Cliente(String nombre, String apellidos, String dni, LocalDate fechaNac, int edad, char sexo,
			String direccion, String localidad, String provincia, int codPostal, String telefono, String correoElec,
			Cliente representante, LocalDate fechaAlta) {
		super(nombre, apellidos, dni, fechaNac, edad, sexo, direccion, localidad, provincia, codPostal, telefono, correoElec);
		this.fAlta = fechaAlta;
		if(super.getEdad()<18) {
			mayorEdad= false;
			this.representante = representante;
		}else {
			mayorEdad= true;
		}
	}
	
	/**
	 * Metodo usado para crear a los clientes desde la base de datos con representante
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
	 * @param representante
	 * @param fechaAlta
	 * @param mayorEdad
	 */
	public Cliente(String nombre, String apellidos, String dni, LocalDate fechaNac, int edad, char sexo,
			String direccion, String localidad, String provincia, int codPostal, String telefono, String correoElec,
			Cliente representante, LocalDate fechaAlta,boolean mayorEdad,String cuentaBancaria) {
		super(nombre, apellidos, dni, fechaNac, edad, sexo, direccion, localidad, provincia, codPostal, telefono, correoElec);
		this.datosBan = new Cuenta_Bancaria(nombre, apellidos, dni, cuentaBancaria);
		this.representante = representante;
		this.fAlta = fechaAlta;
		this.mayorEdad = mayorEdad;
	}
	
	/**
	 * Metodo usado para crear a los clientes desde la base de datos sin representante
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
	 * @param fechaAlta	 
	 * @param mayorEdad
	 */
	public Cliente(String nombre, String apellidos, String dni, LocalDate fechaNac, int edad, char sexo,
			String direccion, String localidad, String provincia, int codPostal, String telefono, String correoElec,
			LocalDate fechaAlta,boolean mayorEdad,String cuentaBancaria) {
		super(nombre, apellidos, dni, fechaNac, edad, sexo, direccion, localidad, provincia, codPostal, telefono, correoElec);
		this.datosBan = new Cuenta_Bancaria(nombre, apellidos, dni, cuentaBancaria);
		this.fAlta = fechaAlta;
		this.mayorEdad = mayorEdad;
	}
	
	public Cliente(String dni) {
		super(dni);
	}
	
	public Cuenta_Bancaria getDatosBan() {
		return datosBan;
	}

	public void setDatosBan(Cuenta_Bancaria datosBan) {
		this.datosBan = datosBan;
	}

	public LocalDate getfAlta() {
		return fAlta;
	}

	public void setfAlta(LocalDate fAlta) {
		this.fAlta = fAlta;
	}

	public Boolean getMayorEdad() {
		return mayorEdad;
	}

	public void setMayorEdad(Boolean mayorEdad) {
		this.mayorEdad = mayorEdad;
	}

	public Cliente getRepresentante() {
		return representante;
	}

	public void setRepresentante(Cliente representante) {
		this.representante = representante;
	}

	@Override
	public String toString() {
		if(mayorEdad) {
			return  super.toString()+" fecha alta:" + fAlta.toString();
		}else {
			return  super.toString()+" fecha alta:" + fAlta.toString() + "\n"
			+ "\t Representante:\n" + representante;
		}
	}

	
	
}
