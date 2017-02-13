package com.tw.galaxy.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * parser who can accept "how many" query and give the total price 
 */
public class HowManyParser implements Parser{

	public boolean parse(String content) {

		String patternStr = "how many Credits is (.*) \\?";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(content);

		if (matcher.matches()) {
			
			String wordsStr = matcher.group(1);
			String[] words = wordsStr.split(" ");
			String goodName = words[words.length - 1];

			String aliasWordStr = wordsStr.substring(0, wordsStr.lastIndexOf(goodName));
			int num = SymbolParser.getNumberFromAliasStr(aliasWordStr);
			
			if (num != -1) {
				
				Float price = PriceParser.getPriceMap().get(goodName);
				
				if (price != null) {
					
					System.out.println(wordsStr + " is " +(int)(price * num) + " Credits");
					return true;
				}
			}
		}

		return false;

	}

}
