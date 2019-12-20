package privatpersonEvents;

import de.gmo.simulation.Privatperson;
import de.gmo.simulation.Steuerung;
import de.gmo.simulation.Unternehmen;

public class GruendeUnternehmen extends SubjectEvent<Privatperson> {
	public GruendeUnternehmen(Privatperson subject) {
		// TODO Auto-generated constructor stub
		this.subject=subject;
		date=Steuerung.getTime()+18+Math.random()*60;
		priority=1;
		length=Math.random()/4.0;
		activeEvent=true;
	}
	@Override
	public void act() {
		// TODO Auto-generated method stub
		System.out.println("Person "+subject.hashCode()+"gruendet Unternehmen");
		new Unternehmen(subject);
	}

}
