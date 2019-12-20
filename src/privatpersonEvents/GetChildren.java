package privatpersonEvents;

import de.gmo.simulation.Privatperson;
import de.gmo.simulation.Steuerung;

public class GetChildren extends SubjectEvent<Privatperson> {
	public GetChildren(Privatperson subject) {
		this.subject=subject;
		this.date=subject.getGeburtstag()+18+Math.random()*20; 
	}
	@Override
	public void act() {
		System.out.println("getting child");
		Privatperson toAdd=new Privatperson(subject.getMeinStaat(),subject);
		subject.children.add(toAdd); 
		subject.addEvent(new RegularPayment(subject, toAdd, 20.0, Steuerung.month, 18.0));
		subject.addEvent(new RegularPayment(subject,subject.getMeinStaat(),subject.getPayment()/(3+subject.children.size()),Steuerung.month,18));
		subject.getMeinStaat().einwohner.add(toAdd);
	}

}
