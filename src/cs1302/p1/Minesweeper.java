package cs1302.p1;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

/**
 * This class represents a Minesweeper game.
 * 
 * @author YOUR NAME <YOUR EMAIL>
 */
public class Minesweeper {
	private Random r = new Random();
	private int rows;
	private int cols;
	private int numMines;
	private int[][] mineArray;
	private int[][] mineArrayCopy;
	private boolean showMines = false;
	private int score = 0;
	private int minesFound = 0;
	private int numMinesOnField = 0;
	private int x,y;//refers to Current coordinates in mine array
	private String action = "";
	
	/**
	 * Constructs an object instance of the {@link Minesweeper} class using the
	 * information provided in <code>seedFile</code>. Documentation about the
	 * format of seed files can be found in the project's <code>README.md</code>
	 * file.
	 * 
	 * @param seedFile
	 *            the seed file used to construct the game
	 * @see <a
	 *      href="https://github.com/mepcotterell-cs1302/cs1302-minesweeper-alpha/blob/master/README.md#seed-files">README.md#seed-files</a>
	 */
	public Minesweeper(File seedFile) {
		
		try {
			
			File f = seedFile;
			
			Scanner s = new Scanner(f);
			
			rows = s.nextInt();
			cols = s.nextInt();
			numMines = s.nextInt();
			
			mineArray = new int[rows][cols];
			
			if ((rows > 10 || rows < 1) || (cols > 10 || cols < 1)) {
				System.out.println("[]_[] says, \"Cannont create a mine field with that many rows and/or columns!\"");
				System.exit(0);
			}
			int linesProcessed = 0;
			while(numMines > linesProcessed){
				int x = s.nextInt();
				int y = s.nextInt();
				if((rows > x || rows < x) || (cols > y || cols < y)){
					this.placeMine(x, y);
					numMinesOnField++;
				}
				
				linesProcessed++;
			}
			
		} catch (Exception e) {
			// handler exception here
		} // try

	} // Minesweeper

	/**
	 * Constructs an object instance of the {@link Minesweeper} class using the
	 * <code>rows</code> and <code>cols</code> values as the game grid's number
	 * of rows and columns respectively. Additionally, One quarter (rounded up)
	 * of the squares in the grid will will be assigned mines, randomly.
	 * 
	 * @param rows
	 *            the number of rows in the game grid
	 * @param cols
	 *            the number of cols in the game grid
	 */
	public Minesweeper(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		
		if ((rows > 10 || rows < 1) || (cols > 10 || cols < 1)) {
			System.out.println("[]_[] says, \"Cannont create a mine field with that many rows and/or columns!\"");
			System.exit(0);
		}
		mineArray = new int[rows][cols];
		
		this.randomPlaceMine();

	} // Minesweeper
	
