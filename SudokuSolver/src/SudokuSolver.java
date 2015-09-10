import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class SudokuSolver 
{
	
	public static ArrayList<Puzzle> thePuz;//list of all the puzzles we will scan in
	private static String DELIM = ",";//delimiter for scanners, avoiding spaces
	
	/**
	 * Method to recursively read in the sudoku file
	 */
	public void loadPointsFromFile()
	{
		ArrayList<Puzzle> toReturn = new ArrayList<Puzzle>();
		
		JFileChooser chooser = new JFileChooser(); //a file chooser
		chooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt")); //for txt files
		int returnVal = chooser.showOpenDialog(chooser); //show the dialog to OPEN
		if(returnVal == JFileChooser.APPROVE_OPTION) //if they picked something
		{
			try {
				Scanner sc = new Scanner(chooser.getSelectedFile());//create a scanner for the given file
				sc.useDelimiter(DELIM);//limit the scanner to semicolons and not spaces
				toReturn = puzzles(toReturn, sc);//fill our arrayList with all of the puzzles
				sc.close();//close scanner
			}
			catch(Exception e) {//if we find something wrong, stop
				System.out.println("Error loading from file: "+e);
				e.printStackTrace();			}
		}
		thePuz = toReturn;//fill in our instance variable array list of puzzles
	}
	
	public ArrayList<Puzzle> puzzles(ArrayList<Puzzle> p, Scanner sc)
	{
		if(sc.hasNext() == false)
			return p;//if there are no more puzzles to scan, stop trying to make them
		p.add(loadPuz(sc));//add in the next puzzle
		return puzzles(p, sc);//recurse to get the next puzzle; keep going until there are no more puzzles to scan
	}
	
	public Puzzle loadPuz(Scanner sc)
	{
		String title = sc.nextLine();//take in the title of the puzzle
		int[][] grid = new int[9][9];//create an empty grid to store all of the ints
		int[][] theGrid = loadPuz(grid, 0, sc);//fill in the grid
		return new Puzzle(title, theGrid);//return a puzzle with the given title and ints
	}
	
	public int[][] loadPuz(int[][] grid, int i, Scanner sc)
	{
		if(i > 8)
		{
			return grid;//if we run out of rows, return the grid
		}
		Scanner s = new Scanner(sc.nextLine());//take in the next line
		s.useDelimiter(DELIM);//delimit the scanner to semicolons
		loadRow(grid, i, 0, s);//load the current row of the grid
		return loadPuz(grid, i+1, sc);//recurse until we have a full grid
	}
	
	public int[][] loadRow(int[][] grid, int row, int col, Scanner s)
	{
		if(col > 8)
		{
			s.close();//close the temporary scanner
			return grid;//if we reach the end of the row, return the grid with the filled row
		}
		grid[row][col] = s.nextInt();//load the current point of the grid
		return loadRow(grid, row, col+1, s);//iterate to next point in grid
	}
	

	/**
	 * A method to get the arraylist of all the puzzles
	 * @return an arraylist containing all the puzzles we scanned in
	 */
	public static ArrayList<Puzzle> getPuz() 
	{
		return thePuz;//return all of the puzzles
	}
	
	/**
	 * Launcher method for the printing method
	 */
	public void print()
	{
		print(0);//print from the first puzzle
	}
	
	/**
	 * A method to print all the puzzles and solutions that are scanned in.
	 * @param i the current puzzle we are on
	 */
	public void print(int i)
	{
		if(i == thePuz.size())
		{
			return;//if we run out of puzzles, stop printing
		}
		Puzzle p = getPuz().get(i);//get the puzzle at the current iter value
		p.string();//print it out before solving
		p.solve();//solve the puzzle
		p.string();//print out the solution
		print(i+1);//recurse to the next puzzle
	}

}
