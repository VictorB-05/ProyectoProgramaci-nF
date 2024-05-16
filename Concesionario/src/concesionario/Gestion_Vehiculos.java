package concesionario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class Gestion_Vehiculos {
	
	static Scanner scanner = new Scanner(System.in);
	
	/**
	 * 
	 */
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
					altaVehiculo();
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
					listarVehiculos();
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

	private static void altaVehiculo() {
		
		
	}
	/**
	 * 
	 * @throws Exception
	 */
	private static void listarVehiculos() throws Exception{
		try(Conexion conex = new Conexion();
			Statement base = conex.getConn().createStatement()){
			ResultSet salida = base.executeQuery("SELECT * FROM vehiculo");
			crearVehiculo(salida);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * metodo que crea un vehiculo que llegue desde una base de datos
	 * @author victor
	 * @param resultado la sentencia donde esta el vehiculo que quiere construir
	 * @throws SQLException
	 * @throws Exception
	 */
	private static void crearVehiculo(ResultSet resultado) throws SQLException,Exception {
		String matricula = resultado.getString("matricula");
		String numeroBastidor = resultado.getString("bastidor");
		String marca = resultado.getString("marca");
		String modelo = resultado.getString("modelo");
		int anyoProduc = resultado.getInt("anyo");
		double tamDeposit = resultado.getShort("deposito");
		int numPuertas = resultado.getShort("n_puertas");
		int potencia = resultado.getShort("potencia");
		String motor = resultado.getString("motorizacion");
		String tipo_vehiculo = resultado.getString("tipo_vehiculo");
		double consumo = resultado.getShort("consumo");
		LocalDate fechaMatricula = resultado.getDate("fecha_matricula").toLocalDate();
		String nive = resultado.getString("nive");
		String etiquetaEco = resultado.getString("etiqueta_eco");
		
		new Vehiculo(matricula, numeroBastidor, marca, modelo, anyoProduc, motor, tipo_vehiculo, potencia, tamDeposit, numPuertas, consumo, fechaMatricula, nive, etiquetaEco);	
	}
	
}
