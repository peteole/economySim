package de.gmo.simulation;

import java.util.LinkedList;
import java.util.List;

public class StandardProduktionsmittel extends Produktionsmittel {
	private double value;
	double maxProdValuePerYear;
	public StandardProduktionsmittel(Unternehmen betreiber, double maxProdValuePerYear, double optimumAverageAmountOfWorkersForMe) {
		this.betreiber=betreiber;
		this.maxProdValuePerYear=maxProdValuePerYear;
		this.optimumAverageAmountOfWorkersForMe=optimumAverageAmountOfWorkersForMe;
		//this.meinStaat=betreiber.meinStaat;
		value=maxProdValuePerYear/optimumAverageAmountOfWorkersForMe;
		komplexitaet=value/10;
		minQualification=value/10;
		stundenlohn=komplexitaet/1000;
		betreiber.giveMoney(value, betreiber.getMeinStaat());
		this.location=betreiber.getLocation();
		addEvent(new ManageProduktionsmittel(this));
		this.costPerMonth=maxProdValuePerYear/200;//200
		//this.costPerMonth=1000;
	}
	@Override
	public List<Wert> claimProducts() {
		// TODO Auto-generated method stub
		LinkedList<Wert> toReturn=new LinkedList<Wert>();
		double valueProduced=(Steuerung.getTime()-getLastChange())*maxProdValuePerYear*(1.0-Math.pow(4,-((double) workingWithMe.size())/optimumAverageAmountOfWorkersForMe ));
		
		toReturn.add(new Product(betreiber, valueProduced));
		return toReturn;
	}
}
