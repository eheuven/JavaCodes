import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SmallGames {

	public static void coinFlip (String choice) {
		/** 
		 * Flips a coin and checks if guess is correct
		 * Input: "heads" or "tails"
		 * Prints "Correct!" or "Incorrect!" 
		 */
		
		int userChoice = -1;
		if (choice.equals("heads")) {
			userChoice = 0;
		}else if(choice.equals("tails")){
			userChoice = 1;
		}else {
			System.out.println("Error: wrong input '" + choice + "', enter 'heads' or 'tails'");
			return;
		}
		
		int coinThrow = (int) Math.round(Math.random()); // random 0 or 1

		if (userChoice == coinThrow) {
			System.out.println("Correct!");
		}else {
			System.out.println("Incorrect!");
		}
	}
	
	public static void ThreeCardMonte() {
		/** 
		 * Guess the ace in three cards until streak is broken
		 * Input: card number
		 * Output: hidden/open cards, winning streak and "Too bad!"
		 */
		Scanner input = new Scanner(System.in);
		int guess = 0;
		List<String> cards = Arrays.asList("A","K","Q");
		String[] showCards =  {"#","#","#"};

		for(int streak = 1; streak >= 0; streak++) {

			Collections.shuffle(cards);
			System.out.println("# # #");
			System.out.println("Wich one is the ace?");
			guess = input.nextInt() -1;
			
			if(guess < 0 || (guess >= cards.size())) {// wrong input
				System.out.println(cards.size());
				System.out.printf("Error: wrong input %d, pick card 1,2 or 3%n",guess+1);
				break;
			}else if(cards.get(guess) == "A") {
				showCards[guess] = "A";
				System.out.printf("%s %s %s%n",showCards[0], showCards[1], showCards[2]);
				System.out.printf("Correct! You have a winning streak of %d%n",streak);
				showCards[guess] = "#"; // hide for next time
			}else {
				streak = -1;
				showCards[guess] = cards.get(guess);
				showCards[cards.indexOf("A")] = "A";
				System.out.printf("%s %s %s%n",showCards[0], showCards[1], showCards[2]);
				System.out.println("Too bad!");
				break;
			}
		}
	}
	
	public static void higherLower() {
		/** 
		 * guess the number
		 * Input: number between 1 and 50
		 * prints "higher", "lower" or "Correct!" 
		 */
		Scanner input = new Scanner(System.in);
		int guess = -1;
		int answer = (int)(Math.random()*50 + 1);
		
		for(int i = 9; i<10; i--) {
			
			System.out.println("Enter a number:");
			guess = input.nextInt();

			if(guess == answer){
				System.out.println("Correct!");
				break;
			}else if(guess < 1 || guess > 50) {
				System.out.printf("Error: wrong input, enter a number between 0 and 50");
				break;
			}else if(i == 0) {
				System.out.printf("You lost! No guesses left, the answer was %d",answer);
				break;
			}else if(guess > answer) {
				System.out.printf("Lower %n%d guesses left%n%n",i);
			}else if(guess < answer) {
				System.out.printf("Higher %n%d guesses left%n%n",i);
			}
		}
	}
	
	public static void hangmanRequired() {
		/**
		 * Guess the letters in the word
		 * Input: character
		 * prints word with guessed letters, guesses left and missed letters
		 */
		Scanner input = new Scanner(System.in);
		
		// pick a word
		String[] words = {"test","quiz","hangman"};
		String word = words[(int)(Math.random()*(words.length))];
		char guess = ' ';
		
		// print and hide word
		String display = "";
		for(int n = 0; n < word.length(); n++) {
			display += "_ ";
		}
		System.out.println(display);
		
		// keep track of changes
		ArrayList<Character> correctLetters = new ArrayList<>();
		String missedLetters = "";
		int misses = 0;
				
		while(misses < 10){
			guess = input.next().charAt(0); // first char of input
			
			if(word.indexOf(guess)!=-1) {// letter in word
				display = "";
				correctLetters.add(guess);
				for(char letter : word.toCharArray()) {
					if(correctLetters.contains(letter)) {
						display += Character.toString(letter) + " ";
					}else {
						display += "_ ";
					}
				}
				System.out.println(display);
				if(display.indexOf('_')==-1) {// whole word guessed
					System.out.println("You win! You guessed the word!");
					break;
				}	
			}else {
				if(missedLetters.indexOf(guess) == -1) {// update missed letters
					missedLetters += Character.toString(guess) + " ";
				}
				misses++;
				System.out.println(display);
				System.out.printf("%d guesses left. Missed letters: %s%n",10-misses,missedLetters);
				if(misses == 10) { // out of guesses
					System.out.println("You lose! The word was: " + word);
				}				
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Heads or tails?");
		String coin = sc.next();
		coinFlip(coin);
		
		ThreeCardMonte();
		
		higherLower();
		
		hangmanRequired();
	}
}
