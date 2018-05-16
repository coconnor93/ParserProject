package testProj;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import javax.swing.*;

/**
 * The user interface that contains all the functionality of the system
 * @author Christopher O'Connor
 * @version 1.0
 */
public class GUI extends JFrame {
	
	/**
	 * Declaration variables for the UI, includes:
	 * The ArrayList of filePaths the user will have the choice of.
	 * Variables of window size and layout of UI.
	 * Images included within UI.
	 * Instance of Parser class and Visualisation interface.
	 * selectedProject the project that the user selected from the drop-down list.
	 */

	private String[] filePaths = { "C:\\Users\\Smithers\\gitdemo\\jsoniterator\\log.txt",
			"C:\\Users\\Smithers\\gitdemo\\interviews\\log.txt", "C:\\Users\\Smithers\\gitdemo\\boon\\log.txt",
			"C:\\Users\\Smithers\\gitdemo\\Java_1\\log.txt"};
	private int height = 800;
	private int width = 800;
	private JComboBox<String> projects = new JComboBox<String>(filePaths);
	private String spiralURL = "/images/spiral.png", waterfallURL = "/images/waterfall.jpg";
	private int align = 10, hgap = 10, vgap = 100;
	FlowLayout flowLayout = new FlowLayout(align, hgap, vgap);
	private Parser parse = new Parser();
	private Visualisation vis;
	private String selectedProject;
	
	/**
	 * Constructor that calls the initUI method that contains the design of the UI
	 */

	public GUI() {
		initUI();
	}
	
	/**
	 * Method that creates the main frame and panel that the functionality of the UI will contain.
	 */

	public void initUI() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();

		frame.setContentPane(panel);
		frame.setTitle("Project Display");
		frame.pack();
		frame.setSize(height, width);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		panel.setBackground(Color.PINK);
		panel.setLayout(flowLayout);
		flowLayout.setAlignment(FlowLayout.CENTER);
		JLabel title = new JLabel("SOFTWARE VISUALISATION");
		title.setFont(new Font("Helvetica", Font.PLAIN, 48));
		panel.add(title);
		JLabel projSelection = new JLabel("Select the project you wish to use: ");
		panel.add(projSelection);
		projSelection.setFont(new Font("Helvetica", Font.ITALIC, 16));

		panel.add(projects);
		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		projects.setForeground(Color.BLUE);
		projects.setEditable(false);
		projects.addActionListener(new ActionListener() {
			/**
			 * Method that ensures the project selected by the user from the drop-down
			 * list will be the one queried through the selected visualisation technique
			 */
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox<String> combo = (JComboBox<String>) event.getSource();
				selectedProject = (String) combo.getSelectedItem();
			}
		});
		JLabel spiral = new JLabel();
		spiral.setIcon(new ImageIcon(getClass().getResource(spiralURL)));
		panel.add(spiral);
		spiral.addMouseListener(new MouseAdapter() {
			/**
			 * Method that ensures that the spiral visualisation will execute when
			 * the user selects a project and the spiral image
			 */
			public void mousePressed(MouseEvent me) {
				vis = new ProcessSpiral();
				vis.setFilepath(selectedProject);
				frame.setVisible(false);
				vis.Start();
			}
		});

		JLabel waterfall = new JLabel();
		waterfall.setIcon(new ImageIcon(getClass().getResource(waterfallURL)));
		panel.add(waterfall);
		waterfall.addMouseListener(new MouseAdapter() {
			/**
			 * Method that ensures that the waterfall visualisation will execute when
			 * the user selects a project and the waterfall image
			 */
			public void mousePressed(MouseEvent me) {
				vis = new ProcessWaterfall();
				vis.setFilepath(selectedProject);
				frame.setVisible(false);
				vis.Start();
			}
		});
		panel.validate();
		panel.repaint();
	}
	
	/**
	 * Main method that instigates a new instance of the GUI
	 * @param args
	 */

	public static void main(String[] args) {
		new GUI();
	}
}
