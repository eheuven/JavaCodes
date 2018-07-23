import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Coordinate {
	
	int rowNumber;
	int columnNumber;
	int blockNumber;
	int assignedValue;
	List<Integer> possibleValueList = new ArrayList<>();
	ArrayList<ArrayList <Coordinate>> influenceableCoordinates = new ArrayList<>();
	
	public Coordinate(int coordNum, int value) {
		rowNumber = coordNum / 9;
		columnNumber = coordNum % 9;
		blockNumber = (rowNumber / 3)*3 + (columnNumber / 3);
		assignValue(value);
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
	
	public void makeInfluenceableCoordinateLists(ArrayList<ArrayList <Coordinate>> nestedCoordinateList,ArrayList<ArrayList <Coordinate>> coordinatePerColumnList,ArrayList<ArrayList <Coordinate>> coordinatePerBlockList) {
			influenceableCoordinates.add(new ArrayList<>(nestedCoordinateList.get(rowNumber).subList(0, 9)));
			influenceableCoordinates.add(new ArrayList<>(coordinatePerColumnList.get(columnNumber).subList(0, 9)));
			influenceableCoordinates.add(new ArrayList<>(coordinatePerBlockList.get(blockNumber).subList(0, 9)));
			
			for (ArrayList <Coordinate> unit : influenceableCoordinates) { 
				unit.remove(this); 
			}
	}
	
	public ArrayList<ArrayList <Coordinate>> getInfluenceableCoords (){
		return influenceableCoordinates;
	}
	
	
	
}