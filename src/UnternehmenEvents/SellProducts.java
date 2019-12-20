package UnternehmenEvents;

import java.util.Iterator;
import java.util.function.Consumer;

import de.gmo.simulation.ExecuteRegularAction;
import de.gmo.simulation.Product;
import de.gmo.simulation.Unternehmen;
import de.gmo.simulation.Wert;
import privatpersonEvents.*;

public class SellProducts extends ExecuteRegularAction<Unternehmen> {

	public SellProducts(Unternehmen subject, double period) {
		super(subject, period);
	}

	@Override
	public void act() {
		subject.sellProducts();
	}

}
