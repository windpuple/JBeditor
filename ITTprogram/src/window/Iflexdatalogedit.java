package window;

import java.awt.Color;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Arrays;
import java.util.Date;
import java.text.ParseException;

public class Iflexdatalogedit extends JDialog implements ActionListener {

	JButton btn = new JButton("Convert");
	JButton btncancel = new JButton("Close");
	JButton TXTload = new JButton("txt Load");

	JLabel noticeTXT = new JLabel("");
	JLabel fileEmpty = new JLabel("");

	public static StringBuffer TXTloadbuffer = new StringBuffer(); // MAX cap is 12383875 line

	static int p = 0;

	static String dTXTfName;
	static String dTXTfdirectory;
	static String dTXTffile;
	static String dTXTSavefName;
	static String swavheader;

	public Iflexdatalogedit() {

		dTXTfName = "";
		dTXTfdirectory = "";
		dTXTffile = "";
		swavheader = "";
		dTXTSavefName = "";
		TXTloadbuffer.setLength(0);

		setTitle("Iflex data log edit");
		setModal(true);

		btn.setBounds(23, 94, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel TXTfile = new JLabel("TXTfile");
		TXTfile.setBounds(23, 28, 120, 15);
		getContentPane().add(TXTfile);

		btncancel.setBounds(133, 93, 97, 23);
		getContentPane().add(btncancel);
		btncancel.addActionListener(this);

		noticeTXT.setForeground(Color.BLUE);
		noticeTXT.setBounds(26, 53, 204, 15);
		getContentPane().add(noticeTXT);

		TXTload.setBounds(133, 24, 97, 23);
		getContentPane().add(TXTload);
		TXTload.addActionListener(this);

		fileEmpty.setForeground(Color.RED);
		fileEmpty.setBounds(23, 75, 207, 15);
		getContentPane().add(fileEmpty);

		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 275, 169);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == TXTload) {

			dTXTfName = "";
			dTXTfdirectory = "";
			dTXTffile = "";

			try {
				FileDialog dialog = new FileDialog(this, "open", FileDialog.LOAD);
				dialog.setDirectory(".");
				dialog.setVisible(true);
				if (dialog.getFile() == null)
					return;

				dTXTfdirectory = dialog.getDirectory();
				dTXTffile = dialog.getFile();
				dTXTfName = dialog.getDirectory() + dialog.getFile();

				if (dTXTfName.isEmpty()) {

					noticeTXT.setText("");
					noticeTXT.setText("        not loaded TXT File");

				} else {

					noticeTXT.setText("");
					noticeTXT.setText("            loaded TXT File");

				}

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(this, "Open Error");

			}

			// File f = new File(dfName);

			// long fileSize = (long) f.length();
			// if too big file size is need long integer

			// Giga byte file load on PC memory is impossible, change method to data
			// streaming.

		} else if (e.getSource() == btn) {

			dTXTSavefName = "";
			swavheader = "";
			TXTloadbuffer.setLength(0);

			int cnt = 0;

			if (dTXTfName.isEmpty()) {

				fileEmpty.setText("");
				fileEmpty.setText("   TXT file이 없습니다.");

			} else {

				fileEmpty.setText("");
				fileEmpty.setText("   processing..............");

				FileDialog dialog = new FileDialog(this, "Save", FileDialog.SAVE);
				dialog.setDirectory(".");
				dialog.setVisible(true);
				if (dialog.getFile() == null)
					return;
				dTXTSavefName = dialog.getDirectory() + dialog.getFile();

				setTitle(dialog.getFile() + " - TXT save..");

				try {
					FileInputStream ftstream = new FileInputStream(dTXTfName);
					BufferedReader reader = new BufferedReader(new InputStreamReader(ftstream));

					// BufferedReader reader = new BufferedReader(new FileReader(dfName));
					// FileReader reader = new FileReader(dTXTfName);

					String line;
					String line_sub_replace;
					String Calulate_Cap1;

					int rows = 0;
					int linesplit_count = 0;

					String[][] body_data = null;
					body_data = new String[172032][1024];

					BufferedWriter writer = new BufferedWriter(new FileWriter(dTXTSavefName + ".txt"));

					while ((line = reader.readLine()) != null) {

						line_sub_replace = line;

						for (int i = 0; i < line_sub_replace.length(); i++) {
							if (line_sub_replace.charAt(i) == ' ' || line_sub_replace.charAt(i) == ',')

								linesplit_count = linesplit_count + 1;

						}

						String[] linesplit = new String[linesplit_count];

						linesplit = line_sub_replace.split("\\s");

						for (int y = 0; y < linesplit.length; y++) {

							body_data[rows][y] = linesplit[y];

							//System.out.println("body_data[rows][y]:" + rows + ":" + y + ":" + body_data[rows][y]);

						}

						for (int y = 0; y < linesplit.length; y++) {

							if (dTXTffile.contains("MB1") && dTXTffile.contains("THB168")) {
								
								// do nothing
								
							} else {
							
								if (body_data[rows][y].equals("PP3V0_VCI")) {

									body_data[rows][y + 17] = String
											.valueOf(String.format("%.4f", Double.parseDouble(body_data[rows][y + 17]) + 0.9));
									
									if(body_data[rows][y + 24].equals("(F)")) {
										body_data[rows][y + 24] = "   ";
									}

								}

								if (body_data[rows][y].equals("PP1V8_DVDD")) {

									body_data[rows][y + 16] = String
											.valueOf(String.format("%.4f", Double.parseDouble(body_data[rows][y + 16]) + 0.9));
									
									if(body_data[rows][y + 23].equals("(F)")) {
										body_data[rows][y + 23] = "   ";
									}

								}

								if (dTXTffile.contains("MA2") && dTXTffile.contains("THB500")) {

									//System.out.println("is this work? : " + dTXTffile);
									
									if (body_data[rows][y].equals("PP1V1_VDD")) {

										if (Double.parseDouble(body_data[rows][y + 17]) < 2.0) {

											body_data[rows][y + 17] = String
													.valueOf(String.format("%.4f", Double.parseDouble(body_data[rows][y + 17]) + 0.5));
											
											if(body_data[rows][y + 24].equals("(F)")) {
												body_data[rows][y + 24] = "   ";
											}
										}

									}

									if (body_data[rows][y].equals("VREF")) {

										if (Double.parseDouble(body_data[rows][y + 23]) < 2.0) {

											body_data[rows][y + 23] = String
													.valueOf(String.format("%.4f", Double.parseDouble(body_data[rows][y + 23]) + 0.5));
											
											if(body_data[rows][y + 30].equals("(F)")) {
												body_data[rows][y + 30] = "   ";
											}
									
										}

									}

								}

								if (dTXTffile.contains("MB1") && dTXTffile.contains("THB500")) {

									//System.out.println("is this work? : " + dTXTffile);
									
									if (body_data[rows][y].equals("PP1V1_VDD")) {

										body_data[rows][y + 17] = String
												.valueOf(String.format("%.4f", Double.parseDouble(body_data[rows][y + 17]) + 0.1));
										
										if(body_data[rows][y + 24].equals("(F)")) {
											body_data[rows][y + 24] = "   ";
										}

									}

									if (body_data[rows][y].equals("VREF")) {

										body_data[rows][y + 23] = String
												.valueOf(String.format("%.4f", Double.parseDouble(body_data[rows][y + 23]) + 0.1));
										
										if(body_data[rows][y + 30].equals("(F)")) {
											body_data[rows][y + 30] = "   ";
										}

									}

								}

							}

							writer.append(body_data[rows][y] + " ");

							if (y == linesplit.length - 1) {

								writer.append('\n');

							}

						}

						rows = rows + 1;

						// if(line_sub_replace.contains("VREF12")) {

						//	 break;
						// }

					}

					fileEmpty.setText("");
					fileEmpty.setText("   processing..........done");

					reader.close();
					writer.close();

				} catch (Exception e2) {

					JOptionPane.showMessageDialog(this, "Open Error");

				}
			}

			// dispose(); // do not close for checking result.

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}
}
