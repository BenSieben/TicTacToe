/*Draws the game board for the tic tac toe program
 *By Ben Sieben
 *Created May 6 2012
 *Last Updated May 21 2012
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe extends JPanel implements MouseListener, MouseMotionListener
{
	//FIELDS
	private int gridSize, turnCount, p1Wins, p2Wins, draws, width, height, tHeight, bHeight, drawRow, drawColumn, cellWidth, cellHeight, screen, highlightSpot;
	private boolean drawTile, isHoveringOverTile, isSinglePlayer;
	private static Color PLAYER_1_COLOR = Color.RED;
	private static Color PLAYER_2_COLOR = Color.BLUE;
	private static Color HIGHLIGHT_COLOR = Color.GRAY.brighter();
	private int[][] table;

	//CONSTRUCTOR
	public TicTacToe (int size, boolean b) {
		if (size < 3 || size > 7) {
			throw new IllegalArgumentException(
				"Error: The grid size may only be from 3 to 7");
		}
		gridSize = size;
		turnCount = 1;
		p1Wins = 0;
		p2Wins = 0;
		draws = 0;
		width = 800;
		height = 600;
		tHeight = (int)(height*(1.0/(gridSize+1.0)));
		bHeight = height-tHeight;
		drawRow = 0;
		drawColumn = 0;
		table = new int[gridSize][gridSize];
		drawTile = false;
		isSinglePlayer = b;
		cellHeight = (int)(1.0*bHeight/gridSize);
		cellWidth = (int)(1.0*width/gridSize);
		screen = 0;
		highlightSpot = -1;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	//METHODS

	//Draws the board
	public void paintComponent (Graphics g) {
		width = getWidth();
		height = getHeight();
		tHeight = (int)(height*(1.0/(gridSize+1.0))); //Represent height of score table
		bHeight = height-tHeight; //Represents height of the game board

		cellHeight = (int)(1.0*bHeight/gridSize);
		cellWidth = (int)(1.0*width/gridSize);

		if(screen == 0) {
			g.setColor(Color.WHITE);
			g.fillRect(0,0,width,height);
			Font f = new Font("MONOSPACED",Font.BOLD,(int)(width/15.0));
			g.setFont(f);
			g.setColor(HIGHLIGHT_COLOR);
			if(highlightSpot == 0) {
				g.fillRect(0,height*1/3,width,height*1/3);
			} else if (highlightSpot == 1) {
				g.fillRect(0,height*2/3,width,height*1/3);
			}
			highlightSpot = -1;
			g.setColor(Color.BLACK);
			g.drawLine(0,height*1/3,width,height*1/3);
			g.drawLine(0,height*2/3,width,height*2/3);
			g.drawString("TicTacToe",0,height*5/18);
			g.drawString("Single player",0,height*11/18);
			g.drawString("Multiplayer",0,height*17/18);
		}
		if(screen == 1) {
			g.setColor(Color.WHITE);
			g.fillRect(0,0,width,height);
			Font f = new Font("MONOSPACED",Font.BOLD,(int)(width/30.0));
			g.setFont(f);
			g.setColor(HIGHLIGHT_COLOR);
			if(highlightSpot == 0) {
				g.fillRect(0,height*1/6,width,height*1/6);
			} else if (highlightSpot == 1) {
				g.fillRect(0,height*2/6,width,height*1/6);
			} else if (highlightSpot == 2) {
				g.fillRect(0,height*3/6,width,height*1/6);
			} else if (highlightSpot == 3) {
				g.fillRect(0,height*4/6,width,height*1/6);
			} else if (highlightSpot == 4) {
				g.fillRect(0,height*5/6,width,height*1/6);
			}
			highlightSpot = -1;
			g.setColor(Color.BLACK);
			for(int i = 0; i < 6; i++) {
				g.drawLine(0,height*i/6,width,height*i/6);
			}
			g.drawString("Please select a grid size",0,height*3/24);
			g.drawString("3x3",0,height*7/24);
			g.drawString("4x4",0,height*11/24);
			g.drawString("5x5",0,height*15/24);
			g.drawString("6x6",0,height*19/24);
			g.drawString("7x7",0,height*23/24);
		}
		if(screen == 2) {
			clearDrawing(g);
			if(drawTile) {
				if(isSinglePlayer == false) {
					if(turnCount % 2 != 0) {
						table[drawRow][drawColumn] = 1;
					} else {
						table[drawRow][drawColumn] = 100;
					}
					turnCount++;
				} else {
					table[drawRow][drawColumn] = 1;
					turnCount++;
					determineWin(g);
					AI com = new AI(gridSize);
					com.makeMove(table,gridSize);
				    turnCount++;
			    }
				drawTile = false;
		  	}
			for(int c = 0; c < gridSize; c++) {
				for(int r = 0; r < gridSize; r++) {
					if(table[r][c] == 1) {
						g.setColor(PLAYER_1_COLOR);
						g.fillRect(width*c/gridSize,tHeight+(bHeight*r/gridSize),cellWidth,cellHeight);
					} else if (table[r][c] == 100) {
						g.setColor(PLAYER_2_COLOR);
						g.fillRect(width*c/gridSize,tHeight+(bHeight*r/gridSize),cellWidth,cellHeight);
					} else if (table[r][c] == 1000) {
						g.setColor(HIGHLIGHT_COLOR);
						g.fillRect(width*c/gridSize,tHeight+(bHeight*r/gridSize),cellWidth,cellHeight);
						table[r][c] = 0;
					}
				}
			}
			determineWin(g);

			g.setColor(Color.GRAY);
			g.fillRect(0,0,width,(int)(height*(1.0/(gridSize+1.0))));

			Font f = new Font("MONOSPACED",Font.BOLD,(int)(width/25.0));
			g.setFont(f);

			g.setColor(PLAYER_1_COLOR);
			g.drawString("Player 1 Wins",0,(int)(tHeight/2.0));
			g.drawString(""+p1Wins,0,tHeight);
			g.setColor(Color.BLACK);
			g.drawString("Draws",(int)(width/3.0),(int)(tHeight/2.0));
			g.drawString(""+draws,(int)(width/3.0),tHeight);
			g.setColor(PLAYER_2_COLOR);
			g.drawString("Player 2 Wins",(int)(width*2.0/3.0),(int)(tHeight/2.0));
			g.drawString(""+p2Wins,(int)(width*2.0/3.0),tHeight);

			g.setColor(Color.BLACK);
			g.drawLine((int)(width/3.0),0,(int)(width/3.0),tHeight);
			g.drawLine((int)(width*2.0/3.0),0,(int)(width*2.0/3.0),tHeight);
			g.drawLine(0,tHeight,width,tHeight);


			for (double i=1.0;i<gridSize;i++) {
				g.drawLine((int)(width*(i/gridSize)),tHeight,(int)(width*(i/gridSize)),height);
				g.drawLine(0,(int)(bHeight*((i+1)/gridSize)),width,(int)(bHeight*((i+1)/gridSize)));
			}
		}
	}

    //Both press and release of mouse
    public void mouseClicked(MouseEvent e) {
    }

    //Mouse starts to hover over object
    public void mouseEntered(MouseEvent e) {
    }

    //Mouse stops to hover over object
    public void mouseExited(MouseEvent e) {
    }

    //Mouse was pressed
    public void mousePressed(MouseEvent e) {
	  double mouseX = e.getX();
	  double mouseY = e.getY();
    	if(e.getButton() == MouseEvent.BUTTON1) {
    		if(screen == 0) {
    			if(mouseY > height*1/3 && mouseY < height*2/3) {
    				isSinglePlayer = true;
    				screen++;
    			} else if (mouseY > height*2/3) {
    				isSinglePlayer = false;
    				screen++;
    			}
    		} else if (screen == 1) {
    			if(mouseY > height*1/6 && mouseY < height*2/6) {
    				gridSize = 3;
    				screen++;
    			} else if (mouseY > height*2/6 && mouseY < height*3/6) {
    				gridSize = 4;
    				screen++;
    			} else if (mouseY > height*3/6 && mouseY < height*4/6) {
    				gridSize = 5;
    				screen++;
    			} else if (mouseY > height*4/6 && mouseY < height*5/6) {
    				gridSize = 6;
    				screen++;
    			} else if (mouseY > height*5/6) {
    				gridSize = 7;
    				screen++;
    			}
    			table = new int[gridSize][gridSize];
    		} else if (screen == 2) {
			  	int c = 0;
			  	int r = 0;
			  	if(mouseY > tHeight) {
			  	  	c = findColumn(mouseX);
			  	  	r = findRow(mouseY);
			  	}
			  	else {
			  		c = -1;
			  		r = -1;
			  	}
			  	fillSquare(r,c);
    		}
    	} else if (e.getButton() == MouseEvent.BUTTON3) {
    		if(screen == 0) {
    		} else {
    			screen --;
    			turnCount = 1;
    		}
    	}
  	  repaint();
    }

    //Mouse released
    public void mouseReleased(MouseEvent e) {
    }

    //Mouse clicked on an object and dragged it
    public void mouseDragged(MouseEvent e) {
    }

    //Mouse moved over an object
    public void mouseMoved(MouseEvent e) {
    	double x = e.getX();
    	double y = e.getY();
    	int r = 0;
    	int c = 0;
		if(screen == 0) {
			if(y > height*1/3 && y < height*2/3) {
				highlightSpot = 0;
			} else if (y > height*2/3) {
				highlightSpot = 1;
			}
		} else if (screen == 1) {
			if(y > height*1/6 && y < height*2/6) {
				highlightSpot = 0;
			} else if (y > height*2/6 && y < height*3/6) {
				highlightSpot = 1;
			} else if (y > height*3/6 && y < height*4/6) {
				highlightSpot = 2;
			} else if (y > height*4/6 && y < height*5/6) {
				highlightSpot = 3;
			} else if (y > height*5/6) {
				highlightSpot = 4;
			}
		} else if (screen == 2) {
		  	if(y > tHeight) {
		  	  	c = findColumn(x);
		  	  	r = findRow(y);
		  	  	if(table[r][c] == 0) {
		  	  		table[r][c] = 1000;
		  	  	}
		  	}
		}
		repaint();
    }

    //Figures out and returns which column the mouse click was in
    private int findColumn (double x) {
    	for(double i=1.0;i<=gridSize;i++) {
    		if(x < i/gridSize*width) {
    			return (int)(i-1);
    		}
    	}
    	return gridSize;
    }

    //Figures out and returns which row the mouse click was in
    private int findRow (double y) {
    	for(double i=1.0;i<=gridSize;i++) {
    		if(y < tHeight+(i/gridSize*bHeight)) {
    			return (int)(i-1);
    		}
    	}
    	return gridSize;
    }

    //Checks if the point is valid or not and gives the coordinates if it is
    private boolean fillSquare(int r, int c) {
    	if(r == -1 && c == -1) {
    		return false;
    	}
    	if(table[r][c] == 0) {
    		drawTile = true;
    		drawRow = r;
    		drawColumn = c;
    		return true;
    	}
    	return false;
    }

    //Checks if there is a win on the board
    //0 means there is no win yet
    //1 means player 1 won
    //2 means player 2 won
    //3 means there was a draw after all the cells were filled
    private int checkForWin() {
    	int sum = 0;
    	int cellsFilled = 0;
    	//Checking for horizontal wins in this loop
    	for(int r = 0; r < gridSize; r++) {
    		sum = 0;
    		for(int c = 0; c < gridSize; c++) {
    			sum += table[r][c];
    			if(sum == 1 * gridSize) {
    				return 1;
    			} else if (sum == 100 * gridSize) {
    				return 2;
    			}
    		}
    	}
		//Checking for vertical wins in this loop
		for(int c = 0; c < gridSize; c++) {
			sum = 0;
			for(int r = 0; r < gridSize; r++) {
				sum += table[r][c];
				if(sum == 1 * gridSize) {
					return 1;
				} else if (sum == 100 * gridSize) {
					return 2;
				}
			}
		}
		//Checking for diagnol wins in this loop
		sum = 0;
		for(int i = 0; i < gridSize; i++) {
			sum += table[i][i];
			if(sum == 1 * gridSize) {
				return 1;
			} else if (sum == 100 * gridSize) {
				return 2;
			}
		}
		sum = 0;
		for(int i = gridSize-1; i >= 0; i --) {
			sum += table[i][gridSize-1-i];
			if(sum == 1 * gridSize) {
				return 1;
			} else if (sum == 100 * gridSize) {
				return 2;
			}
		}
		//Checks if all the tiles are filled
		for (int r = 0; r < gridSize; r ++) {
			for(int c = 0; c < gridSize; c ++) {
				if(table[r][c] != 0) {
					cellsFilled++;
				}
				if(cellsFilled == gridSize * gridSize) {
					return 3;
				}
			}
		}
    	return 0;
    }

    //Retrieves the gridSize int (so the AI class may use it)
    public int getGridSize() {
    	return gridSize;
    }

    //Uses the checkForWin to determine a win and take appropriate action
    private void determineWin(Graphics g) {
		//If somebody won or there was a tie
		if (checkForWin() != 0) {
			if(checkForWin() == 1) {
				p1Wins++;
			} else if (checkForWin() == 2) {
				p2Wins++;
			} else if (checkForWin() == 3) {
				draws++;
			}
			turnCount = 1;
			clearBoard(g);
		}
    }

    //Cleans up the board by drawing over the cells and resetting the array
    public void clearBoard(Graphics g) {
		for(int i = 0; i < gridSize; i ++) {
			for(int j = 0; j < gridSize; j++) {
				g.clearRect(width*i/gridSize,tHeight+(bHeight*j/gridSize),cellWidth,cellHeight);
				table[i][j] = 0;
			}
		}
    }

    //Cleans up the drawing board for when the screen changes from 1 to 2
    private void clearDrawing(Graphics g) {
		for(int i = 0; i < gridSize; i ++) {
			for(int j = 0; j < gridSize; j++) {
				g.clearRect(width*i/gridSize,tHeight+(bHeight*j/gridSize),cellWidth,cellHeight);
			}
		}
    }
}