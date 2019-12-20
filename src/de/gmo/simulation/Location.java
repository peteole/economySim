package de.gmo.simulation;

public class Location {
	private double x;
	private double y;
	private String name;
	public Location(double x, double y, String name) {
		this.x=x;
		this.y=y;
		this.name=name;
	}
	public Location(double x, double y) {
		this.x=x;
		this.y=y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public String getName() {
		return name;
	}
	public double getDistanceTo(Location goal) {
		double dx=goal.getX()-x;
		double dy=goal.getY()-y;
		return Math.sqrt(dx*dx+dy+dy);
	}
}
