package UnternehmenEvents;

import java.util.Iterator;
import java.util.function.Consumer;

import de.gmo.simulation.ExecuteRegularAction;
import de.gmo.simulation.Privatperson;
import de.gmo.simulation.Produktionsmittel;
import de.gmo.simulation.Unternehmen;
import privatpersonEvents.SubjectEvent;

public class ManageUnternehmen extends ExecuteRegularAction<Unternehmen> {

	public ManageUnternehmen(Unternehmen subject, double period) {
		super(subject, period);
	}

	@Override
	public void act() {
		if(!subject.getMeinStaat().unternehmen.contains(subject)) {
			System.out.println("problem");
		}
		if (subject.bargeld.value < -subject.getUmsatz()*10) {
			System.out.println("Unternehmen aufgeloest");
			subject.clearEvents();
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = subject.getMeineProduktionsmittel().iterator(); iterator.hasNext();) {
				Produktionsmittel type = (Produktionsmittel) iterator.next();
				subject.getMeinStaat().openJobs.remove(type);
				type.clearEvents();
				while(!type.angestellte.isEmpty()) {
					type.removeAngestellte(type.angestellte.iterator().next());
				}
			}
			subject.getMeinStaat().unternehmen.remove(subject);
			subject.getMeinStaat().giveMoney(-subject.bargeld.value, subject);
			stopAction=true;
		}
	}

}
