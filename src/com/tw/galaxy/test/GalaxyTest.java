package com.tw.galaxy.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import com.tw.galaxy.processor.Processor;

/*
 * GalaxyTest provide the unit test function
 */
public class GalaxyTest {

	private static final String TestPath="tests/";
	private static final String TestDataPath=TestPath+"testData/";
	private static final String TestTmpDataFile=TestPath+"tmpData/tmp";
	private static final String TestCaseFile=TestPath+"tests.txt";
	
	
	private PrintStream oldPs;
	private String testName = null;

	public GalaxyTest() {

		oldPs = System.out;

	}
	

	public void setTestName(String testName) {

		this.testName = testName;
	}
	

	private ArrayList<String> readLinesFromFile(String fileName) {

		File file = new File(fileName);

		if (!file.exists()) {

			return null;
		}

		try {

			ArrayList<String> lines = new ArrayList<String>();

			FileReader fr = new FileReader(file);

			BufferedReader bf = new BufferedReader(fr);

			String line = null;

			while ((line = bf.readLine()) != null) {

				lines.add(line.trim());
			}

			bf.close();

			return lines;

		} catch (Exception e) {

			return null;
		}

	}
	

	private void setTmpOutFile() throws IOException {

		File f = new File(TestTmpDataFile);
		f.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(f);
		PrintStream printStream = new PrintStream(fileOutputStream);
		System.setOut(printStream);
	}

	
	private void executeQueryFromFile(String fileName) throws IOException {

		Processor processor = Processor.createProcessor();
		
		ArrayList<String> queries=this.readLinesFromFile(TestDataPath+fileName);
		
		for(String query:queries){
			
			processor.executeQuery(query);
		}
		
	}

	
	private void compareFile(String expectResultFileName) throws IOException {

		System.setOut(oldPs);
		
		ArrayList<String> runningResultStrs=this.readLinesFromFile(TestTmpDataFile);
		ArrayList<String> expectingResultStrs=this.readLinesFromFile(TestDataPath+expectResultFileName);
		
		if(runningResultStrs.size()!=expectingResultStrs.size()){
			
			System.out.println(this.testName + " .........Failed");
			
			return;
		}
		
		for(int i=0;i<runningResultStrs.size();i++)
		{
			if(!runningResultStrs.get(i).equals(expectingResultStrs.get(i))){
				
				System.out.println(this.testName + " .........Failed");
				
				return;
			}
		}
		
		System.out.println(this.testName + " .........Passed!");
		
	}

	
	public void runTest(String testName, String inputQueryFileName, String expectResultFileName) {

		this.setTestName(testName);

		try {
			setTmpOutFile();
			executeQueryFromFile(inputQueryFileName);
			compareFile(expectResultFileName);
		} catch (IOException e) {

			System.out.println("please check if the required files are all provided!!!");
		}

	}
	
	
	public static void runTests(){
		
		GalaxyTest test = new GalaxyTest();
		
		ArrayList<String> testQueries=test.readLinesFromFile(TestCaseFile);
		
		for(String testQuery:testQueries){
			
			String[] queryWords=testQuery.split(",");
			
			test.runTest(queryWords[0], queryWords[1], queryWords[2]);
			
		}
	}

	
	public static void main(String[] args) throws IOException {

		
		GalaxyTest.runTests();

	}

}
