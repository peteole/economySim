package analysisTools;

import java.util.Iterator;

import de.gmo.simulation.Privatperson;
import de.gmo.simulation.Steuerung;

public class MonitorAverageIncome extends AnalysisTool {

	public MonitorAverageIncome(int numOfFrames, double period, String name) {
		super(numOfFrames, period, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		if(Steuerung.meinStaat.einwohner.size()==0) {
			return 0;
		}
		double totalIncome=0;
		for (Iterator iterator = Steuerung.meinStaat.einwohner.iterator(); iterator.hasNext();) {
			Privatperson type = (Privatperson) iterator.next();
			totalIncome+=type.getPayment();
		}
		return totalIncome/((double)Steuerung.meinStaat.einwohner.size());
	}

}
