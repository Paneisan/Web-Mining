import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JTabbedPane;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Button;

import javax.swing.JMenuBar;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.GroupLayout;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;

import java.awt.GridLayout;

import javax.swing.BoxLayout;

import java.awt.Panel;

import javax.swing.border.CompoundBorder;

import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;
import javax.swing.JScrollPane;

import java.awt.TextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;


public class GUI extends JFrame {
	
	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {}
	}
	
	public TextField textField_1;
	public JScrollPane scrollPane_2;
	public JPanel contentPane;
	public JTextField textField;
	public JTable jTable1;
	public TableModel jTable1Model;
	public JButton btnStart;
	public JButton btnCancel;
	public JPanel jPanel2;
	public JTextArea jTextArea1;
	public JProgressBar progressBar;
	public JLabel lblNewLabel_1;
	public JLabel lblNewLabel_2;
	public JLabel lblNewLabel_3;
	public Attribute [] attributesArray;
	public DataInstance [] dataInstancesArray;
	public CrossValidation cv;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("Web Page Classification Using an Ant Colony Algorithm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 858, 597);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 396, 63);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Select the dataset folder");
		lblNewLabel.setBounds(51, 11, 131, 14);
		panel.add(lblNewLabel);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		
		textField_1 = new TextField();
		textField_1.setBounds(10, 26, 254, 27);
		panel.add(textField_1);
		
		JButton button = new JButton("Browse");
		button.setBounds(286, 26, 86, 23);
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				final JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showOpenDialog(GUI.this);
				if(returnVal == 0){
					File filein = fc.getSelectedFile();
					textField_1.setText((String)filein.getPath());
					DataSet ds=new DataSet();
					File ifile = null;
					try {
						ifile = ds.InputConvert((String)filein.getPath());
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Input myFileReader = new Input(ifile);
					if(myFileReader.fileIsOk()){
						attributesArray = myFileReader.getAttributesArray();
						dataInstancesArray = myFileReader.getDataInstancesArray();

						lblNewLabel_1.setText(myFileReader.getRelation());
						lblNewLabel_3.setText(String.valueOf(myFileReader.getAttributesNo()));
						lblNewLabel_2.setText(String.valueOf(myFileReader.getInstancesNo()));
						setTableAtt1(myFileReader.getAttributesArray());
						btnStart.setEnabled(true);
					}
				}
			}
		});
		
		progressBar = new JProgressBar();
		progressBar.setBounds(0, 543, 842, 16);
		getContentPane().add(progressBar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Information about Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(429, 11, 403, 63);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblRelation = new JLabel("Relation :");
		lblRelation.setBounds(20, 21, 55, 14);
		panel_1.add(lblRelation);
		
		lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setBounds(85, 21, 125, 14);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblTotalAtributes = new JLabel("Dataset Size :");
		lblTotalAtributes.setBounds(20, 38, 97, 14);
		panel_1.add(lblTotalAtributes);
		
		lblNewLabel_2 = new JLabel();
		lblNewLabel_2.setBounds(114, 38, 108, 14);
		panel_1.add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Attributes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 97, 292, 399);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblTotalAttibutes = new JLabel("Total Attibutes :");
		lblTotalAttibutes.setBounds(10, 32, 99, 14);
		panel_2.add(lblTotalAttibutes);
		
		lblNewLabel_3 = new JLabel();
		lblNewLabel_3.setBounds(119, 32, 96, 14);
		panel_2.add(lblNewLabel_3);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 72, 272, 320);
		panel_2.add(scrollPane_2);
		
		btnStart = new JButton("Start");
		btnStart.setBounds(45, 504, 89, 23);
		getContentPane().add(btnStart);
		btnStart.setEnabled(false);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnCancel.setEnabled(true);
				cv = new CrossValidation(GUI.this);

				cv.setAttributesArray(attributesArray);
				cv.setDataInstancesArray(dataInstancesArray);

				cv.start();
			}
		});
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(312, 85, 520, 458);
		getContentPane().add(scrollPane_1, new GridBagConstraints(1,0,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(5, 2, 0, 3),0,0));
		
		scrollPane_1.setBorder(BorderFactory.createTitledBorder("Output"));
		{
		jTextArea1 = new JTextArea();
		jTextArea1.setEditable(false);
		jTextArea1.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		scrollPane_1.setViewportView(jTextArea1);
		}
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(160, 504, 89, 23);
		getContentPane().add(btnCancel);
		btnCancel.setEnabled(false);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cv.stop();
			}
		});
		
	}
	public void setTableAtt1(Attribute [] attributesArray){
		String numberAndName[][] = new String[attributesArray.length][2];
		for(int n=0; n < attributesArray.length; n++){
			numberAndName[n][0] = new Integer(n + 1).toString();
			numberAndName[n][1] = attributesArray[n].getAttributeName();
		}

		jTable1Model = new MyTableModel(
			numberAndName,
			new String[] { "#", "Name" });
		jTable1 = new JTable();
		scrollPane_2.setViewportView(jTable1);
		jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable1.setModel(jTable1Model);
	}
	class MyTableModel extends DefaultTableModel{
	    /**
		 *
		 */
		public static final long serialVersionUID = 1L;
		public MyTableModel(Object[][] data, Object[] columnNames) {
	        setDataVector(data, columnNames);
	    }
		public boolean isCellEditable(int row, int column)
	    {
	        return false;
	    }
	}
}
