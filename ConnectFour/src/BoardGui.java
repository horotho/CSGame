import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.PriorityQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;



public class BoardGui extends JFrame
{
	public int ai_i, ai_j;
	// Declaring public variables
    public Square[][] squares = new Square[6][7];
    public boolean playerSwitch = true;
    public JLabel player;
    public JOptionPane chooseNewGame;
    public boolean AI;
    //public PriorityQueue<pqNode> aiMoves = new PriorityQueue();
    
	public BoardGui()
	{
		// Calls the inherited constructor for JFrame, and sets the JFrame's size, function on exit, and grid layout
		super("Connect Four");
		this.setBounds(0, 0, 700, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Creates the panel of squares
		JPanel gameboard = new JPanel();
		gameboard.setLayout(new GridLayout(6,6));
		gameboard.setSize(new Dimension(600,600));
		gameboard.setPreferredSize(new Dimension(600,600));
		
		// Creates a mouse listener for each square, then adds it to the game board
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 7; j++)
			{	
				Square sq = new Square(i, j, 70);
				sq.addMouseListener(new Handler());
				squares[i][j] = sq;
				gameboard.add(sq);
			}
		}
		
		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		
		this.add(gameboard, c);
		
		// Creating player one
		JPanel info = new JPanel();
		player = new JLabel("Player One");
		player.setFont(new Font("Times New Roman", Font.BOLD, 28));
		info.add(player);
		
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.BOTH;
		
		this.add(info, c);
		
