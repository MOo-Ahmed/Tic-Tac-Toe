import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	char [][] grid = { {'.' ,'.' ,'.'} , {'.' ,'.' ,'.'} ,{'.' ,'.' ,'.' } } ;
	char Player1Choice , ComputerChoice;
	ArrayList<Integer> visitedByComputer = new ArrayList();

	Game(char c){
		Player1Choice = c ;
		if (c == 'X' || c == 'x')	ComputerChoice = 'O' ;
		else	ComputerChoice = 'X' ;
	}
	
	public void run() {
		int gameState = 0 ;
		PrintGrid();
		while(gameState == 0) {
			Scanner sc = new Scanner(System.in);
			System.out.print("Player 1 turn :   ");
			int p1choice = Integer.parseInt(sc.nextLine());
			if(Player1(p1choice/3, p1choice%3) == false)	continue ;
			gameState = getGameState() ;
			if(gameState != 1 && gameState != 3) {
				Computer();
				gameState = getGameState();
				if(gameState == 2) {
					System.out.println("Computer wins .. Bad luck\n");
				}
			}
			else if(gameState == 1) {
				System.out.println("Congrats Player 1  .. You win\n");
			}
			else if(gameState == 3) {
				System.out.println("No winner ...\n");
			}
			
			PrintGrid();
		}
	}
	
	boolean Player1(int row , int column) {
		if(grid[row][column] != '.')	{
			System.out.println("Invalid choice ..");
			return false ;
		}
		grid[row][column] = Player1Choice ;
		return true ;
	}

	void Computer() {
		Coordinate c = chooseCoordinates();
		grid[c.row][c.col] = ComputerChoice ;
	}

	public void PrintGrid() {
		for(int i = 0 ; i < 3 ; i++) {
			for(int j = 0 ; j < 3 ; j++) {
				System.out.print("  " + (i*3 + j) +"\t" + " " + "[ " + grid[i][j] + " ]\t|");
			}
			System.out.println("\n________________________________________________");
		}
	}

	private Coordinate chooseCoordinates() {
		Coordinate co = null ;
		if(didComputerPlayBefore() == true) {
			if(getBestCoordinate(ComputerChoice) != null) {
				return getBestCoordinate(ComputerChoice);
			}
			else {
				int lastChoice = visitedByComputer.get(visitedByComputer.size()-1) ;
				int r = lastChoice / 3  , c = lastChoice % 3;
				if(r+1 < 3 && grid[r+1][c] == '.') {
					co = new Coordinate(r+1, c) ;
				}
				else if(c+1 < 3 && grid[r][c+1] == '.') {
					co = new Coordinate(r, c+1) ;
				}
				else if(r == 0 && c == 0 && (grid[1][1] == '.' || grid[2][2] == '.') ) {
					if(grid[1][1] == '.') {
						co = new Coordinate(1,1) ;
					}
					else if(grid[2][2] == '.') {
						co = new Coordinate(2,2) ;
					}
				}
				else if(r == 0 && c == 2 && (grid[1][1] == '.' || grid[2][0] == '.')) {
					if(grid[1][1] == '.') {
						co = new Coordinate(1,1) ;
					}
					else if(grid[2][0] == '.') {
						co = new Coordinate(2,0) ;
					}
				}
				else {
					if(getBestCoordinate(Player1Choice) != null) {
						co = getBestCoordinate(Player1Choice) ;
					}
					else {
						boolean haveFoundEmptyPlace = false ;
						for(int i = 0 ; i < 9 && !haveFoundEmptyPlace ; i++) {
							if(grid[i/3][i%3] == '.')	{
								co = new Coordinate(i/3 , i%3) ;
								haveFoundEmptyPlace = true ;
							}
						}
					}
				}
			}
		}
		else {
			if(grid[0][0] == '.') {
				grid[0][0] = ComputerChoice ;
				co = new Coordinate(0,0) ;
			}
			else if(grid[0][1] == '.') {
				grid[0][1] = ComputerChoice ;
				co = new Coordinate(0,1) ;
			}
			else if(grid[0][2] == '.') {
				grid[0][2] = ComputerChoice ;
				co = new Coordinate(0,2) ;
			}
		}

		if(co != null) {
			visitedByComputer.add(co.row*3 + co.col);
		}
		return co ;
	}

	private boolean didComputerPlayBefore() {
		for(int i = 0 ; i < 3 ; i++) {
			for(int j = 0 ; j < 3 ; j++) {
				if(grid[i][j] == ComputerChoice)	return true ;
			}
		}
		return false ;
	}

	private Coordinate getBestCoordinate(char competitior) {
		Coordinate New = null;
		int num = -1 ;
		if( getNumberOfOccurrences(competitior,grid[0][0], 0, grid[0][1], 1, grid[0][2], 2) != -1) {
			num = getNumberOfOccurrences(competitior,grid[0][0], 0, grid[0][1], 1, grid[0][2], 2) ;
		}
		else if(getNumberOfOccurrences(competitior,grid[1][0], 3, grid[1][1], 4, grid[1][2], 5) != -1) {
			num = getNumberOfOccurrences(competitior,grid[1][0], 3, grid[1][1], 4, grid[1][2], 5);
		}
		else if(getNumberOfOccurrences(competitior,grid[2][0], 6, grid[2][1], 7, grid[2][2], 8) != -1) {
			num = getNumberOfOccurrences(competitior,grid[2][0], 6, grid[2][1], 7, grid[2][2], 8);
		}
		else if(getNumberOfOccurrences(competitior,grid[0][0], 0, grid[1][0], 3, grid[2][0], 6) != -1) {
			num = getNumberOfOccurrences(competitior,grid[0][0], 0, grid[1][0], 3, grid[2][0], 6);
		}
		else if(getNumberOfOccurrences(competitior,grid[0][1], 1, grid[1][1], 4, grid[2][1], 7) != -1) {
			num = getNumberOfOccurrences(competitior,grid[0][1], 1, grid[1][1], 4, grid[2][1], 7);
		}
		else if(getNumberOfOccurrences(competitior,grid[0][2], 2, grid[1][2], 5, grid[2][2], 8) != -1) {
			num = getNumberOfOccurrences(competitior,grid[0][2], 2, grid[1][2], 5, grid[2][2], 8);
		}
		else if(getNumberOfOccurrences(competitior,grid[0][0], 0, grid[1][1], 4, grid[2][2], 8) != -1) {
			num = getNumberOfOccurrences(competitior,grid[0][0], 0, grid[1][1], 4, grid[2][2], 8);
		}
		else if(getNumberOfOccurrences(competitior,grid[0][2], 2, grid[1][1], 4, grid[2][0], 6) != -1) {
			num = getNumberOfOccurrences(competitior,grid[0][2], 2, grid[1][1], 4, grid[2][0], 6);
		}
		if(num != -1) {
			New = new Coordinate(num/3, num%3);
		}
		return New;
	}

	private int getNumberOfOccurrences(char test ,char x , int idx1 , char y , int idx2 , char z , int idx3) {
		if(x == '.' && y == test && z == test ) {
			return idx1 ;
		}else if (x == test && y == '.' && z == test) {
			return idx2 ; 
		}
		else if (x == test && y == test && z == '.') {
			return idx3 ;
		}
		return -1 ;
	}


	public int getGameState() {
		int state = 0 ;
		if( (grid[0][0] == Player1Choice && grid[0][1] == Player1Choice && grid[0][2] == Player1Choice) ||
				(grid[1][0] == Player1Choice && grid[1][1] == Player1Choice && grid[1][2] == Player1Choice) ||
				(grid[2][0] == Player1Choice && grid[2][1] == Player1Choice && grid[2][2] == Player1Choice) ||
				(grid[0][0] == Player1Choice && grid[1][1] == Player1Choice && grid[2][2] == Player1Choice) ||
				(grid[0][2] == Player1Choice && grid[1][1] == Player1Choice && grid[2][0] == Player1Choice) ||
				(grid[0][0] == Player1Choice && grid[1][0] == Player1Choice && grid[2][0] == Player1Choice) ||
				(grid[0][1] == Player1Choice && grid[1][1] == Player1Choice && grid[2][1] == Player1Choice) ||
				(grid[0][2] == Player1Choice && grid[1][2] == Player1Choice && grid[2][2] == Player1Choice)) 
		{
			// Player 1 wins
			state = 1 ;
		}

		else if((grid[0][0] == ComputerChoice && grid[0][1] == ComputerChoice && grid[0][2] == ComputerChoice) ||
				(grid[1][0] == ComputerChoice && grid[1][1] == ComputerChoice && grid[1][2] == ComputerChoice) ||
				(grid[2][0] == ComputerChoice && grid[2][1] == ComputerChoice && grid[2][2] == ComputerChoice) ||
				(grid[0][0] == ComputerChoice && grid[1][1] == ComputerChoice && grid[2][2] == ComputerChoice) ||
				(grid[0][2] == ComputerChoice && grid[1][1] == ComputerChoice && grid[2][0] == ComputerChoice) ||
				(grid[0][0] == ComputerChoice && grid[1][0] == ComputerChoice && grid[2][0] == ComputerChoice) ||
				(grid[0][1] == ComputerChoice && grid[1][1] == ComputerChoice && grid[2][1] == ComputerChoice) ||
				(grid[0][2] == ComputerChoice && grid[1][2] == ComputerChoice && grid[2][2] == ComputerChoice)) 
		{
			// The computer wins
			state = 2 ;
		}
		for(int i = 0 ; i < 3 ; i++) {
			for(int j = 0 ; j < 3 ; j++) {
				if(grid[i][j] == '.')	break ;
				if(j == 2 && i == 2 && grid[2][2] != '.')	state = 3 ;
			}
		}
		

		return state ;
	}
}
