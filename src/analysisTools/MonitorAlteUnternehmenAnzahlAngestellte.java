package analysisTools;

import java.util.Iterator;

import de.gmo.simulation.Steuerung;
import de.gmo.simulation.Unternehmen;

public class MonitorAlteUnternehmenAnzahlAngestellte extends AnalysisTool{

	public MonitorAlteUnternehmenAnzahlAngestellte(double length, double period) {
		super(length, period, "Zielanzahl Mitarbeiter >10 alter Unternehmen");
	}

	@Override
	public double getValue() {
		double amountOfOldUnternehmen=0;
		double totalAnzahlAngestellte=0;
		for (Iterator iterator = Steuerung.meinStaat.unternehmen.iterator(); iterator.hasNext();) {
			Unternehmen type = (Unternehmen) iterator.next();
			if(Steuerung.getTime()-type.getErzeugungsdatum()>=10) {
				amountOfOldUnternehmen++;
				totalAnzahlAngestellte+=type.getMeineProduktionsmittel().iterator().next().optimumAverageAmountOfWorkersForMe;
			}
		}
		if(amountOfOldUnternehmen==0) {
			return 0;
		}
		return totalAnzahlAngestellte/amountOfOldUnternehmen;
	}

}
