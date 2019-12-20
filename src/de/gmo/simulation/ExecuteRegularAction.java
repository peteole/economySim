package de.gmo.simulation;

import privatpersonEvents.SubjectEvent;

public abstract class ExecuteRegularAction<T extends ActionObject> extends SubjectEvent<T> {
	protected double period;
	public boolean stopAction=false;

	public ExecuteRegularAction(T subject, double period) {
		this.subject = subject;
		this.period = period;
		this.date = Steuerung.getTime();
	}

	@Override
	public void finish() {
		if(stopAction) {
			return;
		}
		setDate(Steuerung.getTime() + period);
		subject.addEvent(this);
	}

}
