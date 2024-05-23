package concesionario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	public static void altaVendedor() {
	    // Implementar lógica para dar de alta a un vendedor
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
	                System.out.println("Vendedor encontrado. ¿Seguro que quieres eliminarlo? (sí/no):");
	                String confirmacion = Scanners.String.nextLine();
	                if ("sí".equalsIgnoreCase(confirmacion)) {
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
				PreparedStatement pstmt = conex.getConn().prepareStatement("SELECT * FROM cliente WHERE dni = ? ");
				PreparedStatement pstmt2 = conex.getConn().prepareStatement("UPATE FROM vendedor set sueldo ? WHERE dni = ? ")) {
				pstmt.setString(1, dni);
				pstmt2.setString(2, dni);
				try (ResultSet salida = pstmt.executeQuery()) {
					//Crear el vendedor y luego preguntar que sueldo nuevo queire ponerle mostrando el nombre y el sueldo
					if (salida.next()) {
						System.out.println("Vendedor encontrado seguro que quieres eliminarlo:");
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
	    // Implementar lógica para listar todos los vendedores
	}

	public static void listarVendedoresPorDepartamento(String departamento) {
	    // Implementar lógica para listar vendedores por departamento
	}

	public static void listarVendedoresPorCategoria(String categoria) {
	    // Implementar lógica para listar vendedores por categoría
	}

}
