package de.gmo.simulation;

public class UpdateAvCostEinnahmen extends ExecuteRegularAction<Actor> {

	public UpdateAvCostEinnahmen(Actor subject) {
		super(subject, Steuerung.month);
		subject.addEvent(this);
	}

	@Override
	public void act() {
		subject.updateAvCostAndEinnahmen();
	}

}
