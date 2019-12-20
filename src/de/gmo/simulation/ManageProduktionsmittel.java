package de.gmo.simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import privatpersonEvents.SubjectEvent;

public class ManageProduktionsmittel extends ExecuteRegularAction<Produktionsmittel> {
	public ManageProduktionsmittel(Produktionsmittel subject) {
		super(subject, Steuerung.month);
	}

	@Override
	public void act() {
		subject.angestellte.forEach(new Consumer<Privatperson>() {

			@Override
			public void accept(Privatperson arg0) {
				subject.betreiber.giveMoney(arg0.getWorktime() * subject.stundenlohn / Steuerung.hour, arg0);
				arg0.resetWorktime();
			}
		});
		subject.betreiber.giveMoney(subject.costPerMonth, subject.betreiber.getMeinStaat());
		double workerDifference = subject.averageAmountOfWorkersForMe - subject.optimumAverageAmountOfWorkersForMe;
		subject.minQualification *= Math.pow(1.01, workerDifference);
		subject.stundenlohn *= Math.pow(0.9999, workerDifference);
		subject.stundenlohn *= Math.pow(1.005,
				(0.00002 * subject.getBetereiber().bargeld.value + 1 * subject.getBetereiber().getBilanz()-2000)
						/ (1 + subject.betreiber.getUmsatz()));
		if (workerDifference > 0) {
			ArrayList<Privatperson> toRemove = new ArrayList<Privatperson>((int) workerDifference);
			Iterator<Privatperson> it = subject.angestellte.iterator();
			for (int i = 0; i < workerDifference && it.hasNext(); i++) {
				toRemove.add(it.next());
			}
			//System.out.println("kuendige "+toRemove.size()+" Privatperson");
			toRemove.forEach(new Consumer<Privatperson>() {

				@Override
				public void accept(Privatperson arg0) {
					subject.removeAngestellte(arg0);
				}
			});
			subject.betreiber.meinStaat.openJobs.remove(subject);
		} else if (workerDifference < -1) {
			subject.betreiber.meinStaat.openJobs.add(subject);
		}
	}

}
