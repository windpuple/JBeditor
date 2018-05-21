package window;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSlider;
import java.awt.Font;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class AcceptRemoteServerIP extends JDialog implements ActionListener {

	JButton btn = new JButton("»Æ¿Œ");
	JButton btncancel = new JButton("\uCDE8\uC18C");

	private JTextField AddressTest;

	public AcceptRemoteServerIP() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(linearwindow.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));

		setTitle("RemoteServerConnect");
		setModal(true);

		btn.setBounds(69, 48, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel ServerAddress = new JLabel("Server Address");
		ServerAddress.setBounds(13, 17, 109, 15);
		getContentPane().add(ServerAddress);

		btncancel.setBounds(179, 47, 97, 23);
		getContentPane().add(btncancel);
		btncancel.addActionListener(this);

		AddressTest = new JTextField();
		AddressTest.setBounds(122, 14, 208, 21);
		getContentPane().add(AddressTest);
		AddressTest.setColumns(10);

		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 357, 114);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btn) {

			if (AddressTest.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Empty adress.", "Notice", JOptionPane.WARNING_MESSAGE);

			} else {

				  String Address = AddressTest.getText();
				  String args[] = {"-a:"+Address};
				  new jrdesktop.Rdesktopmain(args);
				  
				  dispose();

			}

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}
}