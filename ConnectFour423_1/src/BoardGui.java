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
	
    public Square[][] squares = new Square[6][6];
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
			for(int j = 0; j < 6; j++)
			{	
				Square sq = new Square(i, j, 60);
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
					break;
				}
				
				i--;
			}
			
			for(i = 0; i < 6; i++)
			{
				for(int j = 0; j < 6; j++)
				{
					if(!squares[i][j].getFilled())
					squares[i][j].setColor(playerSwitch ? Color.black : Color.red);
				}
			}
			
			playerSwitch = !playerSwitch;
			player.setText(playerSwitch ? "Player One" : "Player Two");
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
