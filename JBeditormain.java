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
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import java.awt.*;

public class JBeditormain extends JFrame implements ActionListener {
	JButton btnCopy = new JButton("Copy");
	JButton btnPaste = new JButton("Paste");
	JButton btnCut = new JButton("Cut");
	JButton btnDel = new JButton("Delete");
	
	public static JTextArea txtJBeditormain = new JTextArea("", 100, 400);
	
	String copyText;

	JMenuItem mnuNew, mnuSave, mnuOpen, mnuDBE, mnuMAP, mnuExit;
	JMenuItem mnuCopy, mnuPaste, mnuCut, mnuDel, mnuSbinedit, mnurotation;
	JMenuItem mnulinear, mnusine, mnutbl2swav;
	JMenuItem mnuAbout, mnuEtc1, mnuEtc2;

	// �˾� �޴�
	JPopupMenu popup;
	JMenuItem m_white, m_blue, m_yellow;

	public static String Finalarray = "";
	public static StringBuffer txtJBeditormainbuffer = new StringBuffer();
	
	// ��ü thread�� thread number Ȯ��.
	int index = 0; 

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
		txtJBeditormain.setFont(new Font("�ü�ü", Font.PLAIN, 13));
		txtJBeditormain.setWrapStyleWord(true);
		txtJBeditormain.setLineWrap(false);

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

		// �˾� �޴�(��Ŭ���� ����)
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
		popup.add(m_col); // �˾��� �ִ°�
		txtJBeditormain.add(popup); // �˾� �޴� �߰�

