package de.gmo.simulation;

import java.util.HashSet;
import java.util.List;

public abstract class Produktionsmittel extends ActionObject{
	protected double minQualification;
	protected double komplexitaet;
	public Unternehmen betreiber;
	protected Location location;
	protected HashSet<Privatperson> workingWithMe = new HashSet<Privatperson>();
	private double lastChange;
	public double optimumAverageAmountOfWorkersForMe;
	public double averageAmountOfWorkersForMe=0;
	public HashSet<Privatperson> angestellte=new HashSet<Privatperson>();
	public double costPerMonth;
	public double stundenlohn;
	/**
	 * @param Person, die beginnen will, zu arbeiten
	 * @return ob das Arbeiten funtkioniert hat
	 */
	public boolean startWorking(Privatperson toStart) {
		if (toStart.getLocation() != location || !angestellte.contains(toStart)) {
			return false;
		}
		getProducts();
		workingWithMe.add(toStart);
		return true;
	}

	public boolean stopWorking(Privatperson toStop) {
		getProducts();
		return workingWithMe.remove(toStop);
	}

	public void getProducts() {
		averageAmountOfWorkersForMe+=20*(workingWithMe.size()-averageAmountOfWorkersForMe)*(Steuerung.getTime()-getLastChange());
		betreiber.products.addAll(claimProducts());
		lastChange=Steuerung.getTime();
	}

	/**
	 * gets the products produced since the last change in productivity and returns
	 * them.
	 */
	public abstract List<Wert> claimProducts();
	public void addAngestellte(Privatperson toAdd) {
		toAdd.getQualification(this);
		toAdd.arbeitsstelle=this;
		toAdd.arbeitgeber=betreiber;
		toAdd.stundenlohn=stundenlohn;
		angestellte.add(toAdd);
		betreiber.addEmployee(toAdd);
	}
	public void removeAngestellte(Privatperson toRemove) {
		stopWorking(toRemove);
		toRemove.arbeitsstelle=null;
		toRemove.arbeitgeber=null;
		angestellte.remove(toRemove);
		betreiber.getEmployees().remove(toRemove);
	}
	protected Unternehmen getBetereiber() {
		return betreiber;
	}

	public double getCostForQualification(Privatperson privatperson) {
		return Math.max(50,komplexitaet-privatperson.getLevelOfQualification())/privatperson.getSkillFactor();
	}
	public double getKomplexitaet() {
		return komplexitaet;
	}
	public Location getLocation() {
		return location;
	}

	public double getStundenlohn() {
		// TODO Auto-generated method stub
		return stundenlohn;
	}

	public double getLastChange() {
		return lastChange;
	}

}
