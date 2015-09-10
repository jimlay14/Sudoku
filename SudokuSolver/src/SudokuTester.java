
public class SudokuTester 
{

	public static void main(String[] args)
	{
		SudokuSolver t = new SudokuSolver();
		t.loadPointsFromFile();
		t.print();
	}
}
