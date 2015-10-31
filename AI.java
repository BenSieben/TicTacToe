/*The AI that the computer controlled player has
 *As of now, it randomly selcts cells to fill in
 *By Ben Sieben
 *Created May 15 2012
 *Last Updated May 20 2012
 */
 public class AI
 {
 	//FIELDS
 	private int column, row;

 	//CONSTRUCTOR
 	public AI(int boardSize) {
 		column = (int)(Math.random() * (boardSize-1) + 0.5);
 		row = (int)(Math.random() * (boardSize-1) + 0.5);
 	}

 	//METHODS

 	//Makes a random move
 	public void makeMove(int[][]t, int s) {
 		while(t[column][row] > 0) {
	 		column = (int)(Math.random() * (s-1) + 0.5);
	 		row = (int)(Math.random() * (s-1) + 0.5);
 		}
 		t[column][row] = 100;
 	}

 	//Returns the column randomly selected
 	public int getColumn() {
 		return column;
 	}

 	//Returns the row randomly selected
 	public int getRow() {
 		return row;
 	}
 }