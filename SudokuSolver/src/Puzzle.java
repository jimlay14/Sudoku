//import java.util.ArrayList;


public class Puzzle
	{
		//a private class with the variables of a puzzle, including the title and ints
		public String title;//string of the puzzle number
		private int[][] ints;//ints of the puzzle
		
		public Puzzle(String t, int[][] problem)
		{
			title = t;
			ints = problem;
		}
		
		public Puzzle()
		{
			title = null;
			ints = new int[9][9];
		}
		
		/**
		 * Method to print a row of the puzzle
		 * @param i the row to print
		 * @param j the column to print
		 */
		public void printRow(int i, int j)
		{
			if(i >= 9 || j>= 9)
				System.out.println("");//print to the next line when we reach the end of the rows
			//or if we reach the end of a row
			else
			{
				
				if(i != 2 && i != 5)//if we are not on a horizontal divider
					if(ints[i][j] != 0)//if not 0
						System.out.print("" + ints[i][j] + " ");//print the letter
					else
						System.out.print("* ");//print a * if on a 0
				else
					if(ints[i][j] != 0)
						System.out.print("" + ints[i][j] + "_");//if we are on a horizontal divider, add underscore
					else
						System.out.print("*_");//underscore with 0
			
				if(j == 2 || j == 5)
					System.out.print("|");//vertical divider
				j++;//iterate to next column
				printRow(i, j);//print next number
			}
		}
		
		/**
		 * Method to print a full sudoku grid
		 * @param i the row to print (iterates through using the printRow method)
		 */
		public void printGrid(int i)
		{
			if(i >= 9)
				return;//stop if we have run out of rows
			printRow(i, 0);//print the row
			i++;//iterate
			printGrid(i);//call the method again until all the rows are printed
		}
		
		/**
		 * Method for printing out the Sudoku puzzle
		 */
		public void string()
		{
			System.out.println(title);//print out the title first!
			printGrid(0);//launch the grid printing
			System.out.println("");//clear next line for puzzles following
		}
		
		/**
		 * Launcher method for the checkRow method; only takes in the input for the number to check and the row to check
		 * @param row the row to check
		 * @param toCheck the input to check
		 * @return statement on whether the number is in the row or not
		 */
		public boolean checkRow(int row, int toCheck)
		{
			return checkRow(row, 0, toCheck);//launch the method at the given row
		}
		
		/**
		 * Method to check if a given int is already in the row
		 * @param i the row to check
		 * @param j the column to check
		 * @param toCheck the input of what number to check
		 * @return false if the number is already in, true if it is not in the row
		 */
		public boolean checkRow(int i, int j, int toCheck)
		{		
			if(toCheck == ints[i][j])
				return false;//the number is in the row already
			j++;
			if(j >= 9)
				return true;//the number is not in the row (and thus it is true that the int can be put in the row)
			return checkRow(i, j, toCheck);//continue checking until we have determined if the number is in the row or not
		}	
		
		/**
		 * Method to check if a given int is in a column already
		 * @param i the column to check
		 * @param j the row (iterated through each)
		 * @param toCheck the int to check if it is already there
		 * @return false if the number is already there; true if not
		 */
		public boolean checkCol(int i, int j, int toCheck)
		{
			if(toCheck == ints[i][j])
				return false;//return false; the given int is in this column already
			i++;
			if(i >= 9)
				return true;//return true if we did not find the int in the column
			return checkCol(i, j, toCheck);//continue checking until we find the right result
		}
		
		/**
		 * Launcher method to check if a given int is in a column already
		 * @param col the column to check for the int
		 * @param toCheck the int to look for
		 * @return statement of whether the int is in the column
		 */
		public boolean checkCol(int col, int toCheck)
		{
			return checkCol(0, col, toCheck);//launch method at the given column
		}
		
		/**
		 * A method to check a limited amount of columns, for use with the check box method
		 * @param i The row to check
		 * @param j The column to check
		 * @param m the maximum amount to check
		 * @param toCheck the number we are looking for
		 * @return statement of whether we found the int in the row
		 */
		public boolean checkRow(int i, int j, int m, int toCheck)
		{
			//System.out.println("" + i + " " + j + " " +ints[i][j]);
			if(toCheck == ints[i][j])
				return false;//return false; the given int is in this column already
			j++;
			if(j >= m)
				return true;//return true if we did not find the int in the column
			return checkRow(i, j, m, toCheck);//continue checking until we find the right result
		}
		
		/**
		 * A method to check the box of any given point for any other matching numbers
		 * @param i the row value of the point we are checking
		 * @param j the column value of the point we are checking
		 * @param toCheck the int we are looking for
		 * @return statement of whether the point can validly be put in a box
		 */
		public boolean checkBox(int i, int j, int toCheck)
		{
			int k = (i/3)*3;//iter for row, upper row of box
			int m = (j/3)*3;//iter for columns; leftmost column of box
			int max = m+3;//maximum value to scan
			if(checkRow(k, m, max, toCheck) == false)
				return false;//if we find the number in the first row, return false
			if(checkRow(k+1, m, max, toCheck) == false)
				return false;//if we find the number in the second row, return false
			if(checkRow(k+2, m, max, toCheck) == false)
				return false;//if we find the number in the third row, return false
			return true;//return true; the number is not in the box
		}
		
		/**
		 * Methdo that checks if a given int will work at a given point i, j
		 * @param i the row to look in
		 * @param j the column to look in
		 * @param toCheck the int we are trying to put in
		 * @return whether we put the int in or not
		 */
		public boolean checkPoint(int i, int j, int toCheck)
		{	
			if(checkRow(i, toCheck) == true && checkCol(j, toCheck) == true && checkBox(i, j, toCheck) == true)
			{	
				return true;//if the given int fits in a space and does not have any conflicts in its row, box, or column, return true
			}
			return false;
		}
		
		/**
		 * 
		 * @param i the int value of the row we are in
		 * @param j the int value of the column we are in
		 * @param t the int we are currently checking to see if it belongs in this spot
		 * @return boolean of whether we found a possible answer in this spot
		 */
		public boolean solve(int i, int j, int t)
		{
			if(t > 9)
			{
				return false;//return false if no ints can fit in a given spot
			}
			if(j > 8)
			{
				i++;
				j = 0;//iteration to next row
			}
			if(i > 8)
			{
				return true; //found a solution
			}
			if(ints[i][j] == 0) //see if there is no int in this spot already
			{
					if(checkPoint(i, j, t) == true)//if the spot is good
					{
						ints[i][j] = t;//put the int here
						if(solve(i, j+1, 1) == true) //try to solve the rest of the board, starting with the next column
						{
							return true; //if we could, great! We can stop
						}
						ints[i][j] = 0; //if not, remove the int, and retry
					}
					return solve(i, j, t+1);//retry at this spot using the next int
			}
			else
			{
				return solve(i, j+1, 1);//iterate to next spot if there is already an int there
			}		
		}
		
		public void solve()
		{
			solve(0,0,1);//start the solver method
		}


}
