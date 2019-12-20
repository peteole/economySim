package analysisTools;

import java.util.Iterator;
import java.util.function.Consumer;

import de.gmo.simulation.Privatperson;
import de.gmo.simulation.Steuerung;

public class MonitorStundenlohn extends AnalysisTool {

	public MonitorStundenlohn(double length, double period, String name) {
		super(length, period, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		int numOfWorkers=0;
		double totalStundenlohn=0;
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = Steuerung.meinStaat.einwohner.iterator(); iterator.hasNext();) {
			Privatperson type = (Privatperson) iterator.next();
			if(type.arbeitsstelle!=null) {
				numOfWorkers++;
				totalStundenlohn+=type.arbeitsstelle.stundenlohn;
			}
		}
		if(numOfWorkers==0) {
			return 0;
		}
		return totalStundenlohn/((double)numOfWorkers);
	}

}
