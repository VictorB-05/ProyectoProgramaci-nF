package concesionario;

import java.time.LocalDate;

public class Venta implements IVenta{
	private Vehiculo vehiculo;
	private Cliente cliente;
	private Vendedor vendedor;
	private Tipo_Operacion tOperacion;
	private LocalDate fecha;
	private Tipo_Venta tVenta;
	
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

	public void settOperacion(Tipo_Operacion tOperacion) {
		this.tOperacion = tOperacion;
	}

	public Tipo_Operacion gettOperacion() {
		return tOperacion;
	}

}
