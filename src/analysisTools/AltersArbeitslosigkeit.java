package analysisTools;
import java.util.Iterator;

import de.gmo.simulation.*;
public class AltersArbeitslosigkeit extends AnalysisTool {
	

	public AltersArbeitslosigkeit(double length, double period) {
		super(length, period, "Arbeitslosigkeit 30-40");
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		int arbeitslose=0;
		int personen=0;
		for (Iterator it = Steuerung.meinStaat.einwohner.iterator(); it.hasNext();) {
			Privatperson type = (Privatperson) it.next();
			double alter=Steuerung.getTime()-type.getGeburtstag();
			if(alter>=30&&alter<=40) {
				personen++;
				if(type.arbeitsstelle!=null) {
					arbeitslose++;
				}
			}
		}
		return (double)arbeitslose/(double)personen;
	}

}
