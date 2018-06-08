import java.util.ArrayList;
import java.util.Scanner;

public class Mastermind {

	public static void main(String[] args) {
		/**
		 * guess the 4 pin codes with 6 possible colors within 10 turns
		 * Input: 4 colors (order matters)
		 * Output: 0-4 times "black" and 0-4 times "white"
		 * "black" for each pin with for correct position and "white" for correct color with wrong position
		 */
		Scanner sc = new Scanner(System.in);
		
		// make 4 pin code
		String[] colors = {"red", "blue","yellow","green","purple","orange"};
		String[] code = new String[4];
		String answer = "";
		for(int i = 0; i < 4; i++) {
			code[i] = colors[(int)(Math.random()*6)];
			answer += code[i] + " ";
			
			System.out.println(code[i]);
		}
			
		// input
		ArrayList<String> wrongGuess = new ArrayList<>();
		ArrayList<String> unGuessed = new ArrayList<>();
		String output = "";
			
		for(int turn = 1; turn <= 10; turn++) {
			System.out.println("Enter 4 colors. (red/blue/yellow/green/purple/orange)");
			
			for(int n = 0; n < 4; n++) {
				String color = sc.next();
				if(code[n].equals(color)) {
					output += "black ";
				}else {
					wrongGuess.add(color);
					unGuessed.add(code[n]);
				}
			}
			for(int pin = 0; pin < unGuessed.size(); pin++) {
				while(wrongGuess.contains(unGuessed.get(pin))) {
					output += "white ";
					wrongGuess.remove(wrongGuess.indexOf(unGuessed.get(pin)));
				}
			}
			
			if(output.equals("black black black black ")) {
				System.out.println("You win! You guessed the code!");
				break;
			}else if(turn == 10) {
				System.out.printf("You lost! You failed to guess the code in 10 turns! The code was: %s",answer);
			}else {
				System.out.println(output);
				System.out.printf("%d tries left%n%n",10-turn);
			}

			output = "";
			wrongGuess.clear();
			unGuessed.clear();
		}
		
		
		
			
		
		
		
		
	}
}
