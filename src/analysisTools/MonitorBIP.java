package analysisTools;

import java.util.Iterator;

import de.gmo.simulation.Steuerung;
import de.gmo.simulation.Unternehmen;

public class MonitorBIP extends AnalysisTool {

	public MonitorBIP(int numOfFrames, double period, String name) {
		super(numOfFrames, period, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		double totalUmsatz=0;
		for (Iterator iterator = Steuerung.meinStaat.unternehmen.iterator(); iterator.hasNext();) {
			Unternehmen type = (Unternehmen) iterator.next();
			totalUmsatz+=type.getUmsatz();
		}
		return totalUmsatz;
	}

}
