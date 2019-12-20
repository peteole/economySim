package analysisTools;

import de.gmo.simulation.Event;
import de.gmo.simulation.Steuerung;

public abstract class AnalysisTool extends Event {
	double period;
	int numOfFrames;
	double[] xValues;
	double[] yValues;
	Graph graph;
	/**
	 * Anzahl der bis jetzt aufgezeichneten Frames
	 */
	int frameCounter = 0;
	String name;
	public static double lastDrawUpdate=0;
	public AnalysisTool(double length, double period, String name) {
		// TODO Auto-generated constructor stub
		this.numOfFrames = (int) (length / period);
		this.period = period;
		this.date = Steuerung.getTime();
		this.name = name;
		xValues = new double[numOfFrames];
		yValues = new double[numOfFrames];
		Steuerung.events.add(this);
		DisplayGraphics.graphs.add(getGraph());
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		if (frameCounter < numOfFrames) {
			xValues[frameCounter] = Steuerung.getTime();
			yValues[frameCounter] = getValue();
			frameCounter++;
			getGraph();
			date += period;
			Steuerung.events.add(this);
		}
		if(Steuerung.getTime()-lastDrawUpdate>1) {
			DisplayGraphics.update();
			lastDrawUpdate=Steuerung.getTime();
		}
	}

	public abstract double getValue();

	public Graph getGraph() {
		double[] newXValues=new double[frameCounter];
		System.arraycopy(xValues, 0, newXValues, 0, frameCounter);
		double[] newYValues=new double[frameCounter];
		System.arraycopy(yValues, 0, newYValues, 0, frameCounter);
		if(frameCounter<2) {
			newXValues=xValues;
			newYValues=yValues;
		}
		if (graph == null) {
			graph= new Graph(newXValues, newYValues, name);
		}else {
			graph.xPoints=newXValues;
			graph.yPoints=newYValues;
			graph.updateScale();
		}
		return graph;
	}

	public void drawGraph() {
		DisplayGraphics.graphs.add(getGraph());
	}
}
