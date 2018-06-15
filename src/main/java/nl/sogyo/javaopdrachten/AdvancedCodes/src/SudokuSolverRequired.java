import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class SudokuSolverRequired {
	static ArrayList<ArrayList<Coordinate>> nestedCoordinateList = new ArrayList<>();
	static ArrayList<ArrayList<Coordinate>> coordinatePerColumnList = new ArrayList<>();
	static ArrayList<ArrayList<Coordinate>> coordinatePerBlockList = new ArrayList<>();
	public static boolean change = true;

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
		while(change) {
			change = false;
			for(ArrayList<Coordinate> row : nestedCoordinateList) {
				for(Coordinate coord : row) {
					fillSudokuField(coord);
					removePosDueCoordsWithSamePos(coord);
				}
			}
		}

	}
	
	private static void removePosDueCoordsWithSamePos(Coordinate givenCoord) {
		ArrayList<ArrayList <Coordinate>> influenceableCoordList = makeInfluenceableCoordinateLists(givenCoord);

		if (givenCoord.getAssignedValue() == 0) {
			for(ArrayList <Coordinate> unit : influenceableCoordList) {
				removePosIfSameAmountPosAsCoords(unit,givenCoord);
			}
		}
	}

	private static void removePosIfSameAmountPosAsCoords(ArrayList <Coordinate> unit,Coordinate givenCoord) {
		ArrayList <Coordinate> samePosCoordsList = makeSamePosCoordList(unit,givenCoord);
		HashSet <Integer> AllPosOfSamePosCoordsSet = makeCoordPosSet(samePosCoordsList);
		
		if (samePosCoordsList.size() == AllPosOfSamePosCoordsSet.size()) {
			for(Coordinate loopCoord : unit) {
				if (!samePosCoordsList.contains(loopCoord) && loopCoord != givenCoord) {
					for(Integer possibility : AllPosOfSamePosCoordsSet) {
						for(Coordinate coord: samePosCoordsList) {
							coord.printCoordData();
						}
						loopCoord.removePos(possibility);
					}
				}
			}
		}
	}

	private static HashSet<Integer> makeCoordPosSet(ArrayList<Coordinate> coordList) {
		HashSet <Integer> allPosOfCoordListSet = new HashSet<>();
		for(Coordinate coord : coordList) {
			allPosOfCoordListSet.addAll(coord.getPossibleValueList());
		}
		return allPosOfCoordListSet;
	}

	private static ArrayList<Coordinate> makeSamePosCoordList(ArrayList<Coordinate> unit, Coordinate givenCoord) { 
		ArrayList <Coordinate> samePosCoords = new ArrayList<>();
		for (Coordinate loopCoord : unit) {
			if (loopCoord.getPossibleValueList().size() != 0) {
				HashSet <Integer> bothCoordPos = new HashSet<>(givenCoord.getPossibleValueList());
				bothCoordPos.addAll(loopCoord.getPossibleValueList());
				if(bothCoordPos.size() <= givenCoord.getPossibleValueList().size() +1) {
					samePosCoords.add(loopCoord);
				}
			}
		}
		return samePosCoords;
	}

	private static void fillSudokuField(Coordinate coord) {
		if (coord.getAssignedValue() == 0) {
			assignIfOnlyPosInUnit(coord); 
			assignIfCoordOnePos(coord); 
		}
		
		if (coord.getAssignedValue() != 0) {
			removePosDueAssignedValue(coord); 
		}	
	}

	private static void assignIfOnlyPosInUnit(Coordinate givenCoord) {
		for(Integer pos : givenCoord.getPossibleValueList()) {
			for (ArrayList <Coordinate> unit : makeInfluenceableCoordinateLists(givenCoord)) {
				if (!posInUnit(pos, unit)) {
					givenCoord.assignValue(pos);
					return; 
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
		}
	}

	private static void removePosDueAssignedValue(Coordinate givenCoord) {
		ArrayList<ArrayList <Coordinate>> influenceableCoordinates = makeInfluenceableCoordinateLists(givenCoord);
		for(ArrayList <Coordinate> unit : influenceableCoordinates) {
			for(Coordinate loopCoord : unit) {
				loopCoord.removePos(givenCoord.getAssignedValue());
			}	
		}	
	}

	private static ArrayList<ArrayList <Coordinate>> makeInfluenceableCoordinateLists(Coordinate coord) {
		ArrayList<ArrayList <Coordinate>> influenceableCoordinates = new ArrayList<>();
			influenceableCoordinates.add(new ArrayList<>(nestedCoordinateList.get(coord.getRowNumber()).subList(0, 9)));
			influenceableCoordinates.add(new ArrayList<>(coordinatePerColumnList.get(coord.getColNumber()).subList(0, 9)));
			influenceableCoordinates.add(new ArrayList<>(coordinatePerBlockList.get(coord.getBlockNumber()).subList(0, 9)));
			
			for (ArrayList <Coordinate> unit : influenceableCoordinates) { 
				unit.remove(coord); 
			}
			return influenceableCoordinates; // store in coord?
	}

	private static boolean checkUnitList(ArrayList<ArrayList<Coordinate>> UnitList) {
		HashSet <Integer> unitValues = new HashSet<>();
		for(ArrayList<Coordinate> unit : UnitList) {
			for(Coordinate coord : unit) {
				unitValues.add(coord.getAssignedValue());
			}
			if(unitValues.size() != 9) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean checkSudoku() {
		return checkUnitList(nestedCoordinateList) && checkUnitList(coordinatePerColumnList) && checkUnitList(coordinatePerBlockList);
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
		System.out.println(checkSudoku());
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
	
	public void printCoordData() {
		System.out.printf("row: %d, col: %d, block: %d, value: %d, possibilites: ", rowNumber,columnNumber,blockNumber,assignedValue);
		System.out.println(possibleValueList);
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
		// check change
	}
	
	public void removePos(int posValue) {
		boolean removal = possibleValueList.remove(Integer.valueOf(posValue));
		
		if (removal) {
			SudokuSolverRequired.change = true;
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