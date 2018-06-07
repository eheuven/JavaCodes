//package nl.sogyo.javaopdrachten.quote;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class Quote {
    static String[][] quotes = {// made static
        {"galileo", "eppur si muove"},
        {"archimedes", "eureka!"},
        {"erasmus", "in regione caecorum rex est luscus"},
        {"socrates", "I know nothing except the fact of my ignorance"},
        {"rené descartes", "cogito, ergo sum"}, // é instead of �
        {"sir isaac newton", "if I have seen further it is by standing on the shoulders of giants"}
    };

    public static void main(String... args) {
        /** displays the quote based on the day of the month */
    	// date
    	int today = LocalDate.now().getDayOfMonth();
    	DayOfWeek weekday = LocalDate.now().getDayOfWeek();
    	Month month = LocalDate.now().getMonth();
    	String dayName = weekday.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    	String monthName = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    	
    	// quote
    	String[] quote = quotes[(today-1) % quotes.length];
    	boolean capitalize = true;
    	String person = "";
    	
    	for(char letter : quote[0].toCharArray()) {// capitalize names
    		if (capitalize) {
    			person += Character.toString(letter).toUpperCase();
    			capitalize = false;
    		} else {
    			person += Character.toString(letter);
    			if(letter == ' ') {
    				capitalize = true;
    			}
    		}
    	}
    	
    	capitalize = true;
    	char[] quoteChars = quote[1].toCharArray();
    	quoteChars[0] = Character.toUpperCase(quoteChars[0]);
    	String saying = new String(quoteChars);
    	char lastChar = quoteChars[quoteChars.length-1];
    	if (lastChar != '!' && lastChar != '.' && lastChar != ',' && lastChar != ';' && lastChar != ':') {
    		saying += ".";
    	}
    	
    	System.out.printf("Quote for %s the %dth of %s:%n",dayName,today,monthName);
    	System.out.printf("\"%s\" -- %s%n",saying,person);
    }
}
