/*The main method for the TicTacToe Program
 *By Ben Sieben
 *Created May 6 2012
 *Last Updated May 6 2012
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main
{
    //The main method
	public static void main(String[] args)
	{
	  JFrame w = new JFrame("TicTacToe");
	  w.setBounds(100, 100, 800, 600);
	  w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  TicTacToe panel = new TicTacToe(3,true);
	  panel.setBackground(Color.WHITE);
	  w.add(panel);
	  w.setResizable(true);
	  w.setVisible(true);
	}
}