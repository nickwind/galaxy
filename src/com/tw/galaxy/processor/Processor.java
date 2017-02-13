package com.tw.galaxy.processor;
import java.util.ArrayList;
import java.util.Scanner;

import com.tw.galaxy.parser.HowManyParser;
import com.tw.galaxy.parser.HowMuchParser;
import com.tw.galaxy.parser.Parser;
import com.tw.galaxy.parser.PriceParser;
import com.tw.galaxy.parser.SymbolParser;

/*
 * processor that accept query from keyborad input or test data and pass it to the corresponding parser
 */
public class Processor {

	private ArrayList<Parser> parsers = null;

	private Processor(ArrayList<Parser> parsers) {

		this.parsers = parsers;
	}

	
	public static Processor createProcessor() {

		SymbolParser symbolParser = new SymbolParser();
		PriceParser priceParser = new PriceParser();
		HowMuchParser howMuchParser = new HowMuchParser();
		HowManyParser howManyParser = new HowManyParser();

		ArrayList<Parser> parsers = new ArrayList<Parser>();
		parsers.add(symbolParser);
		parsers.add(priceParser);
		parsers.add(howMuchParser);
		parsers.add(howManyParser);

		Processor processor = new Processor(parsers);

		return processor;

	}

	
	public void executeQuery(String query) {

		if (!query.equals("")) {

			boolean isProcessed = false;

			for (Parser parser : parsers) {

				if (parser.parse(query)) {

					isProcessed = true;
					break;
				}
			}

			if (!isProcessed) {
				System.out.println("I have no idea what you are talking about");
			}
		}
	}

	
	public void run() {

		Scanner sc = new Scanner(System.in);

		while (sc.hasNext()) {

			String query = sc.nextLine();
			this.executeQuery(query);

		}

		sc.close();
	}

	
	public static void main(String[] args) {
		
		Processor processor=Processor.createProcessor();
		processor.run();

	}

}
