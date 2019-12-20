package multipleLayerLinkedSorting;

import java.util.ArrayList;
import java.util.Comparator;

import de.gmo.simulation.Actor;
import de.gmo.simulation.Staat;
import de.gmo.simulation.Wert;

public class SortableContainer<T> {
	// private ArrayList<Sortable> nextSortables=new ArrayList<Sortable>();
	public SortableContainer(T object, double sortValue) {
		// TODO Auto-generated constructor stub
		this.object=object;
		this.sortValue=sortValue;
	}
	protected T object;
	//private Comparator<T> comp;
	private SortableContainer<T>[] nextSortables=new SortableContainer[0];
	protected double sortValue;

	/*
	 * public abstract Sortable getNextSortable(int level); public abstract void
	 * setNextSortable(int level, Sortable ID); public abstract void
	 * addNextSortable( Sortable ID); public abstract ArrayList<Sortable>
	 * getNextSortable(); public abstract void setNextSortable(ArrayList<Sortable>
	 * ID); public abstract double getSortValue(); public abstract void
	 * setSortValue(double sortValue);
	 */
	// public abstract int getID();
	public SortableContainer<T> getNextSortable(int level) {
		return nextSortables[level];
	}

	public void setNextSortable(int level, SortableContainer<T> ID) {
		nextSortables[level] = ID;
	}

	public void addNextSortable(SortableContainer<T> toAdd) {
		SortableContainer<T>[] newNextSortables = new SortableContainer[nextSortables.length + 1];
		System.arraycopy(nextSortables, 0, newNextSortables, 0, nextSortables.length);
		newNextSortables[nextSortables.length]=toAdd;
		nextSortables=newNextSortables;
	}
	public void removeLastSortable() {
		SortableContainer<T>[] newNextSortables = new SortableContainer[nextSortables.length - 1];
		System.arraycopy(nextSortables, 0, newNextSortables, 0, nextSortables.length-1);
		nextSortables=newNextSortables;
	}
	public SortableContainer<T>[] getNextSortable() {
		return nextSortables;
	}

	public void setNextSortable(SortableContainer<T>[] toSet) {
		this.nextSortables = toSet;
	}

	public double getSortValue() {
		return sortValue;
	}

	public void setSortValue(double time) {
		this.sortValue = time;
	}
}
