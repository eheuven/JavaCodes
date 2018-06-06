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
        {"rené descartes", "cogito, ergo sum"},
        {"sir isaac newton", "if I have seen further it is by standing on the shoulders of giants"}
    };

    public static void main(String... args) {
        /** displays the quote based on the day of the month */
    	int today = LocalDate.now().getDayOfMonth();
    	DayOfWeek weekday = LocalDate.now().getDayOfWeek();
    	Month month = LocalDate.now().getMonth();
    	String dayName = weekday.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    	String monthName = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    	
    	String[] quote = quotes[(today-1) % quotes.length];
    	String person = "";
    	boolean capitalize = true;
    	
    	for(char letter : quote[0].toCharArray()) {
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
    	
    	// capitalize first letter (toUppercase) add "." (if not punctuation) quote
    	
    	System.out.printf("Quote for %s the %dth of %s:%n",dayName,today,monthName);
    	System.out.printf("\"%s\" -- %s%n",quote[1],person);
        
        
    }
}
