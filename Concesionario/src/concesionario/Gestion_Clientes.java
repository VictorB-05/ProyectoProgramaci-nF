package concesionario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
					altaCliente();
					break;
				case 2:
					System.out.println("Introduce el dni: ");
					dni = Scanners.String.nextLine();
					consultaDatos(dni);
					break;
				case 3:
					System.out.println("Introduce el dni: ");
					dni = Scanners.String.nextLine();
					modificarDatos(dni);
					break;
				case 4:
					System.out.println("Introduce eL dni: ");
					dni = Scanners.String.nextLine();
					asignarCuentaB(dni);
					break;
				case 5:
					listarClientes();
					break;
				case 6:
					listarClientesMenores();
					break;
				case 7:
					int anyo = Scanners.IntroI("Introduce el año a visualizar :");
					listarClientesAlta(anyo);
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
		} while (opcion != 8);
	}
	
	public static void altaCliente() {
		//Preguntar si se mete la cuenta bancaria directamente aquí
	}
	

	/**
	 * Metodo que recibe un DNI y da los datos del cliente con ese DNI que este en
	 * la base de datos se utiliza un PreparedStatement para mayor integridad de la
	 * base de datos
	 * <p>
	 * La conexión esta hecha atraves de la clase Conexion que importa AutoCloseable
	 * lo que la hace usable en un try-with esto sirve para que el programador no
	 * tenga que preocuparse de que por algún error la conexión no se cierre
	 * 
	 * @param dni
	 * @throws Exception
	 * @see crearCliente
	 * @see Cliente
	 * @see Conexion
	 */
	public static void consultaDatos(String dni) throws Exception {
		try (Conexion conex = new Conexion();
			PreparedStatement pstmt = conex.getConn().prepareStatement("SELECT * FROM cliente WHERE dni = ? ")) {
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
	
	public static void modificarDatos(String dni) {
		//Preguntar si se cambia aquí el Iban o no obviamente el DNI no
	}
	
	/**
	 * Asigna una nueva cuenta bancaria a un cliente basado en su DNI.
	 * <p>
	 * Este método busca un cliente en la base de datos usando el DNI proporcionado.
	 * Si el cliente ya tiene una cuenta bancaria asociada, informa al usuario.
	 * Si no tiene una cuenta bancaria asociada, permite al usuario introducir un nuevo IBAN,
	 * verifica si el IBAN es correcto y no está ya en la base de datos, y luego actualiza
	 * la información del cliente con la nueva cuenta bancaria.
	 * </p>
	 * 
	 * @param dni El DNI del cliente a buscar.
	 * @throws Exception
	 */
	public static void asignarCuentaB(String dni) throws Exception {
		try(Conexion conex = new Conexion();
			PreparedStatement pstmt = conex.getConn().prepareStatement("SELECT * FROM cliente WHERE dni = ? ")) {
			pstmt.setString(1, dni);
			try (ResultSet salida = pstmt.executeQuery()) {
				if (salida.next()) {
					Cliente cliente = crearCliente(salida);
					System.out.println(cliente);
					if(cliente.getDatosBan().getCuentaIban()!=null) {
						System.out.println("El cliente ya tiene una cuenta asociada");
					}else {
						boolean rep = true;
						String iban = null;
						while(rep) {
							iban= Scanners.IntroS("Introduce el nuevo iban");
							rep = ibanIncorrecto(iban);
							if(rep) {
								System.out.println("El iban es incorrecto");
							}else {
								if(ibanRep(iban, conex)) {
									System.out.println("El iban es correcto, pero esta en la base de datos");
									rep = true;
								}else {
									System.out.println("El iban es correcto");
								}
							}
						}						
						cliente.setDatosBan(crearCuentaB(cliente.getNombre(),cliente.getApellidos(),cliente.getDni(),iban));
						introducirCuenta(conex,cliente);
						System.out.println("Cliente y cuenta banxcaria actualizados correctamente");
					}
				} else {
					System.out.println("DNI no encontrado en la base de datos");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Da los datos de todos los cliente que hay en la base de datos
	 * <p>
	 * La conexión esta hecha atraves de la clase Conexion que importa AutoCloseable
	 * lo que la hace usable en un try-with esto sirve para que el programador no
	 * tenga que preocuparse de que por algún error la conexión no se cierre
	 * 
	 * @param dni
	 * @throws Exception
	 * @see crearCliente
	 * @see Cliente
	 * @see Conexion
	 */
	public static void listarClientes() throws Exception {
		try (Conexion conex = new Conexion();
				Statement base = conex.getConn().createStatement();
				ResultSet salida = base.executeQuery("SELECT * FROM cliente")) {
			listarClientes(salida);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Da los datos de todos los cliente que hay en la base de datos que sean menor de edad
	 * <p>
	 * La conexión esta hecha atraves de la clase Conexion que importa AutoCloseable
	 * lo que la hace usable en un try-with esto sirve para que el programador no
	 * tenga que preocuparse de que por algún error la conexión no se cierre
	 * 
	 * @param dni
	 * @throws Exception
	 * @see crearCliente
	 * @see Cliente
	 * @see Conexion
	 */
	public static void listarClientesMenores() throws Exception {
		try (Conexion conex = new Conexion();
				Statement base = conex.getConn().createStatement();
				ResultSet salida = base.executeQuery("SELECT * FROM cliente WHERE EDAD < 18")) {
			listarClientes(salida);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Da los datos de todos los cliente que hay en la base de datos que su año de alta 
	 * sea el pasado por el usuario
	 * <p>
	 * La conexión esta hecha atraves de la clase Conexion que importa AutoCloseable
	 * lo que la hace usable en un try-with esto sirve para que el programador no
	 * tenga que preocuparse de que por algún error la conexión no se cierre
	 * 
	 * @param dni
	 * @throws Exception
	 * @see crearCliente
	 * @see Cliente
	 * @see Conexion
	 */
	public static void listarClientesAlta(int anyo) throws Exception {
		try (Conexion conex = new Conexion();
			PreparedStatement pstmt = conex.getConn().prepareStatement("SELECT * FROM cliente WHERE fecha_nacimiento like ? ")) {
			pstmt.setString(1, "%/%/"+anyo);
			try (ResultSet salida = pstmt.executeQuery()) {
				listarClientes(salida);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que atraves de un ResultSet crea los clientes por el metodo crearCliente
	 * y luego los imprime por pantalla
	 * 
	 * @param salida
	 * @throws SQLException
	 * @throws Exception
	 * @see crearCliente
	 */
	private static void listarClientes(ResultSet salida) throws SQLException, Exception {
		System.out.println("\tLista clientes:");
		System.out.println("___________________________________________________________________________________________________________________________________________________________________");
		while (salida.next()) {
			Cliente cliente = crearCliente(salida);
			if (cliente.getMayorEdad()) {
				System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellidos() + " DNI: "
						+ cliente.getDni() + " fecha nacimiento: " + cliente.getFechaNac() + " edad: "
						+ cliente.getEdad() + " sexo: " + cliente.getSexo() + " direccion: " + cliente.getDireccion()
						+ " localidad: " + cliente.getLocalidad() + " provincia: " + cliente.getProvincia()+"\n"
						+ "Codigo postal: " + cliente.getCodPostal() + " telefono: " + cliente.getTelefono()
						+ " correo: " + cliente.getCorreoElec());
			}else {
				System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellidos() + " DNI: "
						+ cliente.getDni() + " fecha nacimiento: " + cliente.getFechaNac() + " edad: "
						+ cliente.getEdad() + " sexo: " + cliente.getSexo() + " direccion: " + cliente.getDireccion()
						+ " localidad: " + cliente.getLocalidad() + " provincia: " + cliente.getProvincia()+"\n"
						+ "Codigo postal: " + cliente.getCodPostal() + " telefono: " + cliente.getTelefono()
						+ " correo: " + cliente.getCorreoElec()+" DNI representante: "+ cliente.getRepresentante().getDni());
			}
			System.out.println("___________________________________________________________________________________________________________________________________________________________________");
		}
	}

	/**
	 * Metodo que crea un cliente que llegue desde una base de datos
	 * <p>
	 * Se cambia el String fecha Nacimiento, fecha alta por un localDate al introducirlo en
	 * cliente
	 * 
	 * @param resultado la sentencia donde esta el vehiculo que quiere construir
	 * @throws SQLException
	 * @throws Exception
	 */
	private static Cliente crearCliente(ResultSet resultado) throws SQLException, Exception {
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
		if (mayor_edad.equals("S")) {
			mayorEdad = true;
		} else if (mayor_edad.equals("N")) {
			mayorEdad = false;
		} else {
			// si hay algun error el programa calcula si es mayor de edad
			mayorEdad = LocalDate.now().isAfter(fechaNac.plusYears(18));
		}
		String representanteDni = resultado.getString("representante_dni");
		Cliente cliente;

		if (!(representanteDni != null) || representanteDni.isEmpty() || representanteDni.isBlank()) {
			cliente = new Cliente(nombre, apellidos, dni, fechaNac, edad, sexo, direccion, localidad, provincia,
					codPostal, telefono, correo_e, fechaAlta, mayorEdad, cuentaBancaria);
		} else {
			Cliente representante = new Cliente(representanteDni);
			cliente = new Cliente(nombre, apellidos, dni, fechaNac, edad, sexo, direccion, localidad, provincia,
					codPostal, telefono, correo_e, representante, fechaAlta, mayorEdad, cuentaBancaria);
		}

		return cliente;
	}

	private static Cuenta_Bancaria crearCuentaB(String nombre,String apellido,String dni, String iban) {
		String banco = Scanners.IntroS("Banco al que pertenece la cuenta bancaria");
		String provincia = Scanners.IntroS("Provincia a la que pertenece el banco");
		Cuenta_Bancaria cuentaB = new Cuenta_Bancaria(nombre, apellido, dni, banco, iban, provincia);		
		return cuentaB;
	}
	
	/**
	 * Comprueba la longitud del iban y el formato para determinar si es correcto
	 * @param iban
	 * @return <b>true</b> si el iban es incorrecto <br>
	 * 			<b>false</b> si el iban es correcto
	 */
	private static boolean ibanIncorrecto(String iban) {
		boolean incorrecto;
	    if (iban == null || iban.length() != 24) {
	    	incorrecto = true;
	    }else {
	    	incorrecto = !iban.matches("ES\\d{22}");
	    }
	    return incorrecto;
	}
	
	/**
	 * Comprueba si el iban esta en la base de datos
	 * @param iban
	 * @param conex 
	 * @return <b>true</b> si el iban esta en la base de datos <br>
	 *         <b>false</b> si el iban no esta en la base de datos
	 * @throws SQLException
	 */
	private static boolean ibanRep(String iban, Conexion conex) throws SQLException {
	    boolean rep = false;
	    String query = "SELECT * FROM cuentabancaria WHERE cuenta_iban = ?";
	    
	    try (PreparedStatement pstmt = conex.getConn().prepareStatement(query)) {
	        pstmt.setString(1, iban);
	        
	        try (ResultSet salida = pstmt.executeQuery()) {
	            if (salida.next()) {
	                rep = true;
	            }
	        }
	    }
	    
	    return rep;
	}
	
	private static void introducirCuenta(Conexion conex,Cliente cliente) throws SQLException {
	    try (PreparedStatement pstmt1 = conex.getConn().prepareStatement(
	            "INSERT INTO cuentabancaria (cuenta_iban, titular, entidad, provincia) VALUES (?, ?, ?, ?)")) {
	        
	        pstmt1.setString(1, cliente.getDatosBan().getCuentaIban());
	        pstmt1.setString(2, cliente.getDatosBan().getTitular());
	        pstmt1.setString(3, cliente.getDatosBan().getBanco());
	        pstmt1.setString(4, cliente.getDatosBan().getProvincia());
	        
	        pstmt1.executeUpdate();
	    }

	    try (PreparedStatement pstmt2 = conex.getConn().prepareStatement(
	            "UPDATE cliente SET cuenta_bancaria = ? WHERE dni = ?")) {
	        
	        pstmt2.setString(1, cliente.getDatosBan().getCuentaIban());
	        pstmt2.setString(2, cliente.getDni());
	        
	        pstmt2.executeUpdate();
	    }
	}

}
