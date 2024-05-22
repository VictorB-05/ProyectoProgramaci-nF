
package concesionario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Gestion_Vehiculos {

	public static void menu() {
		int opcion = 6;
		String matricula;
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

				// Leer la opción del usuario
				opcion = Scanners.IntroI("Seleccione una opción: ");

				// Realizar la operación correspondiente según la opción seleccionada
				switch (opcion) {
				case 1:
					altaVehiculo();
					break;
				case 2:
					System.out.println("Introduce la matricula: ");
					matricula = Scanners.String.nextLine();
					consultaDatos(matricula);
					break;
				case 3:
					System.out.println("Introduce la matricula: ");
					matricula = Scanners.String.nextLine();
					bajaVehiculo(matricula);
					break;
				case 4:
					System.out.println("Introduce la matricula: ");
					matricula = Scanners.String.nextLine();
					modificarDatos(matricula);
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

			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (opcion != 6);
	}

	/**
	 * Metodo que intorduce un vehiculo a la base de datos metido desde teclado para
	 * insertarlo atraves de un PreparedStatement
	 * <p> La conexión esta hecha atraves de la clase Conexion que importa AutoCloseable
	 * lo que la hace usable en un try-with esto sirve para que el programador no tenga 
	 * que preocuparse de que por algún error la conexión no se cierre
	 * @author victor
	 * @see PreparedStatement
	 * @see crearVehiculo
	 * @see Vehiculo 
	 * @see Conexion
	 */
	public static void altaVehiculo() {
		String matricula = Scanners.IntroS("Ingrese la matrícula: ");

		String numeroBastidor = Scanners.IntroS("Ingrese el número de bastidor: ");

		String marca = Scanners.IntroS("Ingrese la marca: ");

		String modelo = Scanners.IntroS("Ingrese el modelo: ");

		int anyoProduc = Scanners.IntroI("Ingrese el año de producción: ");

		TipoVehiculo tipo = leerTipoVehiculo();
		Motorizacion motor = leerMotorizacion();

		int potencia = Scanners.IntroI("Ingrese la potencia: ");

		double tamDeposit = Scanners.IntroD("Ingrese el tamaño del depósito: ");

		int numPuertas = Scanners.IntroI("Ingrese el número de puertas: ");

		double consumo = Scanners.IntroD("Ingrese el consumo: ");

		LocalDate fechaMatricula = null;
		boolean rep = false;
		do {
			try {
				String fechaMatriculaStr = Scanners.IntroS("Ingrese la fecha de matriculación (YYYY-MM-DD): ");
				fechaMatricula = LocalDate.parse(fechaMatriculaStr);
				rep = false;
			} catch (DateTimeParseException e) {
				System.out.println("Formato de fecha no valido");
				rep = true;
			}
		} while (rep);

		String nive = Scanners.IntroS("Ingrese el NIVE: ");

		EtiquetaEco etiquetaEco = leerEtiquetaEco();

		Vehiculo vehiculo = new Vehiculo(matricula, numeroBastidor, marca, modelo, anyoProduc, tipo, motor, potencia,
				tamDeposit, numPuertas, consumo, fechaMatricula, nive, etiquetaEco);

		try (Conexion conx = new Conexion();
				PreparedStatement pstmt = conx.getConn().prepareStatement(
						"INSERT INTO vehiculo (matricula, bastidor, marca, modelo, anyo, deposito, n_puertas, potencia, motorizacion, tipo_vehiculo, consumo, fecha_matricula, nive, etiqueta_eco) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
			pstmt.setString(1, vehiculo.getMatricula());
			pstmt.setString(2, vehiculo.getNumeroBastidor());
			pstmt.setString(3, vehiculo.getMarca());
			pstmt.setString(4, vehiculo.getModelo());
			pstmt.setInt(5, vehiculo.getAnyoProduc());
			pstmt.setDouble(6, vehiculo.getTamDeposit());
			pstmt.setInt(7, vehiculo.getNumPuertas());
			pstmt.setInt(8, vehiculo.getPotencia());
			pstmt.setString(9, vehiculo.getMotor().toString());
			pstmt.setString(10, vehiculo.getTipo().toString());
			pstmt.setDouble(11, vehiculo.getConsumo());
			pstmt.setString(12, Scanners.desmontarDate(vehiculo.getFechaMatricula().toString()));
			pstmt.setString(13, vehiculo.getnive());
			pstmt.setString(14, vehiculo.getEtiquetaEco().toString());

			pstmt.executeUpdate();
			System.out.println("Vehiculo ingresado");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo que recibe una matricula y da los datos del vehiculo con esa matricula
	 * que este en la base de datos se utiliza un PreparedStatement para mayor
	 * integridad de la base de datos
	 * <p> La conexión esta hecha atraves de la clase Conexion que importa AutoCloseable
	 * lo que la hace usable en un try-with esto sirve para que el programador no tenga 
	 * que preocuparse de que por algún error la conexión no se cierre
	 * @param matricula
	 * @throws Exception	 
	 * @see crearVehiculo
	 * @see Vehiculo 
	 * @see Conexion
	 */
	public static void consultaDatos(String matricula) throws Exception {
		try (Conexion conex = new Conexion();
				PreparedStatement pstmt = conex.getConn()
						.prepareStatement("SELECT * FROM vehiculo WHERE matricula = ? ")) {
			pstmt.setString(1, matricula);
			try (ResultSet salida = pstmt.executeQuery()) {
				if (salida.next()) {
					Vehiculo vehiculo = crearVehiculo(salida);
					System.out.println(vehiculo);
				} else {
					System.out.println("Matricula no encontrada en la base de datos");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que recibe una matricula y elimina el vehiculo con esa matricula que
	 * este en la base de datos se utiliza un PreparedStatement para mayor
	 * integridad de la base de datos
	 * <p> La conexión esta hecha atraves de la clase Conexion que importa AutoCloseable
	 * lo que la hace usable en un try-with esto sirve para que el programador no tenga 
	 * que preocuparse de que por algún error la conexión no se cierre
	 * @param matricula
	 * @throws Exception
	 * @see crearVehiculo
	 * @see Vehiculo 
	 * @see Conexion
	 */
	public static void bajaVehiculo(String matricula) throws Exception {
		try (Conexion conex = new Conexion();
				PreparedStatement pstmt = conex.getConn()
						.prepareStatement("SELECT * FROM vehiculo WHERE matricula = ? ");
				PreparedStatement baja = conex.getConn()
						.prepareStatement("DELETE FROM vehiculo WHERE matricula = ? ")) {
			pstmt.setString(1, matricula);
			baja.setString(1, matricula);
			try (ResultSet salida = pstmt.executeQuery()) {
				if (salida.next()) {
					Vehiculo vehiculo = crearVehiculo(salida);
					System.out.println(vehiculo);
					System.out.println("Quieres eliminar este vehiculo (SI/NO)");
					String opcion = Scanners.String.nextLine();
					if (opcion.equalsIgnoreCase("SI")) {
						baja.execute();
					} else {
						System.out.println("Vehiculo no eliminado");
					}
				} else {
					System.out.println("Matricula no encontrada en la base de datos");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  Esta clase modifica los datos de un vehiculo en la base de datos atraves de una matricula
	 *  dada por el usuario para la integridad de la base de datos se usa un PreparedStatement,
	 *  busacamos el vehiculo de la matricula si no existe la matricula se notifica al usuario.
	 *  Si existe se la mostraran las opciones de que atributos cambiar y cuales son en el vehiculo.
	 *  <p>
	 *  Además cada vez que se elige una opción se actualizara el metodo tanto en el obejto vehiculo,
	 *  como en la base de datos
	 *  <p> La conexión esta hecha atraves de la clase Conexion que importa AutoCloseable
	 *  lo que la hace usable en un try-with esto sirve para que el programador no tenga 
	 * @param matricula
	 * @throws Exception
	 * @see crearVehiculo
	 * @see Vehiculo 
	 * @see Conexion
	 */
	public static void modificarDatos(String matricula) throws Exception {
		try (Conexion conex = new Conexion();
				PreparedStatement pstmt = conex.getConn()
						.prepareStatement("SELECT * FROM vehiculo WHERE matricula = ?")) {
			pstmt.setString(1, matricula);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
				    Vehiculo vehiculo = crearVehiculo(rs);
				    System.out.println("Vehículo encontrado opciones a actualizar:");
				    System.out.println("1. Número de bastidor ------ "+vehiculo.getNumeroBastidor());
				    System.out.println("2. Marca ------------------- "+vehiculo.getMarca());
				    System.out.println("3. Modelo ------------------ "+vehiculo.getModelo());
				    System.out.println("4. Año de producción ------- "+vehiculo.getAnyoProduc());
				    System.out.println("5. Tamaño del depósito ----- "+vehiculo.getModelo());
				    System.out.println("6. Número de puertas ------- "+vehiculo.getNumPuertas());
				    System.out.println("7. Potencia ---------------- "+vehiculo.getPotencia());
				    System.out.println("8. Motorización ------------ "+vehiculo.getMotor());
				    System.out.println("9. Tipo de vehículo -------- "+vehiculo.getTipo());
				    System.out.println("10. Consumo ---------------- "+vehiculo.getConsumo());
				    System.out.println("11. Fecha de matriculación - "+vehiculo.getFechaMatricula());
				    System.out.println("12. Nive ------------------- "+vehiculo.getnive());
				    System.out.println("13. Etiqueta ecológica ----- "+vehiculo.getEtiquetaEco());
				    int op = Scanners.IntroI("Selecciona el campo que deseas actualizar:");

				    String updateSQL = null;

				    // los diferentes updates dependiendo de la opción
				    switch (op) {
				    case 1:
				        updateSQL = "UPDATE vehiculo SET bastidor = ? WHERE matricula = ?";
				        break;
				    case 2:
				        updateSQL = "UPDATE vehiculo SET marca = ? WHERE matricula = ?";
				        break;
				    case 3:
				        updateSQL = "UPDATE vehiculo SET modelo = ? WHERE matricula = ?";
				        break;
				    case 4:
				        updateSQL = "UPDATE vehiculo SET anyo = ? WHERE matricula = ?";
				        break;
				    case 5:
				        updateSQL = "UPDATE vehiculo SET deposito = ? WHERE matricula = ?";
				        break;
				    case 6:
				        updateSQL = "UPDATE vehiculo SET n_puertas = ? WHERE matricula = ?";
				        break;
				    case 7:
				        updateSQL = "UPDATE vehiculo SET potencia = ? WHERE matricula = ?";
				        break;
				    case 8:
				        updateSQL = "UPDATE vehiculo SET motorizacion = ? WHERE matricula = ?";
				        break;
				    case 9:
				        updateSQL = "UPDATE vehiculo SET tipo_vehiculo = ? WHERE matricula = ?";
				        break;
				    case 10:
				        updateSQL = "UPDATE vehiculo SET consumo = ? WHERE matricula = ?";
				        break;
				    case 11:
				        updateSQL = "UPDATE vehiculo SET fecha_matricula = ? WHERE matricula = ?";
				        break;
				    case 12:
				        updateSQL = "UPDATE vehiculo SET nive = ? WHERE matricula = ?";
				        break;
				    case 13:
				        updateSQL = "UPDATE vehiculo SET etiqueta_eco = ? WHERE matricula = ?";
				        break;
				    default:
				        updateSQL = null;
				        break;
				    }

				    if (updateSQL == null) {
				        System.out.println("Opción no válida");
				    } else {
				        try (PreparedStatement updateStmt = conex.getConn().prepareStatement(updateSQL)) {
				            switch (op) {
				            case 1:
				                String nuevoBastidor = Scanners.IntroS("Ingrese nuevo número de bastidor:");
				                updateStmt.setString(1, nuevoBastidor);
				                break;
				            case 2:
				                String nuevaMarca = Scanners.IntroS("Ingrese nueva marca:");
				                updateStmt.setString(1, nuevaMarca);
				                break;
				            case 3:
				                String nuevoModelo = Scanners.IntroS("Ingrese nuevo modelo:");
				                updateStmt.setString(1, nuevoModelo);
				                break;
				            case 4:
				                int nuevoAnyo = Scanners.IntroI("Ingrese nuevo año de producción:");
				                updateStmt.setInt(1, nuevoAnyo);
				                break;
				            case 5:
				                double nuevoDeposito = Scanners.IntroD("Ingrese nuevo tamaño del depósito:");
				                updateStmt.setDouble(1, nuevoDeposito);
				                break;
				            case 6:
				                int nuevoPuertas = Scanners.IntroI("Ingrese nuevo número de puertas:");
				                updateStmt.setInt(1, nuevoPuertas);
				                break;
				            case 7:
				                int nuevaPotencia = Scanners.IntroI("Ingrese nueva potencia:");
				                updateStmt.setInt(1, nuevaPotencia);
				                break;
				            case 8:
				            	Motorizacion motor = leerMotorizacion();
				                updateStmt.setString(1, motor.toString());
				                break;
				            case 9:
				            	TipoVehiculo tipo = leerTipoVehiculo();
				                updateStmt.setString(1, tipo.toString());
				                break;
				            case 10:
				                double nuevoConsumo = Scanners.IntroD("Ingrese nuevo consumo:");
				                updateStmt.setDouble(1, nuevoConsumo);
				                break;
				            case 11:
				                String nuevaFecha = Scanners
				                    .IntroS("Ingrese nueva fecha de matriculación (YYYY-MM-DD):");
				                updateStmt.setString(1, nuevaFecha);
				                break;
				            case 12:
				                String nuevoNive = Scanners.IntroS("Ingrese nuevo nivel:");
				                updateStmt.setString(1, nuevoNive);
				                break;
				            case 13:
				            	EtiquetaEco eco = leerEtiquetaEco();
				                updateStmt.setString(1, eco.toString());
				                break;
				            }
							updateStmt.setString(2, matricula); // set de la matricula para el where del update

							String opcion = Scanners.IntroS("¿Quieres actualizar este vehículo? (SI/NO):");
							if (opcion.equalsIgnoreCase("SI")) {
								int affectedRows = updateStmt.executeUpdate();
								if (affectedRows > 0) {
									System.out.println("El vehículo se ha actualizado correctamente.");
								} else {
									System.out.println("Hubo un problema al actualizar el vehículo.");
								}
							} else {
								System.out.println("Actualización cancelada.");
							}
						}
					}
				} else {
					System.out.println("Matrícula no encontrada en la base de datos.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que lista todos los vehiculos de la base de datos traves de un ResultSet,
	 * mandando un SELECT * a la base de datos se usa como auxiliar el objeto Vehiculo,
	 * que se crea en el metodo crearVehiculo al que se le pasa el ResultSet
	 * <p> La conexión esta hecha atraves de la clase Conexion que importa AutoCloseable
	 * lo que la hace usable en un try-with esto sirve para que el programador no tenga 
	 * que preocuparse de que por algún error la conexión no se cierre
	 * 
	 * @author Victor
	 * @throws Exception
	 * @see crearVehiculo
	 * @see Vehiculo 
	 * @see Conexion
	 */
	public static void listarVehiculos() throws Exception {
		try (Conexion conex = new Conexion();
				Statement base = conex.getConn().createStatement();
				ResultSet salida = base.executeQuery("SELECT * FROM vehiculo ")) {
			for (int i = 1; salida.next(); i++) {
				Vehiculo vehiculo = crearVehiculo(salida);
				System.out.println("\t Vehiculo: " + i);
				System.out.println(vehiculo);
				System.out.println(
						"____________________________________________________________________________________________________________________________________________________");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que crea un vehiculo que llegue desde una base de datos
	 * <p>
	 * Se cambia el String fecha matricula por un localDate al introducirlo en
	 * vehiculo
	 * 
	 * @author Victor
	 * @param resultado la sentencia donde esta el vehiculo que quiere construir
	 * @throws SQLException
	 * @throws Exception
	 */
	public static Vehiculo crearVehiculo(ResultSet resultado) throws SQLException, Exception {
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
		String fecha_matricula = Scanners.montarDate(resultado.getString("fecha_matricula"));
		LocalDate fechaMatricula = LocalDate.parse(fecha_matricula);
		String nive = resultado.getString("nive");
		String etiquetaEco = resultado.getString("etiqueta_eco");

		return new Vehiculo(matricula, numeroBastidor, marca, modelo, anyoProduc, motor, tipo_vehiculo, potencia,
				tamDeposit, numPuertas, consumo, fechaMatricula, nive, etiquetaEco);

	}

	/**
	 * Metodo el cual muestra todas las opciones del enum TipoVehiculo y las enumera
	 * cuando se introduzca el numero correspondiente a un valor se asignara ese
	 * valor
	 * 
	 * @return el enum correspondiente al numero marcado
	 * @see IntroI
	 */
	private static TipoVehiculo leerTipoVehiculo() {
		int opcion;
		do {
			String mensaje = "Seleccione el tipo de vehículo:\n";
			for (int i = 0; i < TipoVehiculo.values().length; i++) {
				mensaje += (i + 1) + ". " + TipoVehiculo.values()[i]+"\n";
			}
			opcion = Scanners.IntroI(mensaje);
			opcion--;
		} while (opcion > EtiquetaEco.values().length);
		return TipoVehiculo.values()[opcion];
	}

	/**
	 * Metodo el cual muestra todas las opciones del enum Motorizacion y las enumera
	 * cuando se introduzca el numero correspondiente a un valor se asignara ese
	 * valor
	 * 
	 * @return el enum correspondiente al numero marcado
	 * @see IntroI
	 */
	private static Motorizacion leerMotorizacion() {
		int opcion;
		do {
			String mensaje = "Seleccione la motorización:\n";
			for (int i = 0; i < Motorizacion.values().length; i++) {
				mensaje += (i + 1) + ". " + Motorizacion.values()[i]+"\n";
			}
			opcion = Scanners.IntroI(mensaje);
			opcion--;
		} while (opcion > EtiquetaEco.values().length);
		return Motorizacion.values()[opcion - 1];
	}

	/**
	 * Metodo el cual muestra todas las opciones del enum EtiquetaEco y las enumera
	 * cuando se introduzca el numero correspondiente a un valor se asignara ese
	 * valor
	 * 
	 * @return el enum correspondiente al numero marcado
	 * @see IntroI
	 */
	private static EtiquetaEco leerEtiquetaEco() {
		int opcion;
		do {
			String mensaje = "Seleccione la etiqueta ecológica:\n";
			for (int i = 0; i < EtiquetaEco.values().length; i++) {
				mensaje += (i + 1) + ". " + EtiquetaEco.values()[i]+"\n";
			}
			opcion = Scanners.IntroI(mensaje);
			opcion--;
		} while (opcion > EtiquetaEco.values().length);
		return EtiquetaEco.values()[opcion];
	}

}
