package cs1302.p2;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

import cs1302.p1.Minesweeper;

public class MineSweeperArray {
	private Random r = new Random();
	private int row;
	private int col;
	private int numMines;
	private int[][] mineArray;

	
	
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
	public MineSweeperArray(int row, int col, boolean fromFile) {
		this.row = row;
		this.col = col;
		mineArray = new int[row][col];
		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {
				mineArray[x][y] = 0;

			}

		}

	}
	
	public MineSweeperArray(int row, int col) {
		this.row = row;
		this.col = col;
		mineArray = new int[row][col];
		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {
				mineArray[x][y] = 0;

			}

		}

	}

	public int[][] getMineArray() {
		int[][] mineArrayCopy = new int[row][col];

		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {
				mineArrayCopy[x][y] = mineArray[x][y];
			}
		}
		return mineArrayCopy;
	}

	public void placeMine(int x, int y) {
		mineArray[x][y] = 1;

	}

	public void randomPlaceMine() {
		numMines = (int) Math.ceil((double) row * col / 4);
		int n;
		while (numMines > 0) {
			for (int x = 0; x < row; x++) {
				for (int y = 0; y < col; y++) {
					n = r.nextInt(100);
					if (n < 25 && numMines > 0) {
						this.placeMine(x, y);
						numMines--;
					}

				}

			}

		}

	}

	// returns the number of mines that surround a certain square
	public int numberAdjacentMines(int c, int d) {

		int[][] mineArrayCopy = new int[row + 2][col + 2];
		int adjacentMines = 0;

		// Surrounds arrayCopy with zeroes, so the program can search corner and
		// edge squares without fear of going out of bounds
		for (int x = 0; x <= row; x++) {
			for (int y = 0; y <= col; y++) {
				mineArrayCopy[x + 1][y + 1] = mineArray[x][y];
			}
		}
		// Increments coordinates by 1 since the array copy is surrounded by a
		// zero buffer
		c += 1;
		d += 1;

		// Checks the mine array copy for surrounding mines
		for (int i = c - 1; i <= c + 1; i++) {
			for (int j = d - 1; j <= d + 1; j++) {
				adjacentMines += mineArrayCopy[i][j];
			}
		}
		return adjacentMines;
	}

	public void mineArrayToString() {
		System.out.println("");
		int row = mineArray.length;
		int col = mineArray[0].length;
		
		for (int x = 0; x < row; x++) {
			if (x != row-1) {
				System.out.print(x + " ");
			} else {
				System.out.print("  ");
			}
				for (int y = 0; y < col; y++) {

					// Prints bar at start of each square
					if (y != col-1 && x != row-1) {
						System.out.print("|");
					}
					// Prints appropriate symbol: || 0: "   " || 1: "   " || 2:
					// " # " || 3: " F " || 4: " ? " ||
					/*
					if (mineArray[x][y] == 0 || mineArray[x][y] == 1) {
						System.out.print("   ");
					} else if (mineArray[x][y] == 2) {
						System.out.print(" " + this.numberAdjacentMines(x, y)+ " ");
					} else if (mineArray[x][y] == 3) {
						System.out.print(" F ");
					} else if (mineArray[x][y] == 4) {
						System.out.print(" ? ");
					}
					*/
					// Prints the ending bar only on the last column
					if (y == col-1 && x != row-1) {
						System.out.print("|");
					}
					
				}
			System.out.println();
		}

	}

}