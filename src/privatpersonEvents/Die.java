package privatpersonEvents;

import java.util.Iterator;

import de.gmo.simulation.Privatperson;
import de.gmo.simulation.Steuerung;

public class Die extends SubjectEvent<Privatperson> {
	public Die(Privatperson subject) {
		// TODO Auto-generated constructor stub
		this.subject = subject;
		this.date = Steuerung.getTime() +20+ Math.random() * 100.0;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		subject.getMeinStaat().einwohner.remove(subject);
		if (subject.arbeitsstelle != null) {
			subject.arbeitsstelle.removeAngestellte(subject);
		}
		if (subject.children.isEmpty()) {
			subject.giveMoney(subject.bargeld.value, subject.getMeinStaat());
		} else {
			for (Iterator iterator = subject.children.iterator(); iterator.hasNext();) {
				Privatperson type = (Privatperson) iterator.next();
				subject.giveMoney(subject.bargeld.value / ((double) subject.children.size()), type);
			}
		}
		subject.clearEvents();
		System.out.println("dying");
	}

}
