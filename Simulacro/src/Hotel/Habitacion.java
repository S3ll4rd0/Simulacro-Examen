package Hotel;

import java.io.Serializable;

public class Habitacion implements Serializable {

	private static final long serialVersionUID = 1L;
	private int planta;
	private int numero;
	private boolean libre;
	private Cliente cliente;
	
	public Habitacion(int planta, int numero) {
		this.planta = planta;
		this.numero = numero;
		this.libre = true;
		this.cliente = null;
	}
	
	public Habitacion (int planta, int numero, boolean libre) {
		this.planta = planta;
		this.numero = numero;
		this.libre = libre;
		this.cliente = null;
	}
	
	public Habitacion (int planta, int numero, boolean libre, Cliente cliente) {
		this.planta = planta;
		this.numero = numero;
		this.libre = libre;
		this.cliente = cliente;
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getPlanta() {
		return planta;
	}

	public void setPlanta(int planta) {
		this.planta = planta;
	}

	public boolean isLibre() {
		return libre;
	}

	public void setLibre(boolean libre) {
		this.libre = libre;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cli) {
		this.cliente = cli;
	}

	public boolean estaLibre() {
		return libre;
	}
	
	public Cliente elCliente() {
		return cliente;
	}
	
	public void ocuparHab(Cliente c) {
		if (estaLibre()) {
			setCliente(c);
			this.libre = false;
		} else {
			System.out.println("La habitación está ocupada.");
		}
	}
	
	public void liberarHab() {
		this.libre = true;
	}
	
	@Override
	public String toString() {
		String text = "";
		if (isLibre()) {
			text = "Planta: " + getPlanta() + ", número: " + getNumero() + ", disponibilidad: libre";
		} else {
			text = "Planta: " + getPlanta() + ", número: " + getNumero() + ". Cliente: " + this.cliente.toString();
		}
		return text;
	}
}
