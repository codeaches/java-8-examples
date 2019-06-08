package com.codeaches.java8.examples;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompositionExample {

	private static final Logger log = LoggerFactory.getLogger(CompositionExample.class);

	private static final String TOYOTA = "Toyota";
	private static final String MERCEDES = "Mercedes";
	private static final String COROLLA = "Corolla";
	private static final String CAMRY = "Camry";
	private static final String BENZ = "Benz";

	public static void main(String[] args) {

		Function<Integer, Integer> sum = x -> x + x;
		Function<Integer, Integer> square = y -> y * y;

		Integer sumAndSquareResult = sum.compose(square).apply(3);
		Integer squareAndSumResult = sum.andThen(square).apply(3);

		log.info("sum.compose(square): " + sumAndSquareResult);
		log.info("sum.andThen(square): " + squareAndSumResult);

		BiFunction<String, List<Car>, List<Car>> byManufacturer = (manufacturer, cars) -> cars.stream()
				.filter(car -> car.getManufacturer().equals(manufacturer)).collect(Collectors.toList());

		Comparator<Car> carPriceComparator = (car1, car2) -> Double.compare(car2.getPrice(), car1.getPrice());

		Function<List<Car>, List<Car>> sortByPrice = cars -> cars.stream().sorted(carPriceComparator)
				.collect(Collectors.toList());

		Function<List<Car>, Optional<Car>> first = cars -> cars.stream().findFirst();

		// Costliest Car
		Function<List<Car>, Optional<Car>> costliest = first.compose(sortByPrice);
		Optional<Car> costliestCar = costliest.apply(cars);
		log.info(costliestCar.isPresent() ? costliestCar.get().toString() : null);

		// Costliest Toyota Car
		BiFunction<String, List<Car>, Optional<Car>> highestByCarType = byManufacturer.andThen(costliest);
		Optional<Car> costliestToyotaCar = highestByCarType.apply(TOYOTA, cars);
		log.info(costliestToyotaCar.isPresent() ? costliestToyotaCar.get().toString() : null);
	}

	static List<Car> cars = new ArrayList<>();

	static {
		cars.add(new Car(TOYOTA, COROLLA, 2013, Double.valueOf(21000.00)));
		cars.add(new Car(TOYOTA, CAMRY, 2018, Double.valueOf(24000.00)));
		cars.add(new Car(MERCEDES, BENZ, 2019, Double.valueOf(40000.00)));
	}
}
