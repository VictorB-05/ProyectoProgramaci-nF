package concesionario;

import java.util.Scanner;

public class GestionVehiculos {
	
	static Scanner scanner = new Scanner(System.in);
	
	public static void menu() {
		int opcion = 6;
		do {
			try {
				// Mostrar el menú
				System.out.println("\tGestion Vehiculos:");
				System.out.println("-------------------------------------------------------");
				System.out.println("\t1. Alta Vehículo");
				System.out.println("\t2. Consulta Datos Vehículo (Matrícula)");
				System.out.println("\t3. Baja Vehículo (Matrícula)");
				System.out.println("\t4. Modificar datos Vehículo (Matrícula)");
				System.out.println("\t5. Listado de los vehículos");
				System.out.println("\t6. Volver al menú principal");
				System.out.println("-------------------------------------------------------");
				System.out.print("Seleccione una opción: ");

				// Leer la opción del usuario
				opcion = scanner.nextInt();

				// Realizar la operación correspondiente según la opción seleccionada
				switch (opcion) {
				case 1:
					//altaVehiculo();
					break;
				case 2:
					//consultaDatos(matricula);
					break;
				case 3:
					//bajaVehiculo(matricula);
					break;
				case 4:
					//modificarDatos(matricula);
					break;
				case 5:
					//listarVehiculos();
					break;
				case 6:
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Opción no válida. Intente nuevamente.");
				}

			} catch (NumberFormatException e) {
				System.out.println("Formato no valido");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (opcion != 6);
	}
}
