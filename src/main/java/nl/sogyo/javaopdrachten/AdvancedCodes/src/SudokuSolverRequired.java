import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

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
	

	public static void readSudoku(String sudokuInput) {
		initializeCoordinateLists();
		
		char[] valueArray = sudokuInput.toCharArray();
		
		for(int coordNum = 0; coordNum < valueArray.length; coordNum++) {
			Coordinate coord = new Coordinate(coordNum,Character.getNumericValue(valueArray[coordNum])); 
			
			nestedCoordinateList.get(coord.getRowNumber()).add(coord);
			coordinatePerColumnList.get(coord.getColNumber()).add(coord);
			coordinatePerBlockList.get(coord.getBlockNumber()).add(coord);
		}
		for(ArrayList<Coordinate> row : nestedCoordinateList) {
			for(Coordinate coord : row) {
				coord.makeInfluenceableCoordinateLists(nestedCoordinateList, coordinatePerColumnList,coordinatePerBlockList);
			}
		}
	}

	
	public static void solveSudoku() {
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
			for (ArrayList <Coordinate> unit : givenCoord.getInfluenceableCoords()) {
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
		for(ArrayList <Coordinate> unit : givenCoord.getInfluenceableCoords()) {
			for(Coordinate loopCoord : unit) {
				loopCoord.removePos(givenCoord.getAssignedValue());
			}	
		}	
	}

	private static void removePosDueCoordsWithSamePos(Coordinate givenCoord) {

		if (givenCoord.getAssignedValue() == 0) {
			for(ArrayList <Coordinate> unit : givenCoord.getInfluenceableCoords()) {
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
						loopCoord.removePos(possibility);
					}
				}
			}
		}
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
	private static HashSet<Integer> makeCoordPosSet(ArrayList<Coordinate> coordList) {
		HashSet <Integer> allPosOfCoordListSet = new HashSet<>();
		for(Coordinate coord : coordList) {
			allPosOfCoordListSet.addAll(coord.getPossibleValueList());
		}
		return allPosOfCoordListSet;
	}
	

	private static void printRow(ArrayList<Coordinate> row) {
		System.out.print("|");
		for(Coordinate coord : row) {
			coord.printCoord();
			coord.printBoxPart();
		}
		System.out.println();
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
	private static void checkSudoku() {
		if(checkUnitList(nestedCoordinateList) && checkUnitList(coordinatePerColumnList) && checkUnitList(coordinatePerBlockList)) {
			System.out.println("No double numbers");
		}else {
			System.out.println("Error: double numbers");
		}
	}
	
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String sudokuInput = input.next();
		//String sudokuInput = "000820090500000000308040007100000040006402503000090010093004000004035200000700900";
		
		readSudoku(sudokuInput);
		System.out.println("Initial State:");
		printSudoku();
		
		solveSudoku();
		input.close();
		
		System.out.printf("%n Solved:%n");
		printSudoku();
		checkSudoku();
	}

}

