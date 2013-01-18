import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Displayer extends Thread
{
	private boolean isToPause;
	private long timeToWait;
	private JFrame displayerFrame;
	public Object lock = new Object();
	final MyPanel displayerPanel;
	
	
	public Displayer(BufferedImage[] images, long timeToWait)
	{
		this.isToPause = false;
		this.timeToWait = timeToWait;		
		
		displayerFrame = new JFrame();
		displayerFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		displayerFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(WindowEvent winEvt) 
		    {
		    	displayerFrame.dispose();
		    }
		});
		
		displayerPanel = new MyPanel(this, images, images[0].getHeight() + 5);
		displayerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		displayerPanel.setBackground(Color.BLACK);
		

		displayerFrame.add(displayerPanel,BorderLayout.NORTH);
		displayerFrame.add(displayerPanel.slider,BorderLayout.CENTER);
		displayerFrame.add(displayerPanel.buttonsPanel,BorderLayout.SOUTH);
		
		displayerPanel.setPreferredSize(new Dimension(images[0].getWidth(), images[0].getHeight() + 5));
		
		displayerFrame.pack();
		displayerFrame.setVisible(true);
		displayerFrame.setResizable(false);
		
		this.start();
	}
	
	public void run()
	{
		while(true)
		{
			displayerPanel.repaint();
			
			try 
			{
				Thread.sleep(timeToWait);
			} catch (InterruptedException e) 
			{
				return;
			}
			
			if (isToPause)
			{
				synchronized(lock)
				{
					try 
					{
						lock.wait();
					} catch (InterruptedException e) 
					{
						
					}
					isToPause = false;
				}
			}
		}
		
	}	
	
	public void holdDisplayer()
	{
		this.isToPause = true;
	}

}

@SuppressWarnings("serial")
class MyPanel extends JPanel {
	
	final Displayer parentDisplayer;
	final BufferedImage[] images;
	final int height;
	int currentImage=0;
	
	final JButton playButton;
	final JButton stopButton;
	final JButton pauseButton;
	
	final JSlider slider;
	
	final JPanel buttonsPanel;
	 
	
	public MyPanel(Displayer displayer, BufferedImage[] images, int h)
	{
		this.images = images;
		this.height = h;
		
		this.parentDisplayer = displayer;
		this.playButton = new JButton();
		this.stopButton = new JButton();
		this.pauseButton = new JButton();
		this.buttonsPanel = new JPanel();
		this.slider = new JSlider(0,images.length);
		
		final JPanel thisPanel = this;
		
		slider.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				currentImage = slider.getValue();
				thisPanel.repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		playButton.setText("Play");
		playButton.setName("btnPlay");
		playButton.setEnabled(false);
		playButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				playActionPerformed(evt);
			}
		});
		
		pauseButton.setText("Pause");
		pauseButton.setName("btnPause");
		pauseButton.setEnabled(true);
		pauseButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				pauseActionPerformed(evt);
			}
		});
		
		stopButton.setText("Stop");
		stopButton.setName("btnStop");
		stopButton.setEnabled(true);
		stopButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				stopActionPerformed(evt);
			}
		});

		playButton.setAlignmentY(height - 50);
		pauseButton.setAlignmentY(height - 50);
		stopButton.setAlignmentY(height - 50);
		
		this.buttonsPanel.add(playButton);
		this.buttonsPanel.add(pauseButton);
		this.buttonsPanel.add(stopButton);
		
	}

	public void paintComponent(Graphics g) 
	{
		currentImage = (currentImage + 1) % images.length;
		
		try 
		{
			g.drawImage(images[currentImage], 0, 0, null);
			slider.setValue(currentImage);
			
	    } catch (Exception e) 
	    {
	    	e.printStackTrace();
	    }
			
	}
	
	private void playActionPerformed(java.awt.event.ActionEvent evt)
	{
		playButton.setEnabled(false);
		pauseButton.setEnabled(true);
		stopButton.setEnabled(true);	
		
		/* Allows the thread to continue playing the video. */
		synchronized(this.parentDisplayer.lock)
		{
			this.parentDisplayer.lock.notify();
		}
	}
	
	private void pauseActionPerformed(java.awt.event.ActionEvent evt)
	{
		playButton.setEnabled(true);
		pauseButton.setEnabled(false);
		stopButton.setEnabled(true);
		
		/* Simply holds the current image. */
		this.parentDisplayer.holdDisplayer();
	}
	
	private void stopActionPerformed(java.awt.event.ActionEvent evt)
	{
		playButton.setEnabled(true);
		pauseButton.setEnabled(false);
		stopButton.setEnabled(false);
		
		/* We stop the image and reset it to the first one. */
		this.currentImage = 0;
		this.parentDisplayer.holdDisplayer();
		this.repaint();
	}
}