		// Adds the options bar with the New Game
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Options");
		JMenu ai_menu = new JMenu("AI");
		
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
		newGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				startNewGame();
			}
		});
		
		
		menu.add(newGame);
		menuBar.add(menu);
		
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.05;
		c.fill = GridBagConstraints.BOTH;
		
		this.add(menuBar, c);
				
		this.setVisible(true);
		this.startNewGame();
		
	}
	
	// Sets each square to the original state and sets the player to player one
	public void startNewGame()
	{
		Object[] options = {"1 Player", "2 Player"};
		Object select = JOptionPane.showOptionDialog(squares[1][1].getParent(), "Select Game Type", "Game Type", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0] );
		if(((int) select) == 1)  AI = false;
		else AI = true;
		
		System.out.println(AI);
		
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				squares[i][j].paintOriginal();
			}
		}
		
		playerSwitch = true;
		player.setText("Player One");
	}

	// Function that checks for four in a row
	public boolean IsFour(Square new_square) {

		// Collects information about the square that was just placed
		Color color = new_square.getColor();
		int I = new_square.getRow();
		int J = new_square.getCol();

		// A nested for loop that checks all adjacent squares for a square of the same color. The system will then know the maximum number
		//  of adjacent, same colored squares. 
		for(int i = I-1; i < I+2; i++)
		{
			for(int j = J-1; j < J+2; j++)
			{

				if( !(i == I && j == J)
				&& 0 <= i && i < 6
				&& 0 <= j && j < 7
                && squares[i][j].getFilled()
				&& color == squares[i][j].getColor() )
				{
					System.out.println("found two");
					int next_i = I + 2*(i-I);
					int next_j = J + 2*(j-J);

					if( 0 <= next_i && next_i < 6
					&& 0 <= next_j && next_j < 7
                	&& squares[next_i][next_j].getFilled()
					&& color == squares[next_i][next_j].getColor() )
					{
						System.out.println("found three");
						int next_next_i = I + 3*(i-I);
						int next_next_j = J + 3*(j-J);

						int prev_i = I - (i-I);
						int prev_j = J - (j-J);

						if( (0 <= next_next_i && next_next_i < 6
							&& 0 <= next_next_j && next_next_j < 7
                			&& squares[next_next_i][next_next_j].getFilled()
							&& color == squares[next_next_i][next_next_j].getColor())
						|| ( 0 <= prev_i && prev_i < 6
							&& 0<= prev_j && prev_j < 7
                			&& squares[prev_i][prev_j].getFilled()
							&& color == squares[prev_i][prev_j].getColor() ) )
						{
							System.out.println("found four");
							return true;
						}
					}
				}
			}
		}
	return false;
	}

	// Moves for the AI
	public void move()
	{
		//System.out.println("Ima Win");   //Sanity check
		// Make an array containing streak lengths of each index
		/*
		int[][] streak = new int[6][7];
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				streak[i][j] = howMany(squares[i][j]);
			}
		}
		*/
		
		// Get first available place in each column
		int[] avalibleSpot = new int[7];
		for(int j = 0; j < 7; j++)
		{
			for(int i = 0; i < 6 && !squares[i][j].getFilled() ; i++)
			{
				avalibleSpot[j] = i;
			}
		}


		for(int j = 0; j < 7; j++)
		{
			int i = avalibleSpot[j];
			if(squares[i][j].getFilled())
				continue;
			if(howMany(i, j, Color.black) == 4)
			{
				ai_i = i; ai_j = j;
            	squares[i][j].setFilled(true);
				squares[i][j].setColor(Color.black);
				squares[i][j].paint();
				return;
			}
		}

		for(int j = 0; j < 7; j++)
		{
			int i = avalibleSpot[j];
			if(squares[i][j].getFilled())
				continue;
			System.out.println("Their four i: " + i + " j: " + j);
			System.out.println("howMany: " + howMany(i, j, Color.red));
			if(howMany(i, j, Color.red) == 4)
			{
				ai_i = i; ai_j = j;
            	squares[i][j].setFilled(true);
				squares[i][j].setColor(Color.black);
				squares[i][j].paint();
				return;
			}
            squares[i][j].setFilled(false);
		}

        int max = 0, max_i = avalibleSpot[3], max_j = 3, how_many = 0;

		for(int j = 0; j < 7; j++)
		{
			int i = avalibleSpot[j];
			if(squares[i][j].getFilled())
				continue;
			how_many = howMany(i, j, Color.black);
			if(how_many > max)
			{
				System.out.println("found longest");
				max = how_many;
				max_i = i;
				max_j = j;
			}
            squares[i][j].setFilled(false);
		}

		ai_i = max_i; ai_j = max_j;
        squares[max_i][max_j].setFilled(true);
		squares[max_i][max_j].setColor(Color.black);
		squares[max_i][max_j].setFilled(true);
		squares[max_i][max_j].paint();
		return;
		
		// See what surrounds the available spots and determine move
		/*for(int j = 0; j < 7; j++)
		{
			int i = avalibleSpot[j];
			if(j == 0) // case at far left side
			{
				// checks what's below, diagonal to right up/down, right
				if(i > 3) // check below if it matters and exists
				{
					if(squares[i+1][j].getColor() == Color.black && squares[i+2][j].getColor() == Color.black) // it's ours
					{
						if(streak[i+1][j] == 3)
						{
							pqNode win = new pqNode(i, j, 10);
							aiMoves.add(win);
							System.out.println("Ima Win");
						}
						else if(streak[i+1][j] >= 2)
						{
							// add to pq with med priority [this might make us win later]
						}
					}
					else if(squares[i+1][j].getColor() == Color.red && squares[i+2][j].getColor() == Color.red) // it's the opponents
					{
						if(streak[i+1][j] == 3)
						{
							// add to pq with almost highest priority [this will make them not win]
						}
						else if(streak[i+1][j] >= 2)
						{
							// add to pq with med priority [this might piss them off a little]
						}
					}
				}
				else if(squares[i][j+1].getFilled() && squares[i][j+2].getFilled()) // check spot to the right exists and matters
				{
					if(squares[i][j+1].getColor() == Color.black && squares[i][j+2].getColor() == Color.black) // it's ours
					{
						if(streak[i][j+1] == 3)
						{
							// add to pq with highest priority [this will make us win]
						}
						else if(streak[i][j+1] >= 2)
						{
							// add to pq with med priority [this might make us win later, maybe need to check 3 over]
						}
					}
					else if(squares[i][j+1].getColor() == Color.red && squares[i][j+2].getColor() == Color.red) // it's the opponents
					{
						if(streak[i][j+1] == 3)
						{
							// add to pq with almost highest priority [this will make them not win]
						}
						else if(streak[i][j+1] >= 2)
						{
							// add to pq with med priority [this might piss them off a little]
						}
					}
				}
				else if(i < 3 && squares[i+1][j+1].getFilled() && squares[i+2][j+2].getFilled()) // check diagonal down/right if it exists and matters
				{
					if(squares[i+1][j+1].getColor() == Color.black && squares[i+2][j+2].getColor() == Color.black) // it's ours
					{
						if(streak[i+1][j+1] == 3)
						{
							// add to pq with highest priority [this will make us win]
						}
						else if(streak[i+1][j+1] >= 2)
						{
							// add to pq with med priority [this might make us win later, maybe need to check 3 over]
						}
					}
					else if(squares[i+1][j+1].getColor() == Color.red && squares[i+2][j+2].getColor() == Color.red) // it's the opponents
					{
						if(streak[i+1][j+1] == 3)
						{
							// add to pq with almost highest priority [this will make them not win]
						}
						else if(streak[i+1][j+1] >= 2)
						{
							// add to pq with med priority [this might piss them off a little]
						}
					}
				}
				else if(i > 2 && squares[i-1][j+1].getFilled() && squares[i-2][j+2].getFilled()) // check diagonal up/right if it exists and matters
				{
					if(squares[i-1][j+1].getColor() == Color.black && squares[i-2][j+2].getColor() == Color.black) // it's ours
					{
						if(streak[i-1][j+1] == 3)
						{
							// add to pq with highest priority [this will make us win]
						}
						else if(streak[i-1][j+1] >= 2)
						{
							// add to pq with med priority [this might make us win later, maybe need to check 3 over]
						}
					}
					else if(squares[i-1][j+1].getColor() == Color.red && squares[i-2][j+2].getColor() == Color.red) // it's the opponents
					{
						if(streak[i-1][j+1] == 3)
						{
							// add to pq with almost highest priority [this will make them not win]
						}
						else if(streak[i-1][j+1] >= 2)
						{
							// add to pq with med priority [this might piss them off a little]
						}
					}
				}
			}
			else if(0 < j && j < 6) // middle section
			{
				// checks in all the directions except above
				if(i > 3) // check below if it matters and exists
				{
					if(squares[i+1][j].getColor() == Color.black && squares[i+2][j].getColor() == Color.black) // it's ours
					{
						if(streak[i+1][j] == 3)
						{
							// add to pq with highest priority [this will make us win]
						}
						else if(streak[i+1][j] >= 2)
						{
							// add to pq with med priority [this might make us win later]
						}
					}
					else if(squares[i+1][j].getColor() == Color.red && squares[i+2][j].getColor() == Color.red) // it's the opponents
					{
						if(streak[i+1][j] == 3)
						{
							// add to pq with almost highest priority [this will make them not win]
						}
						else if(streak[i+1][j] >= 2)
						{
							// add to pq with med priority [this might piss them off a little]
						}
					}
				}
				// do this shit
			}
			else if(j == 6) // case at far right side
			{
				// checks below, left, diagonally up and down to left; basically far left case except looks left instead of right
				if(i > 3) // check below if it matters and exists
				{
					if(squares[i+1][j].getColor() == Color.black && squares[i+2][j].getColor() == Color.black) // it's ours
					{
						if(streak[i+1][j] == 3)
						{
							// add to pq with highest priority [this will make us win]
						}
						else if(streak[i+1][j] >= 2)
						{
							// add to pq with med priority [this might make us win later]
						}
					}
					else if(squares[i+1][j].getColor() == Color.red && squares[i+2][j].getColor() == Color.red) // it's the opponents
					{
						if(streak[i+1][j] == 3)
						{
							// add to pq with almost highest priority [this will make them not win]
						}
						else if(streak[i+1][j] >= 2)
						{
							// add to pq with med priority [this might piss them off a little]
						}
					}
				}
				else if(squares[i][j-1].getFilled() && squares[i][j-2].getFilled()) // check spot to the left exists and matters
				{
					if(squares[i][j-1].getColor() == Color.black && squares[i][j-2].getColor() == Color.black) // it's ours
					{
						if(streak[i][j-1] == 3)
						{
							// add to pq with highest priority [this will make us win]
						}
						else if(streak[i][j-1] >= 2)
						{
							// add to pq with med priority [this might make us win later, maybe need to check 3 over]
						}
					}
					else if(squares[i][j-1].getColor() == Color.red && squares[i][j-2].getColor() == Color.red) // it's the opponents
					{
						if(streak[i][j-1] == 3)
						{
							// add to pq with almost highest priority [this will make them not win]
						}
						else if(streak[i][j-1] >= 2)
						{
							// add to pq with med priority [this might piss them off a little]
						}
					}
				}
				else if(i < 3 && squares[i+1][j-1].getFilled() && squares[i+2][j-2].getFilled()) // check diagonal down/left if it exists and matters
				{
					if(squares[i+1][j-1].getColor() == Color.black && squares[i+2][j-2].getColor() == Color.black) // it's ours
					{
						if(streak[i+1][j-1] == 3)
						{
							// add to pq with highest priority [this will make us win]
						}
						else if(streak[i+1][j-1] >= 2)
						{
							// add to pq with med priority [this might make us win later, maybe need to check 3 over]
						}
					}
					else if(squares[i+1][j-1].getColor() == Color.red && squares[i+2][j-2].getColor() == Color.red) // it's the opponents
					{
						if(streak[i+1][j-1] == 3)
						{
							// add to pq with almost highest priority [this will make them not win]
						}
						else if(streak[i+1][j-1] >= 2)
						{
							// add to pq with med priority [this might piss them off a little]
						}
					}
				}
				else if(i > 2 && squares[i-1][j-1].getFilled() && squares[i-2][j-2].getFilled()) // check diagonal up/left if it exists and matters
				{
					if(squares[i-1][j-1].getColor() == Color.black && squares[i-2][j-2].getColor() == Color.black) // it's ours
					{
						if(streak[i-1][j-1] == 3)
						{
							// add to pq with highest priority [this will make us win]
						}
						else if(streak[i-1][j-1] >= 2)
						{
							// add to pq with med priority [this might make us win later, maybe need to check 3 over]
						}
					}
					else if(squares[i-1][j-1].getColor() == Color.red && squares[i-2][j-2].getColor() == Color.red) // it's the opponents
					{
						if(streak[i-1][j-1] == 3)
						{
							// add to pq with almost highest priority [this will make them not win]
						}
						else if(streak[i-1][j-1] >= 2)
						{
							// add to pq with med priority [this might piss them off a little]
						}
					}
				}
			}
		}*/
	}
	
	public int howMany(int I, int J, Color color) 
	{
		// Collects information about the square that was just placed
		//Color color = new_square.getColor();
		//int I = new_square.getRow();
		//int J = new_square.getCol();
		int howMany = 0;

		// A nested for loop that checks all adjacent squares for a square of the same color. The system will then know the maximum number
		//  of adjacent, same colored squares. 
		for(int i = I-1; i < I+2; i++)
		{
			for(int j = J-1; j < J+2; j++)
			{
				if( !(i == I && j == J)
				&& 0 <= i && i < 6
				&& 0 <= j && j < 7
                && squares[i][j].getFilled()
				&& color == squares[i][j].getColor() )
				{
					howMany = (howMany < 2) ? 2 : howMany;
					int next_i = I + 2*(i-I);
					int next_j = J + 2*(j-J);

					if( 0 <= next_i && next_i < 6
					&& 0 <= next_j && next_j < 7
                	&& squares[next_i][next_j].getFilled()
					&& color == squares[next_i][next_j].getColor() )
					{
						howMany = (howMany < 3) ? 3 : howMany;
						int next_next_i = I + 3*(i-I);
						int next_next_j = J + 3*(j-J);

						int prev_i = I - (i-I);
						int prev_j = J - (j-J);

						if( (0 <= next_next_i && next_next_i < 6
							&& 0 <= next_next_j && next_next_j < 7
                			&& squares[next_next_i][next_next_j].getFilled()
							&& color == squares[next_next_i][next_next_j].getColor())
						|| ( 0 <= prev_i && prev_i < 6
							&& 0<= prev_j && prev_j < 7
                			&& squares[prev_i][prev_j].getFilled()
							&& color == squares[prev_i][prev_j].getColor() ) )
						{
							return 4;
						}
					}
				}
			}
		}
		return howMany;
	}
	
	// The function call for the mouse listener. When a square is clicked, the following code is implemented on this square.
	private class Handler implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			Square sq = ((Square) arg0.getSource());
			int col = sq.getCol(), i = 5;
			
			// traverses down the connect four board until a piece is found
			while(i >= 0)
			{
				if(!squares[i][col].getFilled())
				{
					squares[i][col].paint();
					player.setText(playerSwitch ? "Player Two" : "Player One");
					playerSwitch = !playerSwitch;
					squares[0][col].setIndicator(true, playerSwitch ? Color.red : Color.black);
					break;
				}
				
				i--;
			}

			int I = i;
			
			for(i = 0; i < 6; i++)
			{
				for(int j = 0; j < 7; j++)
				{
					if(!squares[i][j].getFilled())
					squares[i][j].setColor(playerSwitch ? Color.red : Color.black);
				}
			}
			
			// If four in a row are found, display winner and ask for new game
			if(IsFour(squares[I][col]))
			{
				String winnerText = squares[I][col].getColor() == Color.red ? "Player One Wins!" : "Player Two Wins!";
				player.setText(winnerText);
				Object select = JOptionPane.showConfirmDialog(squares[I][col].getParent(), "Start a New Game?", winnerText, 0);
				if((int)select == JOptionPane.YES_OPTION) startNewGame();
				else System.exit(0);
			}
			
			if(AI && !playerSwitch)
			{
				move();// move(some shit);
				playerSwitch = !playerSwitch;
				//System.out.println("poop");
				for(i = 0; i < 6; i++)
				{
					for(int j = 0; j < 7; j++)
					{
						if(!squares[i][j].getFilled())
						squares[i][j].setColor(playerSwitch ? Color.red : Color.black);
					}
				}
			}
			
			// run this again after ai moves
			if(AI)
			{
			// If four in a row are found, display winner and ask for new game
				if(IsFour(squares[ai_i][ai_j]))
				{
					String winnerText = squares[ai_i][ai_j].getColor() == Color.red ? "Player One Wins!" : "Player Two Wins!";
					player.setText(winnerText);
					Object select = JOptionPane.showConfirmDialog(squares[ai_i][ai_j].getParent(), "Start a New Game?", winnerText, 0);
					if((int)select == JOptionPane.YES_OPTION) startNewGame();
					else System.exit(0);
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			Square sq = ((Square) arg0.getSource());
			int col = sq.getCol();
			
			squares[0][col].setIndicator(true, playerSwitch ? Color.red : Color.black);
			
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			Square sq = ((Square) arg0.getSource());
			int col = sq.getCol();
			
			squares[0][col].setIndicator(false, playerSwitch ? Color.red : Color.black);
			
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
						
		}
		
	}
}
