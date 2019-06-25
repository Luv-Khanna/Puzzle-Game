import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * ImagePanel class to control puzzle generation and play.
 * It creates the JPanel and button grid for user interaction
 * It also controls the swapping of the images.  
 * Subclass of JPanel
 * @author Luv Khanna
 *
 */
public class ImagePanel extends JPanel
{
	/**
	 * Array to store all the small buffered images generated from the large image
	 */
	public static BufferedImage smallimages[];
	
	/**
	 * Array to store the location of the selected image
	 */
	public static int from[]=new int[2];
	
	/**
	 * Array to store the location of the destination image
	 */
	public static int to[]=new int[2];
	
	/**
	 * Array to store a temporary location used for swapping
	 */
	public static int temp[]=new int[2];
	
	/**
	 * Array to store a copy of all the small buffered images generated from the large image
	 */
	public static BufferedImage smallimagescopy[]=new BufferedImage[100];
	
	/**
	 * ArrayList to store all the small buffered images generated from the large image
	 */
	public static ArrayList<BufferedImage> smallimageslist=new ArrayList<BufferedImage>();
	
	/**
	 * Array to store the original positions of the small images
	 */
	public static int positions[];
	
    /**
     * ArrayList to store the original positions of the small images
     */
    public static ArrayList<Integer> positionslist= new ArrayList<Integer>();
    
	/**
	 * ArrayList to store the positions of the small images after shufling
	 */
	public static int shuffledpositions[]=new int[100];
	
	/**
	 * 2D Array to store the grid of the buttons
	 */
	public static JButton buttongrid[][];
	
	/**
	 * 2D Array to store the positions of the images in the buttongrid
	 */
	public static int imagenumber[][]=new int[10][10];
	
	/**
	 * 2D Array to store the original positions of the images in the buttongrid
	 */
	public static boolean correctposition[][]=new boolean[10][10];
	
	/**
	 * A flag value to check if the game has ended or not
	 */
	public static boolean gameendflag=false;
	
	/**
	 * Constructor to make the JPanel format and add the mouse listener
	 */
	public ImagePanel()
	{
		//setting overall layout to the panel
		setLayout(new GridLayout(10,10));
		//adding the mouse listener
		this.addMouseListener(new CustomMouseListener());
	}
	
	/* (non-Javadoc)
	 * method to to control puzzle generation and play.
	 * @param g 
	 * 			object of the Graphics class
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void  paintComponent(Graphics g) 
	{
		Random rand=new Random();
		Graphics2D g2d= (Graphics2D) g;
		int rows=10;
		int columns=10;
		int chunks=rows * columns;
		int chunkWidth = 80;
		int chunkHeight = 80;
		int count=0;
		smallimages = new BufferedImage[chunks]; //Image array to hold image chunks
		positions =new int[100];
		//creating the small images
	    for (int x = 0; x < rows; x++) 
	    {
	        for (int y = 0; y < columns; y++) 
	        {
	                //Initialize the image array with image chunks
	          
	        		smallimages[count] = new BufferedImage(chunkWidth, chunkHeight, JavaPuzzle.bufferedimage.getType());

	                // draws the image chunk
	                Graphics2D gr = smallimages[count].createGraphics();
	                gr.drawImage(JavaPuzzle.bufferedimage, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, this);
	                gr.dispose();
	                positions[count]=count;
	                positionslist.add(count);
	                count++;
	         }
	    }
	    buttongrid=new JButton[10][10];
	    //creating each button in the button array grid
	    for(int i=0;i<10;i++)
	    {
	    	for(int j=0;j<10;j++)
	    	{
	    		buttongrid[i][j]=new JButton();
	    		//adding the mouse listener to each button
	    		buttongrid[i][j].addMouseListener(new CustomMouseListener());
	    	}
	    }
	    //adding buttons to the window
	    count=0;
	    smallimageslist = new ArrayList<BufferedImage>(Arrays.asList(smallimages));//converting array to arraylist
	    for(int i=0;i<100;i++)
	    {
	    	int randomnumber=rand.nextInt(smallimageslist.size());	
	    	smallimagescopy[i]=smallimageslist.get(randomnumber);
	    	shuffledpositions[i]=positionslist.get(randomnumber);
	    	smallimageslist.remove(randomnumber);
	    	positionslist.remove(randomnumber);
	    }
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++)
            {
                buttongrid[j][i].setIcon(new ImageIcon(smallimagescopy[count]));
                buttongrid[j][i].setName(String.valueOf(shuffledpositions[count]));
                add(buttongrid[j][i]);
                count++;
            }
        }
        //checking if initial positions are correct
        for(int i=0;i<10;i++)
        {
        	for(int j=0;j<10;j++)
        	{
        		boolean flag=CustomMouseListener.checkposition(i,j);
        	}
        }
	}
}

/**
 * CustomMouseListener class to perform tasks upon performing actions with the mouse
 * Implements the interface MouseListener
 * @author Luv Khanna
 *
 */
class CustomMouseListener implements MouseListener
{

