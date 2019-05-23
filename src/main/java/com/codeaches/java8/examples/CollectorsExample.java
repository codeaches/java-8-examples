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

	public static void main(String[] args) {

		/*------------------------------------------------
		Filtering a Stream
		------------------------------------------------*/
		List<Account> checkingAccounts = accounts.stream()
				.filter(account -> account.getAccountType().equals("Checking")).collect(Collectors.toList());

		System.out.println(checkingAccounts);

		/*------------------------------------------------
		Converting a Stream to a Map
		------------------------------------------------*/
		Map<String, Account> accountMap = accounts.stream()
				.collect(Collectors.toMap(Account::getAccountNumber, Function.identity()));

		System.out.println(accountMap);

		/*------------------------------------------------
		Calculating averages
		------------------------------------------------*/
		Double average = accounts.stream().collect(Collectors.averagingDouble(Account::getBalance));

		System.out.println(average);

		/*------------------------------------------------
		Calculating sum
		------------------------------------------------*/
		Double sum = accounts.stream().collect(Collectors.summingDouble(Account::getBalance));

		System.out.println(sum);

		/*------------------------------------------------
		Calculating sum for each of the group
		------------------------------------------------*/
		Map<String, Double> sumByAccountType = accounts.stream()
				.collect(Collectors.groupingBy(Account::getAccountType, Collectors.summingDouble(Account::getBalance)));

		System.out.println(sumByAccountType);

		/*------------------------------------------------
		Get maximum and minimum element
		------------------------------------------------*/
		Comparator<Account> accountBalanceComparator = (act1, act2) -> Double.compare(act2.getBalance(),
				act1.getBalance());
		Optional<Account> acctsWithMinBalance = accounts.stream().collect(Collectors.maxBy(accountBalanceComparator));

		System.out.println(acctsWithMinBalance.get());

		/*------------------------------------------------
		Partition the stream
		------------------------------------------------*/
		Predicate<Account> checkingAcctPredicate = account -> account.getAccountType().equals("Checking");

		Map<Boolean, List<Account>> mapyByAccountType = accounts.stream()
				.collect(Collectors.partitioningBy(checkingAcctPredicate));

		System.out.println(mapyByAccountType);

		/*------------------------------------------------
		Concatenate
		------------------------------------------------*/
		String accountNumbers = accounts.stream().map(Account::getAccountNumber).collect(Collectors.joining(", "));
		System.out.println(accountNumbers);

	}

	static List<Account> accounts = new ArrayList<>();

	static {
		accounts.add(new Account("John", "8001", "Savings", Double.valueOf(50.00)));
		accounts.add(new Account("Matt", "8002", "Savings", Double.valueOf(37.00)));
		accounts.add(new Account("Debra", "7001", "Checking", Double.valueOf(45.00)));
	}
}
