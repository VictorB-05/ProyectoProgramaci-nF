package concesionario;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
	
	public static String IntroS(String mensaje) {
		String num  = null;
		boolean rep = false;
		do{
			try {
				System.out.println(mensaje);
				num = String.nextLine();
				rep = false;
			}catch (InputMismatchException e) {
				System.out.println("Formato no valido");
				rep = true;
				String.next();
			}
		}while(rep);

		return num;
	}
}
