package analysisTools;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Line2D;

import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class DisplayGraphics extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static double width = 1800;
	static double height = 1000;
	static double maxValue = 150;
	static double minXValue = Double.MAX_VALUE;
	static double minYValue = Double.MAX_VALUE;
	static double maxXValue = -Double.MAX_VALUE;
	static double maxYValue = -Double.MAX_VALUE;
	public static int[] xPoints;
	public static int[] yPoints;
	public static LinkedList<Graph> graphs = new LinkedList<Graph>();
	public static ArrayList<String> toPrint = new ArrayList<String>();
	private static JFrame f;
	private static DisplayGraphics m;

	public void paint(Graphics g) {
		minXValue = Double.MAX_VALUE;
		minYValue = Double.MAX_VALUE;
		maxXValue = -Double.MAX_VALUE;
		maxYValue = -Double.MAX_VALUE;
		setBackground(Color.white);
		for (Iterator iterator = graphs.iterator(); iterator.hasNext();) {
			Graph graph = (Graph) iterator.next();
			for (int i = 0; i < graph.xPoints.length; i++) {
				if (graph.xPoints[i] < minXValue) {
					minXValue = graph.xPoints[i];
				}
				if (graph.yPoints[i] < minYValue) {
					minYValue = graph.yPoints[i];
				}
				if (graph.xPoints[i] > maxXValue) {
					maxXValue = graph.xPoints[i];
				}
				if (graph.yPoints[i] > maxYValue) {
					maxYValue = graph.yPoints[i];
				}
			}
		}
		for (int i = 0; i < toPrint.size(); i++) {

			g.drawString(toPrint.get(i), 10, 10 + i * 20);
		}
		g.drawLine(xDoubleToInt(0), yDoubleToInt(0), xDoubleToInt(0), yDoubleToInt(1000));
		g.drawLine(xDoubleToInt(0), yDoubleToInt(0), xDoubleToInt(100000), yDoubleToInt(0));
		int tmp = (int) Math.log10(maxXValue - minXValue);
		double xScale = Math.pow(10, tmp);
		tmp = (int) Math.log10(maxYValue - minYValue);
		double yScale = Math.pow(10, tmp - 1);
		for (double x = 0; x < maxXValue; x += xScale) {
			// System.out.println("bla");
			g.drawString(Double.toString(x), xDoubleToInt(x), yDoubleToInt(0));
		}
		for (double y = 0; y < maxYValue; y += yScale) {
			// System.out.println("bla");
			g.drawString(Double.toString(y), xDoubleToInt(0), yDoubleToInt(y));
		}
		int d=30;
		g.setFont(getFont().deriveFont(g.getFont().getSize()*2.0F));
		float c=0;
		for (Iterator iterator = graphs.iterator(); iterator.hasNext();) {
			//g.setColor(new Color(getRandColorInt(), getRandColorInt(), getRandColorInt(), 255));
			//g.setColor(getColor(c));
			g.setColor(Color.getHSBColor(c, 1, 1));
			Graph graph = (Graph) iterator.next();
			int[] xPoints = xDoubleToInt(graph.xPoints);
			int[] yPoints = yDoubleToInt(graph.yPoints);
			g.drawPolyline(xPoints, yPoints, xPoints.length);
			g.drawString(graph.name, xPoints[0]+30, d);
			d+=30;
			c+=1.0/((double)graphs.size());
		}

	}

	public static int getRandColorInt() {
		return (int)(Math.random()*256.0);
	}
	/**
	 * @param d
	 * @return Regenbogenfarbe
	 */
	public static Color getColor(float d) {
		d%=1;
		if(d<1.0/3) {
			return new Color(3*d, 1-3*d, 0);
		}
		d-=1.0/3;
		if(d<1.0/3) {
			return new Color(1-3*d, 0, 3*d);
		}
		d-=1.0/3;
		return new Color(0, 3*d, 1-3*d);
		//return new Color((float)Math.max(0,Math.sin(d*3*Math.PI)),(float) Math.max(0,Math.sin((d+1/3)*3*Math.PI)), (float)Math.max(0,Math.sin((d+2/3)*3*Math.PI)));
	}
	public static int xDoubleToInt(double toConvert) {
		return 3 + (int) ((toConvert - minXValue) * (f.getWidth() - 6) / (maxXValue - minXValue));
	}

	public static int yDoubleToInt(double toConvert) {
		return 15 + (int) (f.getHeight() - 80 - (toConvert - minYValue) * (f.getHeight() - 80) / (maxYValue - minYValue));
	}

	public static int[] xDoubleToInt(double[] toConvert) {
		int[] toReturn = new int[toConvert.length];
		for (int i = 0; i < toConvert.length; i++) {
			toReturn[i] = xDoubleToInt(toConvert[i]);
		}
		return toReturn;
	}

	public static int[] yDoubleToInt(double[] toConvert) {
		int[] toReturn = new int[toConvert.length];
		for (int i = 0; i < toConvert.length; i++) {
			toReturn[i] = yDoubleToInt(toConvert[i]);
		}
		return toReturn;
	}
	public static void update() {
		m.repaint();
	}
	public static void draw() {
		m = new DisplayGraphics();
		f = new JFrame();
		f.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		f.add(m);
		f.setSize((int) width, (int) height);
		// f.setLayout(null);
		f.setVisible(true);
	}
}