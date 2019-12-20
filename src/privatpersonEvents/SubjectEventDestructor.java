package privatpersonEvents;

import de.gmo.simulation.ActionObject;
import de.gmo.simulation.Event;

public class SubjectEventDestructor extends Event {
	SubjectEvent<ActionObject> toDestroy;
	public SubjectEventDestructor(SubjectEvent<ActionObject> toDestroy) {
		// TODO Auto-generated constructor stub
		this.toDestroy=toDestroy;
		this.date=toDestroy.getDate()+toDestroy.getLength();
	}
	@Override
	public void process() {
		// TODO Auto-generated method stub
		toDestroy.getSubject().removeEvent(toDestroy);
		toDestroy.armed=false;
		toDestroy.sortedinList=false;
		toDestroy.finish();
	}

}
