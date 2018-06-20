import java.util.HashMap;
import java.util.Map.Entry;

public class SquarePiece {
	int rowNumber;
	int colNumber;
	int value; 
	boolean moveable = true;
	HashMap<String,SquarePiece> adjacentPieces = new HashMap <>();
	
	public SquarePiece(int pieceNum, int pictureNum) {
		rowNumber = pieceNum / 4;
		colNumber = pieceNum % 4;
		value = pictureNum;
	}

	public int getValue() {
		return value;
	}
	
	public boolean adjacentPieceMoveable(String direction) {
		if (adjacentPieces.containsKey(direction)) {
			return adjacentPieces.get(direction).isMoveable(); 
		}
		return false;
	}
	
	public void setAdjacentPiece(String direction, SquarePiece piece) { 
		adjacentPieces.put(direction, piece);
	}
	
	public void setMoveable(boolean enabled) {
		moveable = enabled;
	}
	
	public void setValue(int pictureNum) {
		value = pictureNum;
	}
	
	public int getRowNumber() {
		return rowNumber;
	}
		
	public int getColNumber() {
		return colNumber;
	}
	
	private boolean isMoveable() {
		return moveable;
	}
	
	public SquarePiece getAdjecentPiece(String direction) {
		return adjacentPieces.get(direction);
	}
	
	public void printGridPart() {
		if (rowNumber == 0 && colNumber == 0) {
			System.out.printf("%n ------------- %n| ");
		} else if (colNumber == 0) {
			System.out.printf("|%n| ");
		}
		
		printPicturePart();
		
		if (rowNumber == 3 && colNumber == 3) {
			System.out.printf("|%n ------------- %n");
		}
	}
	private void printPicturePart() {
		if(value == 0) {
			System.out.print("   ");
		}else if (value <10) {
			System.out.print(" " + String.valueOf(value) + " ");
		}else {
			System.out.print(String.valueOf(value) + " ");
		}
	}
		
	public SquarePiece moveAdjecentPiece(String direction) {
		if (adjacentPieces.containsKey(direction)){
			int gapValue = getValue();
			setValue(adjacentPieces.get(direction).getValue());
			adjacentPieces.get(direction).setValue(gapValue);
			return adjacentPieces.get(direction);
		}else {
			System.out.println("Piece does not exist"); 
			return this;
		}
		
	}
	
	public SquarePiece switchPieces(SquarePiece tile) {
		int tileValue = tile.getValue();
		tile.setValue(getValue());
		setValue(tileValue);
		return tile;
	}

	public String getDirection(SquarePiece piece) {
		for(Entry<String, SquarePiece> entry : adjacentPieces.entrySet()) {
			if(entry.getValue() == piece) {
				return entry.getKey();
			}
		}
		return null;
	}



	
}
