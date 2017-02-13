package com.tw.galaxy.converter.validators;

import java.util.HashMap;
import java.util.HashSet;

import com.tw.galaxy.converter.SymbolNumberConvertor;

/*
 * validator checking subtracting
 */
public class SubtractValidator implements Validator {

	static HashMap<String, HashSet<String>> PermitSubtractMap = null;

	static {

		if (PermitSubtractMap == null) {
			PermitSubtractMap = new HashMap<String, HashSet<String>>();
			PermitSubtractMap.put("I", new HashSet<String>());
			PermitSubtractMap.put("X", new HashSet<String>());
			PermitSubtractMap.put("C", new HashSet<String>());
			PermitSubtractMap.put("V", new HashSet<String>());
			PermitSubtractMap.put("L", new HashSet<String>());
			PermitSubtractMap.put("D", new HashSet<String>());

			PermitSubtractMap.get("I").add("V");
			PermitSubtractMap.get("I").add("X");

			PermitSubtractMap.get("X").add("L");
			PermitSubtractMap.get("X").add("C");

			PermitSubtractMap.get("C").add("D");
			PermitSubtractMap.get("C").add("M"); 

		}
	}

	
	public boolean check(String subtractedSymbol, String subtractingSymbol) {
		
		if (PermitSubtractMap.get(subtractedSymbol) != null) {
			
			if (!PermitSubtractMap.get(subtractedSymbol).contains(subtractingSymbol)) {
				
				return false;
			}

		}

		return true;
	}

	
	public boolean validate(String[] symbols) {
		
		//we iterate the symbols array,find the near two symbols that the latter is bigger,
		//then check if the subtraction is permitted

		for (int i = 0; i < symbols.length; i++) {

			int nextPos = i + 1;
			int symbolsLength = symbols.length;

			if (nextPos < symbolsLength) {

				String currentSymbol = symbols[i];
				String nextSymbol = symbols[nextPos];

				HashMap<String, Integer> SymbolNumMap = SymbolNumberConvertor.getSymbolMap();

				int currentSymbolNum = SymbolNumMap.get(currentSymbol);
				int nextSymbolNum = SymbolNumMap.get(nextSymbol);

				if (currentSymbolNum < nextSymbolNum) {

					if (!check(currentSymbol, nextSymbol)) {

						return false;
					}

					i++;

				}
			}
		}

		return true;
	}

}
