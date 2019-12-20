package de.gmo.simulation;

import privatpersonEvents.SubjectEvent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

public abstract class ActionObject {
	//private boolean natUpdated = false;
	protected LinkedList<SubjectEvent<ActionObject>> events = new LinkedList<SubjectEvent<ActionObject>>();
	protected int ID;
	public int getID() {
		return ID;
	}


	public void removeEvent(SubjectEvent<ActionObject> toRemove) {
		if(events.remove(toRemove)) {
			toRemove.armed=false;
			if (!events.isEmpty() && !events.getFirst().sortedinList) {
				//this.sortValue = events.get(0).getDate();
				Steuerung.events.add(events.getFirst());
				events.getFirst().sortedinList=true;
				events.getFirst().armed=true;
			}
		}
		/*
		 * if(toRemove==events.get(0)) { events.remove(0);
		 * Steuerung.actors.remove(this); if(events.size()>0) {
		 * this.nextAction=events.get(0).date; Steuerung.actors.add(this); } }else {
		 * events.remove(toRemove); }
		 */
	}

	/**
	 * @param hinzuzufuegendes Ereignis
	 * @return ob das Ereignis hinzugefuegt werden konnte
	 */
	@SuppressWarnings("unchecked")
	public <T extends ActionObject> boolean addEvent(SubjectEvent<T> toAdd) {
		if(toAdd.date<Steuerung.getTime()) {
			toAdd.addingFailed();
			return false;
		}
		int i = 0;// Stelle, an der toAdd einsortiert werden muesste, also Index des ersten Events
					// mit groesserer NAT
		Iterator<SubjectEvent<ActionObject>> iterator = events.iterator();
		while (iterator.hasNext() && iterator.next().getDate() <= toAdd.getDate()) {
			i++;
		}
		// Kollisionen bestimmen und loesen, wenn noetig
		LinkedList<SubjectEvent<ActionObject>> eventsToRemove = new LinkedList<SubjectEvent<ActionObject>>();// zu entfernende Ereignisse, wenn toAdd hinzugefuegt
																	// wird
		if (toAdd.activeEvent) {
			// Wenn das hinzuzufuegende Event ein aktives Event ist
			Iterator<SubjectEvent<ActionObject>> it = events.iterator();
			// alle Ereignisse, die vor toAdd beginnen
			for (int j = 0; j < i; j++) {
				// if events collide
				SubjectEvent<ActionObject> currentEvent = it.next();
				if (currentEvent.activeEvent && currentEvent.getDate() + currentEvent.length > toAdd.getDate()) {
					if (currentEvent.getPriority() >= toAdd.getPriority()) {
						// don`t add event if it collides with a more important one
						toAdd.addingFailed();
						return false;
					} else {
						eventsToRemove.add(currentEvent);
					}
				}
			}
			//it.next();
			while (it.hasNext()) {
				// if events collide
				SubjectEvent<ActionObject> currentEvent = it.next();
				if (currentEvent.getDate() < toAdd.getDate() + toAdd.length) {
					if (currentEvent.length != -1 && currentEvent.getPriority() >= toAdd.getPriority()) {
						// don`t add event if it collides with a more important one and the other event
						// is collidable
						toAdd.addingFailed();
						return false;
					} else if(currentEvent.activeEvent){
						eventsToRemove.add(currentEvent);
					}
				} else {
					break;
				}
			}
		}
		events.add(i, (SubjectEvent<ActionObject>) toAdd);
		/*Iterator<SubjectEvent> checker=events.iterator();
		double current=checker.next().date;
		while(checker.hasNext()) {
			double next=checker.next().date;
			if(next<current) {
				System.exit(0);
			}
			current=next;
		}*/
		eventsToRemove.forEach(new Consumer<SubjectEvent<ActionObject>>() {

			@Override
			public void accept(SubjectEvent<ActionObject> arg0) {
				arg0.armed=false;
				arg0.addingFailed();
			}
		});
		events.removeAll(eventsToRemove);
		if (i >= 0) {
			// Wenn das hinzuzufuegende Ereignis das erste ist und so eine neue
			// Einsortierung in die PriorityQueue benoetigt
			//this.sortValue = toAdd.getDate();
			Steuerung.events.add(toAdd);
			toAdd.sortedinList=true;
			toAdd.armed=true;
			//natUpdated = true;
		}
		return true;
	}

	public void clearEvents() {
		events.forEach(new Consumer<SubjectEvent<ActionObject>>() {

			@Override
			public void accept(SubjectEvent<ActionObject> arg0) {
				// TODO Auto-generated method stub
				arg0.armed=false;
				try {
					((ExecuteRegularAction<ActionObject>) arg0).stopAction=true;
				} catch (Exception e) {
				}
				
			}
		});
		events.clear();
	}
}
