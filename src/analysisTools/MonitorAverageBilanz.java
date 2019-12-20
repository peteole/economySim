package analysisTools;

import java.util.Iterator;

import de.gmo.simulation.Steuerung;
import de.gmo.simulation.Unternehmen;

public class MonitorAverageBilanz extends AnalysisTool {

	public MonitorAverageBilanz(int numOfFrames, double period, String name) {
		super(numOfFrames, period, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		if(Steuerung.meinStaat.unternehmen.size()==0) {
			return 0;
		}
		double totalBilanz=0;
		for (Iterator iterator = Steuerung.meinStaat.unternehmen.iterator(); iterator.hasNext();) {
			Unternehmen type = (Unternehmen) iterator.next();
			totalBilanz+=type.getBilanz();
		}
		return totalBilanz/((double)Steuerung.meinStaat.unternehmen.size());
	}

}
