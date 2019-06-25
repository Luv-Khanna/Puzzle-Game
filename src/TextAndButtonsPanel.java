import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * TextAndButtonsPanel class to create the buttons for required actions and TextArea
 * Subclass of JPanel
 * @author Luv Khanna
 *
 */
public class TextAndButtonsPanel extends JPanel
{
	/**
	 * The textArea used to display the messages
	 */
	public static JTextArea text;
	
	/**
	 * Constructor to create the buttons and textArea in the required format 
	 */
	public TextAndButtonsPanel()
	{
		setLayout(new GridLayout(2,1));
		JPanel textandscroller=new JPanel();
		JPanel buttons=new JPanel();
		text= new JTextArea("Game started!",3,65);
		text.setCaretPosition(text.getDocument().getLength());
		JScrollPane scroller= new JScrollPane(text);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		textandscroller.add(scroller);
		add(textandscroller);
		JButton load=new JButton("Load Another Image");
		load.addActionListener(new loadNewImage());
		JButton show=new JButton("Show Original Image");
		show.addActionListener(new showImage());
		JButton exit=new JButton("Exit");
		exit.addActionListener(new exitFrame());
		buttons.add(load);
		buttons.add(show);
		buttons.add(exit);
		add(buttons);
	}
	
	
	/**
	 * Inner class showImage to display original image when the button is pressed
	 * Implements the ActionListener interface 
	 * @author Luv Khanna
	 */
	public static class showImage implements ActionListener
	{
		/**
		 * A frame to display the original image
		 */
		public static JFrame tempframe=new JFrame();
		
		/* (non-Javadoc)
		 * method to perform an action when the button is clicked 
		 * @param ae 
		 * 			object of the class ActionEvent
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent ae)
		{
			
			tempframe.getContentPane().add(new MyDrawPanel());
			tempframe.setSize(825,860);
			tempframe.setVisible(true);
		}
	}
	
	/**
	 * Inner class exitFrame to exit the window when the button is clicked 
	 * Implements ActionListener interface
	 * @author Luv Khanna
	 *
	 */
	public static class exitFrame implements ActionListener
	{
		/* (non-Javadoc)
		 * method to perform an action when the button is clicked 
		 * @param ae 
		 * 			object of the class ActionEvent
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent ae)
		{
			if(showImage.tempframe.isVisible())
			{
				System.out.println("Is open");
				showImage.tempframe.dispose();
			}
			JavaPuzzle.frame.dispose();
		}
	}
	
	/**
	 * Inner class loadNewImage to load a new image when the button is clicked
	 * Implements the interface ActionListener
	 * @author Luv Khanna
	 *
	 */
	public static class loadNewImage implements ActionListener
	{
		/**
		 * Stores the new file entered by the user
		 */
		public static File selectedFilecopy=null;
		
		/**
		 * Stores the new image entered by the user
		 */
		public static Image imagecopy=null;
		
		/* (non-Javadoc)
		 * method to perform an action when the button is clicked 
		 * @param ae 
		 * 			object of the class ActionEvent
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent ae)
		{
			selectedFilecopy=JavaPuzzle.chooseFile();
			if(selectedFilecopy==null)
			{
				JOptionPane.showMessageDialog(null, "No file selected!");
			}
			else
			{
				try 
				{
					imagecopy= ImageIO.read(selectedFilecopy);
					if (imagecopy == null) 
					{
						JOptionPane.showMessageDialog(null, "Please select an image file!");
					}
					else
					{
						JavaPuzzle.selectedFile=selectedFilecopy;
                        JavaPuzzle.image=imagecopy;
                        JavaPuzzle.frame.setVisible(false);
                        
                        //rescaling the image
                        JavaPuzzle.image=JavaPuzzle.image.getScaledInstance(800,800,Image.SCALE_DEFAULT);
                        
                        //converting image to buffered image
                        JavaPuzzle.bufferedimage=JavaPuzzle.toBufferedImage(JavaPuzzle.image);

                        JavaPuzzle.frame=new JFrame("Puzzle Image"); //creates a frame with the title
                        JavaPuzzle.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//closes program when frame closes

                        //creating objects of the subclasses of JPanel class
                        JavaPuzzle.imagepanel=new ImagePanel();
                        TextAndButtonsPanel textandbuttonspanel=new TextAndButtonsPanel();

                        //adding the panels to the frame
                        JavaPuzzle.frame.getContentPane().add(BorderLayout.CENTER,JavaPuzzle.imagepanel);
                        JavaPuzzle.frame.getContentPane().add(BorderLayout.SOUTH,textandbuttonspanel);
                        JavaPuzzle.frame.addMouseListener(null);
                        JavaPuzzle.frame.setSize(825,1010);//sets size of frame (width,height)
                        JavaPuzzle.frame.setResizable(false);//doesn't allow the frame to resize
                        JavaPuzzle.frame.setVisible(true);//shows components of frame
					}
				} 
				catch (IOException e) 
				{  
					JOptionPane.showMessageDialog(null, "Fail to load image file!");
				}
			}
		}
	}
}
/**
 * MyDrawPanel Class to display the image on the frame
 * Subclass of JPanel
 * @author Luv Khanna
 *
 */
class MyDrawPanel extends JPanel
{
	/* (non-Javadoc)
	 * method to display image on frame 
	 * @param g
	 * 			Objects of the Graphics class
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g)
	{
		Image i=JavaPuzzle.image;
		g.drawImage(i,0,0,null);
	}
}
