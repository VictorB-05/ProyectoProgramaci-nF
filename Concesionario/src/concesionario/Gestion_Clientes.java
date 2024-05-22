package concesionario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Gestion_Clientes {

	public static void menu() {
		int opcion = 6;
		String dni;
		do {
			try {
				// Mostrar el menú
				System.out.println("\tGestion Vehiculos:");
				System.out.println("-------------------------------------------------------");
				System.out.println("\t1. Alta cliente");
				System.out.println("\t2. Consulta datos cliente (DNI)");
				System.out.println("\t3. Modificar datos cliente (DNI)");
				System.out.println("\t4. Asignar nueva cuenta bancaria (DNI)");
				System.out.println("\t5. Listado de los clientes");
				System.out.println("\t6. Listado de los clientes menores de edad");
				System.out.println("\t7. Listado de los clientes dados de alta (año)");
				System.out.println("\t8. Volver al menú principal");
				System.out.println("-------------------------------------------------------");

				// Leer la opción del usuario
				opcion = Scanners.IntroI("Seleccione una opción: ");

				// Realizar la operación correspondiente según la opción seleccionada
				switch (opcion) {
				case 1:
					//altaCliente();
					break;
				case 2:
					System.out.println("Introduce el dni: ");
					dni = Scanners.String.nextLine();
					consultaDatos(dni);
					break;
				case 3:
					System.out.println("Introduce el dni: ");
					dni = Scanners.String.nextLine();
					//modificarDatos(dni);
					break;
				case 4:
					System.out.println("Introduce ek dni: ");
					dni = Scanners.String.nextLine();
					//asignarCuentaB(dni);
					break;
				case 5:
					//listarClientes();
					break;
				case 6:
					//listarClientesMenores();
					break;
				case 7:
					int anyo = Scanners.IntroI("Introduce el año a visualizar :");
					//listarClientesAlta(anyo);
					break;
				case 8:
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
	 * Metodo que recibe un DNI y da los datos del cliente con ese DNI
	 * que este en la base de datos se utiliza un PreparedStatement para mayor
	 * integridad de la base de datos
	 * <p> La conexión esta hecha atraves de la clase Conexion que importa AutoCloseable
	 * lo que la hace usable en un try-with esto sirve para que el programador no tenga 
	 * que preocuparse de que por algún error la conexión no se cierre
	 * @param dni
	 * @throws Exception	 
	 * @see crearCliente
	 * @see Cliente 
	 * @see Conexion
	 */
	public static void consultaDatos(String dni) throws Exception {
		try (Conexion conex = new Conexion();
				PreparedStatement pstmt = conex.getConn()
						.prepareStatement("SELECT * FROM cliente WHERE dni = ? ")) {
			pstmt.setString(1, dni);
			try (ResultSet salida = pstmt.executeQuery()) {
				if (salida.next()) {
					Cliente cliente = crearCliente(salida);
					System.out.println(cliente);
				} else {
					System.out.println("DNI no encontrado en la base de datos");
				}
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
	 * @param resultado la sentencia donde esta el vehiculo que quiere construir
	 * @throws SQLException
	 * @throws Exception
	 */
	private static Cliente crearCliente(ResultSet resultado) throws SQLException, Exception{
		String dni = resultado.getString("dni");
		String nombre = resultado.getString("nombre");
		String apellidos = resultado.getString("apellidos");
		String fecha_nacimiento = resultado.getString("fecha_nacimiento");
		LocalDate fechaNac = LocalDate.parse(Scanners.montarDate(fecha_nacimiento));
		int edad = resultado.getInt("edad");
		char sexo = resultado.getString("sexo").charAt(0);
		String direccion = resultado.getString("direccion");
		String localidad = resultado.getString("localidad");
		String provincia = resultado.getString("provincia");
		int codPostal = resultado.getInt("cod_postal");
		String telefono = resultado.getString("telefono");
		String correo_e = resultado.getString("correo_e");
		String cuentaBancaria = resultado.getString("cuenta_bancaria");
		String fecha_alta = Scanners.montarDate(resultado.getString("fecha_alta"));
		LocalDate fechaAlta = LocalDate.parse(fecha_alta);
		String mayor_edad = resultado.getString("mayor_edad");
		Boolean mayorEdad;
		if(mayor_edad.equals("S")) {
			mayorEdad = true;
		}else if(mayor_edad.equals("N")) {
			mayorEdad = false;
		}else {
			// si hay algun error el programa calcula si es mayor de edad
			mayorEdad = LocalDate.now().isAfter(fechaNac.plusYears(18));
		}
		String representanteDni = resultado.getString("representante_dni");
		Cliente cliente;

		if(!(representanteDni !=null)||representanteDni.isEmpty()||representanteDni.isBlank()) {
			cliente = new Cliente(nombre, apellidos, dni, fechaNac, edad, sexo, direccion, localidad, provincia, codPostal, telefono, correo_e, fechaAlta);
		}else {
			Cliente representante = new Cliente(representanteDni);
			cliente= new Cliente(nombre, apellidos, dni, fechaNac, edad, sexo, direccion, localidad, provincia, codPostal, telefono, correo_e, representante, fechaAlta);
		}
		
		
		return cliente;
	}
	
	
	
}