	//Copies and returns orginal mine Array for numberAdjacentMines use
	public int[][] arrayCopy(){
		mineArrayCopy =  new int[rows][cols];
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				mineArrayCopy[x][y] = mineArray[x][y];
			}
		}
		return mineArrayCopy;
	}
	// returns the number of mines that surround a certain square
	public int numberAdjacentMines(int c, int d) {

		int[][] mineArrayCopyBuffer = new int[rows + 2][cols + 2];
		int adjacentMines = 0;

		// Surrounds arrayCopy with zeroes, so the program can search corner and
		// edge squares without fear of going out of bounds
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				mineArrayCopyBuffer[x + 1][y + 1] = mineArrayCopy[x][y];
			}
		}
		// Increments coordinates by 1 since the array copy is surrounded by a
		// zero buffer
		c += 1;
		d += 1;

		// Checks the mine array copy for surrounding mines
		for (int i = c - 1; i <= c + 1; i++) {
			for (int j = d - 1; j <= d + 1; j++) {
				adjacentMines += mineArrayCopyBuffer[i][j];
			}
		}
		return adjacentMines;
	}// numberAdjacentMines

	// Places a mine a specified location
	public void placeMine(int x, int y) {
		mineArray[x][y] = 1;

	}

	// Places mine on 25% of the board
	public void randomPlaceMine() {
		numMines = (int) Math.ceil((double) rows * cols / 4);
		numMinesOnField = numMines;
		int n;
		while (numMines > 0) {
			for (int x = 0; x < rows; x++) {
				for (int y = 0; y < cols; y++) {
					n = r.nextInt(100);
					if (n < 25 && numMines > 0) {
						this.placeMine(x, y);
						numMines--;
					}

				}

			}

		}

	}

	public void sweeperArrayString() {
		System.out.println("");
		int row = rows;
		int col = cols;

		for (int x = 0; x < row; x++) {

			System.out.print(x + " ");

			for (int y = 0; y < col; y++) {

				// Prints bar at start of each square

				// Prints appropriate symbol: || No mine 0: "   " || 1: "  Mine  " || Reveal 2:
				// " # " || Mark 3: " F " || Guess 5: " ? " || Guess with mine 6: " ? "

				System.out.print("|");

				if (mineArray[x][y] == 0 || (mineArray[x][y] == 1 && !showMines)) {
					System.out.print("   ");
				}else if((mineArray[x][y] == 1) && showMines){
					System.out.print(" 1 ");
				}else if (mineArray[x][y] == 2) {
					System.out.print(" " + this.numberAdjacentMines(x, y) + " ");
				} else if (mineArray[x][y] == 3) {
					System.out.print(" F ");
				} else if (mineArray[x][y] == 4) {
					System.out.print(" ? ");
				}

				// Prints the ending bar only on the last column
				if (y == col - 1) {
					System.out.print("|");
				}

			}

			System.out.println();
		}

		// Print out number of cols
		System.out.print(" ");
		for (int i = 0; i < col; i++) {
			System.out.print("   " + i);
		}
		
		showMines = false;
	}// sweeperArrayString
	
	//Checks if string is formatted correctly and numbers are in bounds
	public boolean isValidCommand(String command){
		command.trim();
		x = -1; y = -1;
		if(command.equals("nofog")){
			action = command;
			return true;
		}
		boolean validCommand = true;
		action = "";String coordinates = "";
		
		if(command.indexOf(" ") != -1){
			action = command.substring(0,command.indexOf(" ")).trim();
			coordinates = command.substring(command.indexOf(" ")).trim();
		}else{
			validCommand = false;
		}
		
		if(coordinates.indexOf(" ") != -1 && validCommand){
			x = Integer.parseInt(coordinates.substring(0,coordinates.indexOf(" ")));
		}else{
			validCommand = false;
		}
		
		if(validCommand){
			y = Integer.parseInt(coordinates.substring(coordinates.indexOf(" ")).trim());
		}
		
		if(!(x >= 0 && x < rows && y >= 0 && y < cols)){
			validCommand = false;
		}
		
		return validCommand;
	}
	//Parses command
	public void commandParse(){
		// 0: no mine || 1: mine || 2: reveal || 3: mark || 4: guess
		switch(action){
			case "mark":
			case "m":
				mineArray[y][x] = 3;
				break;
				
			case "guess":
			case "g":
				mineArray[x][y] = 4;
				break;
				
			case "reveal":
			case "r":
				if(mineArrayCopy[x][y] == 1){
					youLose();
				}else{
					mineArray[x][y] = 2;
				}
				break;
				
			case "help":
			case "h":
				System.out.println("");
				System.out.println("Commands Available...");
				System.out.println("- Reveal: r/reveal row col");
				System.out.println("-   Mark: m/mark row col");
				System.out.println("-  Guess: g/guess row col");
				System.out.println("-   Help: h/help");
				System.out.println("-   Quit: q/quit");
				break;
				
			case "quit":
			case "q":
				System.out.println("ლ([]_[]ლ)");
				System.out.println("Y U NO PLAY MORE?");
				System.out.println("Bye!");
				System.exit(0);
			case "nofog":
				noFog();
				break;
			default:
				System.out.println("[]_[] says, \"Command not recognized!\"");
				break;
				
			
		}
	}
	
	//Print you Lose statement
	public static void youLose(){
		System.out.println("Oh no... You revealed a mine!");
		System.out.println();
		System.out.println("  __ _  __ _ _ __ ___   ___    _____   _____ _ __ ");
		System.out.println(" / _` |/ _` | '_ ` _ \\ / _ \\  / _ \\ \\ / / _ \\ '__|");
		System.out.println("| (_| | (_| | | | | | |  __/ | (_) \\ V /  __/ |   ");
		System.out.println(" \\__, |\\__,_|_| |_| |_|\\___|  \\___/ \\_/ \\___|_|   ");
		System.out.println(" |___/  ");
		System.exit(0);
	
	}
	
	public void youWin(){
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				if(mineArrayCopy[x][y] == 1 && mineArray[x][y] == 3){
					minesFound++;
				}
			}
		}
		if(minesFound == numMinesOnField){
			System.out.println("");
			System.out.println("░░░░░░░░░▄░░░░░░░░░░░░░░▄░░░░ \"So Doge\"");
			System.out.println("░░░░░░░░▌▒█░░░░░░░░░░░▄▀▒▌░░░ ");
			System.out.println("░░░░░░░░▌▒▒█░░░░░░░░▄▀▒▒▒▐░░░ \"Such Score\"");
			System.out.println("░░░░░░░▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐░░░");
			System.out.println("░░░░░▄▄▀▒░▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐░░░ \"Much Minesweeping\"");
			System.out.println("░░░▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌░░░");
			System.out.println("░░▐▒▒▒▄▄▒▒▒▒░░░▒▒▒▒▒▒▒▀▄▒▒▌░░ \"Wow\"");
			System.out.println("░░▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐░░");
			System.out.println("░▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄▌░");
			System.out.println("░▌░▒▄██▄▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒▌░");
			System.out.println("▀▒▀▐▄█▄█▌▄░▀▒▒░░░░░░░░░░▒▒▒▐░");
			System.out.println("▐▒▒▐▀▐▀▒░▄▄▒▄▒▒▒▒▒▒░▒░▒░▒▒▒▒▌");
			System.out.println("▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒▒▒░▒░▒░▒▒▐░");
			System.out.println("░▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒░▒░▒░▒░▒▒▒▌░");
			System.out.println("░▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▒▄▒▒▐░░");
			System.out.println("░░▀▄▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▄▒▒▒▒▌░░");
			System.out.println("░░░░▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀░░░ CONGRATULATIONS!");
			System.out.println("░░░░░░▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀░░░░░ YOU HAVE WON!");
			System.out.println("░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▀▀░░░░░░░░ SCORE: " + score);
			System.out.println("");
		}
	}
	
	public void noFog(){
		showMines = true;

	}
	/**
	 * Starts the game and execute the game loop.
	 */
	public void run() {
		Scanner input = new Scanner(System.in);
		String command;
		boolean gameIsRunning = true;
		int rounds = 0;
		arrayCopy();//Copies the orginal mineArray. 
		while (gameIsRunning) {
			
			System.out.println("Rounds Completed: " + rounds);
			System.out.println();
			this.sweeperArrayString();
			System.out.println();
			showMines = false;
			command = input.nextLine();
			if(isValidCommand(command)){
				commandParse();
			}else{
				System.out.println("[]_[] says, \"Command not recognized!\"");
			}
			youWin();
			rounds++;
			
			
			
		}

	} // run

	/**
	 * The entry point into the program. This main method does implement some
	 * logic for handling command line arguments. If two integers are provided
	 * as arguments, then a Minesweeper game is created and started with a grid
	 * size corresponding to the integers provided and with 10% (rounded up) of
	 * the squares containing mines, placed randomly. If a single word string is
	 * provided as an argument then it is treated as a seed file and a
	 * Minesweeper game is created and started using the information contained
	 * in the seed file. If none of the above applies, then a usage statement is
	 * displayed and the program exits gracefully.
	 * 
	 * @param args
	 *            the shell arguments provided to the program
	 */
	public static void main(String[] args) {

		/*
		 * The following switch statement has been designed in such a way that
		 * if errors occur within the first two cases, the default case still
		 * gets executed. This was accomplished by special placement of the
		 * break statements.
		 */
		Scanner input = new Scanner(System.in);
		Minesweeper game = null;
		int caseNumber;
		System.out
				.println("Please enter the name of the seed file you wish to use");
		System.out.println("Or enter the grid size: rows cols");

		String userInput = input.nextLine();
		if (userInput.indexOf(".") != -1 && userInput.substring(userInput.indexOf(".")).equals(".txt")) {
			caseNumber = 1;
		} else {
			caseNumber = 2;

		}
		switch (caseNumber) {

		// random game
		case 2:

			int rows,
			cols;

			// try to parse the arguments and create a game
			try {
				rows = Integer.parseInt(userInput.substring(0,userInput.indexOf(" ")));
				cols = Integer.parseInt(userInput.substring(userInput
						.indexOf(" ") + 1));

				game = new Minesweeper(rows, cols);
				break;
			} catch (NumberFormatException nfe) {
				// line intentionally left blank
			} // try

			// seed file game
		case 1:

			String filename = userInput.trim();
			File file = new File("/home/mango/seed1.txt");

			if (file.isFile()) {
				game = new Minesweeper(file);
				break;
			} // if

			// display usage statement
		default:

			System.out.println("Usage: java Minesweeper [FILE]");
			System.out.println("Usage: java Minesweeper [ROWS] [COLS]");
			System.exit(0);

		} // switch

		// if all is good, then run the game
		game.run();

	} // main

} // Minesweeper