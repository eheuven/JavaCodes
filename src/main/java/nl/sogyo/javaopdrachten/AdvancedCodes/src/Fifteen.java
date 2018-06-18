import java.util.ArrayList;

public class Fifteen {
	static ArrayList<SquarePiece> pieces = new ArrayList<>();
	
	
	public static void main(String[] args) {
		/*
		 * randommize steentjes, maar solvable
		 * move witch piece next to gap?
		 */ 
		
		makePieces();
		printGrid();
		pieces.get(1).toGap(findGap());
		printGrid();
	}


	private static void makePieces() {
		ArrayList<String> pictureParts = makePicture();
		for(int pieceNum = 0; pictureParts.size() > 0; pieceNum++) {
			int randomPiece = (int) (Math.round(Math.random()*(pictureParts.size()-1)));
			pieces.add(new SquarePiece(pieceNum, pictureParts.get(randomPiece))); //pictureParts.get(randomPiece)
			pictureParts.remove(randomPiece);
		}
		createAdjecentPieces();
	}
	private static void createAdjecentPieces() {
		for(SquarePiece piece : pieces) {
			if(pieces.indexOf(piece)!= 0) {
				piece.setAdjacentPiece("left", pieces.get(pieces.indexOf(piece)-1));
			}
			if(pieces.indexOf(piece) != pieces.size()-1) {
				piece.setAdjacentPiece("right", pieces.get(pieces.indexOf(piece)+1));
			}
			
			// make up and down
		}
	}


	private static ArrayList<String> makePicture() {
		ArrayList<String> pictureParts = new ArrayList<>();
		pictureParts.add("   ");
		for(int i = 1; i<16; i++) {
			if (i<10) {
				pictureParts.add(" " + String.valueOf(i) + " ");
			}else {
				pictureParts.add(String.valueOf(i) + " ");
			}
		}
		return pictureParts;
	}
	
	
	public static void printGrid() {
		for(SquarePiece piece: pieces) {
			piece.printGridPart();
		}
	}
	
	
	public static SquarePiece findGap() {
		for (SquarePiece piece : pieces) {
			if(piece.getValue().equals("   ")) {
				return piece;
			}
		}
		return null;
	}

}
