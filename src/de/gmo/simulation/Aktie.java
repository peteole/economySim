package de.gmo.simulation;

public class Aktie extends Wert {
	private int Unternehmen;
	private int Besitzer;
	public Aktie(double Startwert, int Unternehmen, int Besitzer){
		this.Unternehmen=Unternehmen;
		this.value=Startwert;
		this.Besitzer=Besitzer;
	}
	public void act(){
		
	}
	public double getValue(){
		return this.value;
	}
}
