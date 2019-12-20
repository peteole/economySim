package privatpersonEvents;

import de.gmo.simulation.ActionObject;
import de.gmo.simulation.Event;
import de.gmo.simulation.Steuerung;

public abstract class SubjectEvent<T extends ActionObject> extends Event {
	protected T subject;
	private SubjectEventDestructor destructor;
	/**
	 * Dauer des Ereignisses. Sonderfall -1: hier dann handelt es sich um ein
	 * passives Event, das nicht mit anderen kollidieren kann.
	 */
	public double length =0;
	public boolean activeEvent=false;
	protected double priority;
	@SuppressWarnings("unchecked")
	@Override
	public void process() {
		if (true) {
			act();
			if (!activeEvent) {
				subject.removeEvent((SubjectEvent<ActionObject>) this);
				armed=false;
				sortedinList=false;
				finish();
			} else {
				destructor = new SubjectEventDestructor((SubjectEvent<ActionObject>) this);
				Steuerung.events.add(destructor);
			}
		}
	}

	public abstract void act();

	public void finish() {

	}
	public void addingFailed() {
		
	}

	public double getPriority() {
		return priority;
	}
	public T getSubject() {
		return subject;
	}
	public double getLength() {
		return length;
	}

}
