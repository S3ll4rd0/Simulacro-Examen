package Hotel;

import java.io.Serializable;
import java.util.Arrays;

public class Planta implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numPlanta;
	private Habitacion [] habitaciones;
	private int numeroHabitacionesLibres;
	
	public Planta(int numPlanta) {
		numeroHabitacionesLibres = 5;
		this.numPlanta = numPlanta;
		this.habitaciones = new Habitacion[5];
		for (int i = 0; i < numeroHabitacionesLibres; i++) {
			habitaciones[i] = new Habitacion(numPlanta, i+1);
		}
	}
	
	public int getNumPlanta() {
		return numPlanta;
	}

	public void setNumPlanta(int numPlanta) {
		this.numPlanta = numPlanta;
	}

	public Habitacion[] getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(Habitacion[] habitaciones) {
		this.habitaciones = habitaciones;
	}

	public int getNumeroHabitacionesLibres() {
		return numeroHabitacionesLibres;
	}

	public void setNumeroHabitacionesLibres(int numeroHabitacionesLibres) {
		this.numeroHabitacionesLibres = numeroHabitacionesLibres;
	}

	public boolean hayLibres() {
		if (numeroHabitacionesLibres > 0) { return true; } else { return false; }
	}
	
	public int primeraLibre() {
		int index = 0;
		for (int i = 0; i < habitaciones.length; i++) {
			if (habitaciones[i].estaLibre()) {
				index = i;
				break;
			} else {
				continue;
			}
		}
		return index;
	}
	
	public void ocuparhabitCliente(Cliente c) {
		
	}
	
	public void liberarHabitacionPlanta() {
		
	}
	
	@Override
	public String toString() {
		return "Planta [numPlanta=" + numPlanta + ", habitaciones=" + Arrays.toString(habitaciones)
				+ ", numeroHabitacionesLibres=" + numeroHabitacionesLibres + "]";
	}
}
