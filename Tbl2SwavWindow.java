package window;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Tbl2SwavWindow extends JDialog implements ActionListener {

	JButton btn = new JButton("확인");
	JButton btncancel = new JButton("\uCDE8\uC18C");
	JButton btnimport = new JButton("import");
	JButton TBLload = new JButton("Load");

	JLabel noticeTBL = new JLabel("");
	JLabel noticepinfile = new JLabel("");
	JLabel fileEmpty = new JLabel("");

	public static String tbl2swavfinalbuffer;
	public static String pininputbuffer;
	public static StringBuilder tblloadbuffer = new StringBuilder();

	public Tbl2SwavWindow(JFrame frame) {
		super(frame);

		tbl2swavfinalbuffer = "";
		pininputbuffer = "";
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

				// System.out.print(pininputbuffer);

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

			FileDialog dialog = new FileDialog(this, "open", FileDialog.LOAD);
			dialog.setDirectory(".");
			dialog.setVisible(true);
			if (dialog.getFile() == null)
				return;
			String dfName = dialog.getDirectory() + dialog.getFile(); 

			File f = new File(dfName);

			int fileSize = (int) f.length();

			try {

				BufferedReader reader = new BufferedReader(new FileReader(dfName));
				// FileReader reader = new FileReader(dfName);

				String line;

				char[] tblorign = new char[fileSize];

				while ((line = reader.readLine()) != null) {

					tblloadbuffer.append(line);
					tblloadbuffer.append('\n');

				}

				reader.close();

				if (tblloadbuffer.length() == 0) {

					noticeTBL.setText("");
					noticeTBL.setText("        not loaded TBL File");

				} else {

					noticeTBL.setText("");
					noticeTBL.setText("            loaded TBL File");

				}

				setTitle(dialog.getFile() + " - TBL file");

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(this, "Open Error");

			}

		}

		if (e.getSource() == btn) {

			int cnt = 0;

			if (tblloadbuffer.length() == 0 || pininputbuffer.isEmpty()) {

				fileEmpty.setText("");
				fileEmpty.setText("   pin 혹은 TBL file이 없습니다.");

			} else {

				//System.out.print(tblloadbuffer);
				
				for (int i = 0; i < tblloadbuffer.length(); i++) {

					if (tblloadbuffer.charAt(i) == '\n') {

						cnt++;

					}

				}

				System.out.println("cnt 값:"+cnt);
					
				
				String[] tbllinebuffer = new String[cnt];
				
				for(int i = 0; i < cnt; i++) {
					
					tbllinebuffer[i] = "";
					
				}
				
				//tbllinebuffer[1].append("안녕하세요");
				
				//System.out.println("tbllinebuffer :"+tbllinebuffer[1].toString());
				
							
				int m =0;
				
				for (int i = 0; i < tbllinebuffer.length ; i++) {

					for (int j = 0; j < tblloadbuffer.length(); j++) {

						tbllinebuffer[i] += (tblloadbuffer.charAt(m));
						
						if (tblloadbuffer.charAt(m) == '\n')
							break;
						System.out.println(tblloadbuffer.charAt(m));
						m++;
						
						
						
						
					}

					//System.out.print(tbllinebuffer[i]);
					
				}

				
				

				
			
			}


				
		
			
			// tbl2swavfinalbuffer = rotation90.resultbuffer;

			// dispose();

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}
}