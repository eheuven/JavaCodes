import java.util.Scanner;

public class Nim {

	public static void main(String[] args) {
		/**
		 * 2 player game where 1 - 4 of 11 matches are removed, remove the last match to lose
		 * has option to play against computer
		 * Input: 1, 2, 3 or 4
		 * Output: number of matches left, move of computer player, win or lose message
		 */
		Scanner sc = new Scanner(System.in);
		int matches = 11;
		int playerTurn = 1;
		int choice = 0;
		int[] compMove = {1,1,2,3,4,1,1,2,3,4,1}; // computer always wins
		
		System.out.println("Do you want to start a 2 player game? true/false");
		boolean twoPlayers = sc.nextBoolean();
		
		while(matches > 0) {
			if(twoPlayers) {
				System.out.printf("%nThere are %d matches.%nHow many does player %d want to take? ", matches,playerTurn);
			}else {	
				System.out.printf("%nThere are %d matches.%nHow many do you want to take? ", matches);	
			}
			
			choice = sc.nextInt();
			if(choice >= 1 && choice <= 4) {
				if (choice <= matches){	
					matches -= choice;

					if(matches == 0) {
						if (twoPlayers) {
							System.out.printf("%nThe last match is taken.%nPlayer %d loses!", playerTurn);
						}else {	
							System.out.printf("%nYou took the last match.%nYou lost!");
						}
						
					}else {
						if(twoPlayers) {
							if (playerTurn == 1) {
								playerTurn = 2;
							}else {
								playerTurn = 1;
							}
						}else {	
							System.out.printf("%nThere are %d matches.%nComputer takes %d matches.%n", matches,compMove[matches-1]);
							matches -= compMove[matches-1];
							if(matches == 0) {
								System.out.printf("%nThe computer took the last match.%nYou win!");
							}	
						}
					}
				}else {
					System.out.println("Error: not enough matches left");
				}
			}else { 
				System.out.println("Error: wrong amount of matches, enter 1, 2, 3 or 4");
			}
		}	
	}
}
