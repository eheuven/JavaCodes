import java.util.HashMap;

public class SquarePiece {
	int rowNumber;
	int colNumber;
	String value; // instead of picture
	HashMap<String,SquarePiece> adjacentPieces = new HashMap <>();
	
	public SquarePiece(int pieceNum, String picture) {
		rowNumber = pieceNum / 4;
		colNumber = pieceNum % 4;
		value = picture;
	}

	public String getValue() {
		return value;
	}
	
	public void setAdjacentPiece(String direction, SquarePiece piece) { // make move method with error if no tile at side
		adjacentPieces.put(direction, piece);
	}
	
	public void setValue(String picturePart) {
		value = picturePart;
	}
	
	public void printGridPart() {
		if (rowNumber == 0 && colNumber == 0) {
			System.out.printf(" ------------- %n| ");
		} else if (colNumber == 0) {
			System.out.printf("|%n| ");
		}
		
		System.out.print(value);
		
		if (rowNumber == 3 && colNumber == 3) {
			System.out.printf("|%n ------------- %n%n");
		}
	}
	
	public void toGap(SquarePiece gap) {
		String currentValue = getValue();
		setValue(gap.getValue());
		gap.setValue(currentValue);
	}
}
