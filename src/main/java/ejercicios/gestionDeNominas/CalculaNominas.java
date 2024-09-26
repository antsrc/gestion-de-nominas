package ejercicios.gestionDeNominas;

import java.util.List;
import java.util.Scanner;

public class CalculaNominas {

	private static GestorBD gbd = new GestorBD();
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		int opcion = 0;

		do {
			System.out.println("Elige una opción:\n" + "0 - Salir del programa\n" + "1 - Dar de alta a un empleado\n"
					+ "2 - Mostrar la información de todos los empleados\n"
					+ "3 - Mostrar el salario de un empleado específico\n" + "4 - Modificar datos de los empleados\n"
					+ "5 - Realizar una copia de seguridad");
			opcion = sc.nextInt();

			// El salario de los empleados se actualiza automáticamente en la base de datos
			
			switch (opcion) {
			case 0:
				System.out.println("Terminando programa...");
				break;
			case 1:
				darAltaEmpleado();
				break;
			case 2:
				mostrarEmpleados();
				break;
			case 3:
				obtenerSalario();
				break;
			case 4:
				modificarDatos();
				break;
			case 5:
				generarCopiaSeguridad();
				break;
			default:
				System.out.println("Por favor, introduzca una opción válida");
			}
		} while (opcion != 0);
	}

	private static void darAltaEmpleado() {
		limpiarBuffer();

		System.out.println("Introduce el nombre:");
		String nombre = sc.nextLine();
		System.out.println("Introduce el dni:");
		String dni = sc.nextLine();
		System.out.println("Introduce el sexo (m/f):");
		char sexo = sc.next().charAt(0);
		System.out.println("Introduce la categoria:");
		int categoria = sc.nextInt();
		System.out.println("Introduce los años de antiguedad:");
		int antiguedad = sc.nextInt();
		Empleado empl;
		try {
			empl = new Empleado(nombre, dni, sexo, categoria, antiguedad);
			gbd.altaEmpleado(empl);
		} catch (DatosNoCorrectosException e) {
			System.out.println(e);
		}

	}

	private static void mostrarEmpleados() {

		try {
			List<Empleado> empleados = gbd.obtenerEmpleados();
			for (Empleado empl : empleados) {
				empl.imprime();
				System.out.println("-----------");
			}
		} catch (DatosNoCorrectosException e) {
			System.out.println(e);
		}

	}

	private static void obtenerSalario() {

		System.out.println("Introduce el DNI del empleado cuyo salario desea conocer:");
		limpiarBuffer();
		String dni = sc.nextLine();
		Integer salario = gbd.obtenerSalario(dni);
		if (salario == null) {
			System.out.println("El DNI introducido no está registrado");
		} else {
			System.out.println("Su salario es: " + gbd.obtenerSalario(dni));
		}

	}

	private static void modificarDatos() {

		System.out.println("Introduce el DNI del trabajador:");
		limpiarBuffer();
		String dni = sc.nextLine();
		System.out.println("Introduce el campo a modificar:");
		String campo = sc.nextLine();
		if (campo.equalsIgnoreCase("nombre") || campo.equalsIgnoreCase("sexo")
				|| (campo.equalsIgnoreCase("categoria") || campo.equalsIgnoreCase("antiguedad"))) {
			System.out.println("Introduce el nuevo valor del campo:");
			String valor = sc.nextLine();
			System.out.println("Filas afectadas: " + gbd.modificarEmpleado(dni, campo, valor));
		} else {
			System.out.println("El campo especificado no es válido");
		}

	}

	private static void generarCopiaSeguridad() {

		System.out.println("Introduce el nombre del archivo:");
		limpiarBuffer();
		String nombreArchivo = sc.nextLine();

		try {
			gbd.generarCopiaSeguridad(nombreArchivo);
		} catch (DatosNoCorrectosException e) {
			e.printStackTrace();
		}

	}

	private static void limpiarBuffer() {
		sc.nextLine();
	}

}
