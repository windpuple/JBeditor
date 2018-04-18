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

	// 그러므로 메뉴아이템을 여기다가 메뉴
	JMenuItem mnuNew, mnuSave, mnuOpen, mnuDBE, mnuExit;
	JMenuItem mnuCopy, mnuPaste, mnuCut, mnuDel, mnuSbinedit, mnurotation;
	JMenuItem mnulinear, mnusine, mnutbl2swav;
	JMenuItem mnuAbout, mnuEtc1, mnuEtc2;

	// 팝업 메뉴
	JPopupMenu popup;
	JMenuItem m_white, m_blue, m_yellow;

	public static String Finalarray = "";
	public static String txtJBeditormainbuffer = "";

	public JBeditormain() {
		super("Intergrated Test Tool");

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

	private void menuLayout() { // 메뉴아이템은 여기서 만들고
		JMenuBar menuBar = new JMenuBar();

		JMenu mnuFile = new JMenu("File"); // 주메뉴
		mnuNew = new JMenuItem("New"); // 부메뉴
		mnuOpen = new JMenuItem("Open...");
		mnuSave = new JMenuItem("Save...");
		mnuDBE = new JMenuItem("Create DBE...");
		mnuExit = new JMenuItem("Exit");
		mnuFile.add(mnuNew);
		mnuFile.add(mnuOpen);
		mnuFile.add(mnuSave);
		mnuFile.add(mnuDBE);
		mnuFile.addSeparator(); // 구분선
		mnuFile.add(mnuExit);

		JMenu mnuEdit = new JMenu("Edit"); // 주메뉴
		mnuCopy = new JMenuItem("Copy");
		mnuPaste = new JMenuItem("paste");
		mnuCut = new JMenuItem("Cut");
		mnuDel = new JMenuItem("Delete");
		mnuSbinedit = new JMenuItem("SbinEdit");
		mnurotation = new JMenuItem("MapRotaion");
		mnuEdit.add(mnuCopy);
		mnuEdit.add(mnuPaste);
		mnuEdit.add(mnuCut);
		mnuEdit.add(mnuDel);
		mnuEdit.add(mnuSbinedit);
		mnuEdit.add(mnurotation);

		JMenu mnuGeneration = new JMenu("Generate"); // 주메뉴
		mnulinear = new JMenuItem("Linear");
		mnusine = new JMenuItem("Sine");

		mnuGeneration.add(mnulinear);
		mnuGeneration.add(mnusine);
		
		JMenu mnuconversion = new JMenu("Conversion"); // 주메뉴
		mnutbl2swav = new JMenuItem("tbl2swav");
		
		mnuconversion.add(mnutbl2swav);
		
		

		JMenu mnuHelp = new JMenu("help");
		mnuAbout = new JMenuItem("ITT About...");
		mnuHelp.add(mnuAbout);
		JMenu mnuEtc = new JMenu("other"); // 부메뉴에 부메뉴
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

		setJMenuBar(menuBar); // Frame에 메뉴바 장착

		mnuNew.addActionListener(this); // 메뉴에 리스너 장착
		mnuSave.addActionListener(this);
		mnuOpen.addActionListener(this);
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
				// 오른쪽 버튼 클릭 시 ...
				if (e.getModifiers() == MouseEvent.BUTTON3_MASK) { // 왼쪽이 1 가운데가 2 오른쪽이 3 BUTTON3_MASK - 오른쪽 버튼
					// System.out.println("반응");
					popup.show(txtJBeditormain, e.getX(), e.getY());
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) { // 메뉴아이템 실행은 여기
		// 복사
		if (e.getSource() == btnCopy || e.getSource() == mnuCopy) {
			// copyText = txtJBeditormain.getText(); // 전체가 다 복사가 된다
			copyText = txtJBeditormain.getSelectedText();
			// System.out.println(copyText);

			// 붙여넣기
		} else if (e.getSource() == btnPaste || e.getSource() == mnuPaste) {
			// txtJBeditormain.setText(copyText);
			int position = txtJBeditormain.getCaretPosition(); // 위치 잡기
			txtJBeditormain.insert(copyText, position); // append 추가 insert 삽입

			// 잘라내기
		} else if (e.getSource() == btnCut || e.getSource() == mnuCut) {
			copyText = txtJBeditormain.getSelectedText();
			int start = txtJBeditormain.getSelectionStart();
			int end = txtJBeditormain.getSelectionEnd();
			txtJBeditormain.replaceRange("", start, end);

			// 지우기
		} else if (e.getSource() == btnDel || e.getSource() == mnuDel) {
			int start = txtJBeditormain.getSelectionStart();
			int end = txtJBeditormain.getSelectionEnd();
			txtJBeditormain.replaceRange("", start, end); // 공백으로 치환하는 법 replaceRange("", 시작위치, 끝위치)

			// Sbin edit
		} else if (e.getSource() == mnuSbinedit) {

			new Sbinwindow(this);

			txtJBeditormain.setText("");
			txtJBeditormain.setText(Sbinwindow.txtSbinbuffer);
			System.out.print(Sbinwindow.txtSbinbuffer);

			Finalarray = "";
			Finalarray = Sbinwindow.txtSbinbuffer;
			Sbinwindow.txtSbinbuffer = "";

		} else if (e.getSource() == mnurotation) {

			new Rotationwindow(this);

			txtJBeditormain.setText("");
			txtJBeditormain.setText(Rotationwindow.rotationfinalbuffer);
			System.out.print(Rotationwindow.rotationfinalbuffer);

			Finalarray = "";
			Finalarray = Rotationwindow.rotationfinalbuffer;
			Rotationwindow.rotationfinalbuffer = "";

		} else if (e.getSource() == mnuNew) { // 새문서
			txtJBeditormain.setText("");
			setTitle("ITT");
			Finalarray = "";

		} else if (e.getSource() == mnuOpen) { // 열기
			FileDialog dialog = new FileDialog(this, "open", FileDialog.LOAD);
			dialog.setDirectory("."); // .은 지금폴더
			dialog.setVisible(true); // 박스는 그냥 틀이고
			if (dialog.getFile() == null)
				return;
			String dfName = dialog.getDirectory() + dialog.getFile(); // 경로명 파일명

			File f = new File(dfName);

			int fileSize = (int) f.length();

			try {
				BufferedReader reader = new BufferedReader(new FileReader(dfName));
				// FileReader reader = new FileReader(dfName);
				txtJBeditormain.setText("");
				int line = 0;
				int m = 0;

				char[] origin = new char[fileSize];

				while ((line = reader.read()) != -1) { // 읽어온 문서의 줄이 없어지면

					// txtJBeditormain.append(linechar + "\n"); // txtJBeditormain에 추가 line 단위 처리시
					origin[m] = (char) line; // txtJBeditormain에 추가 char 단위 처리시
					m++;
				}

				for (int i = 0; i < origin.length; i++) {
					txtJBeditormainbuffer += Character.toString(origin[i]);
				}

				txtJBeditormain.setText(txtJBeditormainbuffer);
				System.out.print(txtJBeditormainbuffer);

				Finalarray = "";
				Finalarray = txtJBeditormainbuffer;
				txtJBeditormainbuffer = "";

				reader.close();

				setTitle(dialog.getFile() + " - ITT");
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Open Error");
			}

			// 저장
		} else if (e.getSource() == mnuSave) {
			// 파일 작업을 위한 경로명 및 파일명 얻기
			FileDialog dialog = new FileDialog(this, "Save", FileDialog.SAVE);
			dialog.setDirectory("."); // .은 지금폴더
			dialog.setVisible(true); // 박스는 그냥 틀이고
			if (dialog.getFile() == null)
				return; // 이걸빼면 취소를 해도 저장이됨
			String dfName = dialog.getDirectory() + dialog.getFile(); // 경로명 파일명
			// System.out.println(dfName);
			// 실제 저장은 여기에서
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(dfName));
				writer.write(txtJBeditormain.getText());
				writer.close();

				setTitle(dialog.getFile() + " - text Save");
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Save error");
			}

			// 종료
		} else if (e.getSource() == mnuDBE) {

			new DBEwindow();

			// txtJBeditormainbuffer = "";

		} else if (e.getSource() == mnulinear) {

			new linearwindow(this);

			txtJBeditormain.setText("");
			txtJBeditormain.setText(linearwindow.linearfinalbuffer);
			System.out.print(linearwindow.linearfinalbuffer);

			Finalarray = "";
			Finalarray = linearwindow.linearfinalbuffer;
			linearwindow.linearfinalbuffer = "";

		} else if (e.getSource() == mnusine) {

			new sinewindow(this);

			txtJBeditormain.setText("");
			txtJBeditormain.setText(sinewindow.sinefinalbuffer);
			System.out.print(sinewindow.sinefinalbuffer);

			Finalarray = "";
			Finalarray = sinewindow.sinefinalbuffer;
			sinewindow.sinefinalbuffer = "";

		} else if (e.getSource() == mnutbl2swav) {

			new Tbl2SwavWindow(this);

			txtJBeditormain.setText("");
			txtJBeditormain.setText(Tbl2SwavWindow.tbl2swavfinalbuffer);
			System.out.print(Tbl2SwavWindow.tbl2swavfinalbuffer);

			Finalarray = "";
			Finalarray = Tbl2SwavWindow.tbl2swavfinalbuffer;
			Tbl2SwavWindow.tbl2swavfinalbuffer = "";

		}	else if (e.getSource() == mnuExit) {
			
			System.exit(0);

		} else if (e.getSource() == mnuAbout) {

			new JBeditorabout(this);

			// 기타1 : calcul
		} else if (e.getSource() == mnuEtc1) {
			try {
				Runtime runtime = Runtime.getRuntime();
				runtime.exec("calc.exe");
			} catch (Exception e2) {

			}

			// 기타2 : notepad
		} else if (e.getSource() == mnuEtc2) { // 기타2
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
