package de.gmo.simulation;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.function.Consumer;

public class Staat extends Actor{
	private int width;// kilometres

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private int height;// kilometres
	public double[][] Wertdichte;
	public HashSet<Produktionsmittel> openJobs = new HashSet<Produktionsmittel>();
	public LinkedList<Privatperson> einwohner =new LinkedList<Privatperson>();
	public LinkedList<Unternehmen> unternehmen=new LinkedList<Unternehmen>();
	public Staat(int width, int height) {
		this.width = width;
		this.height = height;
		for (int i = 0; i < 20; i++) {
			einwohner.add(new Privatperson(this));
		}
	}

	public LinkedList<Produktionsmittel> getJobsForPerson(Privatperson toCheck) {
		LinkedList<Produktionsmittel> options=new LinkedList<Produktionsmittel>();
		openJobs.forEach(new Consumer<Produktionsmittel>() {

			public void accept(Produktionsmittel arg0) {
				if(toCheck.getLevelOfQualification()>=arg0.minQualification) {
					options.add(arg0);
				}
			}
		});
		return options;

	}
}
