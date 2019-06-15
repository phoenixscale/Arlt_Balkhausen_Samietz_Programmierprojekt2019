import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Window {

	private final int WIDTH = 900, HEIGHT = 600;
	private final String TITLE = "Routenplaner";
	private boolean isMapEmpty;

	private JFrame frame;

	public static JTextField eventWindow;
	private JTextField sourceTitle, source, targetTitle, target, distanceWindow, differenceWindow;
	private JTextArea solFilePath, queFilePath, mapFilePath;
	private JButton calculatedistanceButton, calculatedifferenceButton;

	private File map, que, sol;

	public Window() {
		isMapEmpty = true;

		frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setTitle(TITLE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addBasicPathFinder(); // y: 0 - 250
		addQueSolDifference();

		frame.setVisible(true);
	}

	private void addQueSolDifference() {
		addFileChooserMap();
		addFileChooserQue();
		addFileChooserSol();

		addCalculateDifferenceButton();
	}

	private void addBasicPathFinder() {

		sourceTitle = new JTextField("Source");
		sourceTitle.setBounds(75, 75, 100, 30);
		sourceTitle.setEditable(false);
		frame.getContentPane().add(sourceTitle);

		source = new JTextField();
		source.setBounds(200, 75, WIDTH - 275, 30);
		frame.getContentPane().add(source);

		targetTitle = new JTextField("Target");
		targetTitle.setBounds(75, 125, 100, 30);
		targetTitle.setEditable(false);
		frame.getContentPane().add(targetTitle);

		eventWindow = new JTextField("No map has been set.");
		eventWindow.setBounds(75, 237, WIDTH - 150, 30);
		eventWindow.setEditable(false);
		eventWindow.setBackground(Color.WHITE);
		frame.getContentPane().add(eventWindow);

		target = new JTextField();
		target.setBounds(200, 125, WIDTH - 275, 30);
		frame.getContentPane().add(target);

		calculatedistanceButton = new JButton("Calculate Distance");
		calculatedistanceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isMapEmpty == true) {
					eventWindow.setText("Please choose a map before calculating!");
					return;
				} else if (source.getText().equals("")) {
					eventWindow.setText("Please choose a StartNode before calculating!");
					return;
				} else if (target.getText().equals("")) {
					eventWindow.setText("Please choose a EndNode before calculating!");
					return;
				} else if (Integer.parseInt(source.getText()) > Data.getNodeAmount()
						|| Integer.parseInt(source.getText()) < 0) {
					eventWindow.setText("The StartNode you chose is not on the map!");
					return;
				} else if (Integer.parseInt(target.getText()) > Data.getEdgeAmount()
						|| Integer.parseInt(target.getText()) < 0) {
					eventWindow.setText("The EndNode you chose is not on the map!");
					return;
				}

				int sourceIn = Integer.parseInt(source.getText());
				int targetIn = Integer.parseInt(target.getText());

				long time1 = System.currentTimeMillis();
				int tempDistance = Dijkstra.setSourceAndTarget(sourceIn, targetIn);

				distanceWindow.setText("The Distance between " + sourceIn + " and " + targetIn + " is: " + tempDistance
						+ "\tCalculated in " + (System.currentTimeMillis() - time1) / 1000 + " seconds");
				Utility.addLineToFile("Distance from " + sourceIn + " to " + targetIn + " is: " + tempDistance,
						Utility.getLogFileWriter());
			}
		});
		calculatedistanceButton.setBounds(75, 185, 175, 30);
		frame.getContentPane().add(calculatedistanceButton);

		distanceWindow = new JTextField();
		distanceWindow.setBounds(275, 185, WIDTH - 350, 30);
		distanceWindow.setEditable(false);
		distanceWindow.setBackground(Color.WHITE);
		frame.getContentPane().add(distanceWindow);

	}

	private void addFileChooserMap() {
		mapFilePath = new JTextArea();
		mapFilePath.setBounds(200, 300, WIDTH - 275, 30);
		mapFilePath.setEditable(false);
		frame.getContentPane().add(mapFilePath);

		JButton mapFileChooser = new JButton("Map");
		mapFileChooser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String userDirLocation = System.getProperty("user.dir");
				File userDir = new File(userDirLocation);
				JFileChooser fileChooser = new JFileChooser(userDir);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".fmi file", new String[] { "fmi" });
				fileChooser.setFileFilter(filter);
				fileChooser.addChoosableFileFilter(filter);
				String fileName, fileType = null;

				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					map = fileChooser.getSelectedFile();
					fileName = map.getName();
					fileType = fileName.substring(fileName.length() - 4, fileName.length());

					if (fileType.equals(".fmi")) {
						mapFilePath.setText(" " + fileName);
						eventWindow.setText(" " + fileName + " is the map now.");
						isMapEmpty = false;
						Data.initialize(map);
					} else
						map = null;
				}
			}
		});

		mapFileChooser.setBounds(75, 300, 100, 30);
		frame.getContentPane().add(mapFileChooser);
	}

	private void addFileChooserQue() {
		queFilePath = new JTextArea();
		queFilePath.setBounds(200, 350, WIDTH - 275, 30);
		queFilePath.setEditable(false);
		frame.getContentPane().add(queFilePath);

		JButton queFileChooser = new JButton("Question");
		queFileChooser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String userDirLocation = System.getProperty("user.dir");
				File userDir = new File(userDirLocation);
				JFileChooser fileChooser = new JFileChooser(userDir);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".que file", new String[] { "que" });
				fileChooser.setFileFilter(filter);
				fileChooser.addChoosableFileFilter(filter);
				String fileName, fileType = null;

				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					que = fileChooser.getSelectedFile();
					fileName = que.getName();
					fileType = fileName.substring(fileName.length() - 4, fileName.length());

					if (fileType.equals(".que")) {
						queFilePath.setText(" " + fileName);
						eventWindow.setText(" " + fileName + " is the que-file now.");
					} else
						que = null;
				}
			}
		});

		queFileChooser.setBounds(75, 350, 100, 30);
		frame.getContentPane().add(queFileChooser);
	}

	private void addFileChooserSol() {
		solFilePath = new JTextArea();
		solFilePath.setBounds(200, 400, WIDTH - 275, 30);
		solFilePath.setEditable(false);
		frame.getContentPane().add(solFilePath);

		JButton solFileChooser = new JButton("Solution");
		solFileChooser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String userDirLocation = System.getProperty("user.dir");
				File userDir = new File(userDirLocation);
				JFileChooser fileChooser = new JFileChooser(userDir);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".sol file", new String[] { "sol" });
				fileChooser.setFileFilter(filter);
				fileChooser.addChoosableFileFilter(filter);
				String fileName, fileType = null;

				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					sol = fileChooser.getSelectedFile();
					fileName = sol.getName();
					fileType = fileName.substring(fileName.length() - 4, fileName.length());

					if (fileType.equals(".sol")) {
						solFilePath.setText(" " + fileName);
						eventWindow.setText(" " + fileName + " is the sol-file now.");
					} else
						sol = null;
				}
			}
		});

		solFileChooser.setBounds(75, 400, 100, 30);
		frame.getContentPane().add(solFileChooser);
	}

	private void addCalculateDifferenceButton() {

		calculatedifferenceButton = new JButton("Calculate Difference");
		calculatedifferenceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (mapFilePath.getText().equals("")) {
					eventWindow.setText("Please choose a map before calculating!");
					return;
				} else if (queFilePath.getText().equals("")) {
					eventWindow.setText("Please choose a StartNode before calculating!");
					return;
				} else if (solFilePath.getText().equals("")) {
					eventWindow.setText("Please choose a EndNode before calculating!");
					return;
				}
				long time1 = System.currentTimeMillis();

				differenceWindow.setText("The Difference between the .sol file and the calculated Results is "
						+ Benchmark.calculateDifference(map, que, sol) + "\tCalculated in "
						+ (System.currentTimeMillis() - time1) / 1000 + " seconds");
			}
		});
		calculatedifferenceButton.setBounds(75, 475, 175, 30);
		frame.getContentPane().add(calculatedifferenceButton);

		differenceWindow = new JTextField();
		differenceWindow.setBounds(275, 475, WIDTH - 350, 30);
		differenceWindow.setEditable(false);
		differenceWindow.setBackground(Color.WHITE);
		frame.getContentPane().add(differenceWindow);
	}

}
