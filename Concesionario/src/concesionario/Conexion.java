package concesionario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//la intención a hacer que class consnion implemente le interfaz AutoCloseable,
//de sate forme un blogus try-with-resources podrà secrec mi conexion con la base de datos
//de forma automatica cuando esta deis de usaras
public class Conexion implements AutoCloseable {
	
	//atributes
	private Connection connx;
	private final String bd ="concesionario";
	private final String url ="jdbc:mysql://localhost:3306/";
	private final String usuario="root";
	private final String clave="";
	
	//Ver también: Podemos pasar ung und was completa son usuario y contraseña trabajando con clave-valor,
	//de la siguiente maneca ((url de la conexión y el nombre de la BBDD) Usuario y Clave)
	
	//private String urlenlinaLinea "jdbc:mysql://localhost:3306/vehiculos
	public Conexion() throws SQLException {
		//abra la conexión directaments desde el constructor
		connx=DriverManager.getConnection(url+bd,usuario, clave); System.out.println("Conexion efectuada...");
	}
	
	//getter para interactuar con la conexión
	public Connection getConn() {
		return connx;
	}
	
	//Metode de la interier AutoCloseable
	//este metode seca Llamado automáticamente per try-with
	//se encarga de seccar le conexion
	@Override
	public void close() throws SQLException {
		connx.close();
		System.out.println("Conexion cerrada...");
	}
}