package analysisTools;

public class Graph {
	public double[] xPoints;
	public double[] yPoints;
	public String name;
	private String originalName;
	public Graph(double[] xPoints, double[] yPoints, String name) {
		this.xPoints=xPoints;
		this.yPoints=yPoints;
		this.name=name;
		originalName=name;
		updateScale();
	}
	public void updateScale() {
		double minY=Double.POSITIVE_INFINITY;
		double maxY=Double.NEGATIVE_INFINITY;
		for (int i = 0; i < yPoints.length; i++) {
			if(minY>yPoints[i]) {
				minY=yPoints[i];
			}
			if(maxY<yPoints[i]) {
				maxY=yPoints[i];
			}
		}
		maxY*=0.99;
		minY*=0.99;
		double maxPowerOf10=(int)Math.max(Math.log10(Math.abs(maxY)),Math.log10(Math.abs(minY)));
		double scalingFactor=10/Math.pow(10,maxPowerOf10);
		for (int i = 0; i < yPoints.length; i++) {
			yPoints[i]*=scalingFactor;
		}
		this.name=originalName+"*"+Double.toString(scalingFactor);
	}
}
