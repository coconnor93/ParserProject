package testProj;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.Iterator;
import javax.swing.*;
import testProj.ParserTest.Commit;

public class GUI extends JFrame {

	private String[] filePaths = { "C:\\Users\\Smithers\\gitdemo\\jsoniterator\\log.txt",
			"C:\\Users\\Smithers\\gitdemo\\interviews\\log.txt" };
	private int height = 800;
	private int width = 800;
	private JComboBox<String> projects = new JComboBox<String>(filePaths);
	private String spiralURL = "/images/spiral.png", waterfallURL = "/images/waterfall.jpg";
	private int align = 10, hgap = 10, vgap = 100;
	FlowLayout flowLayout = new FlowLayout(align, hgap, vgap);
	private ParserTest parse;
	private SwingTest swing = null;
	private Iterator<Commit> commitIterator;
	private String selectedProject;

	public GUI() {
		initUI();
	}

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

			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox<String> combo = (JComboBox<String>) event.getSource();
				selectedProject = (String) combo.getSelectedItem();
				if (selectedProject == "C:\\Users\\Smithers\\gitdemo\\jsoniterator\\log.txt") {
					try {
						parse.readFile(selectedProject);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				} else if (selectedProject == "C:\\Users\\Smithers\\gitdemo\\interviews\\log.txt") {
					try {
						parse.readFile(selectedProject);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});
		JLabel spiral = new JLabel();
		spiral.setIcon(new ImageIcon(getClass().getResource(spiralURL)));
		panel.add(spiral);
		spiral.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				System.out.println(me);
			}
		});

		JLabel waterfall = new JLabel();
		waterfall.setIcon(new ImageIcon(getClass().getResource(waterfallURL)));
		panel.add(waterfall);
		waterfall.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				System.out.println(me);
			}
		});
		panel.validate();
		panel.repaint();
	}

	public static void main(String[] args) {
		GUI test = new GUI();
	}
}