    /* (non-Javadoc)
     * method to perform task when mouse is clicked
     * @param e
     * 			Object of the MouseEvent class 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) 
    {
    	
    }
    
    /* (non-Javadoc)
     * method to perform task when mouse is pressed
     * @param e
     * 			Object of the MouseEvent class 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) 
    {
        ImagePanel.from[0] = ((int)(((JButton)e.getSource()).getBounds().x)-4)/81;
        ImagePanel.from[1] = ((int)(((JButton)e.getSource()).getBounds().y)-4)/81;
        ImagePanel.temp[0] = 40;
        ImagePanel.temp[1] = 40;
    }

    /* (non-Javadoc)
     * method to perform task when mouse is released
     * @param e
     * 			Object of the MouseEvent class 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) 
    {
    	if(!ImagePanel.gameendflag)
    	{
        int x = (int)e.getPoint().x;
        int y = (int)e.getPoint().y;
        int lels = 0;
        int bruh = 0;
        if((int)e.getPoint().x < 0)
        {
            lels = ((int)(40 * (x/ 40 )))/80 -1;
        }
        else
        {
            lels = ((int)(40 * (x/ 40 )))/80;
        }
        if((int)e.getPoint().y < 0)
        {
            bruh = ((int)(40 * (y/ 40 )))/80 - 1;
        }
        else
        {
            bruh = ((int)(40 * (y/ 40 )))/80;
        }
        ImagePanel.to[0] = lels + ((int)(((JButton)e.getSource()).getBounds().x)-4)/81;
        ImagePanel.to[1] = bruh + ((int)(((JButton)e.getSource()).getBounds().y)-4)/81;
        
        if(new ImageIcon(ImagePanel.smallimages[ImagePanel.from[0]*10+ImagePanel.from[1]]) == ImagePanel.buttongrid[ImagePanel.from[0]][ImagePanel.from[1]].getIcon() ||
                new ImageIcon(ImagePanel.smallimages[ImagePanel.to[0]*10+ImagePanel.to[1]]) == ImagePanel.buttongrid[ImagePanel.to[0]][ImagePanel.to[1]].getIcon())
        {
           
        }
        else 
        {
        	boolean location=checkposition(ImagePanel.from[0],ImagePanel.from[1]);
        	boolean destination=checkposition(ImagePanel.to[0],ImagePanel.to[1]);
        	if(destination)
        	{
        		TextAndButtonsPanel.text.append("\nImage block in correct position!");
        		TextAndButtonsPanel.text.setCaretPosition(TextAndButtonsPanel.text.getDocument().getLength());
        	}
        	else if(location)
        	{
        		TextAndButtonsPanel.text.append("\nImage block in correct position!");
        		TextAndButtonsPanel.text.setCaretPosition(TextAndButtonsPanel.text.getDocument().getLength());
        	}
        	else
        	{
        		Icon copy;
                copy = ImagePanel.buttongrid[ImagePanel.from[0]][ImagePanel.from[1]].getIcon();
                ImagePanel.buttongrid[ImagePanel.from[0]][ImagePanel.from[1]].setIcon(ImagePanel.buttongrid[ImagePanel.to[0]][ImagePanel.to[1]].getIcon());
                ImagePanel.buttongrid[ImagePanel.to[0]][ImagePanel.to[1]].setIcon(copy);
                String namecopy;
                namecopy=ImagePanel.buttongrid[ImagePanel.from[0]][ImagePanel.from[1]].getName();
                ImagePanel.buttongrid[ImagePanel.from[0]][ImagePanel.from[1]].setName(ImagePanel.buttongrid[ImagePanel.to[0]][ImagePanel.to[1]].getName());
                ImagePanel.buttongrid[ImagePanel.to[0]][ImagePanel.to[1]].setName(namecopy);
                boolean pressed=checkposition(ImagePanel.from[0],ImagePanel.from[1]);
                boolean released=checkposition(ImagePanel.to[0],ImagePanel.to[1]);
                if(pressed || released)
                {
                	TextAndButtonsPanel.text.append("\nImage block in correct position!");
                	TextAndButtonsPanel.text.setCaretPosition(TextAndButtonsPanel.text.getDocument().getLength());
                }
                else
                {
                	
                }
        	}
        	//code for checking if game is completed
        	boolean gamecompleted=true;
        	for(int i=0;i<10;i++)
        	{
        		for(int j=0;j<10;j++)
        		{
        			if(!ImagePanel.correctposition[i][j])
        			{
        				gamecompleted=false;
        			}
        		}
        	}
        	if(gamecompleted)
        	{
        		JOptionPane.showMessageDialog(null, "You win!!!");
        		ImagePanel.gameendflag=true;
        	}
        }
    	}
    }
    
    /* (non-Javadoc)
     * method to perform task when mouse is entered
     * @param e
     * 			Object of the MouseEvent class 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) 
    {
    	
    }
    
    /* (non-Javadoc)
     * method to perform task when mouse is exited
     * @param e
     * 			Object of the MouseEvent class 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) 
    {
    	
    }
    
    /**
     * method to check if the image is in the correct position or not
     * @param x
     * 			x position of image in grid
     * @param y
     * 			y position of image in grid
     * @return  returns a boolean value to indicate if the image is in the correct position or not
     */
    public static boolean checkposition(int x,int y)
    {
    	boolean flag=false;
    	int pos1=(10*y)+x;
    	int pos2=Integer.parseInt(ImagePanel.buttongrid[x][y].getName());
    	if(pos1==pos2)
    	{
    		flag=true;
    		ImagePanel.correctposition[x][y]=true;
    	}
    	return flag;
    }
 }
