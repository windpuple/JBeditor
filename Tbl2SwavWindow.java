/* 
 *  
 * 2018.03.26 JB.Jeon ITT(katherine) project start. 
 * 
 *  
 */

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

public class Tbl2SwavWindow extends JDialog implements ActionListener {

	JButton btn = new JButton("Convert");
	JButton btncancel = new JButton("Close");
	JButton btnimport = new JButton("import");
	JButton TBLload = new JButton("Load");

	JLabel noticeTBL = new JLabel("");
	JLabel noticepinfile = new JLabel("");
	JLabel fileEmpty = new JLabel("");

	public static String pininputbuffer;
	public static StringBuilder tblloadbuffer = new StringBuilder(); // MAX cap is 12383875 line

	static int p = 0;

	static String dTBLfName;
	static String dTBLfdirectory;
	static String dTBLffile;
	static String dTBLSavefName;
	static String swavheader;

	public Tbl2SwavWindow(JFrame frame) {
		super(frame);

		pininputbuffer = "";
		dTBLfName = "";
		dTBLfdirectory = "";
		dTBLffile = "";
		swavheader = "";
		dTBLSavefName = "";
		tblloadbuffer.setLength(0);

		setTitle("TBL to Swav converter");
		setModal(true);

		btn.setBounds(23, 153, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel importpin = new JLabel("ImportPinfile");
		importpin.setBounds(23, 35, 78, 15);
		getContentPane().add(importpin);

		JLabel TBLfile = new JLabel("TBLfile");
		TBLfile.setBounds(23, 87, 120, 15);
		getContentPane().add(TBLfile);

		btncancel.setBounds(133, 152, 97, 23);
		getContentPane().add(btncancel);
		btncancel.addActionListener(this);

		noticeTBL.setForeground(Color.BLUE);
		noticeTBL.setBounds(26, 112, 204, 15);
		getContentPane().add(noticeTBL);

		btnimport.setBounds(133, 31, 97, 23);
		getContentPane().add(btnimport);
		btnimport.addActionListener(this);

		TBLload.setBounds(133, 83, 97, 23);
		getContentPane().add(TBLload);
		TBLload.addActionListener(this);

		noticepinfile.setForeground(Color.BLUE);
		noticepinfile.setBounds(23, 62, 207, 15);
		getContentPane().add(noticepinfile);

		fileEmpty.setForeground(Color.RED);
		fileEmpty.setBounds(23, 134, 207, 15);
		getContentPane().add(fileEmpty);

		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 275, 223);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnimport) {

			pininputbuffer = "";

			FileDialog dialog = new FileDialog(this, "open", FileDialog.LOAD);
			dialog.setDirectory(".");
			dialog.setVisible(true);
			if (dialog.getFile() == null)
				return;
			String dfName = dialog.getDirectory() + dialog.getFile(); // 경로명 파일명

			File f = new File(dfName);

			int fileSize = (int) f.length();

			try {

				BufferedReader reader = new BufferedReader(new FileReader(dfName));
				// FileReader reader = new FileReader(dfName);

				int line = 0;
				int m = 0;

				char[] inputorigin = new char[fileSize];

				while ((line = reader.read()) != -1) {

					inputorigin[m] = (char) line;
					m++;
				}

				for (int i = 0; i < inputorigin.length; i++) {
					pininputbuffer += Character.toString(inputorigin[i]);
				}


				reader.close();

				if (pininputbuffer.isEmpty()) {

					noticepinfile.setText("");
					noticepinfile.setText("        not loaded Pin File");

				} else {

					noticepinfile.setText("");
					noticepinfile.setText("            loaded Pin File");

				}

				setTitle(dialog.getFile() + " - pin file");

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(this, "Open Error");

			}

		} else if (e.getSource() == TBLload) {

			dTBLfName = "";
			dTBLfdirectory = "";
			dTBLffile = "";

			try {
				FileDialog dialog = new FileDialog(this, "open", FileDialog.LOAD);
				dialog.setDirectory(".");
				dialog.setVisible(true);
				if (dialog.getFile() == null)
					return;

				dTBLfdirectory = dialog.getDirectory();
				dTBLffile = dialog.getFile();
				dTBLfName = dialog.getDirectory() + dialog.getFile();

				if (dTBLfName.isEmpty()) {

					noticeTBL.setText("");
					noticeTBL.setText("        not loaded TBL File");

				} else {

					noticeTBL.setText("");
					noticeTBL.setText("            loaded TBL File");

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

			dTBLSavefName = "";
			swavheader = "";
			tblloadbuffer.setLength(0);

			int cnt = 0;

			if (dTBLfName.isEmpty() || pininputbuffer.isEmpty()) {

				fileEmpty.setText("");
				fileEmpty.setText("   pin 혹은 TBL file이 없습니다.");

			} else {

				fileEmpty.setText("");
				fileEmpty.setText("   processing..............");

				FileDialog dialog = new FileDialog(this, "Save", FileDialog.SAVE);
				dialog.setDirectory(".");
				dialog.setVisible(true);
				if (dialog.getFile() == null)
					return;
				dTBLSavefName = dialog.getDirectory() + dialog.getFile();

				setTitle(dialog.getFile() + " - TBL save..");

				for (int i = 0; i < pininputbuffer.length(); i++) {

					if (pininputbuffer.charAt(i) == '\n') {

						cnt++;

					}

				}

				String[] tblpinlist = new String[cnt];
				String[] linesplit = new String[3];
				String linesub;

				pininputbuffer.replace("\\s", "");
				pininputbuffer.replace(",", "");
				tblpinlist = pininputbuffer.split("\r\n"); // \n만을 사용하여 split하면, \r을 지울수가 없는 오류가 있다.

				swavheader += "/* signal information */\n";
				swavheader += "version state 0 2 0;\n";
				swavheader += "date 03 10 2006;\n";
				swavheader += "source \"ITTtool\";\n";
				swavheader += "destination \"QUARTET\";\n";
				swavheader += "design \"JBJeon\";\n\n";

				for (int i = 0; i < cnt; i++) {


					swavheader += "signal \"" + tblpinlist[i] + "\" bidir1     {testchannel = \"" + (i + 1)
							+ "\"; pin = \"" + (i + 1) + "\"; }\n";

					tblpinlist[i] = "";

				}

				swavheader += "\ncontrol \"loop_pack\";\n";
				swavheader += "\npattern \"ITT_con_start\";\n";

				try {
					FileInputStream ftstream = new FileInputStream(dTBLfName);
					BufferedReader reader = new BufferedReader(new InputStreamReader(ftstream));

					// BufferedReader reader = new BufferedReader(new FileReader(dfName));
					// FileReader reader = new FileReader(dTBLfName);

					String line;
					int flag = 0;
					int loopcount = 1;

					BufferedWriter writer = new BufferedWriter(new FileWriter(dTBLSavefName));

					writer.append(swavheader);


					while ((line = reader.readLine()) != null) {

						if (line.charAt(0) == ' ' && flag == 0) {

							int p = line.indexOf("\t");
							
							line = line.substring(p+1);
							
							line += " >ts0 cy0;";

							tblloadbuffer.append(line);
							tblloadbuffer.append('\n');

							writer.append(tblloadbuffer.toString());

							tblloadbuffer.setLength(0);

						} else if (line.contains("repeat") && flag == 0) {

							linesplit = line.split("\\s");
							linesplit[1] = linesplit[1].replace(":", "");
							
							int p = line.indexOf("\t");
							
							line = line.substring(p+1);
							

							line += " >ts0 cy0;";
							
							tblloadbuffer.append(line);
							tblloadbuffer.append('\n');
							
							System.out.println("linsplit1 "+Integer.parseInt(linesplit[1]));
							
							for (int j = 0; j < Integer.parseInt(linesplit[1]); j++) {

								writer.append(tblloadbuffer.toString());
								
								System.out.println("repeat count "+j);
								
							}
							
							tblloadbuffer.setLength(0);

						} else if (line.contains("loop") || flag == 1) { // this part is extract loop sequence but as
																			// dont know real char pattern. useless
																			// until update.

							flag = 1;

							linesplit = line.split("\\s");
							linesplit[1] = linesplit[1].replace(":", "");

							int p = line.indexOf("\t");
							
							line = line.substring(p+1);

							line += " >ts0 cy0;";
							
							loopcount = Integer.parseInt(linesplit[1]);

							tblloadbuffer.append(line);
							tblloadbuffer.append('\n');

						} else if (line.contains("endloop") && flag == 1) {

							flag = 0;

							int p = line.indexOf("\t");
							
							line = line.substring(p+1);

							line += " >ts0 cy0;";

							tblloadbuffer.append(line);
							tblloadbuffer.append('\n');
							
							for(int i = 0; i < loopcount; i++) {
							
							writer.append(tblloadbuffer.toString());

							}
							
							loopcount = 1;
							tblloadbuffer.setLength(0);

						} else if (line.contains("repeat") && flag == 1) {

							linesplit = line.split("\\s");
							linesplit[1] = linesplit[1].replace(":", "");
							
							int p = line.indexOf("\t");
							
							line = line.substring(p+1);

							line += " >ts0 cy0;";

							for (int j = 0; j < Integer.parseInt(linesplit[1]); j++) {

								tblloadbuffer.append(line);
								tblloadbuffer.append('\n');

							}

						}

					}

					System.out.println("exit sequence");
					
					writer.append("pattern_end \"ITT_con_end\";");

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
