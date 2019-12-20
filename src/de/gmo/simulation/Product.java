package de.gmo.simulation;

public class Product extends Wert {
	public Product(Actor besitzer, double value) {
		this.besitzer=besitzer;
		this.value=value;
	}
	public double getValue() {
		return value;
	}
	public Product clone() {
		return new Product(besitzer,value);
	}
}
