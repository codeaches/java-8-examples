package com.codeaches.java8.examples;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureExample {

	public static void basicCompletableFuture() throws InterruptedException, ExecutionException {

		// Some heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod = () -> 10;

		CompletableFuture<Integer> asyncFunction = CompletableFuture.supplyAsync(heavyMethod);

		/* Print the result returned by heavyMethod */
		Integer result = asyncFunction.get();
		System.out.println(result);
	}

	public static void completableFutureWithCallback() throws InterruptedException, ExecutionException {

		// Heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod = () -> 10;

		// Print the request
		Consumer<Integer> printer = (x) -> System.out.println(x);

		CompletableFuture<?> asyncFunction = CompletableFuture.supplyAsync(heavyMethod).thenAccept(printer);

		asyncFunction.get();
	}

	public static void completableFutureWithCallbackChain() throws InterruptedException, ExecutionException {

		// Heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod = () -> 10;

		// multiply the input and return the result
		Function<Integer, Integer> multiply = (x) -> x * x;

		// Print the request
		Consumer<Integer> printer = (x) -> System.out.println(x);

		CompletableFuture<?> asyncFunction = CompletableFuture.supplyAsync(heavyMethod).thenApply(multiply)
				.thenAccept(printer);

		asyncFunction.get();
	}

	public static void completableFutureWithParallelCallbacks() throws InterruptedException, ExecutionException {

		// Heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod = () -> 10;

		// add
		Function<Integer, Integer> add = (x) -> x + x;

		// multiply
		Function<Integer, Integer> multiply = (x) -> x * x;

		// Print the request
		Consumer<Integer> printer = (x) -> System.out.println(x);

		CompletableFuture<Integer> asyncFunction = CompletableFuture.supplyAsync(heavyMethod);

		asyncFunction.thenApplyAsync(add).thenAccept(printer);
		asyncFunction.thenApplyAsync(multiply).thenAccept(printer);

		asyncFunction.get();
	}

	public static void completableFutureExceptionHandlerUsingExceptionally()
			throws InterruptedException, ExecutionException {

		// Heavy computation which eventually throws NULL POINTER EXCEPTION
		Supplier<String> heavyMethod = () -> ((String) null).toUpperCase();

		// Print the message
		Consumer<String> printer = (x) -> System.out.println("PRINT MSG: " + x);

		CompletableFuture<Void> asyncFunction = CompletableFuture.supplyAsync(heavyMethod)

				.exceptionally(ex -> {
					System.err.println("heavyMethod threw an exception: " + ex.getLocalizedMessage());
					return "NOTHING TO PRINT";
				}).thenAccept(printer);

		asyncFunction.get();
	}

	public static void completableFutureExceptionHandlerUsingwhenComplete()
			throws InterruptedException, ExecutionException {

		// Heavy computation which eventually throws NULL POINTER EXCEPTION
		Supplier<String> heavyMethod = () -> ((String) null).toUpperCase();

		// Print the message
		Consumer<String> printer = (x) -> {
			System.out.println("PRINT MSG: " + x);
		};

		CompletableFuture<Void> asyncFunction = CompletableFuture.supplyAsync(heavyMethod)

				.whenComplete((String result, Throwable ex) -> {
					if (ex != null) {
						System.err.println("heavyMethod threw an exception: " + ex.getLocalizedMessage());
					}
				}).exceptionally(ex -> {
					return "NOTHING TO PRINT";
				}).thenAccept(printer);

		asyncFunction.get();
	}

	public static void completableFutureWithThenCombine() throws InterruptedException, ExecutionException {

		// Heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod1 = () -> 10;

		// Heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod2 = () -> 15;

		// Print the request
		Consumer<Integer> printer = (x) -> System.out.println(x);

		CompletableFuture<Integer> asyncFunction1 = CompletableFuture.supplyAsync(heavyMethod1);
		CompletableFuture<Integer> asyncFunction2 = CompletableFuture.supplyAsync(heavyMethod2);

		BiFunction<Integer, Integer, Integer> sum = (result1, result2) -> {
			return (result1 + result2);
		};

		CompletableFuture<Void> combinedFunction = asyncFunction1.thenCombine(asyncFunction2, sum).thenAccept(printer);

		combinedFunction.get();
	}

	public static void completableFutureWithRunAfterBoth() throws InterruptedException, ExecutionException {

		// Heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod1 = () -> 10;

		// Heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod2 = () -> 15;

		CompletableFuture<Integer> asyncFunction1 = CompletableFuture.supplyAsync(heavyMethod1);
		CompletableFuture<Integer> asyncFunction2 = CompletableFuture.supplyAsync(heavyMethod2);

		Runnable print = () -> System.out.println("Heavy Load methods completed successfully");

		CompletableFuture<Void> combinedFunction = asyncFunction1.runAfterBoth(asyncFunction2, print);

		combinedFunction.get();
	}

	final static String REPORT = "2013 Toyota Corolla";

	public static void completableFutureWithAcceptEither() throws InterruptedException, ExecutionException {

		// Heavy computation which eventually returns an car details
		Supplier<String> carfax = () -> REPORT;

		// Heavy computation which eventually returns an car details
		Supplier<String> autocheck = () -> REPORT;

		CompletableFuture<String> carfaxResult = CompletableFuture.supplyAsync(carfax);
		CompletableFuture<String> autocheckResult = CompletableFuture.supplyAsync(autocheck);

		Consumer<String> carDetails = (car) -> System.out.println("Car details: " + car);

		CompletableFuture<Void> either = carfaxResult.acceptEither(autocheckResult, carDetails);

		either.get();
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		basicCompletableFuture();
		completableFutureWithCallback();
		completableFutureWithCallbackChain();
		completableFutureWithParallelCallbacks();
		completableFutureExceptionHandlerUsingExceptionally();
		completableFutureExceptionHandlerUsingwhenComplete();
		completableFutureWithThenCombine();
		completableFutureWithRunAfterBoth();
		completableFutureWithAcceptEither();
	}
}
