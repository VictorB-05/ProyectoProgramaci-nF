package concesionario;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase donde hay metodos que facilitan la inserción de datos del usuario y su comprobación
 */
public class Scanners {
	
	public static Scanner Double = new Scanner(System.in);
	public static Scanner Int = new Scanner(System.in);
	public static Scanner String = new Scanner(System.in);	
	
	/**
	 * Metodo por el cual se introduce un numero decimal por teclado y es hasta que no sea correcto no puede para de preguntar
	 * @param mensaje
	 * @return un numero decimal
	 */
	public static double IntroD(String mensaje) {
		double num  = 0;
		boolean rep = false;
		do{
			try {
				System.out.println(mensaje);
				num = Double.nextDouble();
				rep = false;
			}catch (InputMismatchException e) {
				System.out.println("Formato no valido");
				rep = true;
				Double.next();
			}
		}while(rep);

		return num;
	}
	/**
	 * Metodo por el cual se introduce un numero entero por teclado y es hasta que no sea correcto no puede para de preguntar
	 * @param mensaje
	 * @return un numero entero
	 */
	public static int IntroI(String mensaje) {
		int num  = 0;
		boolean rep = false;
		do{
			try {
				System.out.println(mensaje);
				num = Int.nextInt();
				rep = false;
			}catch (InputMismatchException e) {
				System.out.println("Formato no valido");
				rep = true;
				Int.next();
			}
		}while(rep);

		return num;
	}
	
	/**
	 * Metodo que tiene un nextLine()
	 * @param mensaje
	 * @return String pedidido por el usuario
	 */
	public static String IntroS(String mensaje) {
		String num  = null;
		boolean rep = false;
		do{
			
				System.out.println(mensaje);
				num = String.nextLine();
				rep = false;

		}while(rep);

		return num;
	}
	
	/**
	 * Cambia el formato de la fecha para poder parsearlo a un localDate
	 * 
	 * @author Victor
	 * @param fecha se intoduce una fecha que esta en sistema DD/MM/AAAA
	 * @return la fecha sale como AAAA-MM-DD
	 */
	public static String montarDate(String fecha) {
		String[] arrayAux = fecha.split("/");
		fecha = arrayAux[2] + "-" + arrayAux[1] + "-" + arrayAux[0];
		return fecha;
	}

	/**
	 * Cambia el formato de la fecha de un localDate para meterlo en la Base de
	 * datos
	 * 
	 * @author Victor
	 * @param fecha se intoduce una fecha que esta en sistema AAAA-MM-DD
	 * @return la fecha sale como DD/MM/AAAA
	 */
	public static String desmontarDate(String fecha) {
		String[] arrayAux = fecha.split("-");
		fecha = arrayAux[2] + "/" + arrayAux[1] + "/" + arrayAux[0];
		return fecha;
	}
	
	/**
	 * Metodo que piede al usuariio meter una fecha que debe ser correcta en caso de no serlo se la volvera a pedir
	 * @param mensaje
	 * @return Fecha en LocalDate
	 */
	public static LocalDate IntroFecha(String mensaje) {
		LocalDate fecha = null;
		String fechaS;
		boolean rep = false;
		do{
			try {
				System.out.println(mensaje);
				fechaS = String.nextLine();
				fecha = LocalDate.parse(fechaS);
				rep = false;
			}catch (DateTimeParseException e) {
				System.out.println("Formato no valido");
				rep = true;
				String.next();
			}
		}while(rep);
		return fecha;
	}
}
