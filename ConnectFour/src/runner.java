import javax.swing.SwingUtilities;


public class runner
{

	/**
	 * @param args
	 */
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
