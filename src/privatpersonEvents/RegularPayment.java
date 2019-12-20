package privatpersonEvents;


import de.gmo.simulation.Actor;
import de.gmo.simulation.ExecuteRegularAction;
import de.gmo.simulation.Steuerung;

public class RegularPayment extends ExecuteRegularAction<Actor> {
	Actor goal;
	double amount;
	double expirationDate;
	public RegularPayment(Actor subject, Actor goal, double amount, double period, double duration) {
		super(subject,period);
		this.goal=goal;
		this.amount=amount;
		this.expirationDate=duration+Steuerung.getTime();
		this.date=Steuerung.getTime();
	}
	public void act() {
		//System.out.println(subject.hashCode()+" is giving "+amount+" to Actor "+goal.hashCode());
		subject.giveMoney(amount,goal);
		stopAction=date>expirationDate;
	}
}
