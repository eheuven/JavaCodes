import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuSolverRequired {
	static ArrayList<ArrayList<Coordinate>> nestedCoordinateList = new ArrayList<>();
	static ArrayList<ArrayList<Coordinate>> coordinatePerColumnList = new ArrayList<>();
	static ArrayList<ArrayList<Coordinate>> coordinatePerBlockList = new ArrayList<>();
	static boolean change = true;

	private static void initializeCoordinateLists() {
		for(int i = 0; i < 9; i++) {
			nestedCoordinateList.add(new ArrayList<Coordinate>());
			coordinatePerColumnList.add(new ArrayList<Coordinate>());
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
			coordinatePerColumnList.get(coord.getColNumber()).add(coord);
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
	
	private static void solveSudoku() {
		/* rules to implement: 
		 *  rem due coord value: x coords with same x values
		 *  - list of loopItems: loopItem.length <= (givenItem.length)+1 
		 *  - set of posValues: each item.posValues of loopItem list
		 *  - if set.length = list.length
		 *  	--> remove all posValues in unit except for items in loopItems list
		 * 
		 * rem due coord place 
		 * - xy wing
		 * - swordfish
		 */
		
		while(change) {
			change = false;
			for(ArrayList<Coordinate> row : nestedCoordinateList) {
				for(Coordinate coord : row) {
					fillSudokuField(coord);
					// new rule per coordinate
				}
			}
			System.out.println(change);
		}

	}
	
	private static void fillSudokuField(Coordinate coord) {
		if (coord.getAssignedValue() != 0) {
			assignIfOnlyPosInUnit(coord);
			assignIfCoordOnePos(coord);
			if(change) {
				removePosDueAssignedValue(coord);
			}
		}
	}

	private static void assignIfOnlyPosInUnit(Coordinate givenCoord) {
		if(givenCoord.getAssignedValue() == 0) {
			for(Integer pos : givenCoord.getPossibleValueList()) {
				for (ArrayList <Coordinate> unit : makeInfluenceableCoordinateLists(givenCoord)) {
					if (!posInUnit(pos, unit)) {
						givenCoord.assignValue(pos); 
						change = true;
						break;
					}
				}
			}
		}
	}

	private static boolean posInUnit(Integer pos, ArrayList <Coordinate> unit) {
		for(Coordinate coord : unit) {
			if(coord.getPossibleValueList().contains(pos)) {
				return true;
			}
		}
		return false;
	}

	private static void assignIfCoordOnePos(Coordinate coord) {
		if(coord.getPossibleValueList().size() == 1) {
			coord.assignValue(coord.getPossibleValueList().get(0));
			change = true;
		}
	}

	private static void removePosDueAssignedValue(Coordinate givenCoord) {
		ArrayList<ArrayList <Coordinate>> influenceableCoordinates = makeInfluenceableCoordinateLists(givenCoord);
		for(ArrayList <Coordinate> unit : influenceableCoordinates) {
			for(Coordinate loopCoord : unit) {
				if (loopCoord.getAssignedValue() == 0) {
					loopCoord.removePos(givenCoord.getAssignedValue());
				}	
			}	
		}	
	}

	private static ArrayList<ArrayList <Coordinate>> makeInfluenceableCoordinateLists(Coordinate coord) {
		ArrayList<ArrayList <Coordinate>> influenceableCoordinates = new ArrayList<>();
		influenceableCoordinates.add(nestedCoordinateList.get(coord.getRowNumber()));
		influenceableCoordinates.add(coordinatePerColumnList.get(coord.getColNumber()));
		influenceableCoordinates.add(coordinatePerBlockList.get(coord.getBlockNumber()));
		for (ArrayList <Coordinate> coordList : influenceableCoordinates) { 
			coordList.remove(coord);
		}
		return influenceableCoordinates;
	}

	public static void main(String[] args) {
		// scanner.next() for input --> error on .nextInt(), the huge number is recognized as string
		String sudokuInput = "000820090500000000308040007100000040006402503000090010093004000004035200000700900";
		
		readSudoku(sudokuInput);
		
		System.out.println("Initial State:");
		printSudoku();
		
		solveSudoku();
		
		System.out.printf("%n Solved:%n");
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
	List<Integer> possibleValueList = new ArrayList<>();
	
	public Coordinate(int coordNum) {
		rowNumber = coordNum / 9;
		columnNumber = coordNum % 9;
		blockNumber = (rowNumber / 3)*3 + (columnNumber / 3);
	}

	public int getAssignedValue() {
		return assignedValue;	
	}
	
	public List<Integer> getPossibleValueList() {
		return possibleValueList;
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
		if (value == 0) {
			possibleValueList.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
		} else {
			possibleValueList.clear();
		}
	}
	
	public void setPossibilites(ArrayList<Integer> possibilities) {
		possibleValueList = possibilities;
	}
	
	public void removePos(int posValue) {
		possibleValueList.remove(Integer.valueOf(posValue));
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