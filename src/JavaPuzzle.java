import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.io.*;
import javax.swing.JFileChooser.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Java Puzzle class has the main method to control the flow of the program
 * It creates the main java frame which holds the entire game 
 * It accepts the file entered by the user
 * Implements ActionListener 
 * @author Luv Khanna
 *
 */
public class JavaPuzzle implements ActionListener
{
	/**
	 * The main frame of the application
	 */
	public static JFrame frame;
	
	/**
	 * The file entered by the user 
	 */
	public static File selectedFile=null;
	
	/**
	 * Variable to store the image file only
	 */
	public static Image image=null;
	
	/**
	 * Stores the buffered image of the image file
	 */
	public static BufferedImage bufferedimage=null;
	
	/**
	 * An object of the class Imagepanel
	 */
	public static ImagePanel imagepanel;
	
	/**
	 * main method to control the flow of the program
	 * @param args
	 * 				A String array
	 * @throws IOException
	 * 					 To catch an exception, if stream to file cannot be written to or closed.
	 */
	public static void main(String[] args) throws IOException
	{
		
		boolean flag=false;
		while(!flag)
		{
			selectedFile=chooseFile();
			if(selectedFile==null)
			{
				//alert message printing
				JOptionPane.showMessageDialog(null, "No file selected, please select a file");
			}
			else
			{
				try 
				{
					image= ImageIO.read(selectedFile);
					if (image == null) 
					{
						JOptionPane.showMessageDialog(null, "Please select an image file!");
				    }
					else
					{
						flag=true;
					}
				} 
				catch (IOException e) 
				{  
					JOptionPane.showMessageDialog(null, "Image fail to Load!");
				}
			}
		}
		//rescaling the image
		image=image.getScaledInstance(800,800,Image.SCALE_DEFAULT);
			
		//converting image to buffered image
		bufferedimage=toBufferedImage(image);
		
		frame=new JFrame("Puzzle Image"); //creates a frame with the title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//closes program when frame closes
		
		//creating objects of the subclasses of JPanel class
		imagepanel=new ImagePanel();
		TextAndButtonsPanel textandbuttonspanel=new TextAndButtonsPanel();
		
		//adding the panels to the frame
		frame.getContentPane().add(BorderLayout.CENTER,imagepanel);
		frame.getContentPane().add(BorderLayout.SOUTH,textandbuttonspanel);
		frame.addMouseListener(null);//adding a mouse listener
		frame.setSize(825,1010);//sets size of frame (width,height)
		frame.setResizable(false);//doesn't allow the frame to resize
		frame.setVisible(true);//shows components of frame			
	}
	
	/* (non-Javadoc)
	 * method to perform an action when the button is clicked 
	 * @param ae 
	 * 			object of the class ActionEvent
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae) 
	{
		
	}
	
	/**
	 * method to accept any file entered by the user
	 * @return
	 * 			the file entered by the user
	 */
	public static File chooseFile()
	{
		File selectedFile=null;
		JFileChooser filechooser=new JFileChooser(); //creating a file chooser object
		//creating a filter to the file chooser
		FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
		//adding the filter to the file chooser
		filechooser.addChoosableFileFilter(imageFilter);
		//filechooser.setFileFilter(imageFilter);
		//accepting the file through the file chooser
		int value=filechooser.showOpenDialog(null);
		if(value == JFileChooser.APPROVE_OPTION)
		{
			selectedFile=filechooser.getSelectedFile();
		}
		return selectedFile;
	}
		
	/**
	 * method to convert an image to buffered image
	 * @param img
	 * 			the image to be converted 
	 * @return
	 * 			the buffered image converted
	 */
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}
