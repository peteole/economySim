package analysisTools;

import java.util.Iterator;

import de.gmo.simulation.Privatperson;
import de.gmo.simulation.Steuerung;

public class MonitorArbeitslosigkeit extends AnalysisTool {

	public MonitorArbeitslosigkeit(int numOfFrames, double period, String name) {
		super(numOfFrames, period, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		double arbeitendenzahl = 0;
		double arbeitslose = 0;
		for (Iterator iterator = Steuerung.meinStaat.einwohner.iterator(); iterator.hasNext();) {
			Privatperson type = (Privatperson) iterator.next();
			if (Steuerung.getTime() - type.getGeburtstag() >= 18) {
				arbeitendenzahl++;
				if (type.arbeitsstelle == null) {
					arbeitslose++;
				}
			}
		}
		if (arbeitendenzahl == 0) {
			return 100;
		}
		return 100.0 * arbeitslose / arbeitendenzahl;
	}

}
