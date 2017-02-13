package com.tw.galaxy.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.tw.galaxy.converter.validators.RepeatValidator;
import com.tw.galaxy.converter.validators.SubtractValidator;
import com.tw.galaxy.converter.validators.Validator;

/*
 * converter which can convert symbol like I,V,X,L,C,D,M to numbers
 */

public class SymbolNumberConvertor {

	private static HashMap<String, Integer> SymbolNumMap = null; 

	private static HashSet<String> SymbolSet = null;

	private static SymbolNumberConvertor Instance = null;
	
	private ArrayList<Validator> validators=null;

	static {

		if (SymbolNumMap == null) {

			SymbolNumMap = new HashMap<String, Integer>();
			SymbolNumMap.put("I", 1);
			SymbolNumMap.put("V", 5);
			SymbolNumMap.put("X", 10);
			SymbolNumMap.put("L", 50);
			SymbolNumMap.put("C", 100);
			SymbolNumMap.put("D", 500);
			SymbolNumMap.put("M", 1000);

		}

		if (SymbolSet == null) {

			SymbolSet = new HashSet<String>();
			SymbolSet.add("I");
			SymbolSet.add("V");
			SymbolSet.add("X");
			SymbolSet.add("L");
			SymbolSet.add("C");
			SymbolSet.add("D");
			SymbolSet.add("M");

		}
	}

	
	private SymbolNumberConvertor(ArrayList<Validator> validators) {
		
		this.validators=validators;

	}

	
	public static SymbolNumberConvertor getInstance() {

		if (Instance == null) {
			
			ArrayList<Validator> validators=new ArrayList<Validator>();
			
			RepeatValidator repeatChecer = new RepeatValidator();
			SubtractValidator subtractChecker = new SubtractValidator();
			
			validators.add(repeatChecer);
			validators.add(subtractChecker);
			
			
			Instance = new SymbolNumberConvertor(validators);
		}

		return Instance;
	}

	
	public static HashMap<String, Integer> getSymbolMap() {

		return SymbolNumMap;
	}

	
	public static HashSet<String> getSymbolSet() {

		return SymbolSet;
	}
	
	
	private boolean doValidate(String[] symbols){
		
		for(Validator validator:this.validators){
			
		   if(!validator.validate(symbols)){
			   
			   return false;
		   }
			
		}
		
		return true;
		
	}
	
	
	//this is the core method for this class whose function is converting symbol to numbers
	
	private int calculateFromSymbols(String[] symbols){
		
		int result = 0;

		int symbolsLength = symbols.length;

		for (int i = 0; i < symbolsLength; i++) {

			int currentPos = i;
			int nextPos = i + 1;

			String currentSymbol = symbols[currentPos];
			int currentSymbolNum = SymbolNumMap.get(currentSymbol);

			if (nextPos < symbolsLength) {

				//if more than one words left,then compare if the latter symbol is bigger,if that bigger one 
				//should minus the smaller one,if not just get the number and add to the result
				
				String nextSymbol = symbols[nextPos];

				int nextSymbolNum = SymbolNumMap.get(nextSymbol);

				if (currentSymbolNum < nextSymbolNum) {

					result += nextSymbolNum - currentSymbolNum;
					i++;

				} else {

					result += currentSymbolNum;
				}

			} else {

				//if only one symbol left,just get the number and add to the result
				result += currentSymbolNum;
			}
		}

		return result;
	}
	

	public int convertSymbols2Number(String symbolStr) {

		String[] symbols = symbolStr.split("");
		
         if(!this.doValidate(symbols)){
        	 
        	 return -1;
         }

		return this.calculateFromSymbols(symbols);

	}

}
