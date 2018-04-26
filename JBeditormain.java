/* 
 *  
 * 2018.03.26 JB.Jeon ITT(katherine) project start.  
 * 
 *  
 */

package window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class JBeditormain extends JFrame implements ActionListener {
	JButton btnCopy = new JButton("Copy");
	JButton btnPaste = new JButton("Paste");
	JButton btnCut = new JButton("Cut");
	JButton btnDel = new JButton("Delete");
	JTextArea txtJBeditormain = new JTextArea("", 100, 400);
	String copyText;

	JMenuItem mnuNew, mnuSave, mnuOpen, mnuDBE, mnuMAP,  mnuExit;
	JMenuItem mnuCopy, mnuPaste, mnuCut, mnuDel, mnuSbinedit, mnurotation;
	JMenuItem mnulinear, mnusine, mnutbl2swav;
	JMenuItem mnuAbout, mnuEtc1, mnuEtc2;

	// 팝업 메뉴
	JPopupMenu popup;
	JMenuItem m_white, m_blue, m_yellow;

	public static String Finalarray = "";
	//public static String txtJBeditormainbuffer = "";
	public static StringBuilder txtJBeditormainbuffer = new StringBuilder();
	

	public JBeditormain() {
		super("Intergrated Test Tool");

		txtJBeditormainbuffer.setLength(0);
		
		initLayout();
		menuLayout();
		setBounds(200, 100, 800, 900);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initLayout() {
		JPanel panel = new JPanel();
		panel.add(btnCopy);
		panel.add(btnPaste);
		panel.add(btnCut);
		panel.add(btnDel);
		getContentPane().add("South", panel);
		getContentPane().add("Center", txtJBeditormain);
		txtJBeditormain.setFont(new Font("궁서체", Font.PLAIN, 13));
		txtJBeditormain.setWrapStyleWord(true);
		txtJBeditormain.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(txtJBeditormain);
		getContentPane().add(scrollPane);

		btnCopy.addActionListener(this);
		btnPaste.addActionListener(this);
		btnCut.addActionListener(this);
		btnDel.addActionListener(this);
	}

	private void menuLayout() {
		JMenuBar menuBar = new JMenuBar();

		JMenu mnuFile = new JMenu("File");
		mnuNew = new JMenuItem("New");
		mnuOpen = new JMenuItem("Open...");
		mnuSave = new JMenuItem("Save...");
		mnuMAP = new JMenuItem("Back2Map...");
		mnuDBE = new JMenuItem("Map2DBE...");
		mnuExit = new JMenuItem("Exit");
		mnuFile.add(mnuNew);
		mnuFile.add(mnuOpen);
		mnuFile.add(mnuSave);
		mnuFile.addSeparator();
		mnuFile.add(mnuMAP);
		mnuFile.add(mnuDBE);
		mnuFile.addSeparator();
		mnuFile.add(mnuExit);

		JMenu mnuEdit = new JMenu("Edit");
		mnuCopy = new JMenuItem("Copy");
		mnuPaste = new JMenuItem("paste");
		mnuCut = new JMenuItem("Cut");
		mnuDel = new JMenuItem("Delete");
		mnuSbinedit = new JMenuItem("MapSbinEdit");
		mnurotation = new JMenuItem("MapRotaion");
		mnuEdit.add(mnuCopy);
		mnuEdit.add(mnuPaste);
		mnuEdit.add(mnuCut);
		mnuEdit.add(mnuDel);
		mnuEdit.addSeparator();
		mnuEdit.add(mnuSbinedit);
		mnuEdit.add(mnurotation);

		JMenu mnuGeneration = new JMenu("Generate");
		mnulinear = new JMenuItem("Linear");
		mnusine = new JMenuItem("Sine");

		mnuGeneration.add(mnulinear);
		mnuGeneration.add(mnusine);

		JMenu mnuconversion = new JMenu("Conversion");
		mnutbl2swav = new JMenuItem("tbl2swav");

		mnuconversion.add(mnutbl2swav);

		JMenu mnuHelp = new JMenu("help");
		mnuAbout = new JMenuItem("ITT About...");
		mnuHelp.add(mnuAbout);
		JMenu mnuEtc = new JMenu("other");
		mnuEtc1 = new JMenuItem("calculator");
		mnuEtc2 = new JMenuItem("notepad");
		mnuEtc.add(mnuEtc1);
		mnuEtc.add(mnuEtc2);
		mnuHelp.add(mnuEtc);

		menuBar.add(mnuFile);
		menuBar.add(mnuEdit);
		menuBar.add(mnuGeneration);
		menuBar.add(mnuconversion);
		menuBar.add(mnuHelp);

		setJMenuBar(menuBar);

		mnuNew.addActionListener(this);
		mnuSave.addActionListener(this);
		mnuOpen.addActionListener(this);
		mnuMAP.addActionListener(this);
		mnuDBE.addActionListener(this);
		mnuExit.addActionListener(this);
		mnuCopy.addActionListener(this);
		mnuPaste.addActionListener(this);
		mnuCut.addActionListener(this);
		mnuDel.addActionListener(this);
		mnuSbinedit.addActionListener(this);
		mnurotation.addActionListener(this);
		mnulinear.addActionListener(this);
		mnusine.addActionListener(this);
		mnutbl2swav.addActionListener(this);
		mnuAbout.addActionListener(this);
		mnuEtc1.addActionListener(this);
		mnuEtc2.addActionListener(this);

		// 팝업 메뉴(우클릭시 출현)
		popup = new JPopupMenu();
		JMenu m_col = new JMenu("Chage color");
		m_white = new JMenuItem("White");
		m_blue = new JMenuItem("Blue");
		m_yellow = new JMenuItem("Yellow");
		m_col.add(m_white);
		m_col.add(m_blue);
		m_col.add(m_yellow);
		m_white.addActionListener(this);
		m_blue.addActionListener(this);
		m_yellow.addActionListener(this);
		popup.add(m_col); // 팝업에 주는것
		txtJBeditormain.add(popup); // 팝업 메뉴 추가

		txtJBeditormain.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				if (e.getModifiers() == MouseEvent.BUTTON3_MASK) { // 왼쪽이 1 가운데가 2 오른쪽이 3 BUTTON3_MASK - 오른쪽 버튼

					popup.show(txtJBeditormain, e.getX(), e.getY());
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCopy || e.getSource() == mnuCopy) {

			copyText = txtJBeditormain.getSelectedText();

		} else if (e.getSource() == btnPaste || e.getSource() == mnuPaste) {

			int position = txtJBeditormain.getCaretPosition();
			txtJBeditormain.insert(copyText, position);

		} else if (e.getSource() == btnCut || e.getSource() == mnuCut) {
			copyText = txtJBeditormain.getSelectedText();
			int start = txtJBeditormain.getSelectionStart();
			int end = txtJBeditormain.getSelectionEnd();
			txtJBeditormain.replaceRange("", start, end);

		} else if (e.getSource() == btnDel || e.getSource() == mnuDel) {
			int start = txtJBeditormain.getSelectionStart();
			int end = txtJBeditormain.getSelectionEnd();
			txtJBeditormain.replaceRange("", start, end); // 공백으로 치환하는 법 replaceRange("", 시작위치, 끝위치)

		} else if (e.getSource() == mnuSbinedit) {

			new Sbinwindow(this);

			txtJBeditormain.setText("");
			txtJBeditormain.setText(Sbinwindow.txtSbinbuffer);

			Finalarray = "";
			Finalarray = Sbinwindow.txtSbinbuffer;
			Sbinwindow.txtSbinbuffer = "";

		} else if (e.getSource() == mnurotation) {

			new Rotationwindow(this);

			txtJBeditormain.setText("");
			txtJBeditormain.setText(Rotationwindow.rotationfinalbuffer);

			Finalarray = "";
			Finalarray = Rotationwindow.rotationfinalbuffer;
			Rotationwindow.rotationfinalbuffer = "";

		} else if (e.getSource() == mnuNew) {
			txtJBeditormain.setText("");
			setTitle("ITT");
			Finalarray = "";

		} else if (e.getSource() == mnuOpen) {
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
				txtJBeditormain.setText("");
				String line;
				int m = 0;

				char[] origin = new char[fileSize];

				System.out.println("1");
				
				while ((line = reader.readLine()) != null) {

					txtJBeditormainbuffer.append(line);
					txtJBeditormainbuffer.append('\n');
				}
				
				
				/*
				while ((line = reader.read()) != -1) {

					// txtJBeditormain.append(linechar + "\n"); // txtJBeditormain에 추가 line 단위 처리시
					origin[m] = (char) line; // txtJBeditormain에 추가 char 단위 처리시
					m++;
				}
				*/
				

				System.out.println("2");
				
				/*
				for (int i = 0; i < origin.length; i++) {
					txtJBeditormainbuffer += Character.toString(origin[i]);
				}
				*/
				

				System.out.println("3");
				
				txtJBeditormain.setText(txtJBeditormainbuffer.toString());
				
				System.out.println("4");
				
				Finalarray = "";
				Finalarray = txtJBeditormainbuffer.toString();
				//txtJBeditormainbuffer = "";
				txtJBeditormainbuffer.setLength(0);
				
				System.out.println("5");
				
				reader.close();

				setTitle(dialog.getFile() + " - ITT");
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Open Error");
			}

		} else if (e.getSource() == mnuSave) {

			FileDialog dialog = new FileDialog(this, "Save", FileDialog.SAVE);
			dialog.setDirectory(".");
			dialog.setVisible(true);
			if (dialog.getFile() == null)
				return;
			String dfName = dialog.getDirectory() + dialog.getFile();

			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(dfName));
				writer.write(txtJBeditormain.getText());
				writer.close();

				setTitle(dialog.getFile() + " - text Save");
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Save error");
			}

		} else if (e.getSource() == mnuMAP) {

			new MAPwindow();
			
		} else if (e.getSource() == mnuDBE) {

			new DBEwindow();
			
		} else if (e.getSource() == mnulinear) {

			new linearwindow(this);

			txtJBeditormain.setText("");
			txtJBeditormain.setText(linearwindow.linearfinalbuffer);

			Finalarray = "";
			Finalarray = linearwindow.linearfinalbuffer;
			linearwindow.linearfinalbuffer = "";

		} else if (e.getSource() == mnusine) {

			new sinewindow(this);

			txtJBeditormain.setText("");
			txtJBeditormain.setText(sinewindow.sinefinalbuffer);

			Finalarray = "";
			Finalarray = sinewindow.sinefinalbuffer;
			sinewindow.sinefinalbuffer = "";

		} else if (e.getSource() == mnutbl2swav) {

			new Tbl2SwavWindow(this);

		} else if (e.getSource() == mnuExit) {

			System.exit(0);

		} else if (e.getSource() == mnuAbout) {

			new JBeditorabout(this);

		} else if (e.getSource() == mnuEtc1) {
			try {
				Runtime runtime = Runtime.getRuntime();
				runtime.exec("calc.exe");
			} catch (Exception e2) {

			}

		} else if (e.getSource() == mnuEtc2) {
			try {

				Runtime runtime = Runtime.getRuntime();
				runtime.exec("notepad.exe");

			} catch (Exception e2) {

			}

			// 우클릭시에 나타나는 팝업메뉴에 대한 설정
		} else if (e.getSource() == m_white) { // 팝업메뉴
			txtJBeditormain.setBackground(Color.WHITE);
		} else if (e.getSource() == m_blue) { // 팝업메뉴
			txtJBeditormain.setBackground(Color.BLUE);
		} else if (e.getSource() == m_yellow) { // 팝업메뉴
			txtJBeditormain.setBackground(Color.YELLOW);
		}
		txtJBeditormain.requestFocus();
	}

	public static void main(String[] args) {
		new JBeditormain();
	}

}
