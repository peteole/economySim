package de.gmo.simulation;

public class Tools {
	public static double[][] addToArray(double[][] toChangeArray, int Stelle, double[] newValue){
		double[][] newArray=new double[toChangeArray.length+1][toChangeArray[0].length];
		for(int k=0; k<Stelle-1; k++){
			for(int l=0; l<toChangeArray[0].length;l++){
				newArray[k][l]=toChangeArray[k][l];
			}
		}
		newArray[Stelle]=newValue;
		for(int k=Stelle+1; k<toChangeArray.length-1; k++){
			for(int l=0; l<toChangeArray[0].length;l++){
				newArray[k][l]=toChangeArray[k][l];
			}
		}
		return newArray;
		
	}
	public static Aktie[] addToArray(Aktie[] toAddAktien, Aktie aktie){
		Aktie[] newAktien=new Aktie[toAddAktien.length+1];
		for(int z=0; z<toAddAktien.length-1; z++){
			newAktien[z]=toAddAktien[z];
		}
		newAktien[toAddAktien.length]=aktie;
		return newAktien;
	}
}
