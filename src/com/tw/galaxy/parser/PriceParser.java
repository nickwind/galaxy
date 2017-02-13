package com.tw.galaxy.parser;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * parser who can accept "xx is xx Credits" and calculate the price  of good
 */
public class PriceParser implements Parser{
	
	private static HashMap<String, Float> PriceMap=null;

	static {
		if (PriceMap == null) {
			PriceMap = new HashMap<String, Float>();
		}
	}

	public static HashMap<String, Float> getPriceMap() {
		return PriceMap;
	}

	
	public boolean parse(String content) {
		
		String patternStr = "(.*) is (\\d+) Credits";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(content);

		if (matcher.matches()) {
			
			String wordsStr = matcher.group(1);
			int amount = Integer.parseInt(matcher.group(2));

			String[] words = wordsStr.split(" ");

			String goodName = null;

			goodName = words[words.length - 1];
			String aliasWordsStr = wordsStr.substring(0, wordsStr.lastIndexOf(goodName));
			int num = SymbolParser.getNumberFromAliasStr(aliasWordsStr);

			if (num != -1) {
				
				float price =((float)amount) / num;
				PriceMap.put(goodName, price);
				
			} else {
				
				return false;
			}

			return true;
		}
		
		return false;
	}

}
