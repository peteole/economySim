package de.gmo.simulation;

public class Immobilie extends Wert{
	private int xPosition;
	private int yPosition;
	private double Referenzwert;//Wert der Immobilie zur Bauzeit
	private double Baujahr;
	private double wertDichteNull;
	private double lastValue;
	public Immobilie(Staat meinStaat, Actor Besitzer, int xPosition, int yPosition, double Referenzwert){
		this.besitzer=Besitzer;
		//this.value=value;
		this.xPosition=xPosition;
		this.yPosition=yPosition;
		this.Referenzwert=Referenzwert;
		this.Baujahr=Steuerung.getTime();
		this.meinStaat=meinStaat;
		this.wertDichteNull=meinStaat.Wertdichte[xPosition][yPosition];
		lastValue=getValue();
		meinStaat.Wertdichte[xPosition][yPosition]+=lastValue;
		//Steuerung.events.add(this);
	}
	public double getValue() {
		return Referenzwert*Math.pow(0.98, Math.abs(Baujahr-Steuerung.getTime()))*meinStaat.Wertdichte[xPosition][yPosition]/this.wertDichteNull;
		
	}
	public void act(){
		meinStaat.Wertdichte[xPosition][yPosition]-=lastValue-getValue();
		lastValue=getValue();
		//sortValue=Steuerung.getTime()+2;
		//Steuerung.actors.setSortValue(this);
	}
}
