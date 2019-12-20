package de.gmo.simulation;

import multipleLayerLinkedSorting.SortValueGetter;

public abstract class Event implements SortValueGetter{
	public boolean sortedinList=false;
	public boolean armed = true;
	/**
	 * Zeitpunkt, zu dem das Event geschehen soll
	 */
	protected double date;

	public abstract void process();
	public double getDate() {
		return date;
	}
	public void setDate(double date) {
		this.date = date;
	}
	@Override
	public double getSortValue() {
		return date;
	}
}
