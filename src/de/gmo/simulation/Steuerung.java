package de.gmo.simulation;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import analysisTools.AltersArbeitslosigkeit;
import analysisTools.DisplayGraphics;
import analysisTools.MonitorAlteUnternehmenAnzahlAngestellte;
import analysisTools.MonitorAnzahlUnternehmen;
import analysisTools.MonitorArbeitslosigkeit;
import analysisTools.MonitorAverageBilanz;
import analysisTools.MonitorAverageIncome;
import analysisTools.MonitorAverageUmsatz;
import analysisTools.MonitorBIP;
import analysisTools.MonitorLaufzeitProEinwohner;
import analysisTools.MonitorPopulation;
import analysisTools.MonitorStundenlohn;
import multipleLayerLinkedSorting.LinkedOrderedList;

public class Steuerung {
	public static Staat meinStaat;
	public static Queue<Event> events;
	static boolean meineDatenstruktur = true;
	private static double time;
	public static final double month = 1.0 / 12.0;
	public static final double day = month / 30.0;
	public static final double hour = day / 24;
	public static final double minute = hour / 60.0;
	public static final double second = minute / 60.0;
	public static final int yearsToSimulate = 400;

	public static void main(String[] args) {
		if (meineDatenstruktur) {
			events = new LinkedOrderedList<Event>();
		} else {
			events = new PriorityQueue<Event>(new Comparator<Event>() {
				public int compare(Event arg0, Event arg1) {
					return Double.compare(arg0.getSortValue(), arg1.getSortValue());
				}
			});
		}
		long start = System.currentTimeMillis();
		meinStaat = new Staat(10, 10);
		int i = 0;
		new AltersArbeitslosigkeit(yearsToSimulate, 0.1);
		new MonitorAverageIncome(yearsToSimulate, 0.1, "average income/month");
		new MonitorAverageBilanz(yearsToSimulate, 0.1, "av Bilanz/Monat");
		new MonitorArbeitslosigkeit(yearsToSimulate, 0.1, "Arbeitslosigkeitsrate[%]");
		new MonitorPopulation(yearsToSimulate, 0.1, "population");
		new MonitorAnzahlUnternehmen(yearsToSimulate, 0.1, "unternehmen");
		new MonitorAverageUmsatz(yearsToSimulate, 0.1, "av. Umsatz/month");
		new MonitorBIP(yearsToSimulate, 0.1, "BIP/Monat");
		new MonitorStundenlohn(yearsToSimulate, 0.1, "Stundenlohn");
		new MonitorAlteUnternehmenAnzahlAngestellte(yearsToSimulate, 0.1);
		//new MonitorLaufzeitProEinwohner(yearsToSimulate);
		DisplayGraphics.draw();
		while (time < yearsToSimulate) {
			Event currentEvent = events.poll();
			if (currentEvent.getSortValue() < time) {
				System.out.println("Aaaacchhhtuuung");
			}
			if (currentEvent.armed) {
				time = currentEvent.getSortValue();
				// System.out.println("Time:" + time);
				currentEvent.process();
				i++;
				i %= 100000;
				if (i == 0) {
					System.out.println("Time:" + time);
				}
			}
		}
		System.out.println(meinStaat.einwohner.size());
		System.out.println(System.currentTimeMillis() - start);
		if (meineDatenstruktur) {
			LinkedOrderedList<Event> list = (LinkedOrderedList<Event>) events;
			list.printStructure();
		}
	}

	public Steuerung() {
		meinStaat = new Staat(1000, 1000);
	}

	public static double getTime() {
		return time;
	}

	public static void setTime(double toSetTo) {
		time = toSetTo;
	}

	public Staat getStaat() {
		return meinStaat;
	}
}
