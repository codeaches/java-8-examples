package com.codeaches.java8.examples;

public class Car {

	String manufacturer;
	String model;
	int year;
	Double price;

	public Car(String manufacturer, String model, int year, Double price) {

		this.manufacturer = manufacturer;
		this.model = model;
		this.year = year;
		this.price = price;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Car [manufacturer=" + manufacturer + ", model=" + model + ", year=" + year + ", price=" + price + "]";
	}
}
