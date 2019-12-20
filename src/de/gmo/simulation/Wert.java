package de.gmo.simulation;
//import multipleLayerLinkedSorting.Sortable;;
public abstract class Wert extends ActionObject{
	public double value;
	protected Actor besitzer;
	protected Staat meinStaat;
	public double getValue(){
		return value;
	}
	//public abstract void act() ;
	
}
