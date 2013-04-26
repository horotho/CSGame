import javax.swing.SwingUtilities;


public class runner
{

	/**
	 * @param args
	 */
	// When the project is run, create a new BoardGui
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
		    public void run() 
		    {
		      BoardGui gui = new BoardGui();
		    }
		});
	}

}
