package com.mobiquityinc.model;

/**
 * Package model class
 */


import java.util.List;

public class Package {

	private double weightLimit;

	List<Thing> things;

	public void setThings(List<Thing> things) {
		this.things = things;
	}

	

	public double getWeightLimit() {
		return weightLimit;
	}



	public void setWeightLimit(double weightLimit) {
		this.weightLimit = weightLimit;
	}



	public List<Thing> getThings() {
		return things;
	}

	public Package(double w, List<Thing> i) {
		weightLimit = w;
		things = i;

	}

}