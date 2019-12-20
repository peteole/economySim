package privatpersonEvents;

import de.gmo.simulation.ExecuteRegularAction;
//import de.gmo.simulation.Event;
import de.gmo.simulation.Privatperson;
import de.gmo.simulation.Steuerung;

public class ClaimPayment extends ExecuteRegularAction<Privatperson> {
	//Privatperson subject;
	public ClaimPayment(Privatperson subject) {
		super(subject,Steuerung.month);
	}
	@Override
	public void act() {
		if(subject.arbeitgeber==null) {
			return;
		}
		//System.out.println("claiming payment of "+subject.getWorktime()*subject.stundenlohn);
		/*subject.payment+=0.2*(subject.getWorktime()*subject.stundenlohn/Steuerung.hour-subject.payment);
		subject.getArbeitgeber().giveMoney(subject.getWorktime()*subject.stundenlohn/Steuerung.hour, subject);
		subject.resetWorktime();*/
		
	}
}
