package com.mobiquityinc.packer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.regex.Pattern;

import com.mobiquityinc.common.ErrorCodes;
import com.mobiquityinc.common.GeneralConstants;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.exception.InvalidLineContentException;
import com.mobiquityinc.model.Package;
import com.mobiquityinc.model.Thing;
import com.mobiquityinc.service.PackageUtil;

/**
 * main class this class used com.mobiquityinc.packer.PackageUtil to do the main
 * job
 * 
 * @author aeltayary
 *
 */

public class Packer {

	private Packer() {
	}

	/**
	 * validate line content based on regex
	 * 
	 * @param lineContent
	 * @throws APIException
	 */
	private static void validateLineContent(String lineContent) throws InvalidLineContentException {
		if (lineContent.length() == 0) {
			throw new InvalidLineContentException(ErrorCodes.EMPTY_CONTENT_ERROR);
		} else {
			boolean isValid = Pattern.matches(GeneralConstants.CONTENT_REG_EXPR, lineContent);
			if (!isValid)
				throw new InvalidLineContentException(ErrorCodes.INVALID_FILE_CONTENT);
		}
	}

	/**
	 * check if the file value was null or empty If yes an exception will be thrown
	 * 
	 * @param filePath
	 * @throws APIException
	 */
	private static void validateArgument(String filePath) throws APIException {
		if (filePath == null || filePath.isEmpty())
			throw new APIException(ErrorCodes.EMPTY_FILE_VALUE);

	}

	/**
	 * Main method that will do the main job
	 * 
	 * @param filePath
	 * @return
	 * @throws APIException
	 */
	public static String pack(String filePath) throws APIException {
		Scanner fileScanner = null;
		try {
			validateArgument(filePath);
			FileInputStream in = new FileInputStream(filePath);
			fileScanner = new Scanner(in, GeneralConstants.UTF8_ENCODING);
			StringJoiner result = new StringJoiner("\n");
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				Package p;
				try {
					p = constructPackage(line);
				} catch (InvalidLineContentException e) {
					System.out.println("invalid line content, " + line + " line will be ignored");
					continue;
				}
				String packageString = PackageUtil.printBestPackage(p, GeneralConstants.PACKAGE_HUNDRED_LIMIT);
				result.add(packageString);

			}
			return result.toString();
		} catch (FileNotFoundException fex) {
			System.out.println("The system could not be able to find " + filePath);
			throw new APIException(ErrorCodes.INVALID_FILE_PATH);
		} finally {
			closeScanner(fileScanner);

		}

	}

	/**
	 * Close file scanner if it was used
	 * 
	 * @param fileScanner
	 */
	private static void closeScanner(Scanner fileScanner) {
		if (fileScanner != null) {
			fileScanner.close();
		}
	}

	/**
	 * construct package based on line of content
	 * 
	 * @param line
	 * @return
	 * @throws InvalidLineContentException
	 */
	private static Package constructPackage(String line) throws InvalidLineContentException {
		validateLineContent(line);
		String[] lineArray = line.split(" *: *");
		double weightLimit = Integer.parseInt(lineArray[0].trim());
		String[] thingsString = lineArray[1].split(" +");
		ArrayList<Thing> thingList = new ArrayList<Thing>();
		for (String thingString : thingsString) {
			String[] thingDetails = thingString.split(",");
			int id = Integer.parseInt(thingDetails[0].substring(1));
			double weight = Double.parseDouble(thingDetails[1].trim());
			double price = Double.parseDouble(thingDetails[2].substring(1, thingDetails[2].length() - 1));
			Thing thing = new Thing(id, weight, price);
			thingList.add(thing);
		}
		Package p = new Package(weightLimit, thingList);
		return p;

	}

	/**
	 * dummy main method to test the main job
	 * 
	 * @param args
	 * @throws APIException
	 */
	public static void main(String[] args) throws APIException {
		pack(args[0]);
	}

}
