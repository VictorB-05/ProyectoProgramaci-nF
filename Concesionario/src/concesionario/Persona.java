package concesionario;

import java.time.LocalDate;

public class Persona implements IPersona {
	private String nombre;
	private String apellidos;
	private String dni;
	private LocalDate fechaNac;
	private int edad;
	private char sexo;
	private String direccion;
	private String localidad;
	private String provincia;
	private int codPostal;
	private String telefono;
	private String correoElec;

	public Persona(String nombre, String apellidos, String dni, LocalDate fechaNac, int edad, char sexo,
			String direccion, String localidad, String provincia, int codPostal, String telefono, String correoElec) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.fechaNac = fechaNac;
		this.edad = edad;
		this.sexo = sexo;
		this.direccion = direccion;
		this.localidad = localidad;
		this.provincia = provincia;
		this.codPostal = codPostal;
		this.telefono = telefono;
		this.correoElec = correoElec;
	}

	public Persona(String dni) {
		this.dni = dni;
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

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public int getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(int codPostal) {
		this.codPostal = codPostal;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreoElec() {
		return correoElec;
	}

	public void setCorreoElec(String correoElec) {
		this.correoElec = correoElec;
	}

	@Override
	public String toString() {
		return "Nombre: " + nombre + " apellidos: " + apellidos + " dni: " + dni + " fechaNac: " + fechaNac + " edad: "
				+ edad + " sexo: " + sexo + " direccion: " + direccion + " localidad: " + localidad + "\n"
				+ " provincia: " + provincia + " codPostal: " + codPostal + " telefono: " + telefono + " correoElec: "
				+ correoElec;
	}

}
