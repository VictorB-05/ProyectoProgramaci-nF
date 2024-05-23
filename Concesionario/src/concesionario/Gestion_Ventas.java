package concesionario;

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
	                    int year = Scanners.IntroI("Introduce el año a consultar: ");
	                    consultarVentasPorAno(year);
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

	public static void realizarVenta() {
	    // Implementar lógica para realizar una venta
	}

	public static void consultarVentasPorAno(int year) {
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