		txtJBeditormain.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				if (e.getModifiers() == MouseEvent.BUTTON3_MASK) { // ������ 1 ����� 2 �������� 3 BUTTON3_MASK - ������ ��ư

					popup.show(txtJBeditormain, e.getX(), e.getY());
				}
			}
		});
	}

	// this way is not acceptable to Jdialog, the reason is making crash on
	// setvisible function.
	/*
	 * public class Map2BackRunable implements Runnable{
	 * 
	 * private int index = 0;
	 * 
	 * @Override public synchronized void run() {
	 * 
	 * Random r = new Random(System.currentTimeMillis());
	 * 
	 * long s = r.nextInt(1000); // 3�ʳ��� ������.
	 * 
	 * new MAPwindow();
	 * 
	 * index++; System.out.println("current index value: " + index);
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * 
	 */

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
			txtJBeditormain.replaceRange("", start, end); // �������� ġȯ�ϴ� �� replaceRange("", ������ġ, ����ġ)

		} else if (e.getSource() == mnuSbinedit) {

			EventQueue.invokeLater(new Runnable() {
				public synchronized void run() {
					// this will run in swings thread
					
					index++; System.out.println("current index value: " + index);
												
					new Sbinwindow();

					txtJBeditormain.setText("");
					txtJBeditormain.setText(Sbinwindow.txtSbinbuffer);

					Finalarray = "";
					Finalarray = Sbinwindow.txtSbinbuffer;
					Sbinwindow.txtSbinbuffer = "";

				
				}
			});
				

		} else if (e.getSource() == mnurotation) {

			EventQueue.invokeLater(new Runnable() {
				public synchronized void run() {
					// this will run in swings thread
					
					index++; System.out.println("current index value: " + index);
												
					new Rotationwindow();

					txtJBeditormain.setText("");
					txtJBeditormain.setText(Rotationwindow.rotationfinalbuffer);

					Finalarray = "";
					Finalarray = Rotationwindow.rotationfinalbuffer;
					Rotationwindow.rotationfinalbuffer = "";				
				
				}
			});
				

		} else if (e.getSource() == mnuNew) {
			txtJBeditormain.setText("");
			setTitle("ITT");
			Finalarray = "";

		} else if (e.getSource() == mnuOpen) {
			
			EventQueue.invokeLater(new Runnable() {
				public synchronized void run() {
					// this will run in swings thread
					
					index++; System.out.println("current index value: " + index);
							
					new MenuOpen();				
				
				}
			});
			
			
		} else if (e.getSource() == mnuSave) {

			EventQueue.invokeLater(new Runnable() {
				public synchronized void run() {
					// this will run in swings thread
					
					index++; System.out.println("current index value: " + index);
							
					new MenuSave();				
				
				}
			});
						

		} else if (e.getSource() == mnuMAP) {

			// this way is not acceptable to Jdialog, the reason is making crash on
			// setvisible function.

			// System.out.println("Start main method.");

			// Runnable r = new Map2BackRunable(); // ���� ������ Runnable �������̽�
			// ArrayList<Thread> threadList = new ArrayList<Thread>(); // ��������� ���� ��ü

			// Runnable �������̽��� ����� ���ο� �����带 ����ϴ�.
			// Thread test = new Thread(r);

			// test.start(); // �� �޼ҵ带 �����ϸ� Thread ���� run()�� �����Ѵ�.
			// threadList.add(test); ������ �����带 ����Ʈ�� ����

			// for(Thread t : threadList){ try {

			// t.join();

			// } catch (InterruptedException e1) {

			// e1.printStackTrace(); } // �������� ó���� ���������� ��ٸ��ϴ�. }
			// }
			// System.out.println("End main method.");

			// Swing ������ �Ϲ����� thread�� �������� �ʴ´�. ������ eventqueue�� invoker�� ���ؼ� thread�� ������ dispatch thread�� �����ȴ�.
			EventQueue.invokeLater(new Runnable() {
				public synchronized void run() {
					// this will run in swings thread
					
					index++; System.out.println("current index value: " + index);
					
					new MAPwindow();
				
					
				
				}
			});

		} else if (e.getSource() == mnuDBE) {

			EventQueue.invokeLater(new Runnable() {
				public synchronized void run() {
					// this will run in swings thread
					
					index++; System.out.println("current index value: " + index);
					
					new DBEwindow();					
				
				}
			});
			
			

		} else if (e.getSource() == mnulinear) {

			EventQueue.invokeLater(new Runnable() {
				public synchronized void run() {
					// this will run in swings thread
					
					index++; System.out.println("current index value: " + index);
					
					new linearwindow();

					txtJBeditormain.setText("");
					txtJBeditormain.setText(linearwindow.linearfinalbuffer);

					Finalarray = "";
					Finalarray = linearwindow.linearfinalbuffer;
					linearwindow.linearfinalbuffer = "";
				
								
				}
			});
			
			

		} else if (e.getSource() == mnusine) {

			EventQueue.invokeLater(new Runnable() {
				public synchronized void run() {
					// this will run in swings thread
					
					index++; System.out.println("current index value: " + index);
					
					new sinewindow();

					txtJBeditormain.setText("");
					txtJBeditormain.setText(sinewindow.sinefinalbuffer);

					Finalarray = "";
					Finalarray = sinewindow.sinefinalbuffer;
					sinewindow.sinefinalbuffer = "";

				
				}
			});
			
		
		} else if (e.getSource() == mnutbl2swav) {

			EventQueue.invokeLater(new Runnable() {
				public synchronized void run() {
					// this will run in swings thread
					
					index++; System.out.println("current index value: " + index);
					
					new Tbl2SwavWindow();
						
				}
			});
			
			

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

			// ��Ŭ���ÿ� ��Ÿ���� �˾��޴��� ���� ����
		} else if (e.getSource() == m_white) { // �˾��޴�
			txtJBeditormain.setBackground(Color.WHITE);
		} else if (e.getSource() == m_blue) { // �˾��޴�
			txtJBeditormain.setBackground(Color.BLUE);
		} else if (e.getSource() == m_yellow) { // �˾��޴�
			txtJBeditormain.setBackground(Color.YELLOW);
		}
		txtJBeditormain.requestFocus();
	}

	public static void main(final String[] args) throws InterruptedException {
		new JBeditormain();
	}

}
