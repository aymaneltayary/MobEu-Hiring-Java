/**
 * 
 */
package com.mobiquityinc.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import com.mobiquityinc.common.GeneralConstants;
import com.mobiquityinc.model.Package;
import com.mobiquityinc.model.Thing;

/**
 * The utility class that should be used to accomplish the task
 * 
 * @author aeltayary
 *
 */
public class PackageUtil {
	/**
	 * filter things based on weight
	 * Remove the thing that violates the limit
	 * 
	 * @return
	 */
	private static List<Thing> removeInvalidThings(Package pack) {
		List<Thing> filteredList = pack.getThings().stream().filter(t -> t.getWeight() < pack.getWeightLimit())
				.collect(Collectors.toList());
		pack.setThings((filteredList));
		return filteredList;

	}

	/**
	 * Get all possible options of a given package
	 * 
	 * @return
	 */

	private static List<Set<Thing>> getAllOptions(Package pack) {
		List<Set<Thing>> optionsList = new ArrayList<Set<Thing>>();
		for (int i = 1; i < pack.getThings().size(); i++) {
			Set<Thing> targetSet = new HashSet<Thing>(pack.getThings());
			Set<Set<Thing>> combinations = Sets.combinations(targetSet, i);
			for (Set<Thing> combination : combinations) {
				optionsList.add(combination);
			}
		}
		return optionsList;

	}

	/**
	 * Get total weight of set of things
	 * 
	 * @param items
	 * @return
	 */

	private static double getTotalWeight(Set<Thing> set) {
		double totalWeight = 0;
		for (Thing thing : set) {
			totalWeight += thing.getWeight();
		}
		return totalWeight;
	}

	/**
	 * Get total price of set opt things
	 * 
	 * @param items
	 * @return
	 */

	private static double getTotalPrice(Set<Thing> set) {
		double totalPrice = 0;
		for (Thing thing : set) {
			totalPrice += thing.getPrice();
		}
		return totalPrice;
	}

	/**
	 * Calculate the best set of options 
	 * @param optionsList
	 * @param pack
	 * @return
	 */

	public static Set<Thing> getBestOptions(List<Set<Thing>> optionsList, Package pack) {
		Set<Thing> bestOptionSet = new HashSet<Thing>();
		double bestCostVar = 0;
		double WeightLimitVar = GeneralConstants.PACKAGE_HUNDRED_LIMIT;

		for (Set<Thing> optionSet : optionsList) {
			double setWeight = getTotalWeight(optionSet);
			if (setWeight > pack.getWeightLimit()) {
				continue;
			} else {
				double setPrice = getTotalPrice(optionSet);
				if (setPrice > bestCostVar) {
					bestCostVar = setPrice;
					bestOptionSet = optionSet;
					WeightLimitVar = setWeight;
				} else if (setPrice == bestCostVar) { // use lightest weight
					if (setWeight < WeightLimitVar) {
						bestCostVar = setPrice;
						bestOptionSet = optionSet;
						WeightLimitVar = setWeight;
					}
				}
			}
		}
		return bestOptionSet;
	}

	/**
	 * print the best things of the package 
	 * @param pack
	 * @param limit
	 * @return
	 */
	public static String printBestPackage(Package pack, double limit) {
		List<Thing> bestThingsList = selectPackage(pack);
		return printThing(bestThingsList);

	}

	/**
	 * select the best things of the package
	 * remove invalid things
	 * determine the best options
	 * @param pack
	 * @return
	 */
	private static List<Thing> selectPackage(Package pack) {
		removeInvalidThings(pack);
		List<Set<Thing>> optionsList = getAllOptions(pack);
		Set<Thing> bestThings = getBestOptions(optionsList, pack);
		List<Thing> bestThingsList = new ArrayList<Thing>(bestThings);
		return bestThingsList;
	}

	/**
	 * return the ID(s) of thing(s) return value is comma separated
	 * 
	 * @param things
	 * @return
	 */

	private static String printThing(List<Thing> things) {
		if (things.size() == 0) {
			System.out.println("-");
			return "-";
		} else {
			StringJoiner thingJoiner = new StringJoiner(",");
			things.forEach(t -> thingJoiner.add(String.valueOf(t.getId())));
			System.out.println(thingJoiner.toString());
			return thingJoiner.toString();
		}
	}
}
