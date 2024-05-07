package concesionario;

import java.time.LocalDateTime;

public class Cliente extends Persona implements ICliente{
	private Cuenta_Bancaria datosBan;
	private LocalDateTime fAlta;
	private Boolean mayorEdad;
	private Cliente representante;
	
	/**
	 * Constructor de aquellos clientes sin representante
	 **/
	public Cliente(String nombre, String apellidos, String dni, LocalDateTime fechaNac, int edad, char sexo,
			String direccion, String localidad, String provincia, int codPostal, String telefono, String correoElec,
			Cuenta_Bancaria datosBan) {
		super(correoElec, correoElec, correoElec, fechaNac, codPostal, sexo, correoElec, correoElec, correoElec,
				codPostal, correoElec, correoElec);
		this.datosBan = datosBan;
		fAlta = LocalDateTime.now();
	}

	public Cliente(String nombre, String apellidos, String dni, LocalDateTime fechaNac, int edad, char sexo,
			String direccion, String localidad, String provincia, int codPostal, String telefono, String correoElec,
			Cuenta_Bancaria datosBan, Cliente representante) {
		super(correoElec, correoElec, correoElec, fechaNac, codPostal, sexo, correoElec, correoElec, correoElec,
				codPostal, correoElec, correoElec);
		this.datosBan = datosBan;
		this.representante = representante;
		fAlta = LocalDateTime.now();
	}

	public Cuenta_Bancaria getDatosBan() {
		return datosBan;
	}

	public void setDatosBan(Cuenta_Bancaria datosBan) {
		this.datosBan = datosBan;
	}

	public LocalDateTime getfAlta() {
		return fAlta;
	}

	public void setfAlta(LocalDateTime fAlta) {
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

	
	
}
