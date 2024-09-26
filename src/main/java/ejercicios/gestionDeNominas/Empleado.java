package ejercicios.gestionDeNominas;

public class Empleado extends Persona {

	public Integer categoria, anyosTrabajados;

	public Empleado(String nombre, String dni, char sexo, int categoria, int anyosTrabajados)
			throws DatosNoCorrectosException {
		super(nombre, dni, sexo);
		if (categoria < 1 || categoria > 10 || anyosTrabajados < 0) {
			throw new DatosNoCorrectosException("Datos no correctos");
		} else {
			this.categoria = categoria;
			this.anyosTrabajados = anyosTrabajados;
		}
	}

	public Empleado(String nombre, String dni, char sexo) throws DatosNoCorrectosException {
		this(nombre, dni, sexo, 1, 0);
	}

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public Integer getAnyosTrabajados() {
		return anyosTrabajados;
	}
	
	public void setAnyosTrabajados(Integer anyosTrabajados) {
		this.anyosTrabajados = anyosTrabajados;
	}

	public void incrAnyo() {
		this.anyosTrabajados++;
	}

	public void imprime() {
		super.imprime();
		System.out.println("Sexo: " + sexo);
		System.out.println("Categoría: " + categoria);
		System.out.println("Años trabajados: " + anyosTrabajados);
	}

	public Character getSexo() {
		return sexo;
	}
	
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
