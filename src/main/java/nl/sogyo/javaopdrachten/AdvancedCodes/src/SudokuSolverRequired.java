import java.util.ArrayList;
import java.util.Collections;

public class SudokuSolverRequired {
	static ArrayList<ArrayList<Coordinate>> nestedCoordinateList = new ArrayList<>();
	static ArrayList<ArrayList<Coordinate>> coordinatePerBlockList = new ArrayList<>();

	private static void initializeCoordinateLists() {
		for(int i = 0; i < 9; i++) {
			nestedCoordinateList.add(new ArrayList<Coordinate>());
			coordinatePerBlockList.add(new ArrayList<Coordinate>());
		}
	}
	
	public static void printRow(ArrayList<Coordinate> row) {
		System.out.print("|");
		for(Coordinate coord : row) {
			coord.printCoord();
			coord.printBoxPart();
		}
		System.out.println();
	}
	
	public static void readSudoku(String sudokuInput) {
		initializeCoordinateLists();
		char[] valueArray = sudokuInput.toCharArray();
		
		for(int coordNum = 0; coordNum < valueArray.length; coordNum++) {
			Coordinate coord = new Coordinate(coordNum);
			coord.assignValue(Character.getNumericValue(valueArray[coordNum]));
			
			nestedCoordinateList.get(coord.getRowNumber()).add(coord);
			coordinatePerBlockList.get(coord.getBlockNumber()).add(coord);
		}
	}
	
	public static void printSudoku() {
		System.out.println(" ----- ----- ----- ");
		for(ArrayList<Coordinate> row : nestedCoordinateList) {
			printRow(row);
			if(nestedCoordinateList.indexOf(row) % 3 == 2) {
				System.out.println(" ----- ----- ----- ");
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		// scanner.next() for input --> error on .nextInt(), the huge number is recognized as string
		String sudokuInput = "000820090500000000308040007100000040006402503000090010093004000004035200000700900";
		
		readSudoku(sudokuInput);
		printSudoku();
		
		// solve sudoku (translate from python) & track time
		// print solved state & solving time
	}

}

class Coordinate {
	
	int rowNumber;
	int columnNumber;
	int blockNumber;
	int assignedValue;
	ArrayList<Integer> possibleValueList;
	
	public Coordinate(int coordNum) {
		rowNumber = coordNum / 9;
		columnNumber = coordNum % 9;
		blockNumber = rowNumber / 3 + columnNumber / 3;
		
		possibleValueList = new ArrayList<>();
		Collections.addAll(possibleValueList,1,2,3,4,5,6,7,8,9);
	}
	
	public int getAssignedValue() {
		return assignedValue;
	}
	
	public int getRowNumber() {
		return rowNumber;
	}
	
	public int getColNumber() {
		return columnNumber;
	}
	
	public int getBlockNumber() {
		return blockNumber;
	}
	
	public void assignValue(int value) {
		assignedValue = value;
		if (value != 0) {
			possibleValueList.clear();
		}
	}
	
	public void printCoord() {
		if(assignedValue == 0) {
			System.out.print(" ");
		}else {				
			System.out.print(assignedValue);
		}
	}
	
	public void printBoxPart() {
		if (columnNumber % 3 == 2) {
			System.out.print("|");
		} else {
			System.out.print(" ");
		}
	}
	
	
	
	
	
	
}