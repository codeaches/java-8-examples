package com.codeaches.java8.examples;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureExample {

	public static void basicCompletableFuture() throws Exception {

		// Some heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod = () -> {
			return 10;
		};

		CompletableFuture<Integer> asyncFunction = CompletableFuture.supplyAsync(heavyMethod);

		/* Print the result returned by heavyMethod */
		Integer result = asyncFunction.get();
		System.out.println(result);
	}

	public static void completableFutureWithCallback() throws Exception {

		// Heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod = () -> {
			return 10;
		};

		// Print the request
		Consumer<Integer> printer = (x) -> {
			System.out.println(x);
		};

		CompletableFuture<?> asyncFunction = CompletableFuture.supplyAsync(heavyMethod).thenAccept(printer);

		asyncFunction.get();
	}

	public static void completableFutureWithCallbackChain() throws Exception {

		// Heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod = () -> {
			return 10;
		};

		// multiply the input and return the result
		Function<Integer, Integer> multiply = (x) -> {
			return x * x;
		};

		// Print the request
		Consumer<Integer> printer = (x) -> {
			System.out.println(x);
		};

		CompletableFuture<?> asyncFunction = CompletableFuture.supplyAsync(heavyMethod).thenApply(multiply)
				.thenAccept(printer);

		asyncFunction.get();
	}

	public static void completableFutureWithParallelCallbacks() throws Exception {

		// Heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod = () -> {
			return 10;
		};

		// add
		Function<Integer, Integer> add = (x) -> {
			return x + x;
		};

		// multiply
		Function<Integer, Integer> multiply = (x) -> {
			return x * x;
		};

		// Print the request
		Consumer<Integer> printer = (x) -> {
			System.out.println(x);
		};

		CompletableFuture<Integer> asyncFunction = CompletableFuture.supplyAsync(heavyMethod);

		asyncFunction.thenApplyAsync(add).thenAccept(printer);
		asyncFunction.thenApplyAsync(multiply).thenAccept(printer);

		asyncFunction.get();
	}

	public static void completableFutureExceptionHandlerUsing_exceptionally_() throws Exception {

		// Heavy computation which eventually throws NULL POINTER EXCEPTION
		Supplier<String> heavyMethod = () -> {
			return ((String) null).toUpperCase();
		};

		// Print the message
		Consumer<String> printer = (x) -> {
			System.out.println("PRINT MSG: " + x);
		};

		CompletableFuture<Void> asyncFunction = CompletableFuture.supplyAsync(heavyMethod)

				.exceptionally(ex -> {
					System.err.println("heavyMethod threw an exception: " + ex.getLocalizedMessage());
					return "NOTHING TO PRINT";
				}).thenAccept(printer);

		asyncFunction.get();
	}

	public static void completableFutureExceptionHandlerUsing_whenComplete_() throws Exception {

		// Heavy computation which eventually throws NULL POINTER EXCEPTION
		Supplier<String> heavyMethod = () -> {
			return ((String) null).toUpperCase();
		};

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

	public static void completableFutureWith_thenCombine_() throws Exception {

		// Heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod1 = () -> {
			return 10;
		};

		// Heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod2 = () -> {
			return 15;
		};

		// Print the request
		Consumer<Integer> printer = (x) -> {
			System.out.println(x);
		};

		CompletableFuture<Integer> asyncFunction1 = CompletableFuture.supplyAsync(heavyMethod1);
		CompletableFuture<Integer> asyncFunction2 = CompletableFuture.supplyAsync(heavyMethod2);

		BiFunction<Integer, Integer, Integer> sum = (result1, result2) -> {
			return (result1 + result2);
		};

		CompletableFuture<Void> combinedFunction = asyncFunction1.thenCombine(asyncFunction2, sum).thenAccept(printer);

		combinedFunction.get();
	}

	public static void completableFutureWith_runAfterBoth_() throws Exception {

		// Heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod1 = () -> {
			return 10;
		};

		// Heavy computation which eventually returns an Integer
		Supplier<Integer> heavyMethod2 = () -> {
			return 15;
		};

		CompletableFuture<Integer> asyncFunction1 = CompletableFuture.supplyAsync(heavyMethod1);
		CompletableFuture<Integer> asyncFunction2 = CompletableFuture.supplyAsync(heavyMethod2);

		Runnable print = () -> {
			System.out.println("Heavy Load methods completed successfully");
		};

		CompletableFuture<Void> combinedFunction = asyncFunction1.runAfterBoth(asyncFunction2, print);

		combinedFunction.get();
	}

	public static void completableFutureWith_acceptEither_() throws Exception {

		// Heavy computation which eventually returns an car details
		Supplier<String> carfax = () -> {
			return "2013 Toyota Corolla";
		};

		// Heavy computation which eventually returns an car details
		Supplier<String> autocheck = () -> {
			return "2013 Toyota Corolla";
		};

		CompletableFuture<String> carfaxResult = CompletableFuture.supplyAsync(carfax);
		CompletableFuture<String> autocheckResult = CompletableFuture.supplyAsync(autocheck);

		Consumer<String> carDetails = (car) -> {
			System.out.println("Car details: " + car);
		};

		CompletableFuture<Void> either = carfaxResult.acceptEither(autocheckResult, carDetails);

		either.get();
	}

	public static void main(String[] args) throws Exception {

		basicCompletableFuture();
		completableFutureWithCallback();
		completableFutureWithCallbackChain();
		completableFutureWithParallelCallbacks();
		completableFutureExceptionHandlerUsing_exceptionally_();
		completableFutureExceptionHandlerUsing_whenComplete_();
		completableFutureWith_thenCombine_();
		completableFutureWith_runAfterBoth_();
		completableFutureWith_acceptEither_();
	}
}
