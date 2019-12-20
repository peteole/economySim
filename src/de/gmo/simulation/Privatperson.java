package de.gmo.simulation;

import java.util.ArrayList;
import java.util.LinkedList;

import privatpersonEvents.*;

public class Privatperson extends Actor {
	public Privatperson parent;
	public LinkedList<Privatperson> children = new LinkedList<Privatperson>();
	private double levelOfQualification;
	private final double skillFactor;// indicates how much effect a special amount of money has for this person
	private ArrayList<Produktionsmittel> qualifications = new ArrayList<Produktionsmittel>();// contains the machines
																								// the person is
																								// qualified to work
																								// with
	private Location home;
	private double worktime;
	/**
	 * per month
	 */
	// public double payment;
	public double stundenlohn;
	public Unternehmen arbeitgeber;
	public Produktionsmittel arbeitsstelle;

	public Privatperson(Location home, boolean gender, int ID, Staat meinStaat, Unternehmen arbeitgeber,
			double skillFactor, double schoolEducation) {
		this.location = home;
		this.home = home;
		this.meinStaat = meinStaat;
		this.erzeugunsdatum = Steuerung.getTime();
		// this.gender = gender;
		this.ID = ID;
		// this.act();
		this.arbeitgeber = arbeitgeber;
		this.skillFactor = skillFactor;
		this.levelOfQualification = schoolEducation * skillFactor;
		// addEvent(new SetRandomNAT(Steuerung.getTime() + Math.random(), this));
		// addEvent(new SucheArbeitgeber(this));
		// addEvent(new ClaimPayment(this));
		home = new Location(Math.random() * meinStaat.getWidth(), Math.random() * meinStaat.getHeight());
		location = home;
		// addEvent(new ClaimPayment(this));
		addEvent(new Arbeit(this));
		int amountofChildren = (int) (Math.random() * 4.0);
		for (int i = 0; i < amountofChildren; i++) {
			addEvent(new GetChildren(this));
		}
		if (Math.random() < 0.1) {
			addEvent(new GruendeUnternehmen(this));
		}
		addEvent(new Die(this));
		// addEvent(costEinnahmen);
	}

	public Privatperson(Staat meinStaat) {
		this.meinStaat = meinStaat;
		skillFactor = Math.random() + 0.5;
		home = new Location(Math.random() * meinStaat.getWidth(), Math.random() * meinStaat.getHeight());
		location = home;
		erzeugunsdatum = Steuerung.getTime();
		levelOfQualification = Math.random() * 100000 * skillFactor;
		stundenlohn = getMinPayment();
		// addEvent(new ClaimPayment(this));
		addEvent(new Arbeit(this));
		int amountofChildren = (int) (Math.random() * 4.0);
		for (int i = 0; i < amountofChildren; i++) {
			addEvent(new GetChildren(this));
		}
		if (Math.random() < 0.1) {
			addEvent(new GruendeUnternehmen(this));
		}
		if (Math.random() < 0.1) {
			addEvent(new GruendeUnternehmen(this));
		}
		addEvent(new Die(this));
		// addEvent(costEinnahmen);
	}

	public Privatperson(Staat meinStaat, Privatperson parent) {
		this.meinStaat = meinStaat;
		// skillFactor=Math.random()+0.5;
		double a = Math.random();// Anteil des SkillFactors, den die neue Person von seinem Elternteil erbt.
		skillFactor = a * (Math.random() + 0.5) + (1 - a) * parent.skillFactor;
		home = new Location(Math.random() * meinStaat.getWidth(), Math.random() * meinStaat.getHeight());
		location = home;
		erzeugunsdatum = Steuerung.getTime();
		levelOfQualification = Math.random() * 100000 * skillFactor;
		this.parent = parent;
		// addEvent(new ClaimPayment(this));
		addEvent(new Arbeit(this));
		int amountofChildren = (int) (Math.random() * 4.0);
		for (int i = 0; i < amountofChildren; i++) {
			addEvent(new GetChildren(this));
		}
		if (Math.random() < 0.1) {
			addEvent(new GruendeUnternehmen(this));
		}
		addEvent(new SucheArbeitgeber(this));
		addEvent(new Die(this));
		// addEvent(costEinnahmen);
	}

	/**
	 * Erlange Qualifizikation fuer das uebergebene Produktionsmittel. Die Kosten
	 * traegt der Arbeitgeber.
	 * 
	 * @param toGet
	 */
	public void getQualification(Produktionsmittel toGet) {
		toGet.getBetereiber().giveMoney(toGet.getCostForQualification(this), meinStaat);
		levelOfQualification += toGet.getKomplexitaet() * skillFactor;
		qualifications.add(toGet);
		stundenlohn = toGet.stundenlohn;
	}

	public double getSkillFactor() {
		return skillFactor;
	}

	public double getMinPayment() {
		return levelOfQualification * skillFactor / 6000;
	}

	public void addWorktime(double length) {
		if (length > 0) {
			worktime += length;
		}
	}

	public double getWorktime() {
		return worktime;
	}

	public void resetWorktime() {
		worktime = 0;
	}

	public void setLocation(Location toSet) {
		this.location = toSet;
	}

	public Location getHome() {
		return home;
	}

	public Unternehmen getArbeitgeber() {
		return arbeitgeber;
	}

	public ArrayList<Produktionsmittel> getQualifications() {
		return this.qualifications;
	}

	/**
	 * @param angebot
	 * @return je hoeher, desto besser
	 */
	public double evaluateJob(Produktionsmittel angebot) {
		return angebot.getStundenlohn() - 0.05 * angebot.getLocation().getDistanceTo(home);
	}

	public double getLevelOfQualification() {
		return levelOfQualification;
	}

	public double getGeburtstag() {
		return erzeugunsdatum;
	}

	/**
	 * @return monatliche Einnahmen
	 */
	public double getPayment() {
		return getAvEinnahmenPerMonth();
	}
}
