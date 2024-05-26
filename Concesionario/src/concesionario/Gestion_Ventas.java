package concesionario;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * Clase que gestiona las ventas en el concesionario.
 */
public class Gestion_Ventas {

    /**
     * Muestra el menú de gestión de ventas y procesa las opciones seleccionadas por el usuario.
     */
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
					int anyo = Scanners.IntroI("Introduce el año a consultar: ");
					consultarVentasAnyo(anyo);
					break;
				case 3:
					input = Scanners.IntroS("Introduce el dni del vendedor: ");
					consultarVentasPorVendedor(input);
					break;
				case 4:
					input = leerTipoOperacion().toString();
					consultarVentasPorOperacion(input);
					break;
				case 5:
					input = Scanners.IntroS("Introduce la maticula del vehículo: ");
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

	/**     
	 * Realiza una venta solicitando los datos necesarios e interactuando con la base de datos.
	 * 
	 * @throws Exception
	 */
	public static void realizarVenta() throws Exception {
		Vehiculo vehiculo;
		Cliente cliente;
		Vendedor vendedor;
		try (Conexion conex = new Conexion()) {

			String matricula = Scanners.IntroS("Introduce la matricula del vehiculo: ");

			// Buscar el vendedor en la base de datos
			try (PreparedStatement pstmtRep = conex.getConn()
					.prepareStatement("SELECT * FROM vehiculo WHERE matricula = ?")) {
				pstmtRep.setString(1, matricula);
				try (ResultSet resultadoRep = pstmtRep.executeQuery()) {
					if (resultadoRep.next()) {
						// cliente encontrado
						vehiculo = Gestion_Vehiculos.crearVehiculo(resultadoRep);
					} else {
						// cliente no encontrado
						throw new Exception("El vehiculo no existe en la base de datos.");
					}
				}
			}

			String dniCliente = Scanners.IntroS("Introduce el DNI del cliente: ");

			// Buscar el cliente en la base de datos
			try (PreparedStatement pstmtRep = conex.getConn().prepareStatement("SELECT * FROM cliente WHERE dni = ?")) {
				pstmtRep.setString(1, dniCliente);
				try (ResultSet resultadoRep = pstmtRep.executeQuery()) {
					if (resultadoRep.next()) {
						// cliente encontrado
						cliente = Gestion_Clientes.crearCliente(resultadoRep);
					} else {
						// cliente no encontrado
						throw new Exception("El cliente no existe en la base de datos.");
					}
				}
			}
			String dniVendedor = Scanners.IntroS("Introduce el DNI del vendedor: ");

			// Buscar el vendedor en la base de datos
			try (PreparedStatement pstmtRep = conex.getConn()
					.prepareStatement("SELECT * FROM vendedor WHERE dni = ?")) {
				pstmtRep.setString(1, dniVendedor);
				try (ResultSet resultadoRep = pstmtRep.executeQuery()) {
					if (resultadoRep.next()) {
						// cliente encontrado
						vendedor = Gestion_Vendedores.crearVendedor(resultadoRep);
					} else {
						// cliente no encontrado
						throw new Exception("El venderor no existe en la base de datos.");
					}
				}
			}
			Tipo_Operacion tipo = leerTipoOperacion();
			System.out.println("Se ha asignado la fecha de hoy");
			LocalDate fecha = LocalDate.now();
			System.out.println("Se ha asignado la cuenta del cliente");
			Cuenta_Bancaria banco = cliente.getDatosBan();
			Tipo_Venta tVenta = leerTipoVenta();
			try (Statement statement = conex.getConn().createStatement();
					ResultSet salida = statement.executeQuery("SELECT idventa FROM venta order by idventa DESC;");
					PreparedStatement pstmtRep = conex.getConn().prepareStatement(
							"INSERT INTO venta (idventa, vehiculo, cliente, vendedor, cuenta_bancaria, fecha_opera, tipo_operacion, tipo_venta) values (?,?,?,?,?,?,?,?)")) {
				Venta venta = new Venta(vehiculo, cliente, vendedor, tipo, fecha, tVenta);
				int id = 1;
				if (salida.next()) {
					id = salida.getInt("idventa") + 1;
				}
				pstmtRep.setInt(1, id);
				pstmtRep.setString(2, vehiculo.getMatricula());
				pstmtRep.setString(3, cliente.getDni());
				pstmtRep.setString(4, vendedor.getDni());
				pstmtRep.setString(5, banco.getCuentaIban());
				pstmtRep.setDate(6, Date.valueOf(venta.getFecha().toString()));
				pstmtRep.setString(7, venta.gettOperacion().toString());
				pstmtRep.setString(8, venta.gettVenta().toString());
				if(banco.getCuentaIban()!=null) {
					pstmtRep.execute();
				}else {
					throw new Exception("El cliente no tiene cuenta bancaria asignada asignele una");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    /**
     * Consulta las ventas realizadas en un año específico.
     * 
     * @param anyo el año a consultar.
     * @throws Exception 
     */
	public static void consultarVentasAnyo(int anyo) throws Exception {
		try (Conexion conex = new Conexion();
				PreparedStatement pstmt = conex.getConn()
						.prepareStatement("SELECT * FROM venta WHERE fecha_opera like ? ;")) {
			pstmt.setString(1, anyo+"-%-%");
			try (ResultSet salida = pstmt.executeQuery()) {
				while(salida.next()) {
				listaVentas(salida, conex);
				}
				System.out.println("___________________________________________________________________________________________________________________________________________________________");

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Dio un error en la base de datos");
		}
	}

    /**
     * Consulta las ventas realizadas por un vendedor específico.
     * 
     * @param vendedor el DNI del vendedor a consultar.
     * @throws Exception
     */
	public static void consultarVentasPorVendedor(String vendedor) throws Exception {
		try (Conexion conex = new Conexion();
				PreparedStatement pstmt = conex.getConn()
						.prepareStatement("SELECT * FROM venta INNER JOIN vendedor ON vendedor.dni = venta.vendedor WHERE dni = ? ;")) {
			pstmt.setString(1, vendedor);
			try (ResultSet salida = pstmt.executeQuery()) {
				while(salida.next()) {
				listaVentas(salida, conex);
				}
				System.out.println("___________________________________________________________________________________________________________________________________________________________");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Dio un error en la base de datos");
		}	}

	   /**
     * Consulta las ventas realizadas por un tipo de operación específico.
     * 
     * @param operacion el tipo de operación a consultar.
     * @throws Exception 
     */
	public static void consultarVentasPorOperacion(String operacion) throws Exception {
		try (Conexion conex = new Conexion();
				PreparedStatement pstmt = conex.getConn()
						.prepareStatement("SELECT * FROM venta WHERE tipo_venta = ? ;")) {
			pstmt.setString(1, operacion);
			try (ResultSet salida = pstmt.executeQuery()) {
				while(salida.next()) {
				listaVentas(salida, conex);
				}
				System.out.println("___________________________________________________________________________________________________________________________________________________________");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Dio un error en la base de datos");
		}	
	}

    /**
     * Consulta las ventas realizadas de un vehículo específico.
     * 
     * @param vehiculo la matrícula del vehículo a consultar.
     * @throws Exception 
     */
	public static void consultarVentasPorVehiculo(String vehiculo) throws Exception {
		try (Conexion conex = new Conexion();
				PreparedStatement pstmt = conex.getConn()
						.prepareStatement("SELECT * FROM venta INNER JOIN vehiculo ON vehiculo.matricula = venta.vehiculo WHERE matricula = ? ;")) {
			pstmt.setString(1, vehiculo);
			try (ResultSet salida = pstmt.executeQuery()) {
				while(salida.next()) {
				listaVentas(salida, conex);
				}
				System.out.println("___________________________________________________________________________________________________________________________________________________________");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Dio un error en la base de datos");
		}
	}


    /**
     * Lista las ventas obtenidas de la base de datos.
     * 
     * @param resultado el ResultSet con los datos de las ventas.
     * @param conex la conexión a la base de datos.
     * @throws SQLException
     * @throws Exception 
     */
	private static void listaVentas(ResultSet resultado, Conexion conex) throws SQLException, Exception {
		Venta venta = crearVentas(resultado);
		Vehiculo vehiculo = null;
		try (Statement base = conex.getConn().createStatement();
				ResultSet salida = base.executeQuery(
						"SELECT * FROM vehiculo WHERE matricula = '" + venta.getVehiculo().getMatricula() + "';")) {
			if(salida.next()) {
				vehiculo = Gestion_Vehiculos.crearVehiculo(salida);
			}
		}
		venta.setVehiculo(vehiculo);
		Cliente cliente = null;
		try (Statement base = conex.getConn().createStatement();
				ResultSet salida = base
						.executeQuery("SELECT * FROM cliente WHERE dni = '" + venta.getCliente().getDni() + "';")) {
			if(salida.next()) {
			cliente = Gestion_Clientes.crearCliente(salida);
			}
		}
		cliente.setDatosBan(crearCB(conex, cliente.getDatosBan().getCuentaIban()));
		venta.setCliente(cliente);
		Vendedor vendedor = null;
		try (Statement base = conex.getConn().createStatement();
				ResultSet salida = base
						.executeQuery("SELECT * FROM vendedor WHERE dni = '" + venta.getVendedor().getDni() + "';")) {
			if(salida.next()) {
			vendedor = Gestion_Vendedores.crearVendedor(salida);
			}
		}
		venta.setVendedor(vendedor);
		System.out.println("___________________________________________________________________________________________________________________________________________________________");
		System.out.println(venta.toString());
	}

    /**
     * Crea una venta a partir de los datos obtenidos de la base de datos.
     * 
     * @param resultado el ResultSet con los datos de la venta.
     * @return una instancia de Venta con los datos cargados.
     * @throws SQLException 
     * @throws Exception 
     */
	private static Venta crearVentas(ResultSet resultado) throws SQLException, Exception {
		String matricula = resultado.getString("vehiculo");
		String dniC = resultado.getString("cliente");
		String dniV = resultado.getString("vendedor");
		String tipo_operacion = resultado.getString("tipo_operacion");
		LocalDate fechaOpe = LocalDate.parse(resultado.getDate("fecha_opera").toString())  ;
		String tipo_venta = resultado.getString("tipo_venta");
		Venta venta = new Venta(matricula, dniC, dniV, tipo_operacion, fechaOpe, tipo_venta);
		return venta;
	}

	/**
	 * Metodo el cual muestra todas las opciones del enum Motorizacion y las enumera
	 * cuando se introduzca el numero correspondiente a un valor se asignara ese
	 * valor
	 * 
	 * @return el enum correspondiente al numero marcado
	 */
	private static Tipo_Operacion leerTipoOperacion() {
		int opcion;
		do {
			String mensaje = "Seleccione la tipo operacion:\n";
			for (int i = 0; i < Tipo_Operacion.values().length; i++) {
				mensaje += (i + 1) + ". " + Tipo_Operacion.values()[i] + "\n";
			}
			opcion = Scanners.IntroI(mensaje);
			opcion--;
		} while (opcion > Tipo_Operacion.values().length);
		return Tipo_Operacion.values()[opcion];
	}

	/**
	 * Metodo el cual muestra todas las opciones del enum EtiquetaEco y las enumera
	 * cuando se introduzca el numero correspondiente a un valor se asignara ese
	 * valor
	 * 
	 * @return el enum correspondiente al numero marcado
	 */
	private static Tipo_Venta leerTipoVenta() {
		int opcion;
		do {
			String mensaje = "Seleccione la tipo de venta:\n";
			for (int i = 0; i < Tipo_Venta.values().length; i++) {
				mensaje += (i + 1) + ". " + Tipo_Venta.values()[i] + "\n";
			}
			opcion = Scanners.IntroI(mensaje);
			opcion--;
		} while (opcion > Tipo_Venta.values().length);
		return Tipo_Venta.values()[opcion];
	}


    /**
     * Crea una cuenta bancaria a partir de los datos obtenidos de la base de datos.
     * 
     * @param conex la conexión a la base de datos.
     * @param cuentaBancaria el número de cuenta bancaria.
     * @return una instancia de Cuenta_Bancaria con los datos cargados.
     * @throws SQLException
     * @throws Exception
     */
	private static Cuenta_Bancaria crearCB(Conexion conex, String cuentaBancaria) throws SQLException, Exception {
		Cuenta_Bancaria cuenta = null;
		try (Statement base = conex.getConn().createStatement();
				ResultSet salida = base
						.executeQuery("SELECT * FROM cuentabancaria WHERE cuenta_iban = '" + cuentaBancaria + "';")) {
			if(salida.next()) {
				String titular = salida.getString("titular");
				String entidad = salida.getString("entidad");
				String provincia = salida.getString("provincia");
				cuenta = new Cuenta_Bancaria(titular, cuentaBancaria, "", entidad, provincia);
			}
		}
		return cuenta;
	}

}
