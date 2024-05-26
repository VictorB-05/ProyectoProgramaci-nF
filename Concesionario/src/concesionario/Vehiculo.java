package concesionario;

import java.time.LocalDate;
/**
 * objeto para gestionar los vehiculos
 */
public class Vehiculo implements IVehiculo{
	private String matricula;
    private String numeroBastidor;
    private String marca;
    private String modelo;
    private int anyoProduc;
    private Tipo_Vehiculo tipo;
    private Motorizacion motor;
    private int potencia;
    private double tamDeposit;
    private int numPuertas;
    private double consumo;
    private LocalDate fechaMatricula;
    private String nive;
    private Etiqueta_Eco etiquetaEco;
    
    /**
     * Constructor para insertar valores que llegen por teclado
     * @param matricula
     * @param numeroBastidor
     * @param marca
     * @param modelo
     * @param anyoProduc
     * @param tipo
     * @param motor
     * @param potencia
     * @param tamDeposit
     * @param numPuertas
     * @param consumo
     * @param fechaMatricula
     * @param nive
     * @param etiquetaEco
     */
	public Vehiculo(String matricula, String numeroBastidor, String marca, String modelo, int anyoProduc,
			Tipo_Vehiculo tipo, Motorizacion motor, int potencia, double tamDeposit, int numPuertas, double consumo,
			LocalDate fechaMatricula, String nive, Etiqueta_Eco etiquetaEco) {
		this.matricula = matricula;
		this.numeroBastidor = numeroBastidor;
		this.marca = marca;
		this.modelo = modelo;
		this.anyoProduc = anyoProduc;
		this.tipo = tipo;
		this.motor = motor;
		this.potencia = potencia;
		this.tamDeposit = tamDeposit;
		this.numPuertas = numPuertas;
		this.consumo = consumo;
		this.fechaMatricula = fechaMatricula;
		this.nive = nive;
		this.etiquetaEco = etiquetaEco;
	}

	/**
	 * Constructor de valores desde la base de datos
	 * @param matricula
	 * @param numeroBastidor
	 * @param marca
	 * @param modelo
	 * @param anyoProduc
	 * @param motor
	 * @param tipo_vehiculo
	 * @param potencia
	 * @param tamDeposit
	 * @param numPuertas
	 * @param consumo
	 * @param fechaMatricula
	 * @param nive
	 * @param etiquetaEco
	 */
	public Vehiculo(String matricula, String numeroBastidor, String marca, String modelo, int anyoProduc,
			String motor, String tipo_vehiculo, int potencia, double tamDeposit, int numPuertas, double consumo,
			LocalDate fechaMatricula, String nive, String etiquetaEco) {
		this.matricula = matricula;
		this.numeroBastidor = numeroBastidor;
		this.marca = marca;
		this.modelo = modelo;
		this.anyoProduc = anyoProduc;
		setTipo(tipo_vehiculo);
		setMotor(motor);
		this.potencia = potencia;
		this.tamDeposit = tamDeposit;
		this.numPuertas = numPuertas;
		this.consumo = consumo;
		this.fechaMatricula = fechaMatricula;
		this.nive = nive;
		setEtiquetaEco(etiquetaEco);
	}
	
	public Vehiculo(String matricula) {
		this.matricula = matricula;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNumeroBastidor() {
		return numeroBastidor;
	}

	public void setNumeroBastidor(String numeroBastidor) {
		this.numeroBastidor = numeroBastidor;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getAnyoProduc() {
		return anyoProduc;
	}

	public void setAnyoProduc(int anyoProduc) {
		this.anyoProduc = anyoProduc;
	}

	public Tipo_Vehiculo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo_Vehiculo tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * Setter de Tipo_Vehiculo por string
	 */
	public void setTipo(String tipo) {
		try {
			// Se intorduce el string en el enum con valueOf
			this.tipo = Tipo_Vehiculo.valueOf(tipo);
		}catch (IllegalArgumentException e) {
			// Si hay algún error se pone un valor por defecto
			this.tipo = Tipo_Vehiculo.Otros;
		}
	}
	
	public Motorizacion getMotor() {
		return motor;
	}

	public void setMotor(Motorizacion motor) {
		this.motor = motor;
	}
	
	/**
	 * Setter de Motorizacion por string
	 */
	public void setMotor(String motor) {
		try {
			// Se intorduce el string en el enum con valueOf
			this.motor = Motorizacion.valueOf(motor);
		}catch (IllegalArgumentException e) {
			// Si hay algún error se pone un valor por defecto
			this.motor = Motorizacion.Diesel;
		}
	}

	public int getPotencia() {
		return potencia;
	}

	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	public double getTamDeposit() {
		return tamDeposit;
	}

	public void setTamDeposit(double tamDeposit) {
		this.tamDeposit = tamDeposit;
	}

	public int getNumPuertas() {
		return numPuertas;
	}

	public void setNumPuertas(int numPuertas) {
		this.numPuertas = numPuertas;
	}

	public double getConsumo() {
		return consumo;
	}

	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}

	public LocalDate getFechaMatricula() {
		return fechaMatricula;
	}

	public void setFechaMatricula(LocalDate fechaMatricula) {
		this.fechaMatricula = fechaMatricula;
	}

	public String getnive() {
		return nive;
	}

	public void setnive(String nive) {
		this.nive = nive;
	}

	public Etiqueta_Eco getEtiquetaEco() {
		return etiquetaEco;
	}

	public void setEtiquetaEco(Etiqueta_Eco etiquetaEco) {
		this.etiquetaEco = etiquetaEco;
	}
	
	/**
	 * Setter de Etiqueta_Eco por string
	 */
	public void setEtiquetaEco(String etiquetaEco) {
		if(etiquetaEco.equals("0")) {
			this.etiquetaEco = Etiqueta_Eco.CERO;
		}else {
			/* No funciona no entiendo el motivo 
			try {
				// Se intorduce el string en el enum con valueOf
				Etiqueta_Eco.valueOf(etiquetaEco);
			}catch (IllegalArgumentException e) {
				// Si hay algún error se pone un valor por defecto
				this.etiquetaEco = Etiqueta_Eco.C;
			}*/
			if(etiquetaEco.equals("ECO")) {
				this.etiquetaEco = Etiqueta_Eco.ECO;
			}else if (etiquetaEco.equals("C")) {
				this.etiquetaEco = Etiqueta_Eco.C;
			}else if (etiquetaEco.equals("B")) {
				this.etiquetaEco = Etiqueta_Eco.B;
			}else if (etiquetaEco.equals("A")) {
				this.etiquetaEco = Etiqueta_Eco.A;
			}
		}
	}

	@Override
	public String toString() {
		return "Matricula: " + matricula + " Numero bastidor: " + numeroBastidor + " Marca: " + marca
				+ " Modelo: " + modelo + " Año producion: " + anyoProduc + " Tipo: " + tipo + " Motor: " + motor+"\n"
				+ "Potencia: " + potencia + " Tamaño deposito: " + tamDeposit + " Numero puertas: " + numPuertas
				+ " Consumo: "+ consumo + " Fecha matricula: " + fechaMatricula + " NIVE: " + nive + " Etiqueta Ecologica: " + etiquetaEco;
	}
    
    
}
