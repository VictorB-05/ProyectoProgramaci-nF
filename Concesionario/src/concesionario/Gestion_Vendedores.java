package concesionario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Gestion_Vendedores {
	
	public static void menu() {
	    int opcion = 0;
	    String dni;
	    do {
	        try {
	            // Mostrar el menú
	            System.out.println("\tGestion Vendedores:");
	            System.out.println("-------------------------------------------------------");
	            System.out.println("\t1. Alta Vendedor");
	            System.out.println("\t2. Baja Vendedor");
	            System.out.println("\t3. Modificar sueldo (DNI)");
	            System.out.println("\t4. Modificar datos del vendedor (DNI)");
	            System.out.println("\t5. Listado de vendedores");
	            System.out.println("\t6. Vendedores del departamento (Departamento)");
	            System.out.println("\t7. Vendedores de la categoría (Categoría)");
	            System.out.println("\t8. Volver al menú principal");
	            System.out.println("-------------------------------------------------------");

	            // Leer la opción del usuario
	            opcion = Scanners.IntroI("Seleccione una opción: ");

	            // Realizar la operación correspondiente según la opción seleccionada
	            switch (opcion) {
	                case 1:
	                    altaVendedor();
	                    break;
	                case 2:
	                    System.out.println("Introduce el DNI del vendedor: ");
	                    dni = Scanners.String.nextLine();
	                    bajaVendedor(dni);
	                    break;
	                case 3:
	                    System.out.println("Introduce el DNI del vendedor: ");
	                    dni = Scanners.String.nextLine();
	                    modificarSueldo(dni);
	                    break;
	                case 4:
	                    System.out.println("Introduce el DNI del vendedor: ");
	                    dni = Scanners.String.nextLine();
	                    modificarDatosVendedor(dni);
	                    break;
	                case 5:
	                    listarVendedores();
	                    break;
	                case 6:
	                    System.out.println("Introduce el departamento: ");
	                    String departamento = Scanners.String.nextLine();
	                    listarVendedoresPorDepartamento(departamento);
	                    break;
	                case 7:
	                    System.out.println("Introduce la categoría: ");
	                    String categoria = Scanners.String.nextLine();
	                    listarVendedoresPorCategoria(categoria);
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

	/**
	 * Registra un nuevo vendedor en la base de datos.
	 * <p>
	 * Si el vendedor es menor de edad, se solicita el DNI de su representante y se busca en la base de datos.
	 * Si el representante no existe, se muestra un mensaje de error. Si el vendedor es mayor de edad, se registra sin representante.
	 * </p>
	 * 
	 * @throws Exception si ocurre un error, se lanza una excepción.
	 */
	public static void altaVendedor() throws Exception {
	    try (Conexion conex = new Conexion();
	         PreparedStatement pstmt = conex.getConn().prepareStatement(
	             "INSERT INTO (nombre, apellidos, dni, fecha_nacimiento, edad, sexo, direccion, localidad, provincia, cod_postal, telefono, correo_e, categoria, departamento, sueldo_base) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

	        // Recopilar información del vendedor
	        String nombre = Scanners.IntroS("Introduce el nombre: ");
	        String apellidos = Scanners.IntroS("Introduce los apellidos: ");
	        String dni = Scanners.IntroS("Introduce el DNI: ");
	        LocalDate fechaNac = Scanners.IntroFecha("Introduce la fecha de nacimiento (yyyy-mm-dd): ");
	        int edad = Persona.calcularEdad(fechaNac);
	        char sexo = Scanners.IntroS("Introduce el sexo (H/M): ").charAt(0);
	        String direccion = Scanners.IntroS("Introduce la dirección: ");
	        String localidad = Scanners.IntroS("Introduce la localidad: ");
	        String provincia = Scanners.IntroS("Introduce la provincia: ");
	        int codPostal = Scanners.IntroI("Introduce el código postal: ");
	        String telefono = Scanners.IntroS("Introduce el teléfono: ");
	        String correoElec = Scanners.IntroS("Introduce el correo electrónico: ");
	        String caEmpresa = Scanners.IntroS("Introduce el código de la empresa: ");
	        String departamento = Scanners.IntroS("Introduce el departamento: ");
	        int sueldo = Scanners.IntroI("Introduce el sueldo: ");
	        
	        Vendedor vendedor = null;
	        String representanteDni = null;
	      
	        vendedor = new Vendedor(caEmpresa, departamento, sueldo, nombre, apellidos, dni, fechaNac, edad, sexo, direccion, localidad, provincia, codPostal, telefono, correoElec, null, fechaAlta);

	            // Insertar el vendedor en la base de datos
	            pstmt.setString(1, vendedor.getCaEmpresa());
	            pstmt.setString(2, vendedor.getDepartamento());
	            pstmt.setInt(3, vendedor.getSueldo());
	            pstmt.setString(4, vendedor.getNombre());
	            pstmt.setString(5, vendedor.getApellidos());
	            pstmt.setString(6, vendedor.getDni());
	            pstmt.setString(7, Scanners.desmontarDate(vendedor.getFechaNac().toString()));
	            pstmt.setInt(8, vendedor.getEdad());
	            pstmt.setString(9, String.valueOf(vendedor.getSexo()));
	            pstmt.setString(10, vendedor.getDireccion());
	            pstmt.setString(11, vendedor.getLocalidad());
	            pstmt.setString(12, vendedor.getProvincia());
	            pstmt.setInt(13, vendedor.getCodPostal());
	            pstmt.setString(14, vendedor.getTelefono());
	            pstmt.setString(15, vendedor.getCorreoElec());


	            pstmt.executeUpdate();
	            System.out.println("Vendedor registrado correctamente.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Error al registrar el vendedor.");
	    }
	}


	/**
	 * Elimina un vendedor de la base de datos basado en su DNI.
	 * <p>
	 * Este método primero verifica si el vendedor con el DNI proporcionado existe en la base de datos.
	 * Si el vendedor existe, solicita confirmación del usuario antes de eliminar al vendedor.
	 * Si el vendedor no existe, informa al usuario.
	 * </p>
	 * 
	 * @param dni El DNI del vendedor a eliminar.
	 */
	public static void bajaVendedor(String dni) {
	    try (Conexion conex = new Conexion();
	         PreparedStatement pstmt = conex.getConn().prepareStatement("SELECT * FROM vendedor WHERE dni = ? ");
	         PreparedStatement pstmt2 = conex.getConn().prepareStatement("DELETE FROM vendedor WHERE dni = ? ")) {
	        
	        pstmt.setString(1, dni);
	        pstmt2.setString(1, dni);
	        
	        try (ResultSet salida = pstmt.executeQuery()) {
	            if (salida.next()) {
	            	Vendedor vendedor =crearVendedor(salida);
	                System.out.println("Vendedor encontrado. ¿Seguro que quieres eliminarlo? (si/no):");
	                System.out.println("Datos: "+vendedor.getNombre()+" "+vendedor.getApellidos()+" con el DNI "+vendedor.getDni());
	                String confirmacion = Scanners.String.nextLine();
	                if ("si".equalsIgnoreCase(confirmacion)) {
	                    pstmt2.executeUpdate();
	                    System.out.println("Vendedor eliminado correctamente.");
	                } else {
	                    System.out.println("Operación cancelada.");
	                }
	            } else {
	                System.out.println("DNI no encontrado en la base de datos.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	public static void modificarSueldo(String dni) {
		try (Conexion conex = new Conexion();
				PreparedStatement pstmt = conex.getConn().prepareStatement("SELECT * FROM vendedor WHERE dni = ? ");
				PreparedStatement pstmt2 = conex.getConn().prepareStatement("UPDATE vendedor set sueldo_base = ? WHERE dni = ? ")) {
				pstmt.setString(1, dni);
				pstmt2.setString(2, dni);
				try (ResultSet salida = pstmt.executeQuery()) {
					if (salida.next()) {
						Vendedor vendedor = crearVendedor(salida);
						System.out.println("Vendedor encontrado:");
						System.out.println("Datos: " + vendedor.getNombre() + " " + vendedor.getApellidos()
								+ " con el DNI " + vendedor.getDni() + " sueldo actual " + vendedor.getSueldo());
						int sueldo = Scanners.IntroI("Introduce nuevo sueldo");
						pstmt2.setInt(1, sueldo);
						pstmt2.execute();
					} else {
						System.out.println("DNI no encontrado en la base de datos");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
		}	
	}

	public static void modificarDatosVendedor(String dni) {
	    // Implementar lógica para modificar los datos de un vendedor por DNI
	}

	public static void listarVendedores() {
		try (Conexion conex = new Conexion();
				Statement base = conex.getConn().createStatement();
				ResultSet salida = base.executeQuery("SELECT * FROM vendedor")) {
			listarVendedor(salida);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void listarVendedoresPorDepartamento(String departamento) {
		try (Conexion conex = new Conexion();
			PreparedStatement pstmt = conex.getConn().prepareStatement("SELECT * FROM vendedor WHERE departamento = ? ")) {
			pstmt.setString(1, departamento);
			try(ResultSet salida = pstmt.executeQuery()){
				listarVendedor(salida);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void listarVendedoresPorCategoria(String categoria) {
		try (Conexion conex = new Conexion();
				PreparedStatement pstmt = conex.getConn().prepareStatement("SELECT * FROM vendedor WHERE categoria = ? ")) {
				pstmt.setString(1, categoria);
				try(ResultSet salida = pstmt.executeQuery()){
					listarVendedor(salida);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	/**
	 * 
	 * @param resultado
	 * @return
	 * @throws SQLException
	 */
	public static Vendedor crearVendedor(ResultSet resultado) throws SQLException {
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
		String caEmpresa = resultado.getString("categoria");
		String departamento = resultado.getString("departamento");
		int sueldo = resultado.getInt("sueldo_base");
		
		return new Vendedor(nombre, apellidos, dni, fechaNac, edad, sexo, direccion, localidad, provincia, codPostal, telefono, correo_e, caEmpresa, departamento, sueldo);
	}
	
	private static void listarVendedor(ResultSet salida)throws SQLException {
		System.out.println("___________________________________________________________________________________________________________________________________________________________________");
		while(salida.next()) {
			Vendedor vendedor = crearVendedor(salida);
			System.out.println(vendedor);
			System.out.println("___________________________________________________________________________________________________________________________________________________________________");
		}
	}
	
}
