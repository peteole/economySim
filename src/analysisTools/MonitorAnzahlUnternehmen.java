package analysisTools;

import de.gmo.simulation.Steuerung;

public class MonitorAnzahlUnternehmen extends AnalysisTool {

	public MonitorAnzahlUnternehmen(int numOfFrames, double period, String name) {
		super(numOfFrames, period, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return Steuerung.meinStaat.unternehmen.size();
	}

}
