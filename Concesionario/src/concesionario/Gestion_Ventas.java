package concesionario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		try (Conexion conex = new Conexion()) {
			// Si el cliente es menor de edad, se necesita un representante
			String dniCliente = Scanners.IntroS("Introduce el DNI del representante: ");

			// Buscar el representante en la base de datos
			try (PreparedStatement pstmtRep = conex.getConn().prepareStatement("SELECT * FROM cliente WHERE dni = ?")) {
				pstmtRep.setString(1, dniCliente);
				try (ResultSet resultadoRep = pstmtRep.executeQuery()) {
					if (resultadoRep.next()) {
						// Representante encontrado
						Cliente cliente = Gestion_Clientes.crearCliente(resultadoRep); // Método para crear un cliente desde
					} else {
						// Representante no encontrado
						System.out.println("El cliente no existe en la base de datos.");
					}
				}
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

	
}
