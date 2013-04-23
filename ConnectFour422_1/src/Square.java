import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;


public class Square extends JPanel
{
	int row, col, size;
	Color color;
	
	public Square(Color color, int row, int col, int size)
	{
		this.color = color;
		this.size = size;
		this.row = row;
		this.col = col;
		this.setBackground(color);
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        
		g2.setColor(Color.black);
		g2.drawRect(0, 0, width(), height());
		g2.setColor(Color.white);
		g2.fillOval((width()-size)/2, (height()-size)/2, size, size);	
	}
	
	public int height()
	{
		return this.getHeight();
	}
	
	public int width()
	{
		return this.getWidth();
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
}