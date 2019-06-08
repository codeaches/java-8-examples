package com.codeaches.java8.examples;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectorsExample {

	private static final String TOYOTA = "Toyota";
	private static final String MERCEDES = "Mercedes";

	public static void main(String[] args) {

		/*------------------------------------------------
		Filtering a Stream
		------------------------------------------------*/
		List<Car> toyotaCars = cars.stream().filter(car -> car.getManufacturer().equals(TOYOTA))
				.collect(Collectors.toList());

		System.out.println(toyotaCars);

		/*------------------------------------------------
		Converting a Stream to a Map
		------------------------------------------------*/
		Map<String, Car> carMap = cars.stream().collect(Collectors.toMap(Car::getModel, Function.identity()));

		System.out.println(carMap);

		/*------------------------------------------------
		Calculating averages
		------------------------------------------------*/
		Double averagePrice = cars.stream().collect(Collectors.averagingDouble(Car::getPrice));

		System.out.println(averagePrice);

		/*------------------------------------------------
		Calculating sum
		------------------------------------------------*/
		Double totalPrice = cars.stream().collect(Collectors.summingDouble(Car::getPrice));

		System.out.println(totalPrice);

		/*------------------------------------------------
		Calculating sum for each of the group
		------------------------------------------------*/
		Map<String, Double> totalPriceByManufacturer = cars.stream()
				.collect(Collectors.groupingBy(Car::getManufacturer, Collectors.summingDouble(Car::getPrice)));

		System.out.println(totalPriceByManufacturer);

		/*------------------------------------------------
		Get maximum and minimum element
		------------------------------------------------*/
		Comparator<Car> costliestCarComparator = (car1, car2) -> Double.compare(car1.getPrice(), car2.getPrice());
		Optional<Car> costliestCar = cars.stream().collect(Collectors.maxBy(costliestCarComparator));

		System.out.println(costliestCar.get());

		Optional<Car> cheapestCar = cars.stream().collect(Collectors.minBy(costliestCarComparator));

		System.out.println(cheapestCar.get());

		/*------------------------------------------------
		Concatenate
		------------------------------------------------*/
		String models = cars.stream().map(Car::getModel).collect(Collectors.joining(", "));
		System.out.println(models);

		/*------------------------------------------------
		Partition the stream
		------------------------------------------------*/
		Predicate<Car> manufacturerPredicate = car -> car.getManufacturer().equals(TOYOTA);

		Map<Boolean, List<Car>> mapyByManufacturer = cars.stream()
				.collect(Collectors.partitioningBy(manufacturerPredicate));

		System.out.println(mapyByManufacturer);
	}

	static List<Car> cars = new ArrayList<>();
	static {
		cars.add(new Car(TOYOTA, "Corolla", 2013, Double.valueOf(21000.00)));
		cars.add(new Car(TOYOTA, "Camry", 2018, Double.valueOf(24000.00)));
		cars.add(new Car(MERCEDES, "Benz", 2019, Double.valueOf(40000.00)));
	}
}
