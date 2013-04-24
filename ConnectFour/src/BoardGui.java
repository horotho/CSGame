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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


public class BoardGui extends JFrame
{
	
    public Square[][] squares = new Square[6][7];
    public boolean playerSwitch = true;
    public JLabel player;
	
	public BoardGui()
	{
		super("Connect Four");
		this.setBounds(0, 0, 700, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel gameboard = new JPanel();
		gameboard.setLayout(new GridLayout(6,6));
		gameboard.setSize(new Dimension(600,600));
		gameboard.setPreferredSize(new Dimension(600,600));
		
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
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Options");
		
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
		newGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				for(int i = 0; i < 6; i++)
				{
					for(int j = 0; j < 6; j++)
					{
						squares[i][j].paintOriginal();
					}
				}
				
				playerSwitch = true;
				player.setText("Player One");
				System.out.println("Clicked new game.");
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
		
	}

	public boolean IsFour(Square new_square) {

		Color color = new_square.getColor();

		int I = new_square.getRow();
		int J = new_square.getCol();

		for(int i = I-1; i < I+2; i++)
		{
			for(int j = J-1; j < J+2; j++)
			{

				if( !(i == I && j ==j)
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
	
	private class Handler implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			Square sq = ((Square) arg0.getSource());
			int col = sq.getCol(), i = 5;
			
			while(i >= 0)
			{
				if(!squares[i][col].getFilled())
				{
					squares[i][col].paint();
					player.setText(playerSwitch ? "Player Two" : "Player One");
					break;
				}
				
				i--;
			}

			int I = i;
			
			playerSwitch = !playerSwitch;
			
			for(i = 0; i < 6; i++)
			{
				for(int j = 0; j < 6; j++)
				{
					if(!squares[i][j].getFilled())
					squares[i][j].setColor(playerSwitch ? Color.red : Color.black);
				}
			}
			
			if(IsFour(squares[I][col]))
			{
				for(i = 0; i < 6; i++)
				{
					for(int j = 0; j < 6; j++)
					{
						squares[i][j].paintOriginal();
					}
				}
				
				playerSwitch = true;
				player.setText("Player One");
				System.out.println("Clicked new game.");
			}
			
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
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
