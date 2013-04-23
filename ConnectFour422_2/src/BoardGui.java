import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;


public class BoardGui extends JFrame
{
	
    public Square[][] squares = new Square[6][6];
	
	public BoardGui()
	{
		super("Connect Four");
		this.setBounds(0, 0, 700, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(6,6));
		
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 6; j++)
			{	
				Square sq = new Square(i, j, 60);
				sq.addMouseListener(new Handler());
				squares[i][j] = sq;
				this.add(sq);
			}
		}
				
		this.setVisible(true);
		
	}
	
	private class Handler implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			Square sq = ((Square) arg0.getSource());
			int col = sq.getCol(), i = 5;
			
			while(i > -1)
			{
				System.out.println(i);
				if(!squares[i][col].getFilled())
				{
					squares[i][col].paint();
					break;
				}
				
				i--;
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
			// TODO Auto-generated method stub
			
		}
		
	}
}
