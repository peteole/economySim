package analysisTools;

import java.util.Iterator;
import java.util.function.Consumer;

import de.gmo.simulation.Steuerung;
import de.gmo.simulation.Unternehmen;

public class MonitorAverageUmsatz extends AnalysisTool {

	public MonitorAverageUmsatz(int numOfFrames, double period, String name) {
		super(numOfFrames, period, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		if(Steuerung.meinStaat.unternehmen.size()==0) {
			return 0;
		}
		double totalUmsatz=0;
		for (Iterator iterator = Steuerung.meinStaat.unternehmen.iterator(); iterator.hasNext();) {
			Unternehmen type = (Unternehmen) iterator.next();
			totalUmsatz+=type.getUmsatz();
		}
		return totalUmsatz/((double) Steuerung.meinStaat.unternehmen.size());
	}

}
