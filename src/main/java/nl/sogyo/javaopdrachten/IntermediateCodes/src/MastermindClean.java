import java.util.ArrayList;
import java.util.Scanner;

public class MastermindClean {

	public static String[] makeRandomColorArray() {
		/** randomly generates an array with 4 colors */
		String[] colors = {"red", "blue","yellow","green","purple","orange"};
		String[] randomColorArray = new String[4];
		for(int pinNumber = 0; pinNumber < 4; pinNumber++) {
			randomColorArray[pinNumber] = colors[(int)(Math.random()*6)];
		}
		return randomColorArray;
	}
	
	public static String[] guessToArray() {
		/** asks 4 words as input and returns array of this input */
		Scanner sc = new Scanner(System.in);
		String[] colorArray = new String[4];
		for(int pinNumber = 0; pinNumber < 4; pinNumber++) {
			colorArray[pinNumber] = sc.next();
		}
		return colorArray;
	}
	
	public static String compareColorArrays(String[] array1, String[] array2) {
		/**
		 * adds "black " to output sting if color is at same place in array
		 * adds "white " to output sting if color is in both arrays but not in same place 
		 * for each array pair, excess is considered not present in other array
		 */
		ArrayList<String> noMatchArray1 = new ArrayList<>();
		ArrayList<String> noMatchArray2 = new ArrayList<>();
		String blackWhiteString = "";
		
		for(int pinNumber = 0; pinNumber < 4; pinNumber++) {
			if(array1[pinNumber].equals(array2[pinNumber])) {
				blackWhiteString += "black ";
			}else {
				noMatchArray1.add(array1[pinNumber]);
				noMatchArray2.add(array2[pinNumber]);
			}
		}
		
		for(String array1Item : noMatchArray1) {
			if(noMatchArray2.contains(array1Item)) {
				blackWhiteString += "white ";
				noMatchArray2.remove(noMatchArray2.indexOf(array1Item));
			}
		}
		
		return blackWhiteString;
	}
	
	
	public static void main(String[] args) {
		/**
		 * guess the 4 pin codes with 6 possible colors within 10 turns
		 * Input: 4 colors (order matters)
		 * Output: 0-4 times "black" and 0-4 times "white"
		 * "black" for each pin with for correct position and "white" for correct color with wrong position
		 */
		String[] answerArray = makeRandomColorArray();
	
		for(int turn = 1; turn <= 10; turn++) {
			System.out.println("Enter 4 colors. (red/blue/yellow/green/purple/orange)");
			
			String[] guessedArray = guessToArray();
			String blackWhiteString = compareColorArrays(answerArray,guessedArray);
			
			if(blackWhiteString.equals("black black black black ")) {
				System.out.println("You win! You guessed the code!");
				break;
			}else if(turn == 10) {
				System.out.printf("You lost! You failed to guess the code in 10 turns! The code was: %s",String.join(" ",answerArray));
			}else {
				System.out.println(blackWhiteString);
				System.out.printf("%d tries left%n%n",10-turn);
			}
		}
	}
}
