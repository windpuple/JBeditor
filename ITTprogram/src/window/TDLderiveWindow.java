package window;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Toolkit;

public class TDLderiveWindow extends JDialog implements ActionListener {

	JButton btn = new JButton("확인");
	JButton btncancel = new JButton("\uCDE8\uC18C");
	JLabel notice = new JLabel("");

	String ATEbuffer;

	public static StringBuffer TDLderivefinalbuffer = new StringBuffer();


	public TDLderiveWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Rotationwindow.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
	
		ATEbuffer = "";

		TDLderivefinalbuffer.setLength(0);

		setTitle("TDL pin Derive");
		setModal(true);

		btn.setBounds(56, 76, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel clockwise = new JLabel("ATE machine");
		clockwise.setBounds(23, 35, 78, 15);
		getContentPane().add(clockwise);

		btncancel.setBounds(185, 75, 97, 23);
		getContentPane().add(btncancel);
		btncancel.addActionListener(this);

		JComboBox ATEmachineModel = new JComboBox();
		ATEmachineModel.setModel(new DefaultComboBoxModel(new String[] {"", "93000DD", "CredenceQuartet"}));
		ATEmachineModel.setBounds(155, 32, 152, 18);
		getContentPane().add(ATEmachineModel);

		ATEmachineModel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ATEbuffer = ATEmachineModel.getSelectedItem().toString();

			}

		});

		notice.setForeground(Color.RED);
		notice.setBounds(23, 60, 204, 15);
		getContentPane().add(notice);
		
		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 354, 146);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btn) {

			
			if (ATEbuffer == "93000DD") {

				new TDLderive();
				JBeditormain.Finalarray = "";
				JBeditormain.Finalarray = TDLderive.TDLderivebuffer.toString();

				TDLderivefinalbuffer.append(TDLderive.TDLderivebuffer.toString());

				dispose();

			} else if (ATEbuffer == "CredenceQuartet") {

				new TDLderiveQuartet();
				JBeditormain.Finalarray = "";
				JBeditormain.Finalarray = TDLderiveQuartet.TDLderivebuffer.toString();

				TDLderivefinalbuffer.append(TDLderiveQuartet.TDLderivebuffer.toString());

				dispose();

			} else {

				notice.setText("");
				notice.setText("ATE를 선택하지 않았습니다.");

			}
			

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}
}