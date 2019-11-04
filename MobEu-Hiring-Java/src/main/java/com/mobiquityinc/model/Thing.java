/**
 * 
 */
package com.mobiquityinc.model;


/**
 * Thing model class
 * @author aeltayary
 *
 */

public class Thing  {
	int id;
	double weight;
	double price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Thing(int id, double weight, double price) {
		this.id = id;
		this.weight = weight;
		this.price = price;
	}

	public String toString() {
		return "(" + id + "," + weight + "," + price + ")";
	}

}
