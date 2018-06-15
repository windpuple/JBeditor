package window;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class MultiClient extends JFrame implements ActionListener {
	static Socket clientsocket;

	private ObjectOutputStream oos;
	private JFrame jframe, login1; 
	private JTextField jtf, idc, pass;
	private JTextArea jta, jlo;
	private JLabel jlb1, jlb2, jID, jPW;
	private JPanel jp1, jp2, jp3, jp4;
	public String ip;
	private String id;
	private JButton jbtn, jbtn1, jexit;
	public boolean changepower = false;

	Image img = new ImageIcon("./Image/kittychat.jpg").getImage();

	public MultiClient() {

		jframe = new JFrame("Multi Chatting");
		login1 = new JFrame("Login");

		// JProgressBar progressBar = new JProgressBar();
		// progressBar.setStringPainted(true);
		// progressBar.setIndeterminate(true);
		// progressBar.setBounds(32, 303, 195, 14);

		jtf = new JTextField(20); // chatting 글이써지는 field
		// idc = new JPasswordField(20); // IP field passwordfield is showing *****
		idc = new JTextField(20);
		pass = new JTextField(20); // ID text field

		// jta = new JTextArea(43, 43) {
		// chating room image insert
		// public void paintComponent(Graphics g) {
		// g.drawImage(img, 0, 0, null);
		// super.paintComponent(g);
		// }

		// };

		//System.out.println("window IP :" + ip);

		jta = new JTextArea(43, 43); // chatting display textfield

		jlb1 = new JLabel();
		jlb2 = new JLabel();

		jID = new JLabel("IP"); // ID 라벨
		jPW = new JLabel("name"); // 패스워드 라벨
		jbtn = new JButton("Enter"); // 채팅전송 버튼
		jbtn1 = new JButton("Login");  // 로그인 버튼
		jexit = new JButton("exit");  // 종료 버튼
		jp1 = new JPanel();  // 바구니
		jp2 = new JPanel();
		jp3 = new JPanel();  // 로그인 화면
		jp4 = new JPanel();

		jID.setFont(new Font("HY엽서L", Font.PLAIN, (int) 12));
		jID.setHorizontalAlignment(jID.CENTER);
		jPW.setFont(new Font("HY엽서L", Font.PLAIN, (int) 12));
		jPW.setHorizontalAlignment(jPW.CENTER);

		jp1.setLayout(new BorderLayout());
		jp2.setLayout(new BorderLayout());
		jp3.setLayout(new GridLayout(3, 2, 10, 10));

		jp1.add(jbtn, BorderLayout.EAST);
		jp1.add(jtf, BorderLayout.CENTER);
		jp2.add(jlb1, BorderLayout.CENTER);
		jp2.add(jlb2, BorderLayout.EAST);

		jp3.add(jID);
		jp3.add(idc);
		jp3.add(jPW);
		jp3.add(pass);
		jp3.add(jbtn1);
		jp3.add(jexit);
		jframe.add(jp1, BorderLayout.SOUTH);
		jframe.add(jp2, BorderLayout.NORTH);
		login1.add(jp3, BorderLayout.EAST);
		login1.add(jp4, BorderLayout.EAST);

		JScrollPane jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		jframe.add(jsp, BorderLayout.CENTER);
		login1.add(jp3, BorderLayout.CENTER);

		jtf.addActionListener(this); // text field action listener
		jbtn.addActionListener(this); // button action listener
		jexit.addActionListener(this); // button action listener

		jframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					oos.writeObject(id + "#exit");
				} catch (IOException ee) {
					ee.printStackTrace();
				}

				dispose();

			}

			public void windowOpened(WindowEvent e) {
				jtf.requestFocus();
			}
		});

		jbtn1.addActionListener(this);

		jta.setEditable(false);

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int screenHeight = d.height;
		int screenWidth = d.width;

		jframe.pack();
		jframe.setLocation((screenWidth - jframe.getWidth()) / 2, (screenHeight - jframe.getHeight()) / 2);
		jframe.setResizable(true);
		jframe.setVisible(false);

		login1.pack();
		login1.setSize(300, 150);
		login1.setLocation((screenWidth - jframe.getWidth()) / 2, (screenHeight - jframe.getHeight()) / 2);
		login1.setResizable(false);
		login1.setVisible(true);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String msg = jtf.getText();

		String str = e.getActionCommand();

		if (str.equals("Login")) {
			jframe.setVisible(true);
			login1.setVisible(false);

			ip = idc.getText();
			id = pass.getText();

			//System.out.println("after login IP :" + ip);
			
			jlb1.setText(id+" Chatting Window");
			jlb2.setText("Connected Server IP : " + ip);

			try {
				clientsocket = new Socket(ip, 5000);

				if (clientsocket.isConnected() == false) {

					JOptionPane.showMessageDialog(null, "Connect not established", "Notice",
							JOptionPane.WARNING_MESSAGE);
					System.out.println("not connected...");

					login1.dispose();

				} else {

					JOptionPane.showMessageDialog(null, "Connect established", "Notice",
							JOptionPane.INFORMATION_MESSAGE);
					System.out.println("connect done.");

				}

				
				oos = new ObjectOutputStream(clientsocket.getOutputStream());
				oos.writeObject(id + "#" + id+" is joined "+ip+" Channel");
				oos.flush();
				
			    //if client side ois use only client thread, not socket connect side,
				//because outstream and inputstream crash and making forever hanging, freezing NetWork.
				//ois = new ObjectInputStream(clientsocket.getInputStream());
										
				MultiClientThread ct = new MultiClientThread(this);
				Thread t = new Thread(ct);
				t.start();				
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		if (str.equals("exit")) {
			
			try {
				clientsocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			login1.dispose();
			jframe.dispose();

		}

		if (obj == jtf && changepower == true) {
			changepower = false;
			if (msg == null || msg.length() == 0) {
				JOptionPane.showMessageDialog(jframe, "글을쓰세요", "경고", JOptionPane.WARNING_MESSAGE);
			} else {
				id = jtf.getText();
				jtf.setText("");
			}
		}

		if (obj == jtf) {

			if (msg.equals("")) {

				try {
					oos.writeObject(id + "#" + " ");
				} catch (IOException ee) {
					ee.printStackTrace();
				}

			} else {

				try {
					oos.writeObject(id + "#" + msg);
				} catch (IOException ee) {
					ee.printStackTrace();
				}
			}

			jtf.setText("");

			if (msg.equals("exit")) {
				
				jframe.dispose();
			}

		} else if (obj == jbtn) {

			if (msg.equals("")) {

				try {
					oos.writeObject(id + "#" + " ");
				} catch (IOException ee) {
					ee.printStackTrace();
				}

			} else {

				try {
					oos.writeObject(id + "#" + msg);
				} catch (IOException ee) {
					ee.printStackTrace();
				}
			}
			
			jtf.setText("");
			
			if (msg.equals("exit")) {
								
				try {
				
					clientsocket.close();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				jframe.dispose();
			}
		}
	}

	public void exit() throws IOException {
		
		clientsocket.close();
		login1.dispose();
		jframe.dispose();

	}

	public JTextArea getJta() {
		return jta;
	}

	public String getId() {
		return id;
	}

	public void SetName(String a) {
		id = a;
	}

	public void Clear() {
		jta.setText(""); // 초기화
		jtf.requestFocus();
	}

	public void My() {
		jta.setDisabledTextColor(Color.blue);
	}
}