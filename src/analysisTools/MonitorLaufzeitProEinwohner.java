package analysisTools;

import de.gmo.simulation.Steuerung;

public class MonitorLaufzeitProEinwohner extends AnalysisTool {
	double lastRuntime=0;
	public MonitorLaufzeitProEinwohner(double length) {
		super(length, 1, "Laufzeit/Jahr/Person");
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getValue() {
		double toReturn=System.currentTimeMillis()-lastRuntime;
		lastRuntime=System.currentTimeMillis();
		if(Steuerung.getTime()<50) {
			return 0;
		}
		return toReturn/Steuerung.meinStaat.einwohner.size();
	}

}
