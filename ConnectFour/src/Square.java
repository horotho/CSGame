import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;


public class Square extends JPanel
{
	// Private variables of a square object, many of these are used to properly display the board
	private boolean drawn = false, filled = false, triangle = false;
	private int row, col, size;
	private Color color, indicatorColor;
	private Ellipse2D.Double ellipse;
	private GradientPaint gradient = 
			new GradientPaint(0,0, Color.red, 50, 50, Color.yellow, true);
	private GradientPaint gradient2 = 
			new GradientPaint(0,0, new Color(0, 180, 200), 100, 100, Color.blue, true);

	// Constructor, sets the row, column, and size of the square
	public Square(int row, int col, int size)
	{
		this.size = size;
		this.row = row;
		this.col = col;
		color = Color.red;
		indicatorColor = Color.red;
	}
	
	// This code controls the player indicator at the top of the board. Its color is reflected by the current players turn
	public void setIndicator(boolean ind, Color c)
	{
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		triangle = ind ? true : false;
		indicatorColor = c;
		paintComponent(g2);
		repaint();
	}
	
	// Paints the square, to appear like the connect four board. It also reflects the players move. 
	public void paint()
	{
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		paintComponent(g2);
		filled = true;
		repaint();
	}
	
	// The initial paint function, paints the squares a neutral color and sets them to not filled
	public void paintOriginal()
	{
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		paintComponent(g2);
		color = Color.red;
		indicatorColor = Color.red;
		filled = false;
		drawn = false;
		repaint();
	}

	// The method for drawing the different components on the board
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		ellipse = new Ellipse2D.Double((width()-size)/2, (height()-size)/2, size, size);
		Graphics2D g2 = (Graphics2D) g;
        
		g2.setPaint(gradient2);
		g2.fillRect(0, 0, width(), height());
		
		if(!drawn)
		{
			g2.setPaint(gradient);
			g2.fill(ellipse);
			drawn = !drawn;
		}
		else
		{ 
			g2.setPaint(filled ? color : gradient);
			g2.fill(ellipse);
		}
		
		if(triangle)
		{
			g2.setPaint(indicatorColor);
			
			int[] xs = {width()/4, 3*width()/4, width()/2};
			int[] ys = {0, 0, height()/4};
			Polygon triangle = new Polygon(xs, ys, xs.length);
			
			g2.fill(triangle);

		}
		
	}
	
	
	// Getter and setter methods//
	
	
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
	
	public boolean getFilled()
	{
		return filled;
	}
	
	public void setColor(Color c)
	{
		color = c;
		indicatorColor = c;
	}
	
	public Color getColor()
	{
		return color;
	}

	public void setFilled(boolean fill)
	{
		this.filled = fill;
	}

}
