package concesionario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La clase Conexion implementa la interfaz AutoCloseable para gestionar automáticamente 
 * la conexión con la base de datos mediante el uso de bloques try-with-resources.
 */
public class Conexion implements AutoCloseable {
	
	//atributes
	private Connection connx;
	private final String bd ="concesionario";
	private final String url ="jdbc:mysql://localhost:3306/";
	private final String usuario="root";
	private final String clave="";
	
	/**
	 * Crea la conexión a la base de datos
	 * @throws SQLException
	 */
	public Conexion() throws SQLException {
		//abra la conexión directaments desde el constructor
		connx=DriverManager.getConnection(url+bd,usuario, clave); System.out.println("Conexion efectuada...");
	}
	
	/**
	 * 
	 * @return la conexión creada
	 */
	public Connection getConn() {
		return connx;
	}
	
    /**
     * Cierra la conexión. Este método es llamado automáticamente al usar try-with-resources.
     * 
     * @throws SQLException
     */
	@Override
	public void close() throws SQLException {
		connx.close();
		System.out.println("Conexion cerrada...");
	}
}