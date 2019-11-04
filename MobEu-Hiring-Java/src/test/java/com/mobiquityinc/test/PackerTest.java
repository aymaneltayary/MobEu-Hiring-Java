/**
 * 
 */
package com.mobiquityinc.test;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.StringJoiner;

import org.junit.*;
import org.junit.Test;

import com.mobiquityinc.common.ErrorCodes;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.*;

import javassist.expr.NewExpr;
import junit.framework.TestCase;
/**
 * @author aeltayary
 *
 */
public class PackerTest extends TestCase{

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	}
	@Test
	public void testInvalidFilePatht() {
		String filePath="\\";
		try {
			Packer.pack(filePath);
		} catch (APIException apex) {
				assertEquals(apex.getMessage(),ErrorCodes.INVALID_FILE_PATH);
		}
	}
	
	@Test
	public void testEmptyOrNullFilePath() {
		String filePath=null;
		try {
			Packer.pack(filePath);
		} catch (APIException apex) {
				assertEquals(apex.getMessage(),ErrorCodes.EMPTY_FILE_VALUE);
		}
	}
	
	@Test
	public void testValidFileContent() {
		String filePath="valid-content.txt";
			String result;
			StringJoiner expected= new StringJoiner("\n");
			expected.add("4");
			expected.add("-");
			expected.add("2,7");
			expected.add("8,9");
			try {
				result = Packer.pack(filePath);
				assertEquals(result,expected.toString());
			} catch (APIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
	
	@Test
	public void testInvalidLineContent() {
		String filePath="invalid-line-content.txt";
			try {
				String result = Packer.pack(filePath);
				assertTrue(outContent.toString().contains(". line will be ignored"));
			} catch (APIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
	
}
