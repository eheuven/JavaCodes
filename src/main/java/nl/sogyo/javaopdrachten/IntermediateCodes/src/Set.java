import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Set {
	
	public static ArrayList <SetCard> makeGivenCards() {
		String[] shapes = {"diamond","circular","tilde"};
		String[] colours = {"red","green","blue"};
		int[] amounts = {1,2,3};
		String[] fills = {"empty","hatched","filled"};		
		
		int[][] indicesGivenCards = {
			{2,0,0,0}, {1,2,0,0}, {1,0,2,1},
			{1,2,1,2}, {2,2,1,2}, {0,0,2,2},
			{0,0,0,1}, {1,1,2,1}, {2,1,1,0},
			{1,2,2,1}, {2,1,2,1}, {2,0,1,1}			
		};
		
		ArrayList <SetCard> givenCards = new ArrayList<>();
		for (int[] indices : indicesGivenCards) {
			givenCards.add(new SetCard(
				shapes[indices[0]],
				colours[indices[1]],
				amounts[indices[2]],
				fills[indices[3]]
			));
		}
		return givenCards;
	}
	
	public static boolean checkSet(HashSet <SetCard> cards) {
		HashSet<String> allShapes = new HashSet<>();
		HashSet<String> allColours = new HashSet<>();
		HashSet<Integer> allAmounts = new HashSet<>();
		HashSet<String> allFills = new HashSet<>();
		
		for(SetCard card : cards) {
			allShapes.add(card.getShape());
			allColours.add(card.getColour());
			allAmounts.add(card.getAmount());
			allFills.add(card.getFill());
		}
		
		// all 3 same or all 3 different allowed (size 1 or 3)
		return allShapes.size() != 2 &&	allColours.size() != 2 && allAmounts.size() != 2 &&	allFills.size() != 2;
	}
	
	public static HashSet <HashSet <SetCard>> findMatches(ArrayList <SetCard> cardArray) {
		HashSet <HashSet<SetCard>> matches = new HashSet<>();
		
		for(SetCard card1 : cardArray) {
			for(SetCard card2 : cardArray) {
				if (card1 != card2) {
					for(SetCard card3 : cardArray) {
						HashSet <SetCard> threeCards = new HashSet <>();
						Collections.addAll(threeCards, card1,card2,card3);
						
						if (threeCards.size() == 3) {
							if (checkSet(threeCards)) {
								matches.add(threeCards);
							}
						}
					}
				}
			}
		}
		return matches;
	}
	
	public static void main(String[] args) {
		ArrayList <SetCard> givenCards = makeGivenCards();
		HashSet <HashSet <SetCard>> matches = findMatches(givenCards);
		
		System.out.printf("%d Matches found:%n", matches.size());
		
		for(HashSet <SetCard> match : matches) {
			System.out.println("");
			for (SetCard card : match) {
				card.printCard();
			}
		}
	}

}

class SetCard {
	String shape;
	String colour;
	int amount;
	String fill;
	
	public SetCard(String figure, String rgb, int number, String filling){
		shape = figure;
		colour = rgb;
		amount = number;
		fill = filling;
	}
	
	public String getShape() {
		return shape;
	}
	
	public String getColour() {
		return colour;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public String getFill() {
		return fill;
	}
	
	public void printCard() {
		System.out.printf("%d %s %s %s%n", amount, fill, colour, shape);
	}
}