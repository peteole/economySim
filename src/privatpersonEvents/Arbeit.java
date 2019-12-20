package privatpersonEvents;

import de.gmo.simulation.*;

public class Arbeit extends ExecuteRegularAction<Privatperson> {
	public Arbeit(Privatperson subject) {
		super(subject,Steuerung.day);
		this.length=Steuerung.hour*8;
		this.priority=0.9;
		activeEvent=true;
	}
	public Arbeit(Privatperson subject,double date) {
		super(subject, Steuerung.day);
		//this.subject=subject;
		this.date=date;
		priority=0.9;
		length=Steuerung.hour*8;
		activeEvent=true;
	}
	@Override
	public void addingFailed() {
		date+=Steuerung.day;
		//System.out.println("adding event failed, trying next appointment: "+date);
		subject.addEvent(this);
	}	
	@Override
	public void act() {
		//System.out.println("Arbeit");
		if (subject.arbeitsstelle == null) {
			return;
		}
		subject.setLocation(subject.arbeitsstelle.getLocation());
		subject.arbeitsstelle.startWorking(subject);
	}
	@Override
	public void finish() {
		super.finish();
		//System.out.println("zuhause");
		if (subject.arbeitsstelle == null) {
			return;
		}
		subject.arbeitsstelle.stopWorking(subject);
		subject.addWorktime(length);
		subject.setLocation(subject.getHome());
	}
}
