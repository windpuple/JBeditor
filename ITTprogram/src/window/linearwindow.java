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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSlider;
import java.awt.Font;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class linearwindow extends JDialog implements ActionListener {

	JButton btn = new JButton("확인");
	JButton btncancel = new JButton("\uCDE8\uC18C");
	JLabel notice = new JLabel("");

	public static String linearfinalbuffer;
	public static String samplecounttext;

	private JTextField pintext;
	private JTextField sampletext;
	public static JSlider Linearslider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
	private JLabel linearsliderLabel = new JLabel("Scale Adjust");

	public linearwindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(linearwindow.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		

		linearfinalbuffer = "";
		samplecounttext = "";

		setTitle("linear vector generator");
		setModal(true);

		btn.setBounds(23, 116, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel clockwise = new JLabel("Pin count");
		clockwise.setBounds(23, 35, 78, 15);
		getContentPane().add(clockwise);

		JLabel counterclockwise = new JLabel("Sample count");
		counterclockwise.setBounds(23, 73, 120, 15);
		getContentPane().add(counterclockwise);

		btncancel.setBounds(137, 115, 97, 23);
		getContentPane().add(btncancel);
		btncancel.addActionListener(this);

		pintext = new JTextField();
		pintext.setBounds(131, 32, 116, 21);
		getContentPane().add(pintext);
		pintext.setColumns(10);

		sampletext = new JTextField();
		sampletext.setBounds(133, 70, 116, 21);
		getContentPane().add(sampletext);
		sampletext.setColumns(10);

		notice.setForeground(Color.RED);
		notice.setBounds(23, 116, 224, 15);
		getContentPane().add(notice);

		Linearslider.setPaintLabels(true);
		Linearslider.setPaintTicks(true);
		Linearslider.setPaintTrack(true);
		Linearslider.setMajorTickSpacing(50);
		Linearslider.setMinorTickSpacing(10);

		Linearslider.setBounds(34, 177, 200, 38);
		getContentPane().add(Linearslider);

		linearsliderLabel.setForeground(SystemColor.textHighlight);
		linearsliderLabel.setFont(new Font("굴림", Font.BOLD, 12));
		linearsliderLabel.setBounds(85, 152, 97, 15);

		getContentPane().add(linearsliderLabel);

		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 275, 264);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btn) {

			if (sampletext.getText().isEmpty() || pintext.getText().isEmpty()) {
				notice.setText("");
				notice.setText("pin,sample 값이 없습니다.");

			} else {

				String pincounttext;
				String linearbridgebuffer;

				pincounttext = pintext.getText();
				samplecounttext = sampletext.getText();

				String[] linearbuffer = new String[Integer.parseInt(samplecounttext)];

				linearbridgebuffer = "";

				for (int i = 0; i < linearbuffer.length; i++) {

					linearbuffer[i] = "";
				}

				for (int i = 0; i < linearbuffer.length; i++) {

					for (int j = 0; j < Integer.numberOfLeadingZeros(i); j++) {

						linearbuffer[i] += "0"; // 0을 빈자리 개수만큼 만들어 전진 배치

					}

					if (i != 0) {

						linearbuffer[i] = linearbuffer[i] + Integer.toBinaryString(i) + "\n";

					} else {

						linearbuffer[i] = linearbuffer[i] + "\n";

					}

					String sub = linearbuffer[i].substring(32 - Integer.parseInt(pincounttext));
					linearbuffer[i] = sub;

				}

				for (int i = 0; i < linearbuffer.length; i++) {

					linearbridgebuffer += linearbuffer[i];

				}

				linearfinalbuffer = linearbridgebuffer;

				new lineardraw(this);

				dispose();

			}

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}
}