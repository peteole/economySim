package de.gmo.simulation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import multipleLayerLinkedSorting.*;

public abstract class Actor extends ActionObject{
	protected Location location;
	protected double erzeugunsdatum;//in Jahren
	public Location getLocation() {
		return this.location;
	}
	protected Staat meinStaat;
	public Staat getMeinStaat() {
		return meinStaat;
	}
	protected ArrayList<Wert> besitz=new ArrayList<Wert>();
	/**
	 * since last update
	 */
	protected double cost=0;
	/**
	 * since last update
	 */
	protected double einnahmen;
	private double lastAvCostEinnahmenUpdate=0;
	/**
	 * per month
	 */
	private double avEinnahmen=0;
	/**
	 * per month
	 */
	private double avCost=0;
	private UpdateAvCostEinnahmen costEinnahmen=new UpdateAvCostEinnahmen(this);
	public Bargeld bargeld=new Bargeld(0);
	
	public double getMyPossession(){
		/* 
		double MyPossession=meinBargeld.getValue();
		for(int i=0;i<meineAktien.size()-1; i++){
			MyPossession+=meineAktien.get(i).getValue();
		}
		for(int i=0;i<meineImmobilien.size()-1; i++){
			MyPossession+=meineImmobilien.get(i).getValue();
		}
		*/
		double myPossession=0;
		for(int index=0; index<besitz.size()-1;index++){
			myPossession+=besitz.get(index).value;
		}
		return myPossession;
	}
	public ArrayList<Wert> getBesitz(){
		return besitz;
	}
	public void giveMoney(double value, Actor empfaenger) {
		if(Double.isInfinite(value)||Double.isNaN(value)) {
			System.out.println("ouuch");
		}
		this.bargeld.value-=value;//der Sender hat nach der transaktion um value weniger Bargeld
		cost+=value;
		empfaenger.bargeld.value+=value;//der Empfaenger hat nach der Transaktion um value mehr bargeld
		empfaenger.einnahmen+=value;
		
	}
	public void giveWert(Wert zuGeben, Actor empfaenger){
 		zuGeben.besitzer=empfaenger;//wechselnden Besitzer im Wert vermerken
		empfaenger.addWert(zuGeben);//Wert dem Empfaenger hinzufuegen
		besitz.remove(zuGeben);//Wert dem ehemaligen Besitzer entfernen
	}
 	public void giveWert(int index, Actor Kaeufer){
 		besitz.get(index).besitzer=Kaeufer;
		Kaeufer.addWert(besitz.get(index));;
		besitz.remove(index);
	}
 	public void sellWert(int index, Actor Kaeufer, double price) {
 		giveWert(index,Kaeufer);
 		Kaeufer.giveMoney(price, this);
 	}
 	public void sellWert(List<Wert> zuVerkaufen, Actor kaeufer) {
 		//kaeufer.giveMoney(price, this);
 		zuVerkaufen.forEach(new Consumer<Wert>() {
			@Override
			public void accept(Wert arg0) {
				sellWert(arg0,kaeufer,arg0.value);
			}
 		});
 	}
 	public void sellWert(Wert zuVerkaufen, Actor Kaeufer, double price) {
 		giveWert(zuVerkaufen,Kaeufer);//Wert uebergeben
 		Kaeufer.giveMoney(price, this);//Preis zahlen
 	}
 	public void addWert(Wert toGet) {
 		besitz.add(toGet);
 	}
 	public Staat getStaat() {
 		return meinStaat;
 	}

	public void updateAvCostAndEinnahmen() {
		double deltaT=(Steuerung.getTime()-lastAvCostEinnahmenUpdate);
		if(deltaT==0) {
			return;
		}
		avCost+=0.1*(cost*Steuerung.month/deltaT-avCost);
		avEinnahmen+=0.1*(einnahmen*Steuerung.month/deltaT-avEinnahmen);
		cost=0;
		einnahmen=0;
		lastAvCostEinnahmenUpdate=Steuerung.getTime();
	}
	public double getAvEinnahmenPerMonth() {
		return avEinnahmen;
	}
	public double getAvCostPerMonth() {
		return avCost;
	}
	public double getErzeugungsdatum() {
		return erzeugunsdatum;
	}
}
