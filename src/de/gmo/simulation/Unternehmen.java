package de.gmo.simulation;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.function.Consumer;

import UnternehmenEvents.ManageUnternehmen;
import UnternehmenEvents.SellProducts;

public class Unternehmen extends Actor {
	private Actor besitzer;
	private LinkedList<Privatperson> angestellte = new LinkedList<Privatperson>();
	private HashSet<Produktionsmittel> produktionsmittel = new HashSet<Produktionsmittel>();
	public LinkedList<Wert> products = new LinkedList<Wert>();

	public Unternehmen(Actor besitzer) {
		this.besitzer = besitzer;
		besitzer.giveMoney(500000.0, this);
		meinStaat = besitzer.meinStaat;
		location = besitzer.getLocation();
		double valuePerYear = Math.random() * 10000000;
		produktionsmittel.add(
				new StandardProduktionsmittel(this, valuePerYear, 1 + (0.5 + Math.random()) * valuePerYear / 200000));
		addEvent(new SellProducts(this, Steuerung.month));
		produktionsmittel.add(new StandardProduktionsmittel(this, 2000000, 10));
		addEvent(new ManageUnternehmen(this, 1));
		meinStaat.unternehmen.add(this);
	}

	public void sellProducts() {
		Actor me = this;
		products.forEach(new Consumer<Wert>() {
			public void accept(Wert arg0) {
				meinStaat.giveMoney(arg0.value, me);
			}
		});
		products.clear();
	}

	public double getUmsatz() {
		return getAvEinnahmenPerMonth();
	}

	public double getBilanz() {
		return getAvEinnahmenPerMonth() - getAvCostPerMonth();
	}

	public void addEmployee(Privatperson toAdd) {
		angestellte.add(toAdd);
	}

	public LinkedList<Privatperson> getEmployees() {
		return angestellte;
	}
	/*
	 * public void improveProduktionsmittel(double valueToSpend) { double
	 * costForDevelopment = 3 / (produktionsmittel.size() + 3) * valueToSpend * (1 +
	 * Math.random() / 2);// round // about // 3 // times // the // cost // for //
	 * updating // one // machine Produktionsmittel neuerEntwicklungsstand = new
	 * Produktionsmittel(entwicklungsstand.getValue() * (0.9 + Math.random() / 5),
	 * this, (int) (((double) entwicklungsstand.getNumOfWorkers()) * 1 / (1 +
	 * Math.random() / 10000 * costForDevelopment)), new Product(this,
	 * entwicklungsstand.getProductType().value * (1 + Math.random() / 10000 *
	 * costForDevelopment)), entwicklungsstand.getNeededTime() * 1 / (1 +
	 * Math.random() / 10000 * costForDevelopment),
	 * entwicklungsstand.getDifficultyLevel() * (0.9 + Math.random() / 5), null,
	 * entwicklungsstand.getMaterialCostPerProductionCycle()/(1+Math.random()*
	 * costForDevelopment/10000),0.5+Math.random()/2); for(int
	 * i=0;i<angestellte.size();i++) { int
	 * index=angestellte.get(i).getQualifications().indexOf(entwicklungsstand);
	 * if(index!=-1) { //angestellte.get(i).getQualifications().set(index,
	 * neuerEntwicklungsstand); } } entwicklungsstand=neuerEntwicklungsstand;
	 * for(int i=0;i<produktionsmittel.size();i++) { //produktionsmittel.set(i,
	 * entwicklungsstand.clone()); } giveMoney(valueToSpend, meinStaat); }
	 */

	public HashSet<Produktionsmittel> getMeineProduktionsmittel() {
		return produktionsmittel;
	}

}
