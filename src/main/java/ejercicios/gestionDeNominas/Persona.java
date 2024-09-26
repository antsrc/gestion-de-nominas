package ejercicios.gestionDeNominas;

public class Persona {

	public String nombre, dni;
	public Character sexo;

	public Persona(String nombre, char sexo) throws DatosNoCorrectosException {
		this.nombre = nombre;
		if (Character.toLowerCase(sexo) == 'm' || Character.toLowerCase(sexo) == 'f') {
			this.sexo = sexo;
		} else {
			throw new DatosNoCorrectosException("Datos no correctos");
		}
	}

	public Persona(String nombre, String dni, char sexo) throws DatosNoCorrectosException {
		this(nombre, sexo);
		this.dni = dni;
	}
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void imprime() {
		System.out.println("Nombre: " + nombre);
		System.out.println("DNI: " + dni);
	}

}
