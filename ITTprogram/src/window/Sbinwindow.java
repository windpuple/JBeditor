/* 
 *  
 * 2018.03.26 JB.Jeon ITT(katherine) project start. 
 * 
 *  
 */

package window;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Toolkit;

public class Sbinwindow extends JDialog implements ActionListener {
	private JTextField bh_textfield;
	private JTextField af_textfiled;

	JButton btn = new JButton("Confirm");
	JButton btncancel = new JButton("Cancel");

	JLabel notice = new JLabel("");

	public static String txtSbinbuffer = "";;

	public Sbinwindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sbinwindow.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));


		setTitle("Sbin change input");
		setModal(true);

		btn.setBounds(23, 163, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel bh_label = new JLabel("before Sbin");
		bh_label.setBounds(23, 35, 78, 15);
		getContentPane().add(bh_label);

		bh_textfield = new JTextField();
		bh_textfield.setBounds(116, 32, 58, 21);
		getContentPane().add(bh_textfield);
		bh_textfield.setColumns(3);

		JLabel ah_label = new JLabel("after Sbin");
		ah_label.setBounds(23, 73, 78, 15);
		getContentPane().add(ah_label);

		af_textfiled = new JTextField();
		af_textfiled.setBounds(116, 70, 58, 21);
		getContentPane().add(af_textfiled);
		af_textfiled.setColumns(3);

		btncancel.setBounds(133, 162, 97, 23);
		getContentPane().add(btncancel);

		notice.setForeground(Color.RED);
		notice.setBounds(23, 120, 207, 15);
		getContentPane().add(notice);
		btncancel.addActionListener(this);

		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 275, 233);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btn) {

			if (bh_textfield.getText().isEmpty() || af_textfiled.getText().isEmpty()) {
				notice.setText("");
				notice.setText("pin,sample 값이 없습니다.");

			} else {

				String chword;
				String target;
				String sub;

				chword = bh_textfield.getText();
				target = af_textfiled.getText();

				int j = 0;
				int flag = 0;

				char[] read = new char[JBeditormain.Finalarray.length()];

				for (j = 0; j < JBeditormain.Finalarray.length(); j++) {

					read[j] = JBeditormain.Finalarray.charAt(j);

					if (read[j] == '|' || read[j] == '+') {

						flag = 1;

					} else if (read[j] == '-') {

						flag = 0;

					}

					if (!(read[j] == '+' || read[j] == '|')) {

						if (chword.length() == 3 && target.length() == 3) {

							if (flag == 1 && read[j - 2] == chword.charAt(0) && read[j - 1] == chword.charAt(1)
									&& read[j] == chword.charAt(2)) {

								read[j - 2] = target.charAt(0);
								read[j - 1] = target.charAt(1);
								read[j] = target.charAt(2);

							}
						} else if (chword.length() == 3 && target.length() == 2) {

							if (flag == 1 && read[j - 2] == chword.charAt(0) && read[j - 1] == chword.charAt(1)
									&& read[j] == chword.charAt(2)) {

								read[j - 2] = ' ';
								read[j - 1] = target.charAt(0);
								read[j] = target.charAt(1);

							}

						} else if (chword.length() == 3 && target.length() == 1) {

							if (flag == 1 && read[j - 2] == chword.charAt(0) && read[j - 1] == chword.charAt(1)
									&& read[j] == chword.charAt(2)) {
								read[j - 2] = ' ';
								read[j - 1] = ' ';
								read[j] = target.charAt(0);

							}

						} else if (chword.length() == 2 && target.length() == 3) {

							if (flag == 1 && read[j - 1] == chword.charAt(0) && read[j] == chword.charAt(1)) {

								read[j - 2] = target.charAt(0);
								read[j - 1] = target.charAt(1);
								read[j] = target.charAt(2);
							}
						} else if (chword.length() == 2 && target.length() == 2) {

							if (flag == 1 && read[j - 1] == chword.charAt(0) && read[j] == chword.charAt(1)) {

								read[j - 1] = target.charAt(0);
								read[j] = target.charAt(1);

							}

						} else if (chword.length() == 2 && target.length() == 1) {

							if (flag == 1 && read[j - 1] == chword.charAt(0) && read[j] == chword.charAt(1)) {

								read[j - 1] = ' ';
								read[j] = target.charAt(0);

							}

						} else if (chword.length() == 1 && target.length() == 3) {
							if (flag == 1 && read[j] == chword.charAt(0)) {

								read[j - 2] = target.charAt(0);
								read[j - 1] = target.charAt(1);
								read[j] = target.charAt(2);

							}
						} else if (chword.length() == 1 && target.length() == 2) {

							if (flag == 1 && read[j] == chword.charAt(0)) {

								read[j - 1] = target.charAt(0);
								read[j] = target.charAt(1);

							}

						} else if (chword.length() == 1 && target.length() == 1) {

							if (flag == 1 && read[j] == chword.charAt(0)) {

								read[j] = target.charAt(0);

							}

						}

					} else if (flag == 1 && read[j] == '\n') {

						read[j] = '\n';

					}

					if (read[j] == '\n') {

						flag = 0;

					}

				}

				for (int i = 0; i < read.length; i++) {
					txtSbinbuffer += Character.toString(read[i]);
				}

				if (chword.length() == 3 && target.length() == 1) {

					txtSbinbuffer = txtSbinbuffer.replace("null", "");
					txtSbinbuffer = txtSbinbuffer.replace(chword + "(", "  " + target + "(");

				} else if (chword.length() == 3 && target.length() == 2) {

					txtSbinbuffer = txtSbinbuffer.replace("null", "");
					txtSbinbuffer = txtSbinbuffer.replace(chword + "(", " " + target + "(");

				} else if (chword.length() == 2 && target.length() == 1) {

					txtSbinbuffer = txtSbinbuffer.replace("null", "");
					txtSbinbuffer = txtSbinbuffer.replace(chword + "(", " " + target + "(");

				} else if (chword.length() == 1 && target.length() == 3) {

					txtSbinbuffer = txtSbinbuffer.replace("null", "");

					int p1 = txtSbinbuffer.indexOf(chword + "(");
					sub = txtSbinbuffer.substring(p1 - 3, p1 + 2);
					txtSbinbuffer = txtSbinbuffer.replace(sub, target + "(");

				} else if (chword.length() == 2 && target.length() == 3) {

					txtSbinbuffer = txtSbinbuffer.replace("null", "");

					int p1 = txtSbinbuffer.indexOf(chword + "(");
					sub = txtSbinbuffer.substring(p1 - 2, p1 + 2);
					txtSbinbuffer = txtSbinbuffer.replace(sub, target + "(");

				} else if (chword.length() == 1 && target.length() == 2) {

					txtSbinbuffer = txtSbinbuffer.replace("null", "");

					int p1 = txtSbinbuffer.indexOf(chword + "(");
					sub = txtSbinbuffer.substring(p1 - 1, p1 + 2);
					txtSbinbuffer = txtSbinbuffer.replace(sub, target + "(");

				} else if (chword.length() == target.length()) {

					txtSbinbuffer = txtSbinbuffer.replace("null", "");
					txtSbinbuffer = txtSbinbuffer.replace(chword + "(", target + "(");

				}

				dispose();

			}

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}
}