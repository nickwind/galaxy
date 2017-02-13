package com.tw.galaxy.converter.validators;
import java.util.HashMap;
import java.util.HashSet;

import com.tw.galaxy.converter.SymbolNumberConvertor;

/*
 * validator checking if the symbol repeated more than three times in succession 
 */
public class RepeatValidator implements Validator {
	
	private static final int MaxRepeatCount=3;

	private static HashSet<String> PermitRepeatWords = null;
	static {
		
		if (PermitRepeatWords == null) {
			PermitRepeatWords = new HashSet<String>();
			PermitRepeatWords.add("I");
			PermitRepeatWords.add("X");
			PermitRepeatWords.add("C");
			PermitRepeatWords.add("M");
		}
	}

	
	@Override
	public boolean validate(String[] symbols) {

		String currentWord = null;
		String lastWord = null;
		int existCount = 1;

		for (int i = 0; i < symbols.length; i++) {
			
			currentWord = symbols[i];

			if (lastWord != null) {
				
				if (currentWord.equals(lastWord)) {
					
					existCount++;
				} else {
					
					//current symbol is different from the last one,so we check if the symbol who
					//repeated is in accordance with the rule here
					
					if (existCount > 1 && !PermitRepeatWords.contains(lastWord)) {
						
						return false;
					}

					if (existCount == MaxRepeatCount) {
						
						int nextPos = i + 1;
			
						if (nextPos < symbols.length) {
							
							String nextWord = symbols[nextPos];

							HashMap<String, Integer> symbolMap = SymbolNumberConvertor.getSymbolMap();
							
							if (nextWord == lastWord
									&& (symbolMap.get(lastWord) > symbolMap.get(currentWord))) {
								
								return false;
							}
						}
					}
					else if(existCount > MaxRepeatCount)
					{
						return false;
					}

					existCount = 1;
				}

			}

			lastWord = currentWord;

		}

		return true;
	}
	
}
