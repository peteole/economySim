package privatpersonEvents;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import de.gmo.simulation.*;

public class SucheArbeitgeber extends ExecuteRegularAction<Privatperson> {
	public SucheArbeitgeber(Privatperson subject) {
		super(subject, 1);
		setDate(subject.getGeburtstag() + 18);
	}

	@Override
	public void act() {
		LinkedList<Produktionsmittel> options = subject.getMeinStaat().getJobsForPerson(subject);
		Produktionsmittel bestOption = subject.arbeitsstelle;
		Iterator<Produktionsmittel> it = options.iterator();
		double bestEvaluation=Double.NEGATIVE_INFINITY;
		if (bestOption == null) {
			if (options.isEmpty()) {
				return;
			}
			bestOption = it.next();
			bestEvaluation = subject.evaluateJob(bestOption);
		} else {
			bestEvaluation = subject.evaluateJob(bestOption) + 5;
		}
		while (it.hasNext()) {
			Produktionsmittel option = it.next();
			if (subject.evaluateJob(option) > bestEvaluation) {
				bestOption = option;
				bestEvaluation = subject.evaluateJob(option);
			}
		}
		if (bestOption != subject.arbeitsstelle) {
			if (subject.arbeitsstelle != null) {
				subject.arbeitsstelle.removeAngestellte(subject);
			}
			bestOption.addAngestellte(subject);
		}
		if(subject.arbeitsstelle!=null&&subject.arbeitsstelle.stundenlohn<2) {
			subject.arbeitsstelle.removeAngestellte(subject);
		}
	}


}
