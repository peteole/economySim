package analysisTools;

import de.gmo.simulation.Steuerung;

public class MonitorPopulation extends AnalysisTool {

	public MonitorPopulation(int numOfFrames, double period, String name) {
		super(numOfFrames, period, name);
	}

	@Override
	public double getValue() {
		return ((double)Steuerung.meinStaat.einwohner.size());
	}

}
