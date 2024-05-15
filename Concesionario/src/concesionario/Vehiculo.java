package concesionario;

import java.time.LocalDate;

public class Vehiculo implements IVehiculo{
	private String matricula;
    private String numeroBastidor;
    private String marca;
    private String modelo;
    private int anyoProduc;
    private TipoVehiculo tipo;
    private Motorizacion motor;
    private int potencia;
    private double tamDeposit;
    private int numPuertas;
    private double consumo;
    private LocalDate fechaMatricula;
    private String nive;
    private EtiquetaEco etiquetaEco;
    
	public Vehiculo(String matricula, String numeroBastidor, String marca, String modelo, int anyoProduc,
			TipoVehiculo tipo, Motorizacion motor, int potencia, double tamDeposit, int numPuertas, double consumo,
			LocalDate fechaMatricula, String nive, EtiquetaEco etiquetaEco) {
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

	public Vehiculo(String matricula, String numeroBastidor, String marca, String modelo, int anyoProduc,
			String motor, String tipo_vehiculo, int potencia, double tamDeposit, int numPuertas, double consumo,
			LocalDate fechaMatricula, String nive, String etiquetaEco) {
		this.matricula = matricula;
		this.numeroBastidor = numeroBastidor;
		this.marca = marca;
		this.modelo = modelo;
		this.anyoProduc = anyoProduc;
		setTipo(tipo);
		setMotor(motor);
		this.potencia = potencia;
		this.tamDeposit = tamDeposit;
		this.numPuertas = numPuertas;
		this.consumo = consumo;
		this.fechaMatricula = fechaMatricula;
		this.nive = nive;
		this.etiquetaEco = etiquetaEco;
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

	public TipoVehiculo getTipo() {
		return tipo;
	}

	public void setTipo(TipoVehiculo tipo) {
		this.tipo = tipo;
	}
	
	public void setTipo(String tipo) {
		try {
			// Se intorduce el string en el enum con valueOf
			this.tipo = TipoVehiculo.valueOf(tipo);
		}catch (IllegalArgumentException e) {
			// Si hay algún error se pone un valor por defecto
			this.tipo = TipoVehiculo.otros;
		}
	}
	
	public Motorizacion getMotor() {
		return motor;
	}

	public void setMotor(Motorizacion motor) {
		this.motor = motor;
	}
	
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

	public EtiquetaEco getEtiquetaEco() {
		return etiquetaEco;
	}

	public void setEtiquetaEco(EtiquetaEco etiquetaEco) {
		this.etiquetaEco = etiquetaEco;
	}
	
	public void setEtiquetaEco(String etiquetaEco) {
		if(etiquetaEco.equals("0")) {
			this.etiquetaEco = EtiquetaEco.CERO;
		}
		try {
			// Se intorduce el string en el enum con valueOf
			EtiquetaEco.valueOf(etiquetaEco);
		}catch (IllegalArgumentException e) {
			// Si hay algún error se pone un valor por defecto
			this.etiquetaEco = EtiquetaEco.C;
		}
	}

	@Override
	public String toString() {
		return "Vehiculo [matricula=" + matricula + ", numeroBastidor=" + numeroBastidor + ", marca=" + marca
				+ ", modelo=" + modelo + ", anyoProduc=" + anyoProduc + ", tipo=" + tipo + ", motor=" + motor
				+ ", potencia=" + potencia + ", tamDeposit=" + tamDeposit + ", numPuertas=" + numPuertas + ", consumo="
				+ consumo + ", fechaMatricula=" + fechaMatricula + ", nive=" + nive + ", etiquetaEco=" + etiquetaEco
				+ "]";
	}
    
    
}
