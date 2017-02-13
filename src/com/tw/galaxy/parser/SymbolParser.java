package com.tw.galaxy.parser;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tw.galaxy.converter.SymbolNumberConvertor;

/*
 * Parser who can accept "xx is xx " query and mapp alias word to symbol
 */
public class SymbolParser implements Parser {
	
	private static HashMap<String, String> AliasWordMap;
	
	private static StringBuffer PermitSymbolsRegStr = null;

	static {
		
		if (PermitSymbolsRegStr == null) {

			PermitSymbolsRegStr = new StringBuffer();
			PermitSymbolsRegStr.append("[");
			
			for (String symbol : SymbolNumberConvertor.getSymbolSet()) {
				PermitSymbolsRegStr.append(symbol);
			}
			
			PermitSymbolsRegStr.append("]");
		}

		if (AliasWordMap == null) {
			AliasWordMap = new HashMap<String, String>();
		}
	}

	
	public SymbolParser() {

	}

	
	public static String getSymbolFromAlias(String alias) {
		return AliasWordMap.get(alias);
	}

	
	public boolean parse(String content) {
		
		String patternStr = "(\\w+) is (" + PermitSymbolsRegStr + ")";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(content);

		if (matcher.matches()) {
			
			String alias = matcher.group(1);
			String symbol = matcher.group(2);

			AliasWordMap.put(alias, symbol);
			return true;
			
		} else {
			
			return false;
		}

	}

	
	public static int getNumberFromAliasStr(String aliasStr) {
		
		String[] aliasWords = aliasStr.split(" ");
		StringBuffer symbols = new StringBuffer();

		for (int i = 0; i < aliasWords.length; i++) {
			
			String currentWord = aliasWords[i];

			String symbol = getSymbolFromAlias(currentWord);
			
			if (symbol == null) {
				return -1;
			}
			symbols.append(symbol);

		}
		
		int num = SymbolNumberConvertor.getInstance().convertSymbols2Number(symbols.toString());
		
		return num;
	}

}
