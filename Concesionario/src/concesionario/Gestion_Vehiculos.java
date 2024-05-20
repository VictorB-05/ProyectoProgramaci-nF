
package concesionario;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class Gestion_Vehiculos {
		
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
				opcion = Scanners.Int.nextInt();

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
        System.out.print("Ingrese la matrícula: ");
        String matricula = Scanners.String.nextLine();

        System.out.print("Ingrese el número de bastidor: ");
        String numeroBastidor = Scanners.String.nextLine();

        System.out.print("Ingrese la marca: ");
        String marca = Scanners.String.nextLine();

        System.out.print("Ingrese el modelo: ");
        String modelo = Scanners.String.nextLine();

        System.out.print("Ingrese el año de producción: ");
        int anyoProduc = Scanners.Int.nextInt();

        TipoVehiculo tipo = leerTipoVehiculo();
        Motorizacion motor = leerMotorizacion();

        System.out.print("Ingrese la potencia: ");
        int potencia = Scanners.Int.nextInt();

        System.out.print("Ingrese el tamaño del depósito: ");
        double tamDeposit = Scanners.Double.nextDouble();

        System.out.print("Ingrese el número de puertas: ");
        int numPuertas = Scanners.Int.nextInt();

        System.out.print("Ingrese el consumo: ");
        double consumo = Scanners.Double.nextDouble();

        System.out.print("Ingrese la fecha de matriculación (YYYY-MM-DD): ");
        String fechaMatriculaStr = Scanners.String.nextLine();
        LocalDate fechaMatricula = LocalDate.parse(fechaMatriculaStr);

        System.out.print("Ingrese el nivel: ");
        String nive = Scanners.String.nextLine();

        EtiquetaEco etiquetaEco = leerEtiquetaEco();

        Vehiculo vehiculo = new Vehiculo(matricula, numeroBastidor, marca, modelo, anyoProduc, tipo, motor, potencia, tamDeposit, numPuertas, consumo, fechaMatricula, nive, etiquetaEco);
        
        try(Conexion conx = new Conexion();
        	PreparedStatement pstmt = conx.getConn().prepareStatement("INSERT INTO vehiculo (matricula, bastidor, marca, modelo, anyo, deposito, n_puertas, potencia, motorizacion, tipo_vehiculo, consumo, fecha_matricula, nive, etiqueta_eco) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")){
        	
        } catch (SQLException e) {
			e.printStackTrace();
		}
        
	}
	/**
	 *  <p>Metodo que lista todos los vehiculos de la base de datos
	 * 
	 * @author Victor
	 * @throws Exception
	 */
	private static void listarVehiculos() throws Exception{
		try(Conexion conex = new Conexion();
			Statement base = conex.getConn().createStatement()){
			ResultSet salida = base.executeQuery("SELECT * FROM vehiculo");
			for(int i = 1;salida.next();i++) {
				Vehiculo vehiculo = crearVehiculo(salida);
				System.out.println("\t Vehiculo: "+i);
				System.out.println(vehiculo);
				System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>Metodo que crea un vehiculo que llegue desde una base de datos
	 * <p>Se cambia el String fecha matricula por un localDate al introducirlo en vehiculo
	 * 
	 * @author Victor
	 * @param resultado la sentencia donde esta el vehiculo que quiere construir
	 * @throws SQLException
	 * @throws Exception
	 */
	private static Vehiculo crearVehiculo(ResultSet resultado) throws SQLException,Exception {
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
		String fecha_matricula = montarDate(resultado.getString("fecha_matricula"));
		LocalDate fechaMatricula = LocalDate.parse(fecha_matricula); 
		String nive = resultado.getString("nive");
		String etiquetaEco = resultado.getString("etiqueta_eco");
		
		return 	new Vehiculo(matricula, numeroBastidor, marca, modelo, anyoProduc, motor, tipo_vehiculo, potencia, tamDeposit, numPuertas, consumo, fechaMatricula, nive, etiquetaEco);	

	}
	/**
	 * Cambia el formato de la fecha para poder parsearlo a un localDate
	 * @author Victor
	 * @param fecha se intoduce una fecha que esta en sistema DD/MM/AAAA
	 * @return la fecha sale como AAAA-MM-DD
	 */
	private static String montarDate(String fecha) {
		String [] arrayAux = fecha.split("/");
		fecha = arrayAux[2]+"-"+arrayAux[1]+"-"+arrayAux[0];
		return fecha;
	}
	
	/**
	 * Cambia el formato de la fecha de un localDate para meterlo en la Base de datos
	 * @author Victor
	 * @param fecha se intoduce una fecha que esta en sistema AAAA-MM-DD
	 * @return la fecha sale como DD/MM/AAAA
	 */
	private static String desmontarDate(String fecha) {
		String [] arrayAux = fecha.split("-");
		fecha = arrayAux[2]+"/"+arrayAux[1]+"/"+arrayAux[0];
		return fecha;
	}
	
	private static TipoVehiculo leerTipoVehiculo() {
        System.out.println("Seleccione el tipo de vehículo:");
        for (int i = 0; i < TipoVehiculo.values().length; i++) {
            System.out.println((i + 1) + ". " + TipoVehiculo.values()[i]);
        }
        int opcion = Scanners.Int.nextInt();
        return TipoVehiculo.values()[opcion - 1];
    }

    private static Motorizacion leerMotorizacion() {
        System.out.println("Seleccione la motorización:");
        for (int i = 0; i < Motorizacion.values().length; i++) {
            System.out.println((i + 1) + ". " + Motorizacion.values()[i]);
        }
        int opcion = Scanners.Int.nextInt();
        return Motorizacion.values()[opcion - 1];
    }
	
	 private static EtiquetaEco leerEtiquetaEco() {
	        System.out.println("Seleccione la etiqueta ecológica:");
	        for (int i = 0; i < EtiquetaEco.values().length; i++) {
	            System.out.println((i + 1) + ". " + EtiquetaEco.values()[i]);
	        }
	        int opcion = Scanners.Int.nextInt();
	        return EtiquetaEco.values()[opcion - 1];
	    }

}
