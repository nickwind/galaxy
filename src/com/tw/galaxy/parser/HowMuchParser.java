package com.tw.galaxy.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * parser who can accept "how many" query and convert alias words to symbols and calculate the number
 */
public class HowMuchParser  implements Parser{
	
	public boolean parse(String query){
		
		String patternStr="how much is (.*) \\?";
	    Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(query);
        
        if(matcher.matches()){
        	
        	String aliasStr=matcher.group(1);
        	
        	System.out.println(aliasStr+" is "+SymbolParser.getNumberFromAliasStr(aliasStr));
        	
        	return true;
        	
        }
        
        return false;
	}

}
