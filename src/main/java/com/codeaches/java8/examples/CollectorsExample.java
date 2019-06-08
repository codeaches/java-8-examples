package com.codeaches.java8.examples;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectorsExample {

	private static final Logger log = LoggerFactory.getLogger(CollectorsExample.class);

	private static final String TOYOTA = "Toyota";
	private static final String MERCEDES = "Mercedes";
	private static final String COROLLA = "Corolla";
	private static final String CAMRY = "Camry";
	private static final String BENZ = "Benz";

	public static void main(String[] args) {

		/*------------------------------------------------
		Filtering a Stream
		------------------------------------------------*/
		List<Car> toyotaCars = cars.stream().filter(car -> car.getManufacturer().equals(TOYOTA))
				.collect(Collectors.toList());

		log.info(toyotaCars.toString());

		/*------------------------------------------------
		Converting a Stream to a Map
		------------------------------------------------*/
		Map<String, Car> carMap = cars.stream().collect(Collectors.toMap(Car::getModel, Function.identity()));

		log.info(carMap.toString());

		/*------------------------------------------------
		Calculating averages
		------------------------------------------------*/
		Double averagePrice = cars.stream().collect(Collectors.averagingDouble(Car::getPrice));

		log.info(averagePrice.toString());

		/*------------------------------------------------
		Calculating sum
		------------------------------------------------*/
		Double totalPrice = cars.stream().collect(Collectors.summingDouble(Car::getPrice));

		log.info(totalPrice.toString());

		/*------------------------------------------------
		Calculating sum for each of the group
		------------------------------------------------*/
		Map<String, Double> totalPriceByManufacturer = cars.stream()
				.collect(Collectors.groupingBy(Car::getManufacturer, Collectors.summingDouble(Car::getPrice)));

		log.info(totalPriceByManufacturer.toString());

		/*------------------------------------------------
		Get maximum and minimum element
		------------------------------------------------*/
		Comparator<Car> costliestCarComparator = (car1, car2) -> Double.compare(car1.getPrice(), car2.getPrice());
		Optional<Car> costliestCar = cars.stream().collect(Collectors.maxBy(costliestCarComparator));

		log.info(costliestCar.isPresent() ? costliestCar.get().toString() : null);

		Optional<Car> cheapestCar = cars.stream().collect(Collectors.minBy(costliestCarComparator));

		log.info(cheapestCar.isPresent() ? cheapestCar.get().toString() : null);

		/*------------------------------------------------
		Concatenate
		------------------------------------------------*/
		String models = cars.stream().map(Car::getModel).collect(Collectors.joining(", "));
		log.info(models);

		/*------------------------------------------------
		Partition the stream
		------------------------------------------------*/
		Predicate<Car> manufacturerPredicate = car -> car.getManufacturer().equals(TOYOTA);

		Map<Boolean, List<Car>> mapyByManufacturer = cars.stream()
				.collect(Collectors.partitioningBy(manufacturerPredicate));

		log.info(mapyByManufacturer.toString());
	}

	static List<Car> cars = new ArrayList<>();
	static {
		cars.add(new Car(TOYOTA, COROLLA, 2013, Double.valueOf(21000.00)));
		cars.add(new Car(TOYOTA, CAMRY, 2018, Double.valueOf(24000.00)));
		cars.add(new Car(MERCEDES, BENZ, 2019, Double.valueOf(40000.00)));
	}
}
