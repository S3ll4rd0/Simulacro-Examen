package Hotel;

import java.io.Serializable;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre;
	private int llegada;
	private int salida;
	private Habitacion habitacionCliente;
	
	public Cliente(String nombre, int llegada, int salida) {
		this.nombre = nombre;
		this.llegada = llegada;
		this.salida = salida;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getLlegada() {
		return llegada;
	}

	public void setLlegada(int llegada) {
		this.llegada = llegada;
	}

	public int getSalida() {
		return salida;
	}

	public void setSalida(int salida) {
		this.salida = salida;
	}

	public Habitacion getHabitacionCliente() {
		return habitacionCliente;
	}

	public void setHabitacionCliente(Habitacion habitacionCliente) {
		this.habitacionCliente = habitacionCliente;
	}

	public int laLlegada() {
		return llegada;
	}
	
	@Override
	public String toString() {
		return "Nombre: " + nombre + ", día de llegada: " + llegada + ", día de salida: " + salida;
	}
}
