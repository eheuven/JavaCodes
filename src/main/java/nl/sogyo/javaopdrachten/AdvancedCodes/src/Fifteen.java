import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Fifteen {
	static ArrayList<SquarePiece> pieces = new ArrayList<>();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		makePieces();
		SquarePiece gap = findGap();
		printGrid();
		
		//System.out.printf("Let the computer solve this? (y/n) ");
		//if(sc.nextLine().equals("y")) {
			FifteenSolver computer = new FifteenSolver(pieces);
				
			computer.solve(gap);

			System.out.println("Computer finished solving the puzzle!");
			
		/*} else {
			while(unsolved()) {
				System.out.printf("Move which piece relative to the gap? (left/right/above/under) ");
				gap = gap.moveAdjecentPiece(sc.next());
				printGrid();
			}
			System.out.println("You solved the puzzle!");
		}*/
		sc.close();
	}

	private static void makePieces() {
		ArrayList<Integer> pictureParts = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15));
		for(int pieceNum = 0; pictureParts.size() > 0; pieceNum++) {
			int randomPieceNumber = (int) (Math.round(Math.random()*(pictureParts.size()-1)));
			pieces.add(new SquarePiece(pieceNum, pictureParts.get(randomPieceNumber))); //pictureParts.get(randomPiece)
			pictureParts.remove(randomPieceNumber);
		}
		createAdjecentPieces();
	}
	private static void createAdjecentPieces() {
		for(SquarePiece piece : pieces) {
			int pieceNr = pieces.indexOf(piece);
			if(pieceNr%4 != 0) {
				piece.setAdjacentPiece("left", pieces.get(pieceNr-1));
			}
			if(pieceNr%4 != 3) {
				piece.setAdjacentPiece("right", pieces.get(pieceNr+1));
			}
			if(pieceNr/4 != 0) {
				piece.setAdjacentPiece("above", pieces.get(pieceNr-4));
			}
			if(pieceNr/4 != 3) {
				piece.setAdjacentPiece("under", pieces.get(pieceNr+4));
			}
		}
	}

	
	public static void printGrid() {
		for(SquarePiece piece: pieces) {
			piece.printGridPart();
		}
	}
	
	
	private static boolean unsolved() {
		if (pieces.get(0).getValue() == 0) {
			return true;
		} else {	
			for(int i = 0; i < pieces.size()-2; i++) {
				if(pieces.get(i+1).getValue() != pieces.get(i).getValue()+1) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public static SquarePiece findGap() {
		for (SquarePiece piece : pieces) {
			if(piece.getValue() == 0) {
				return piece;
			}
		}
		return null;
	}
}
