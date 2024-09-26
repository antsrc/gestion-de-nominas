package ejercicios.gestionDeNominas;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestorBD {

	private static final String URL = "jdbc:mysql://localhost:3306/gestion_de_nominas";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	public int altaEmpleado(Empleado empl) {

		String sql = "INSERT INTO EMPLEADOS (nombre, dni, sexo, categoria, antiguedad) VALUES ('" + empl.getNombre()
				+ "', '" + empl.getDni() + "', '" + empl.getSexo() + "', " + empl.getCategoria() + ", "
				+ empl.getAnyosTrabajados() + ")";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD); Statement st = con.createStatement()) {

			return st.executeUpdate(sql);

		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		}
	}

	public int altaEmpleado(String rutaArchivo) throws DatosNoCorrectosException {

		List<Empleado> empleados = generarEmpleados(rutaArchivo);
		int cont = 0;

		for (Empleado empl : empleados) {
			cont += altaEmpleado(empl);
		}

		return cont;
	}

	public List<Empleado> obtenerEmpleados() throws DatosNoCorrectosException {

		List<Empleado> empleados = new ArrayList<>();
		String sql = "SELECT * FROM EMPLEADOS";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {

				String nombre = rs.getString("nombre");
				String dni = rs.getString("dni");
				Character sexo = rs.getString("sexo").charAt(0);
				Integer categoria = rs.getInt("categoria");
				Integer antiguedad = rs.getInt("antiguedad");

				Empleado empl = new Empleado(nombre, dni, sexo, categoria, antiguedad);
				empleados.add(empl);
			}

		} catch (SQLException e) {
			System.out.println(e);
		}

		return empleados;
	}

	public Integer obtenerSalario(String dni) {

		Integer salario = null;
		String sql = "SELECT SUELDO FROM NOMINAS WHERE DNI = '" + dni + "'";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

			if (rs.next()) {
				salario = rs.getInt("sueldo");
			}

		} catch (SQLException e) {
			System.out.println(e);
		}

		return salario;

	}

	public int modificarEmpleado(String dni, String campo, String valor) {

		if (campo.equalsIgnoreCase("nombre") || campo.equalsIgnoreCase("sexo")) {
			valor = "'" + valor + "'";
		}

		String sql = "UPDATE EMPLEADOS SET " + campo + " = " + valor + " WHERE DNI = '" + dni + "'";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD); Statement st = con.createStatement()) {

			return st.executeUpdate(sql);

		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		}

	}
	
	public void generarCopiaSeguridad(String nombreArchivo) throws DatosNoCorrectosException {
		
		List<Empleado> empleados = obtenerEmpleados();
		
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombreArchivo))) {

			dos.writeUTF("DNI,nombre,sexo,categoria,antiguedad,sueldo\n");
			
			for (Empleado empl : empleados) {
				String linea = empl.getDni() + "," + empl.getNombre() + "," + empl.getSexo() + "," + empl.getCategoria() + "," + empl.getAnyosTrabajados() + Nomina.sueldo(empl)+ "\n";
				dos.writeUTF(linea);
			}

			System.out.println("Archivo " + nombreArchivo + " generado con éxito");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<Empleado> generarEmpleados(String rutaArchivo) throws DatosNoCorrectosException {

		List<String> lineas = new ArrayList<>();
		List<Empleado> empleados = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {

			String linea;

			while (true) {
				linea = br.readLine();
				if (linea != null) {
					lineas.add(linea);
				} else {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String linea : lineas) {
			String[] datos = linea.split(", ");
			Empleado empl = new Empleado(datos[0], datos[1], datos[2].charAt(0),
					datos[3].equals("null") ? 1 : Integer.parseInt(datos[3]),
					datos[4].equals("null") ? 0 : Integer.parseInt(datos[4]));
			empleados.add(empl);
		}

		return empleados;

	}

}
