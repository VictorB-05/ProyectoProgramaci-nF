package concesionario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Gestion_Ventas {
	
	public static void menu() {
	    int opcion = 0;
	    String input;
	    do {
	        try {
	            // Mostrar el menú
	            System.out.println("\tGestion de Ventas:");
	            System.out.println("-------------------------------------------------------");
	            System.out.println("\t1. Realizar Venta");
	            System.out.println("\t2. Consultar Ventas del año (Año)");
	            System.out.println("\t3. Consultar Ventas del vendedor (Vendedor)");
	            System.out.println("\t4. Consultar Ventas por tipo de operación (Operación)");
	            System.out.println("\t5. Consultar Ventas por tipo de vehículo (Vehículo)");
	            System.out.println("\t6. Volver al menú principal");
	            System.out.println("-------------------------------------------------------");

	            // Leer la opción del usuario
	            opcion = Scanners.IntroI("Seleccione una opción: ");

	            // Realizar la operación correspondiente según la opción seleccionada
	            switch (opcion) {
	                case 1:
	                    realizarVenta();
	                    break;
	                case 2:
	                    int anyo = Scanners.IntroI("Introduce el año a consultar: ");
	                    consultarVentasAnyo(anyo);
	                    break;
	                case 3:
	                    System.out.println("Introduce el nombre del vendedor: ");
	                    input = Scanners.String.nextLine();
	                    consultarVentasPorVendedor(input);
	                    break;
	                case 4:
	                    System.out.println("Introduce el tipo de operación: ");
	                    input = Scanners.String.nextLine();
	                    consultarVentasPorOperacion(input);
	                    break;
	                case 5:
	                    System.out.println("Introduce el tipo de vehículo: ");
	                    input = Scanners.String.nextLine();
	                    consultarVentasPorVehiculo(input);
	                    break;
	                case 6:
	                    System.out.println("Saliendo...");
	                    break;
	                default:
	                    System.out.println("Opción no válida. Intente nuevamente.");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } while (opcion != 6);
	}

	public static void realizarVenta()throws Exception {
		Vehiculo vehiculo;
		Cliente cliente;
		Vendedor vendedor;
		try (Conexion conex = new Conexion()) {
			
			String matricula = Scanners.IntroS("Introduce la matricula del vehiculo: ");
			
			// Buscar el vendedor en la base de datos
			try (PreparedStatement pstmtRep = conex.getConn().prepareStatement("SELECT * FROM vehiculo WHERE matricula = ?")) {
				pstmtRep.setString(1, matricula);
				try (ResultSet resultadoRep = pstmtRep.executeQuery()) {
					if (resultadoRep.next()) {
						// cliente encontrado
						vehiculo = Gestion_Vehiculos.crearVehiculo(resultadoRep); 
					} else {
						// cliente no encontrado
						throw new Exception("El vehiculo no existe en la base de datos.");
					}
				}
			}
			
			String dniCliente = Scanners.IntroS("Introduce el DNI del cliente: ");

			// Buscar el cliente en la base de datos
			try (PreparedStatement pstmtRep = conex.getConn().prepareStatement("SELECT * FROM cliente WHERE dni = ?")) {
				pstmtRep.setString(1, dniCliente);
				try (ResultSet resultadoRep = pstmtRep.executeQuery()) {
					if (resultadoRep.next()) {
						// cliente encontrado
						cliente = Gestion_Clientes.crearCliente(resultadoRep); 
					} else {
						// cliente no encontrado
						throw new Exception("El cliente no existe en la base de datos.");
					}
				}
			}
			String dniVendedor = Scanners.IntroS("Introduce el DNI del vendedor: ");

			// Buscar el vendedor en la base de datos
			try (PreparedStatement pstmtRep = conex.getConn().prepareStatement("SELECT * FROM vendedor WHERE dni = ?")) {
				pstmtRep.setString(1, dniVendedor);
				try (ResultSet resultadoRep = pstmtRep.executeQuery()) {
					if (resultadoRep.next()) {
						// cliente encontrado
						vendedor = Gestion_Vendedores.crearVendedor(resultadoRep); 
					} else {
						// cliente no encontrado
						throw new Exception("El venderor no existe en la base de datos.");
					}
				}
			}
			Tipo_Operacion tipo = leerTipoOperacion();
			System.out.println("Se ha asignado la fecha de hoy");
			LocalDate fecha = LocalDate.now(); 
			System.out.println("Se ha asignado la cuenta del cliente");
			Cuenta_Bancaria banco = cliente.getDatosBan();
			Tipo_Venta tVenta = leerTipoVenta();
			try (Statement statement = conex.getConn().createStatement();
					ResultSet salida = statement.executeQuery("SELECT idventa FROM venta order by idventa DESC;");
					PreparedStatement pstmtRep = conex.getConn().prepareStatement("INSERT INTO venta (idventa, vehiculo, cliente, vendedor, cuenta_bancaria, fecha_opera, tipo_operacion, tipo_venta) values (?,?,?,?,?,?,?,?)")){
				Venta venta = new Venta(vehiculo, cliente, vendedor, tipo, fecha, tVenta);
				int id = 1;
				if(salida.next()) {
					id = salida.getInt("idventa") +1;
				}
				pstmtRep.setInt(1, id);
				pstmtRep.setString(2, vehiculo.getMatricula());
				pstmtRep.setString(3, cliente.getDni());
				pstmtRep.setString(4, vendedor.getDni());
				pstmtRep.setString(5, banco.getCuentaIban());
				pstmtRep.setString(6, venta.getFecha().toString());
				pstmtRep.setString(7, venta.gettOperacion().toString());
				pstmtRep.setString(8, venta.gettVenta().toString());
				pstmtRep.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void consultarVentasAnyo(int anyo) {
	    // Implementar lógica para consultar las ventas del año dado
	}

	public static void consultarVentasPorVendedor(String vendedor) {
	    // Implementar lógica para consultar las ventas de un vendedor específico
	}

	public static void consultarVentasPorOperacion(String operacion) {
	    // Implementar lógica para consultar las ventas por tipo de operación
	}

	public static void consultarVentasPorVehiculo(String vehiculo) {
	    // Implementar lógica para consultar las ventas por tipo de vehículo
	}

	/**
	 * Metodo el cual muestra todas las opciones del enum Motorizacion y las enumera
	 * cuando se introduzca el numero correspondiente a un valor se asignara ese
	 * valor
	 * 
	 * @return el enum correspondiente al numero marcado
	 * @see IntroI
	 */
	private static Tipo_Operacion leerTipoOperacion() {
		int opcion;
		do {
			String mensaje = "Seleccione la tipo operacion:\n";
			for (int i = 0; i < Tipo_Operacion.values().length; i++) {
				mensaje += (i + 1) + ". " + Tipo_Operacion.values()[i]+"\n";
			}
			opcion = Scanners.IntroI(mensaje);
			opcion--;
		} while (opcion > Tipo_Operacion.values().length);
		return Tipo_Operacion.values()[opcion];
	}

	/**
	 * Metodo el cual muestra todas las opciones del enum EtiquetaEco y las enumera
	 * cuando se introduzca el numero correspondiente a un valor se asignara ese
	 * valor
	 * 
	 * @return el enum correspondiente al numero marcado
	 * @see IntroI
	 */
	private static Tipo_Venta leerTipoVenta() {
		int opcion;
		do {
			String mensaje = "Seleccione la tipo de venta:\n";
			for (int i = 0; i < Tipo_Venta.values().length; i++) {
				mensaje += (i + 1) + ". " + Tipo_Venta.values()[i]+"\n";
			}
			opcion = Scanners.IntroI(mensaje);
			opcion--;
		} while (opcion > Tipo_Venta.values().length);
		return Tipo_Venta.values()[opcion];
	}
	
}
