/**
 * 
 */
package com.mobiquityinc.common;

/**
 * This class include all contants
 * that could be used in the  
 * @author aeltayary
 *
 */
public interface GeneralConstants {

	public static final String UTF8_ENCODING="UTF-8";
	//public static final String CONTENT_REG_EXPR="[0-9]+  *:  *[\\([0-9]+,?[0-9]*\\.?[0-9]*,€[0-9]+\\) ]+";
	public static final String CONTENT_REG_EXPR="[0-9]+  *:  *[\\([^\\s][0-9]+,?[0-9]*\\.?[0-9]*,€[0-9]+\\) ]+";
	public static final int PACKAGE_HUNDRED_LIMIT=100;
}
