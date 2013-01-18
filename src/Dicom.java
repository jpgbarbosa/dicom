import java.awt.image.BufferedImage;
import java.util.Vector;
import fr.apteryx.imageio.dicom.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class Dicom extends javax.swing.JFrame implements
		ListSelectionListener 
{
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                                         ATTRIBUTES                                              *      
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	private static final long serialVersionUID = 1L;
	private ReadDicomDir dirRead = new ReadDicomDir();
	
	private String filesDirectory;
	/* Used to know which is the current line selected in the images list. */
	private int currentSelection = 0;
	
	DefaultListSelectionModel list;
	
	int previousLast = 0, previousIndex = 0;
	
	String[] colTitles = new String[4];
	
	public static void main(String args[]) 
	{
		try {
			java.awt.EventQueue.invokeLater(new Runnable() 
			{
				public void run() 
				{
					new Dicom().setVisible(true);
				}
			});

		} catch (Exception e) 
		{
			System.out.println("Error in the Aplication...");
		}
	}
	

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                                   GRAPHIC INTERFACE                                             *      
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public Dicom() 
	{
		//TODO: Please change this accordingly
		filesDirectory = "C:/Users/Barbosa/Documents/EI/4Ano/1oSemestre/IM/TP3/Normais/";
		//filesDirectory = "C:/Users/Penetra/Desktop/DEI/4� Ano/1� Semestre/IM/Projects/TP3/Normais/";
		//filesDirectory = "E:/Ivo/FCTUC/Mestrado/1� Ano/IM/Fichas/TP3/Files/Normais/";
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() 
	{
		txtPath = new javax.swing.JTextField();
		lblTitle = new javax.swing.JLabel();
		jScrollPaneImages = new javax.swing.JScrollPane();
		txtArea = new javax.swing.JTextArea();
		jScrollPane2 = new javax.swing.JScrollPane();
		tableExames = new javax.swing.JTable();
		menuBar = new javax.swing.JMenuBar();
		fileMenu = new javax.swing.JMenu();
		openMenuItem = new javax.swing.JMenuItem();
		saveMenuItem = new javax.swing.JMenuItem();
		saveAsMenuItem = new javax.swing.JMenuItem();
		exitMenuItem = new javax.swing.JMenuItem();
		editMenu = new javax.swing.JMenu();
		cutMenuItem = new javax.swing.JMenuItem();
		copyMenuItem = new javax.swing.JMenuItem();
		pasteMenuItem = new javax.swing.JMenuItem();
		deleteMenuItem = new javax.swing.JMenuItem();
		helpMenu = new javax.swing.JMenu();
		contentsMenuItem = new javax.swing.JMenuItem();
		aboutMenuItem = new javax.swing.JMenuItem();
		
		/* CONTENT WITH TABS AND TEXTAREAS */
		tabsPatient = new javax.swing.JTabbedPane();
		panelPatients = new javax.swing.JPanel();
		panelSeries = new javax.swing.JPanel();
		panelStudies = new javax.swing.JPanel();
		panelImages = new javax.swing.JPanel();
		
		textPatients = new javax.swing.JTextArea();
		textSeries = new javax.swing.JTextArea();
		textStudies = new javax.swing.JTextArea();
		textImages = new javax.swing.JTextArea();
		
		colTitles[0] = "Tipo Exame";
		colTitles[1] = "ID";
		colTitles[2] = "Data";
		colTitles[3] = "Paciente";
		
		/* Player buttons. */
		readDirectoryButton = new javax.swing.JButton();
		showButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		
		txtPath.setText(filesDirectory);
		txtPath.setName("txtPath");

		lblTitle.setText("Dicom Dir Path:");
		lblTitle.setName("lblPath");
		

		
		readDirectoryButton.setText("Read Directory");
		readDirectoryButton.setName("btnRead");
		readDirectoryButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				readDirectoryActionPerformed(evt);
			}
		});
		
		showButton.setText("Show");
		showButton.setName("btnShow");
		showButton.setEnabled(false);
		showButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				showActionPerformed(evt);
			}
		});
				
		textPatients.setColumns(50);
		textPatients.setRows(32);
		
		panelPatients.add(textPatients);
		
		textSeries.setColumns(50);
		textSeries.setRows(32);
		
		panelSeries.add(textSeries);
		
		textStudies.setColumns(50);
		textStudies.setRows(32);
		
		panelStudies.add(textStudies);
		
		textImages.setColumns(50);
		textImages.setRows(32);
				
		jScrollPaneImages.setViewportView(textImages);
		panelImages.add(jScrollPaneImages);
		
		tabsPatient.addTab("Patients", panelPatients);
		tabsPatient.addTab("Series", panelStudies);
		tabsPatient.addTab("Studies", panelSeries);
		tabsPatient.addTab("Images", panelImages);
		
		tableExames.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {  }, new String[] { colTitles[0],
						colTitles[1], colTitles[2], colTitles[3] }));
		jScrollPane2.setViewportView(tableExames);

		fileMenu.setText("File");
		openMenuItem.setText("Open");
		fileMenu.add(openMenuItem);

		saveMenuItem.setText("Save");
		fileMenu.add(saveMenuItem);

		saveAsMenuItem.setText("Save As ...");
		fileMenu.add(saveAsMenuItem);

		exitMenuItem.setText("Exit");
		exitMenuItem.addActionListener(new java.awt.event.ActionListener() 
		{
			public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
				exitMenuItemActionPerformed(evt);
			}
		});

		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);

		editMenu.setText("Edit");
		cutMenuItem.setText("Cut");
		editMenu.add(cutMenuItem);

		copyMenuItem.setText("Copy");
		editMenu.add(copyMenuItem);

		pasteMenuItem.setText("Paste");
		editMenu.add(pasteMenuItem);

		deleteMenuItem.setText("Delete");
		editMenu.add(deleteMenuItem);

		menuBar.add(editMenu);

		helpMenu.setText("Help");
		contentsMenuItem.setText("Contents");
		helpMenu.add(contentsMenuItem);

		aboutMenuItem.setText("About");
		helpMenu.add(aboutMenuItem);

		menuBar.add(helpMenu);

		setJMenuBar(menuBar);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout
						.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(
										org.jdesktop.layout.GroupLayout.LEADING)
								.add(org.jdesktop.layout.GroupLayout.TRAILING,
										layout.createSequentialGroup()
												.add(layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.TRAILING)
														.add(org.jdesktop.layout.GroupLayout.LEADING,
																layout.createSequentialGroup()
																		.add(lblTitle,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																				90,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED,
																				16,
																				Short.MAX_VALUE)
																		.add(txtPath,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																				950,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
														.add(org.jdesktop.layout.GroupLayout.LEADING,
																layout.createSequentialGroup()
																		.add(jScrollPane2,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(tabsPatient,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				271,
																				Short.MAX_VALUE)))
												.addContainerGap())
								.add(org.jdesktop.layout.GroupLayout.BASELINE,
										layout.createSequentialGroup()
												.add(readDirectoryButton)
												.add(10,10,10)
												.add(showButton)
												.add(10,10,10)
												
								))));
		layout.setVerticalGroup(layout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout
						.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(
										org.jdesktop.layout.GroupLayout.BASELINE)
								.add(lblTitle)
								.add(txtPath,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.add(layout
								.createParallelGroup(
										org.jdesktop.layout.GroupLayout.LEADING)
								.add(tabsPatient,
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										550, Short.MAX_VALUE)
								.add(jScrollPane2,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
										550,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						).addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
								.add(
										layout.createParallelGroup(
														org.jdesktop.layout.GroupLayout.BASELINE)
								.add(readDirectoryButton)
								.add(showButton)
						).addContainerGap()));
		pack();
		
	}// </editor-fold>//GEN-END:initComponents

	private void readDirectoryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		try 
		{
			readDirectoryButton.setEnabled(false);
			showButton.setEnabled(true);
			Vector<String> colNames = new Vector<String>();
			colNames.addElement(colTitles[0]);
			colNames.addElement(colTitles[1]);
			colNames.addElement(colTitles[2]);
			colNames.addElement(colTitles[3]);

			tableExames.getColumnModel().getColumn(0)
					.setHeaderValue(colTitles[0]);
			tableExames.getColumnModel().getColumn(1).setHeaderValue(colTitles[1]);
			tableExames.getColumnModel().getColumn(2).setHeaderValue(colTitles[2]);
			tableExames.getColumnModel().getColumn(3)
					.setHeaderValue(colTitles[3]);

			Vector<Vector<String>> results = null;

			try 
			{
				dirRead.readDirectory(txtPath.getText());
				results = new Vector<Vector<String>>();
				
				for (Attributes row : dirRead.getExamsAttributes())
				{
					//Create row vector
					Vector<String> v = new Vector<String>();
					
					// Add Exam Type
					v.add(row.getSeriesAttributes().findString(Tag.Modality));
					
					//Add Patient ID
					v.add(row.getPatientAttributes().findString(Tag.PatientID));
					
					// Add Study date
					v.add(row.getStudyAttributes().findString(Tag.StudyDate));
					
					//Add patient name
					v.add(row.getPatientAttributes().findString(Tag.PatientsName));
					
					// Add row to table vector
					results.addElement(v);
				}

			} catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, "Invalid DicomDir with " + e.getMessage(),
						"Warning", JOptionPane.WARNING_MESSAGE);
			}

			TableModel tbModel = new DefaultTableModel(results, colNames) 
			{
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int col) 
				{
					return false;
				}
			};

			tableExames.setModel(tbModel);

			list = new DefaultListSelectionModel();

			list.addListSelectionListener(this);
			tableExames.setSelectionModel(list);
			tableExames.validate();
			txtArea.setText("");

			this.repaint();

		} catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Error in reading dicomDir.");
		}
	}// GEN-LAST:event_jButton1ActionPerformed
	
	private void showActionPerformed(java.awt.event.ActionEvent evt)
	{
		try 
		{
			BufferedImage[] images = dirRead.readImages(dirRead.getExamImagesPath(currentSelection));
			
			new Displayer(images, Math.round(dirRead.getFrameTime(currentSelection)));
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exitMenuItemActionPerformed
		System.exit(0);
	}// GEN-LAST:event_exitMenuItemActionPerformed

	public void valueChanged(ListSelectionEvent e) 
	{
		DefaultListSelectionModel auxiliar = (DefaultListSelectionModel) (e
				.getSource());
		
		if (auxiliar.equals(list) && e.getValueIsAdjusting() == false) 
		{
			/* We update this variable so we can play the right video. */
			currentSelection = auxiliar.getAnchorSelectionIndex();
			
			Attributes attTemp = (Attributes) dirRead.getExamsAttributes().elementAt(currentSelection);
			textPatients.setText(attTemp.getPatientAttributesToString());
			textStudies.setText(attTemp.getStudyAttributesToString());
			textSeries.setText(attTemp.getSeriesAttributesToString());
			textImages.setText(attTemp.getImageAttributesToString());
			txtArea.setCaretPosition(0);
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JMenuItem aboutMenuItem;
	private javax.swing.JMenuItem contentsMenuItem;
	private javax.swing.JMenuItem copyMenuItem;
	private javax.swing.JMenuItem cutMenuItem;
	private javax.swing.JMenuItem deleteMenuItem;
	private javax.swing.JMenu editMenu;
	private javax.swing.JMenuItem exitMenuItem;
	private javax.swing.JMenu fileMenu;
	private javax.swing.JMenu helpMenu;
	private javax.swing.JScrollPane jScrollPaneImages;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JLabel lblTitle;
	private javax.swing.JMenuBar menuBar;
	private javax.swing.JMenuItem openMenuItem;
	private javax.swing.JMenuItem pasteMenuItem;
	private javax.swing.JMenuItem saveAsMenuItem;
	private javax.swing.JMenuItem saveMenuItem;
	private javax.swing.JTable tableExames;
	private javax.swing.JTextArea txtArea;
	private javax.swing.JTextField txtPath;
	private javax.swing.JButton readDirectoryButton;
	private javax.swing.JButton showButton;
	private javax.swing.JTabbedPane tabsPatient;
	private javax.swing.JPanel panelPatients;
	private javax.swing.JTextArea textPatients;
	private javax.swing.JPanel panelSeries;
	private javax.swing.JTextArea textSeries;
	private javax.swing.JPanel panelStudies;
	private javax.swing.JTextArea textStudies;
	private javax.swing.JPanel panelImages;
	private javax.swing.JTextArea textImages;
	
	// End of variables declaration//GEN-END:variables
}