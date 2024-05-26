package concesionario;

import java.time.LocalDate;

/**
 * objeto para gestionar las ventas
 */
public class Venta implements IVenta{
	private Vehiculo vehiculo;
	private Cliente cliente;
	private Vendedor vendedor;
	private Tipo_Operacion tOperacion;
	private LocalDate fecha;
	private Tipo_Venta tVenta;
	
	/**
	 * Constructor para valores desde la base de datos
	 * @param matricula
	 * @param clienteDni
	 * @param vendedorDni
	 * @param tOperacion
	 * @param fecha
	 * @param tVenta
	 */
	public Venta(String  matricula, String clienteDni, String vendedorDni, String tOperacion, LocalDate fecha,
			String tVenta) {
		this.vehiculo = new Vehiculo(matricula);
		this.cliente = new Cliente(clienteDni);
		this.vendedor = new Vendedor(vendedorDni);
		settOperacion(tOperacion);
		this.fecha = fecha;
		settVenta(tVenta);
	}

	/**
	 * Constructor para insertar valores que llegen por teclado
	 * @param vehiculo
	 * @param cliente
	 * @param vendedor
	 * @param tOperacion
	 * @param fecha
	 * @param tVenta
	 */
	public Venta(Vehiculo vehiculo, Cliente cliente, Vendedor vendedor, Tipo_Operacion tOperacion, LocalDate fecha,
			Tipo_Venta tVenta) {
		this.vehiculo = vehiculo;
		this.cliente = cliente;
		this.vendedor = vendedor;
		this.tOperacion = tOperacion;
		this.fecha = fecha;
		this.tVenta = tVenta;
	}
	
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Tipo_Venta gettVenta() {
		return tVenta;
	}

	public void settVenta(Tipo_Venta tVenta) {
		this.tVenta = tVenta;
	}
	
	/**
	 * Setter de Tipo_Venta por string
	 */
	public void settVenta(String tVenta) {
		try {
			this.tVenta =Tipo_Venta.valueOf(tVenta);
		}catch (IllegalArgumentException e) {
			//Si da un error se pone un valor por defecto
			System.out.println("Error en el sistema valor por defecto en tipo venta");
			this.tVenta = Tipo_Venta.Otro;
		}
	}

	public Tipo_Operacion gettOperacion() {
		return tOperacion;
	}
	
	public void settOperacion(Tipo_Operacion tOperacion) {
		this.tOperacion = tOperacion;
	}
	
	/**
	 * Setter de Tipo_Operacion por string
	 */
	public void settOperacion(String tOperacion) {
		try {
			this.tOperacion =Tipo_Operacion.valueOf(tOperacion);
		}catch (IllegalArgumentException e) {
			//Si da un error se pone un valor por defecto
			System.out.println("Error en el sistema valor por defecto en tipo venta");
			this.tOperacion = Tipo_Operacion.Otro;
		}
	}

	@Override
	public String toString() {
		return "Fecha: "+ fecha.toString()+" Tipo de operacion: "+ tOperacion+" Tipo venta: "+tVenta+"\n"+
				"Por el vendedor: "+vendedor.toString()+"\n"+
				"El vehiculo: "+vehiculo.toString()+"\n"+
				"Al cliente: "+cliente.toString(); 
	}
	
}
