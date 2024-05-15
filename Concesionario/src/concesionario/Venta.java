package concesionario;

import java.time.LocalDate;

public class Venta implements IVenta{
	private Vehiculo vehiculo;
	private Cliente cliente;
	private Vendedor vendedor;
	private TipoOperacion tOperacion;
	private LocalDate fecha;
	private TipoVehiculo tVehiculo;
	
	public Venta(Vehiculo vehiculo, Cliente cliente, Vendedor vendedor, TipoOperacion tOperacion, LocalDate fecha,
			TipoVehiculo tVehiculo) {
		this.vehiculo = vehiculo;
		this.cliente = cliente;
		this.vendedor = vendedor;
		this.tOperacion = tOperacion;
		this.fecha = fecha;
		this.tVehiculo = tVehiculo;
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

	public TipoOperacion gettOperacion() {
		return tOperacion;
	}

	public void settOperacion(TipoOperacion tOperacion) {
		this.tOperacion = tOperacion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public TipoVehiculo gettVehiculo() {
		return tVehiculo;
	}

	public void settVehiculo(TipoVehiculo tVehiculo) {
		this.tVehiculo = tVehiculo;
	}

	
	
	
}
