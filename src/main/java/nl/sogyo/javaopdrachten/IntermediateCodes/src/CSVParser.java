import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVParser {
	ArrayList<List<String>> rowNestedList;
	
	public CSVParser(String pathInputFile) {
		rowNestedList = readFileToList(pathInputFile);
	}
	
	public ArrayList<List<String>> getRowNestedList() {
		return rowNestedList;
	}
	
	private ArrayList<List<String>> makeColumnList(BufferedReader dataFile) throws IOException {
		ArrayList <List<String>> entryList = new ArrayList <>();
		String line = "";
		while((line = dataFile.readLine())!= null) {
			String[] fields = line.split(",");
			entryList.add(Arrays.asList(fields));
		}
		return entryList;
	}
	
	private ArrayList<List<String>> readFileToList(String inputFilename) {
		try {
			BufferedReader dataFile = new BufferedReader(new FileReader(inputFilename));
			ArrayList<List<String>> columnList = makeColumnList(dataFile);
			dataFile.close();
			return columnList;
		} catch (IOException e) {
			System.out.println("Input file missing");
			return null;
		} 
	}
	
	public boolean changeColumnTo(String columnName, String replacement) {
		int columnIndex = rowNestedList.get(0).indexOf(columnName);
		if (columnIndex != -1){
			for (List<String> row : rowNestedList) {
				if(rowNestedList.indexOf(row) != 0) {
					row.set(columnIndex, replacement);
				}
			}
			return true;
		} else {
			System.out.println("Column name doesn’t exists in the input file");
			return false;
		}
	}
	
	private ArrayList<String> NestedListToStringList(ArrayList<List<String>> nestedList){
		ArrayList<String> rowList = new ArrayList<>();
		for(List<String> columnList : nestedList) {
			String row = ""; 
			for(String column: columnList) {
				if (row.length() == 0) {
					row += column;
				} else {
					row += "," + column;
				}
			}
			rowList.add(row);
		}
		return rowList;
	}
	
	
	public void writeFile(String filePath) {
		File file = new File(filePath);
		ArrayList<String> rowList = NestedListToStringList(rowNestedList);

		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			for(String row:rowList) {
				output.write(row); 
				output.newLine();
			}
			output.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void writeFileWithChangedColumn(String inputFilePath, String columnName, String replacement, String outputFilePath) {
		CSVParser givenFile = new CSVParser(inputFilePath);
		
		if (givenFile.getRowNestedList() != null) {
			boolean columnChangeSuccesfull = givenFile.changeColumnTo(columnName,replacement); 
			if (columnChangeSuccesfull) {
				givenFile.writeFile(outputFilePath);
			}			
		}
	}
	
	public static void main(String[] args) {
		String inputFilePath = "..\\..\\..\\..\\..\\resources\\intermediate\\csv-parser.csv";
		String changeColumn = "City";
		String replacement = "London";
		String outputFilePath = "src\\csv-parser_city-london.csv";
		
		writeFileWithChangedColumn(inputFilePath, changeColumn, replacement, outputFilePath);
	}
}


