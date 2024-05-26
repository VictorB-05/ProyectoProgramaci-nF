package concesionario;
/**
 * Main de la aplicacción
 */
public class Principal {
	
	/**
	 * Menu principal
	 * @param args
	 */
	public static void main(String[] args) {
		int opcion = 6;
		do {
			try {
				// Mostrar el menú
				System.out.println("\tMENU:");
				System.out.println("-----------------------------------------");
				System.out.println("\t1. GESTIÓN DE VEHÍCULOS");
				System.out.println("\t2. GESTIÓN DE CLIENTES");
				System.out.println("\t3. GESTIÓN DE VENDEDORES");
				System.out.println("\t4. GESTIÓN DE VENTAS");
				System.out.println("\t5. SALIR");
				System.out.println("-----------------------------------------");

				// Leer la opción del usuario
				opcion = Scanners.IntroI("Seleccione una opción: ");

				// Realizar la operación correspondiente según la opción seleccionada
				switch (opcion) {
				case 1:
					Gestion_Vehiculos.menu();
					break;
				case 2:
					Gestion_Clientes.menu();
					break;
				case 3:
					Gestion_Vendedores.menu();
					break;
				case 4:
					Gestion_Ventas.menu();
					break;
				case 5:
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Opción no válida. Intente nuevamente.");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (opcion != 5);
	}
}
